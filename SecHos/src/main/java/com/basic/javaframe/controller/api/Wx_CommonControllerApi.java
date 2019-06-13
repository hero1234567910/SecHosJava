package com.basic.javaframe.controller.api;

import java.awt.Frame;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.basic.javaframe.service.*;
import org.aspectj.weaver.ast.Var;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.basic.javaframe.common.WebSocket.WebSocketServer;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.enumresource.DelFlagEnum;
import com.basic.javaframe.common.enumresource.OrderStatuEnum;
import com.basic.javaframe.common.enumresource.ReceiveOrderEnum;
import com.basic.javaframe.common.exception.MyException;
import com.basic.javaframe.common.utils.AmountUtils;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.XMLUtil;
import com.basic.javaframe.controller.BaseController;
import com.basic.javaframe.entity.Frame_Config;
import com.basic.javaframe.entity.HosOrder;
import com.basic.javaframe.entity.HosOrderitem;
import com.basic.javaframe.entity.HosUser;
import com.basic.javaframe.service.api.Wx_CommonServiceIApi;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin
@RequestMapping(value="wx/common")
public class Wx_CommonControllerApi extends BaseController{
	
	@Value(value = "${wx.api.appid}")
	public String appid;
	
	@Autowired
	Wx_CommonServiceIApi wx_CommonServiceApi;
	
	@Autowired
	HosOrderService hosOrderService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	HosUserService hosUserService;
	
	@Autowired
	HosOrderitemService itemService;

	@Autowired
	HosGoodsService hosGoodsService;
	
	@Autowired
	WebSocketServer socketServer;
	
	@Autowired
	Frame_ConfigService configService;
	
	/**
	 * 统一下单接口
	 * <p>Title: placeOrder</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@Transactional
	@PassToken
	@ResponseBody
	@RequestMapping(value="/placeOrder",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R placeOrder(@RequestBody Map<String, String> params){
		//判断是否接单
		Frame_Config config =  configService.getConfigByName("是否接单");
		if(ReceiveOrderEnum.AlreadyClose.getCode() == Integer.parseInt(config.getConfigValue())){
			return R.error("对不起,食堂已停止接单");
		}
		if (params.get("orderMoney") == "" || params.get("orderMoney") == null) {
			return R.error("orderMoney"+"参数不能为空");
		}
		
		//校验下前端传的价格和后端计算比较。。
		System.out.println(params.get("orderItem"));
		JSONArray array2 = JSONArray.parseArray(params.get("orderItem"));
		System.out.println(array2);
		//数据库计算出的总价
		int totalPrice2=0;
		for (int i = 0; i < array2.size(); i++) {
			JSONObject obj = array2.getJSONObject(i);
			HosOrderitem orderitem = new HosOrderitem();
			//从页面上获取的商品行号
			String guid = obj.getString("goodsGuid");
			//从页面上获取的商品单价
			BigDecimal gPrice = obj.getBigDecimal("goodsPrice");
			int gPrice2=Integer.valueOf(AmountUtils.changeY2F(String.valueOf(gPrice)));
			////从页面上获取的商品数量
			int gCount = obj.getInteger("goodsCount");
			//从后端数据库获取的单价
			BigDecimal price = hosGoodsService.getGoodsPriceByGuid(guid);
			int price2 = Integer.valueOf(AmountUtils.changeY2F(String.valueOf(price)));
			System.out.println(price2+"/"+gPrice2);
			//比较
			if(price2!=gPrice2){
				return R.error("商品单价异常");
			}else if(gCount==0){
				return R.error("商品数量异常");
			}
			//后台通过数据库计算得出总价
			int tempPrice = price2*gCount;
			System.out.println(tempPrice);
			totalPrice2=totalPrice2+tempPrice;
			System.out.println(totalPrice2);
		}
		System.out.println(totalPrice2);
		//获取该用户的openid
		String userGuid = params.get("orderUserGuid");
		HosUser user = hosUserService.getUserByGuid(userGuid);
		if (user == null) {
			return R.error("未找到对应用户");
		}
		if (user.getOpenid() == null || user.getOpenid() == "") {
			return R.error("获取用户信息异常");
		}
		
		//单位分
		int money = Integer.valueOf(AmountUtils.changeY2F(params.get("orderMoney"))); 

		if(totalPrice2!=money){
			return R.error("订单总价异常");
		}

		//商户订单号
        String out_trade_no = System.currentTimeMillis()+wx_CommonServiceApi.getRandomStringByLength(7);
		
		String xml = null;
		try {
			xml = wx_CommonServiceApi.placeOrder(money,out_trade_no,user.getOpenid());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (xml == "") {
			//如果返回结果为空，说明调用接口异常
			return R.error("调用下单接口失败");
		}
		
		//解析xml
		Map<String, String> map = new HashMap<>();
		map = XMLUtil.xml2Map(xml);
		//对返回结果进行解析
		String return_code = map.get("return_code");
		if (return_code == null || return_code == "") {
			return R.error("return_code为空");
		}
		if ("FAIL".equals(return_code)) {
			return R.error(map.get("return_msg"));
		}
		if ("FAIL".equals(map.get("result_code"))) {
			return R.error("错误代码："+map.get("err_code")+" 错误原因："+map.get("err_code_des"));
		}
		
		//数据库创建订单
		HosOrder order = new HosOrder();
		String orderGuid = UUID.randomUUID().toString();
		order.setCreateTime(DateUtil.changeDate(new Date()));
		order.setRowGuid(orderGuid);
		order.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
		order.setMerchantNumber(out_trade_no);
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderStatus(OrderStatuEnum.READYPAY.getCode());
		order.setOrderUserGuid(params.get("orderUserGuid"));
		order.setConsigneeName(params.get("consigneeName"));
		order.setConsigneeMobile(params.get("consigneeMobile"));
		order.setConsigneeInpatient(params.get("consigneeInpatient"));
		order.setConsigneeStorey(params.get("consigneeStorey"));
		order.setConsigneeBedNumber(params.get("consigneeBedNumber"));
		order.setRemark(params.get("remark"));
		order.setReserveTime(DateUtil.changeStrToDate2(params.get("reserveTime")));
		order.setReserveTimeSuffix(params.get("reserveTimeSuffix"));
		//转decimal
		BigDecimal number = new BigDecimal(params.get("orderMoney"));
		order.setOrderMoney(number);
		hosOrderService.save(order);
		
		JSONArray array = JSONArray.parseArray(params.get("orderItem"));
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			HosOrderitem orderitem = new HosOrderitem();
			orderitem.setCount(obj.getInteger("goodsCount"));
			orderitem.setCreateTime(DateUtil.changeDate(new Date()));
			orderitem.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
			orderitem.setGoodsGuid(obj.getString("goodsGuid"));
			orderitem.setItemPrice(obj.getBigDecimal("goodsPrice"));
			orderitem.setOrderGuid(orderGuid);
			orderitem.setRowGuid(UUID.randomUUID().toString());
			orderitem.setTotalMoney(obj.getBigDecimal("totalPrice"));
			itemService.save(orderitem);
		}
		
		//准备数据返回
		SortedMap<Object, Object> obj =
                new TreeMap<Object, Object>();
		obj.put("appId",appid);
		obj.put("timeStamp",System.currentTimeMillis()/1000);
		obj.put("nonceStr",wx_CommonServiceApi.getRandomStringByLength(32));
		obj.put("package","prepay_id="+map.get("prepay_id"));
		obj.put("signType", "MD5");
		obj.put("paySign",wx_CommonServiceApi.createSign(obj));
		return R.ok("下单成功").put("data", obj);
	}
	
	/**
	 * 重复统一下单接口
	 * <p>Title: placeOrder</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@Transactional
	@PassToken
	@ResponseBody
	@RequestMapping(value="/placeOrderAgain",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R placeOrderAgain(@RequestBody Map<String, String> params){
		if (params.get("orderMoney") == "" || params.get("orderMoney") == null) {
			return R.error("orderMoney"+"参数不能为空");
		}
		if (params.get("orderUserGuid") == "" || params.get("orderUserGuid") == null) {
			return R.error("orderUserGuid"+"参数不能为空");
		}
		if (params.get("merchantNumber") == "" || params.get("merchantNumber") == null) {
			return R.error("merchantNumber"+"参数不能为空");
		}

		//获取该用户的openid
		String userGuid = params.get("orderUserGuid");
		HosUser user = hosUserService.getUserByGuid(userGuid);
		if (user == null) {
			return R.error("未找到对应用户");
		}
		if (user.getOpenid() == null || user.getOpenid() == "") {
			return R.error("获取用户信息异常");
		}
		
		//单位分
		int money = Integer.valueOf(AmountUtils.changeY2F(params.get("orderMoney"))); 

		//商户订单号
        String out_trade_no = params.get("merchantNumber");
		
		String xml = null;
		try {
			xml = wx_CommonServiceApi.placeOrder(money,out_trade_no,user.getOpenid());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (xml == "") {
			//如果返回结果为空，说明调用接口异常
			return R.error("调用下单接口失败");
		}
		
		//解析xml
		Map<String, String> map = new HashMap<>();
		map = XMLUtil.xml2Map(xml);
		//对返回结果进行解析
		String return_code = map.get("return_code");
		if (return_code == null || return_code == "") {
			return R.error("return_code为空");
		}
		if ("FAIL".equals(return_code)) {
			return R.error(map.get("return_msg"));
		}
		if ("FAIL".equals(map.get("result_code"))) {
			return R.error("错误代码："+map.get("err_code")+" 错误原因："+map.get("err_code_des"));
		}
		
		//准备数据返回
		SortedMap<Object, Object> obj =
                new TreeMap<Object, Object>();
		obj.put("appId",appid);
		obj.put("timeStamp",System.currentTimeMillis()/1000);
		obj.put("nonceStr",wx_CommonServiceApi.getRandomStringByLength(32));
		obj.put("package","prepay_id="+map.get("prepay_id"));
		obj.put("signType", "MD5");
		obj.put("paySign",wx_CommonServiceApi.createSign(obj));
		return R.ok("下单成功").put("data", obj);
	}
	
	/**
	 * 获取网页授权token，openid
	 * <p>Title: code2Token</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @param code
	 * @return
	 */
	@PassToken
	@RequestMapping(value="/code2Token",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public R code2Token(@RequestBody String code){
		String result = wx_CommonServiceApi.code2Token(code);
		
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject.containsKey("errcode")) {
			String errcode = jsonObject.getString("errcode");
			return R.error("获取网页授权用户信息异常,errcode为"+errcode);
		}
		//获取openid,微信昵称，头像
		String openid = jsonObject.getString("openid");
		String nickname = jsonObject.getString("nickname");
		String headimgurl = jsonObject.getString("headimgurl");
		
		System.out.println(openid+"   "+openid+"   "+headimgurl);
		
		//根据openid查询是否有该用户,没有则生成一条新用户，有则返回该用户信息
		HosUser user = hosUserService.getUserByOpenid(openid);
		if (user == null) {
			HosUser hosUser = new HosUser();
			hosUser.setDelFlag(DelFlagEnum.NDELFLAG.getCode());
			hosUser.setCreateTime(DateUtil.changeDate(new Date()));
			String uuid = java.util.UUID.randomUUID().toString();
			hosUser.setRowGuid(uuid);
			hosUser.setOpenid(openid);
			hosUser.setHosUserName(nickname);
			//设定微信头像
			hosUser.setHosHeadImgUrl(headimgurl);
			hosUserService.save(hosUser);
			return R.ok().put("data", hosUser);
		}
		return R.ok().put("data", user);	
	}
	
	/**
	 * 回调接口
	 * <p>Title: wx_callback</p>  
	 * <p>Description: </p>
	 * @author hero
	 */
	@ResponseBody
	@RequestMapping(value="/wx_callback",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public String wx_callback(HttpServletRequest request){
		
		//xml转map
		Map<String, String> hashmap = new HashMap<>();
		try {
			hashmap = parseXml(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("微信回调接口参数》》》"+JSONObject.toJSONString(hashmap));
		
		if ("FAIL".equals(hashmap.get("return_code"))) {
			throw new MyException("微信回调异常，异常信息》》"+hashmap.get("return_msg"));
		}
		
		SortedMap<Object, Object> sm =
                new TreeMap<Object, Object>(hashmap);
		
		System.out.println("sm>>>>"+JSONObject.toJSONString(sm));
		//验签
		String sign = wx_CommonServiceApi.createSign(sm);
		if (!hashmap.get("sign").equals(sign)) {
			throw new MyException("验签失败，sign比对不同");
		}
		//校验返回的订单金额是否与商户侧的订单金额一致
		int orderMoney = Integer.valueOf(hashmap.get("total_fee"));//单位分
		//根据商户订单号查询该订单金额
		HosOrder order = hosOrderService.queryByOrderNumber(hashmap.get("out_trade_no"));
		if (order == null) {
			throw new MyException("未查询到对应订单信息");
		}
		if (order.getOrderStatus() != OrderStatuEnum.READYPAY.getCode()) {
			sm.clear();
			sm.put("return_code", "SUCCESS");
			sm.put("return_msg", "this infomation is already deal");
			
			String xmlWx = XMLUtil.mapToXml(sm, true);
			logger.info("返回给微信的xml为"+xmlWx);
		}
		//int单位分转decimal并和回调金额比较
		int money = Integer.valueOf(AmountUtils.changeY2F(order.getOrderMoney().toString()));
		if (orderMoney != money) {
			logger.info("回调金额为"+orderMoney+"  数据库已存入订单金额为"+money+"(实际已元为单位存储)");
			//报错，提示金额不一致，做下一步处理...
			sm.clear();
			sm.put("return_code", "FAIL");
			sm.put("return_msg", "金额不一致");
			String xmlwx = XMLUtil.mapToXml(sm, true);
			logger.info("返回给微信的xml为"+xmlwx);
			return xmlwx;
		}
		
		//若前面都通过，更新订单状态并返回正确信息给微信
		HosOrder hosOrder = new HosOrder();
		hosOrder.setRowGuid(order.getRowGuid());
		hosOrder.setOrderStatus(OrderStatuEnum.RECEIVEDORDER.getCode());
		hosOrderService.update(hosOrder);
		
		sm.clear();
		sm.put("return_code", "SUCCESS");
		sm.put("return_msg", "OK");
		
		String xmlWx = XMLUtil.mapToXml(sm, true);
		logger.info("返回给微信的xml为"+xmlWx);
		
		//发送推送消息
		try {
			socketServer.sendInfo("您有新的订单"+JSONObject.toJSONString(order,SerializerFeature.WriteMapNullValue), "20");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xmlWx;
	}
	
	/**
	 * 退款
	 * <p>Title: refund</p>  
	 * <p>Description: </p>
	 * @author hero  
	 * @return
	 */
	@ApiOperation(value="退款")
	@ResponseBody
	@RequestMapping(value="/refund",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R refund(@RequestParam String out_trade_no){
		//根据商户订单号查询是否有该订单存在
		HosOrder order = hosOrderService.getOrderByMerchantNumber(out_trade_no);
		if (order == null) {
			logger.info("未查询到该退款订单");
			return R.error("退款异常，请联系技术人员");
		}
		//检查订单状态若已退款或已完成或已取消则不允许退款
		if (order.getOrderStatus() == OrderStatuEnum.ALREADYREFUND.getCode() 
				|| order.getOrderStatus() == OrderStatuEnum.CANCALE.getCode() 
				|| order.getOrderStatus() == OrderStatuEnum.COMPLETED.getCode() 
				|| order.getOrderStatus() == OrderStatuEnum.READYPAY.getCode()) {
			
			return R.error("该订单已经无法退款");
		}
		
		String result = wx_CommonServiceApi.refund(out_trade_no,order);
		//解析结果
		if (result == "") {
			//如果返回结果为空，说明调用接口异常
			return R.error("调用下单接口失败");
		}
		
		//解析xml
		Map<String, String> map = new HashMap<>();
		map = XMLUtil.xml2Map(result);
		//对返回结果进行解析
		String return_code = map.get("return_code");
		if (return_code == null || return_code == "") {
			return R.error("return_code为空");
		}
		if ("FAIL".equals(return_code)) {
			return R.error(map.get("return_msg"));
		}
		if ("FAIL".equals(map.get("result_code"))) {
			return R.error("错误代码："+map.get("err_code")+" 错误原因："+map.get("err_code_des"));
		}
		
		//更新订单状态
		HosOrder hosOrder = new HosOrder();
		hosOrder.setRowGuid(order.getRowGuid());
		hosOrder.setOrderStatus(OrderStatuEnum.ALREADYREFUND.getCode());
		hosOrderService.update(hosOrder);
		return R.ok("退款成功");
	}
	
		/**
	     * 解析微信发来的请求（XML）
	     * 
	     * @param request
	     * @return
	     * @throws Exception
     	*/
		@SuppressWarnings("unchecked")
		public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
	        // 将解析结果存储在HashMap中
	        Map<String, String> map = new HashMap<String, String>();
	
	        // 从request中取得输入流
	        InputStream inputStream = request.getInputStream();
	        // 读取输入流
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream);
	        // 得到xml根元素
	        Element root = document.getRootElement();
	        // 得到根元素的所有子节点
	        List<Element> elementList = root.elements();
	
	        // 遍历所有子节点
	        for (Element e : elementList)
	            map.put(e.getName(), e.getText());
	
	        // 释放资源
	        inputStream.close();
	        inputStream = null;
	        return map;
		}
	
}
