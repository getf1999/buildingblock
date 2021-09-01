package com.getf.buildingblock.infrastructure.fastdev.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.login.user.LoginUserManager;
import com.getf.buildingblock.infrastructure.fastdev.config.FastDevTableConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

public class OperationInfoInterceptor extends BaseInterceptor{

    @Resource
    LoginUserManager loginUserManager;

    @Value("${getf.buildingblock.infrastucture.fastdev.interceptor.operationInfoInterceptor.addByFieldName:addBy}")
    private String addByFieldName;
    @Value("${getf.buildingblock.infrastucture.fastdev.interceptor.operationInfoInterceptor.editByFieldName:editBy}")
    private String editByFieldName;
    @Value("${getf.buildingblock.infrastucture.fastdev.interceptor.operationInfoInterceptor.addByNameFieldName:addByName}")
    private String addByNameFieldName;
    @Value("${getf.buildingblock.infrastucture.fastdev.interceptor.operationInfoInterceptor.editByNameFieldName:editByName}")
    private String editByNameFieldName;
    @Value("${getf.buildingblock.infrastucture.fastdev.interceptor.operationInfoInterceptor.addTimeFieldName:addTime}")
    private String addTimeFieldName;
    @Value("${getf.buildingblock.infrastucture.fastdev.interceptor.operationInfoInterceptor.editTimeFieldName:editTime}")
    private String editTimeFieldName;

    @Override
    public void beforeSave(JSONObject jsonObject, int operationType, FastDevTableConfig.TableConfig tableConfig) {
        if (operationType == 1) {
            jsonObject.put(addByFieldName, loginUserManager.getUser().getUserId());
            jsonObject.put(addByNameFieldName, loginUserManager.getUser().getUserName());
            jsonObject.put(addTimeFieldName, new Date());
        }
        jsonObject.put(editByFieldName, loginUserManager.getUser().getUserId());
        jsonObject.put(editByNameFieldName, loginUserManager.getUser().getUserName());
        jsonObject.put(editTimeFieldName, new Date());
    }

    @Override
    public Integer getOrder() {
        return 1000;
    }
}
