package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.dao.InformationCategoryDao;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.InformationCategory;
import com.basic.javaframe.service.InformationCategoryService;




@Service("informationCategoryService")
@Transactional
public class InformationCategoryServiceImpl implements InformationCategoryService {
	@Autowired
	private InformationCategoryDao informationCategoryDao;

	@Override
	public List<InformationCategory> getList(Map<String, Object> map){
		return informationCategoryDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return informationCategoryDao.getCount(map);
	}

	@Override
	public void save(InformationCategory informationCategory){
		informationCategoryDao.save(informationCategory);
	}

	@Override
	public void update(InformationCategory informationCategory){
		informationCategoryDao.update(informationCategory);
	}

	@Override
	public void deleteBatch(String[] rowIds){
		informationCategoryDao.deleteBatch(rowIds);
	}

	@Override
	public JSONArray findTrees() {
		//查询所有顶级
		List<InformationCategory> TopTrees = informationCategoryDao.findTopTrees();
		//递归获取子
		return getChildDepts(TopTrees);
	}
	
	public JSONArray getChildDepts(List<InformationCategory> TopTrees){
		JSONArray array = new JSONArray();
		for (InformationCategory informationCategory : TopTrees) {
			JSONObject json = new JSONObject();
			json.put("name", informationCategory.getCategoryName());
			json.put("categoryCode", informationCategory.getCategoryCode());
			json.put("rowGuid", informationCategory.getRowGuid());
			//获取子部门
			List<InformationCategory> childDept = informationCategoryDao.getByPCategoryCode(informationCategory.getCategoryCode());
			json.put("children", getChildDepts(childDept));
			array.add(json);
		}
		return array;
	}

	@Override
	public List<InformationCategory> getAllList() {
		// TODO Auto-generated method stub
		return informationCategoryDao.getAllList();
	}

	@Override
	public List<InformationCategory> getCategoryByGuid(String categoryGuid) {
		// TODO Auto-generated method stub
		return informationCategoryDao.getCategoryByGuid(categoryGuid);
	}
	
}
