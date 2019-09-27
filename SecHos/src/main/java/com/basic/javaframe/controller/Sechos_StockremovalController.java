package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.entity.Sechos_Outbound;
import com.basic.javaframe.entity.Sechos_Stockremoval;
import com.basic.javaframe.entity.Sechos_Storageamount;
import com.basic.javaframe.service.Sechos_OutboundService;
import com.basic.javaframe.service.Sechos_StorageamountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.service.Sechos_StockremovalService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-09-25 14:43:58
 */
@Api(value = "材料已出库管理")
@RestController
@CrossOrigin
@RequestMapping("sys/sechosstockremoval")
public class Sechos_StockremovalController {
	@Autowired
	private Sechos_StockremovalService sechosStockremovalService;

	@Autowired
	private Sechos_OutboundService sechosOutboundService;

	@Autowired
	private Sechos_StorageamountService sechosStorageamountService;


	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="已出库列表数据")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Sechos_Stockremoval> sechosStockremovalList = sechosStockremovalService.getList(query);
		int total = sechosStockremovalService.getCount(query);
		PageUtils pageUtil = new PageUtils(sechosStockremovalList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增材料出库")
    @ResponseBody
	@Transactional
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody Map<String,Object> params){
    	Sechos_Outbound sechosOutbound = new Sechos_Outbound();
		Sechos_Stockremoval sechosStockremoval = new Sechos_Stockremoval();
		String outboundGuid = params.get("outboundGuid").toString();
		String personGuid = params.get("personGuid").toString();
		Date createTime = DateUtil.changeDate(new Date());
		sechosStockremoval.setPersonGuid(personGuid);
		sechosStockremoval.setOutboundGuid(outboundGuid);
		sechosStockremoval.setDrugOutDate(createTime);

		sechosStockremovalService.insertRemove(outboundGuid);
		sechosStockremovalService.insertOutDate(sechosStockremoval);

		List<Sechos_Stockremoval> sechosStockremovals = sechosStockremovalService.getListByOutboundGuid(outboundGuid);
		for(int i =0 ;i<sechosStockremovals.size();i++){
			String drugGuid = sechosStockremovals.get(i).getDrugGuid();
			String drugCode = sechosStockremovals.get(i).getDrugCode();
			Integer storageSum = sechosStorageamountService.getSumByGuid(drugGuid);
			Integer Num = sechosStockremovals.get(i).getDrugAmount();
			if((storageSum-Num)<0){
				sechosStockremovalService.deleteInsert(outboundGuid);
				return R.error("药品代码为 "+drugCode+" 的库存不足,无法完成出库!请等待配货。");
			}else{
				sechosStorageamountService.subtractNum(Num,drugCode);
			}
		}
		sechosOutbound.setRowGuid(outboundGuid);
		sechosOutbound.setOutboundStatus(4);
		sechosOutbound.setOutboundDate(createTime);
		sechosOutboundService.update(sechosOutbound);
        return R.ok();  
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody Sechos_Stockremoval sechosStockremoval){
		sechosStockremovalService.update(sechosStockremoval);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		sechosStockremovalService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
