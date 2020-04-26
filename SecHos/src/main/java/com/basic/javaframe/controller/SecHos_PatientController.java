package com.basic.javaframe.controller;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.*;
import com.basic.javaframe.entity.SecHos_Patient;
import com.basic.javaframe.service.SecHos_PatientService;
import com.basic.javaframe.service.Sechos_PopuPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * @author wzl
 * @date 2019-06-14 13:57:13
 */
@Api(value = "患者管理")
@RestController
@CrossOrigin
@RequestMapping("sys/patient")
public class SecHos_PatientController {
    @Autowired
    private SecHos_PatientService secHosPatientService;

    @Autowired
    private Sechos_PopuPersonService sechosPopuPersonService;

    @Value(value = "${nl.appsecret}")
    private String appsecret;

    @Value(value = "${nl.appkey}")
    private String appkey;

    @Value(value = "${wx.api.appid}")
    private String appid;

    @Value(value = "${nl.url}")
    private String nlUrl;

    public static String gb2312ToUtf8(String str) {

        String urlEncode = "";

        try {

            urlEncode = URLEncoder.encode(str, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        return urlEncode;
    }

    /**
     * 列表数据
     */
    @PassToken
    @ApiOperation(value = "获取患者列表")
    @ResponseBody
    @RequestMapping(value = "/listData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public LayuiUtil listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SecHos_Patient> secHosPatientList = secHosPatientService.getList(query);
        int total = secHosPatientService.getCount(query);
        PageUtils pageUtil = new PageUtils(secHosPatientList, total, query.getLimit(), query.getPage());
        return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增
     **/
    @ApiOperation(value = "添加患者")
    @ResponseBody
    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R add(@RequestBody SecHos_Patient secHosPatient) {
        //如果排序号为空，则自动转为0
        if (secHosPatient.getSortSq() == null) {
            secHosPatient.setSortSq(0);
        }
        //生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
        secHosPatient.setRowGuid(uuid);
        Date createTime = DateUtil.changeDate(new Date());
        secHosPatient.setCreateTime(createTime);
        secHosPatientService.save(secHosPatient);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改患者")
    @PassToken
    @ResponseBody
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public R update(@RequestBody SecHos_Patient secHosPatient) {
        secHosPatientService.update(secHosPatient);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除患者")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public R delete(@RequestBody String[] rowGuids) {
        secHosPatientService.deleteBatch(rowGuids);
        return R.ok();
    }

    /**
     * 查找患者
     */
    @ApiOperation(value = "查找患者")
    @ResponseBody
    @RequestMapping(value = "/getPatientByGuid/{rowGuid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R getPatientByGuid(@PathVariable("rowGuid") String rowGuid) {
        SecHos_Patient secHosPatient = secHosPatientService.getPatientByGuid(rowGuid);
        return R.ok().put("data", secHosPatient);
    }

    /**
     * 对接新咨询
     * <p>Title: toAsk</p>
     * <p>Description: </p>
     *
     * @return
     * @throws UnsupportedEncodingException
     * @author hero
     */
    @ApiOperation(value = "对接新咨询")
    @ResponseBody
    @RequestMapping(value = "/toAsk", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @PassToken
    public R toAsk(@RequestBody Map<String, String> map) throws UnsupportedEncodingException {
        String patienGuid = map.get("patienGuid");
        if (StringUtil.isBlank(patienGuid)) {
            return R.error("patienGuid不能为空");
        }
        //根据patienGuid查询用户信息
        SecHos_Patient secHosPatient = secHosPatientService.getPatientByGuid(patienGuid);
        if (StringUtil.isBlank(secHosPatient.getPatientIdcard()) || secHosPatient.getPatientIdcard() == "") {
            return R.error().put("data", "zwbd");
        }

        if (StringUtil.isBlank(secHosPatient.getPatientMobile()) || secHosPatient.getPatientMobile() == "") {
            return R.error().put("data", "sjhgx");
        }


        //拼接
        SortedMap<Object, Object> obj =
                new TreeMap<Object, Object>();
        obj.put("appkey", appkey);
        obj.put("idcard", secHosPatient.getPatientIdcard());
        obj.put("mobile", secHosPatient.getPatientMobile());
        obj.put("patientName", secHosPatient.getPatientName());
        obj.put("tid", secHosPatient.getRowGuid());
//		obj.put("appid", appid);
//		obj.put("api", "wx");
//		obj.put("", value)
        String signature = createSign(obj);

        String patientName = secHosPatient.getPatientName();
//		patientName = new String(patientName.getBytes("ISO-8859-1"),"UTF-8");
        System.out.println(getEncoding(patientName));


        patientName = gb2312ToUtf8(patientName);
        String url = nlUrl + "page.html?appkey=" + appkey + "&idcard=" + secHosPatient.getPatientIdcard() + "&mobile=" + secHosPatient.getPatientMobile() + "&patientName=" + patientName + "&tid=" + secHosPatient.getRowGuid() + "&api=wx&signature=" + signature + "&module=newRS" + "&appid=wx870abf50c6bc6da3";
        System.out.println("纳里健康的第一版url为》》》" + url);

        String enUrl = URLEncoder.encode(url, "GBK");
        System.out.println("编码过后的url》》》" + enUrl);
        String wxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx870abf50c6bc6da3&redirect_uri=" + enUrl + "&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
        System.out.println("最终解析的url》》》" + wxUrl);

        Map<String, String> p = new HashMap<String, String>();
        p.put("wxUrl", wxUrl);
        p.put("url", url);
        return R.ok().put("data", p);
    }

    public String createSign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        String s = sb.toString();
        s = s.substring(0, s.length() - 1);
        s = s + appsecret;
        System.out.println(s);
        String sign = null;
        try {
            sign = MD5Util.md5Password(s).toLowerCase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(sign + ">>>>" + MD5Util.md5Password(s).toLowerCase() + ">>>>>>>" + MD5Util.toMD5(s));
        return sign;
    }

    public String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
                String s = encode;
                return s; //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return ""; //如果都不是，说明输入的内容不属于常见的编码格式。
    }


}
