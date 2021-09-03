package com.getf.buildingblock.infrastructure.fastdev.config.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastructure.model.result.ListPageResult;
import com.getf.buildingblock.infrastructure.model.result.Result;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;

public interface Interceptor {
    Integer getOrder();

    /**
     * 查询前处理
     * @param filterInfo
     * @param tableConfig
     * @param type 0 普通list  1 tree
     */
    void beforeQuery(FilterInfo filterInfo, FastDevTableConfig.TableConfig tableConfig,int type);

    /**
     * 查询后处理
     * @param data
     * @param tableConfig
     * @param type 0 普通list  1 tree
     */
    void afterQuery(JSONArray data,ListPageResult listPageResult, FastDevTableConfig.TableConfig tableConfig,int type);

    /**
     * 数据校验
     * @param tableConfig
     * @param jsonObject
     * @param operationType 1 add 2 edit
     * @return
     */
    Result validation(JSONObject jsonObject, int operationType,FastDevTableConfig.TableConfig tableConfig);

    /**
     * 保存前处理
     * @param tableConfig
     * @param jsonObject
     * @param operationType 1 add 2 edit
     */
    void beforeSave(JSONObject jsonObject, int operationType,FastDevTableConfig.TableConfig tableConfig);

    /**
     * 根据id获取之后
     * @param jsonObject
     * @param tableConfig
     */
    void afterGet(JSONObject jsonObject,FastDevTableConfig.TableConfig tableConfig);

    /**
     * 删除校验
     * @param id
     * @param tableConfig
     */
    Result validationDelete(Long id,FastDevTableConfig.TableConfig tableConfig);
}
