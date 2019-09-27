package com.basic.javaframe;

import java.awt.Frame;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.utils.SpringContextUtils;
import com.basic.javaframe.service.Frame_ModuleRightService;
import com.basic.javaframe.service.Frame_ModuleService;
import com.basic.javaframe.service.impl.Frame_ModuleServiceImpl;


/**
  * 定时任务
  * @ClassName: ScheduledTask 
  * @Description: 定时任务
  * @author keeny
  * @date 2018年12月17日 下午1:30:40 
  *
 */
@Component
public class ScheduledTask {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	
	
	/**
	 *    财务统计
	 * @Title: financeStatic 
	 * @Description: 查询所有在用状态的企业 循环统计上一个月份生效合同数 计算金额
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
//	@Scheduled(fixedRate = 10000)
//	public void financeStatic() {
//		
//		
//	}
}
