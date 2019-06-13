package com.basic.javaframe.service.impl;

import com.basic.javaframe.dao.Frame_ConfigDao;
import com.basic.javaframe.entity.Frame_Config;
import com.basic.javaframe.service.Frame_ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: FrameConfigService</p>
 * <p>Description: 系统参数IMPL服务层</p>
 *
 * @author wzl
 */
@Service("configService")
public class Frame_ConfigServiceImpl implements Frame_ConfigService {

    @Autowired
    private Frame_ConfigDao configDao;

    /**
     * 获取系统参数类别信息
     * @param params 系统参数信息
     * @return
     */
    @Override
    public List<Frame_Config> getConfig(Map<String, Object> params) {
        return configDao.getConfig(params);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return configDao.getCount(params);
    }

    /**
     * 新增系统参数
     * @param config 系统参数信息
     */
    @Override
    public void insertConfig(Frame_Config config) {
        configDao.insertConfig(config);
    }

    /**
     * 更新系统参数
     * @param config 系统参数信息
     */
    @Override
    public void updateConfig(Frame_Config config) {
        configDao.updateConfig(config);
    }

    /**
     * 删除系统参数
     * @param ids 需要删除的数据ID
     */
    @Override
    public void deleteConfig(Integer[] ids) {
        configDao.deleteConfig(ids);
    }

    /**
     * 获取系统默认密码
     * @param
     * *configValue 系统密码信息
     */
    @Override
    public String getDefaultPassWord() {
        String configValue =  configDao.getDefaultPassWord();
        return configValue;
    }

    @Override
    public <T> int checkConfigName(T t) {
        return configDao.checkConfigName(t);
    }

    @Override
    public void deleteConfigByCategoryGuid(String categoryGuid) {
        configDao.deleteConfigByCategoryGuid(categoryGuid);
    }

	@Override
	public Frame_Config getConfigByName(String name) {
		// TODO Auto-generated method stub
		return configDao.getConfigByName(name);
	}
}
