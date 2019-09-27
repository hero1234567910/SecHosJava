package com.basic.javaframe.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.dao.Frame_DeptDao;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.service.Frame_DeptService;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 IMPL服务层
 *
 * @author wzl
 * @date 2019-03-06
 */
@Service("deptService")
public class Frame_DeptServiceImpl implements Frame_DeptService {

    @Autowired
    private Frame_DeptDao frame_deptDao;


    /**
     * 查询部门管理信息
     *
     * @param rowId 部门管理ID
     * @return 部门管理信息
     */
    @Override
    public Frame_Dept selectFrameDeptById(Integer rowId) {
        return frame_deptDao.selectFrameDeptById(rowId);
    }

    /**
     * 查询部门名字
     *
     * @param name 部门管理信息
     * @return 部门管理集合
     */
    @Override
    public Frame_Dept selectFrameDeptByName(String name) {
        return frame_deptDao.selectFrameDeptByName(name);
    }

    /**
     * 查询部门管理列表
     *
     * @param params 部门管理信息
     * @return 部门管理集合
     */
    @Override
    public List<Frame_Dept> selectFrameDeptList(Map<String, Object> params) {
        return frame_deptDao.selectFrameDeptList(params);
    }


    /**
     * 增加部门
     *
     * @param frameDept 部门管理信息
     * @return 部门管理集合
     */
    @Override
    public void insertFrameDept(Frame_Dept frameDept) {
         frame_deptDao.insertFrameDept(frameDept);
    }

    /**
     * 更新部门
     *
     * @param frameDept 部门管理信息
     * @return 部门管理集合
     */
    @Override
    public void updateFrameDept(Frame_Dept frameDept) {
        frame_deptDao.updateFrameDept(frameDept);
    }


    /**
     * 删除部门
     *
     * @param ids 部门id
     * @return 部门管理集合
     */
    @Override
    public void deleteFrameDeptById(Integer[] ids) {
    	//根据ids获取是否有子集
    	List<Integer> list = new ArrayList<>(Arrays.asList(ids));
    	for (Integer it : ids) {
    		ids = deleteFrameDeptById2(it, ids);
		}
        frame_deptDao.deleteFrameDeptById(ids);
    }
    
    //递归获取
    public Integer[] deleteFrameDeptById2(Integer rowId,Integer[] ids){
    	List<Integer> list = new ArrayList<>(Arrays.asList(ids));
    	Frame_Dept dept = frame_deptDao.selectFrameDeptById(rowId);
    	List<Frame_Dept> deptList = frame_deptDao.getByPdeptCode(dept.getDeptCode());
    	if (deptList != null &&deptList.size() != 0) {
			for (int i = 0; i < deptList.size(); i++) {
				list.add(deptList.get(i).getRowId());
				ids=new Integer[list.size()];
		    	list.toArray(ids);//3,5   //3,5,1   
		    	ids = deleteFrameDeptById2(deptList.get(i).getRowId(),ids);
			}
		}
    	return ids;
    }

    /**
     * 批量删除部门
     *
     * @param ids 部门id
     * @return 部门管理集合
     */
    @Override
    public void deleteFrameDeptByIds(Integer[] ids) {
        frame_deptDao.deleteFrameDeptByIds(ids);
    }

	@Override
	public int getCount(Map<String, Object> params) {
		return frame_deptDao.getCount(params);
	}
	
	@Override
	public JSONArray findTopDepts() {
		
		//查询所有顶级部门
		List<Frame_Dept> deptTopTrees = frame_deptDao.findTopDepts();
		//递归获取子部门
		return getChildDepts(deptTopTrees);
	}

    @Override
    public String getByDeptCode(String deptCode) {
        return frame_deptDao.getByDeptCode(deptCode);
    }

    public JSONArray getChildDepts(List<Frame_Dept> deptTopTrees){
		JSONArray array = new JSONArray();
		for (Frame_Dept frame_Dept : deptTopTrees) {
			JSONObject json = new JSONObject();
			json.put("deptName", frame_Dept.getDeptName());
            json.put("name", frame_Dept.getDeptName());
			json.put("deptCode", frame_Dept.getDeptCode());
			json.put("rowGuid", frame_Dept.getRowGuid());
			//获取子部门
			List<Frame_Dept> childDept = frame_deptDao.getByPdeptCode(frame_Dept.getDeptCode());
			json.put("children", getChildDepts(childDept));
			array.add(json);
		}
		return array;
	}

	@Override
	public <T> int checkDept(T t) {
		// TODO Auto-generated method stub
		return frame_deptDao.checkDept(t);
	}

    @Override
    public void getDeptNameByGuid(String rowGuid) {
        frame_deptDao.getDeptNameByGuid(rowGuid);
    }

    @Override
    public String getDeptGuidByName(String deptName) {
        return frame_deptDao.getDeptGuidByName(deptName);
    }
}
