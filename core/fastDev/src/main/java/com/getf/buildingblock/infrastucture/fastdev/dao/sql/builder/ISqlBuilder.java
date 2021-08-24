package com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.data.SqlInfoParamMap;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;

import java.util.List;

public interface ISqlBuilder {
    SqlInfoParamMap buildQueryByFilterInfo(String sql, FilterInfo filterInfo);

    SqlInfoParamMap buildQuery(String tableName, FilterInfo filterInfo);

    SqlInfoParamMap buildInsert(String tableName, JSONObject jsonObject, List<String> ignoreFields);

    SqlInfoParamMap buildGetById(String tableName,String primaryKeyName,Long value);

    SqlInfoParamMap buildUpdate(String tableName,String primaryKeyName,JSONObject jsonObject, List<String> ignoreFields);

    /**
     * 获取出去歧义的SQL 如 传入 demo sqlserver返回 [demo] mysql 返回 `demo`
     * @param str
     * @return
     */
    String getDisambiguationSql(String str);
}
