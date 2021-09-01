package com.getf.buildingblock.base.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastructure.fastdev.config.interceptor.BaseInterceptor;
import com.getf.buildingblock.infrastructure.util.IdUtil;
import lombok.var;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class UserInterceptor extends BaseInterceptor {
    private static final String KEY_PASSWORD="password";
    private static final String KEY_ID="id";

    @Override
    public void beforeSave(JSONObject jsonObject, int operationType, FastDevTableConfig.TableConfig tableConfig) {
        if(operationType==1){
            var id= IdUtil.newId();
            jsonObject.put(KEY_PASSWORD, DigestUtils.sha256Hex(id+jsonObject.getString(KEY_PASSWORD)));
            jsonObject.put(KEY_ID,id);
        }else{
            if(jsonObject.getString(KEY_PASSWORD)!=null){
                var id=jsonObject.getLong(KEY_ID);
                jsonObject.put(KEY_PASSWORD, DigestUtils.sha256Hex(id+jsonObject.getString(KEY_PASSWORD)));
            }
        }
    }
}
