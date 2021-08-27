package com.getf.buildingblock.infrastructure.fastdev.config.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.model.result.ListPageResult;
import com.getf.buildingblock.infrastructure.model.result.Result;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastructure.fastdev.config.FastDevTableConfig;

public abstract class BaseInterceptor implements Interceptor{
    @Override
    public Integer getOrder() {
        return null;
    }

    @Override
    public void beforeQuery(FilterInfo filterInfo, FastDevTableConfig.TableConfig tableConfig) {
    }

    @Override
    public void afterQuery(JSONArray data, ListPageResult listPageResult, FastDevTableConfig.TableConfig tableConfig) {

    }

    @Override
    public Result validation(JSONObject jsonObject, int operationType, FastDevTableConfig.TableConfig tableConfig) {
        return new Result();
    }

    @Override
    public void beforeSave(JSONObject jsonObject, int operationType, FastDevTableConfig.TableConfig tableConfig) {

    }

    @Override
    public void afterGet(JSONObject jsonObject, FastDevTableConfig.TableConfig tableConfig) {

    }

    @Override
    public Result validationDelete(Long id, FastDevTableConfig.TableConfig tableConfig) {
        return new Result();
    }
}
