package com.basic.javaframe.service.impl;

import java.util.List;

import com.basic.javaframe.entity.Frame_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.dao.Frame_ModuleRightDao;
import com.basic.javaframe.entity.Frame_ModuleRight;
import com.basic.javaframe.service.Frame_ModuleRightService;
import com.basic.javaframe.service.Frame_ModuleService;

import springfox.documentation.spring.web.json.Json;

@Service("moduleRightService")
public class Frame_ModuleRightServiceImpl implements Frame_ModuleRightService {
	
	@Autowired
	private Frame_ModuleRightDao moduleRightDao;
	
	@Autowired
	private Frame_ModuleService moduleService;
	
	@Override
	public void insertFrameModuleRight(Frame_ModuleRight moduleRight) {
		moduleRightDao.insertFrameModuleRight(moduleRight);
	}

	@Override
	public void insertModuleRightBatch(List<Frame_ModuleRight> frameModuleRights) {
		moduleRightDao.insertModuleRightBatch(frameModuleRights);
	}

	@Override
	public void deleteModuleRightBatch(String allowTo) {
		moduleRightDao.deleteModuleRightBatch(allowTo);
	}

	@Override
	public void deleteModuleRightByGuid(String[] rowGuid) {
		moduleRightDao.deleteModuleRoleByGuid(rowGuid);
	}

	@Override
	public void updateFrameModuleRight(Frame_ModuleRight moduleRight) {
		moduleRightDao.updateFrameModuleRight(moduleRight);
	}

	@Override
	public void deleteFrameModuleRightById(Integer[] ids) {
		moduleRightDao.deleteFrameModuleRightById(ids);
	}
	
	//查询到该角色所拥有的的模块后将其匹配至树中
	@Override
	public JSONArray selectModuleByRoleGuid(String roleGuid) {
		//查询
		List<Frame_ModuleRight> moduleRightList = moduleRightDao.selectModuleByRoleGuid(roleGuid);
		
		//查询模块树
		JSONArray moduleArray = moduleService.findModules();
		if (moduleRightList.size() == 0) {
			return 	moduleArray;	
		}
		//匹配
		JSONArray newTree = eachJsonTree(moduleArray,moduleRightList);
		
		return newTree;
	}
	
	//递归遍历json树
	public JSONArray eachJsonTree(JSONArray array,List<Frame_ModuleRight> moduleRightList){
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				//遍历角色模块
				for (int j = 0; j < moduleRightList.size(); j++) {
					if (obj.getString("value").equals(moduleRightList.get(j).getModuleGuid())) {
						obj.put("checked", true);
					}
				}
				if (obj.getJSONArray("data") != null && obj.getJSONArray("data").size() > 0) {
					//继续遍历下一层
					eachJsonTree(obj.getJSONArray("data"), moduleRightList);
				}
			}
		}
		return array;
	}
	
	@Override
	public JSONArray selectModuleByRole(String roleGuid) {
		//查询
		List<Frame_ModuleRight> moduleRightList = moduleRightDao.selectModuleByRoleGuid(roleGuid);
		//查询模块树
		JSONArray moduleArray = moduleService.findModules();
		if (moduleRightList.size() == 0) {
			return null;
		}
		//匹配
		JSONArray js = new JSONArray();
		JSONArray newTree = eachJson(moduleArray,moduleRightList,js);
		return newTree;
	}
	
	//递归遍历json树
	public JSONArray eachJson(JSONArray array,List<Frame_ModuleRight> moduleRightList,JSONArray js){
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				//遍历角色模块
				JSONObject jso = new JSONObject();
				for (int j = 0; j < moduleRightList.size(); j++) {
					if (obj.getString("value").equals(moduleRightList.get(j).getModuleGuid())) {
						jso = jso.parseObject(obj.toJSONString());
						jso.remove("data");
						js.add(jso);
					}
				}
				if (obj != null) {
					if (obj.getJSONArray("data") != null && obj.getJSONArray("data").size() > 0) {
						//继续遍历下一层
						JSONArray js2 = new JSONArray();
						jso.put("data", js2);
						eachJson(obj.getJSONArray("data"), moduleRightList,js2);
					}
				}
			}
			
		}
		return js;
	}
	
}
