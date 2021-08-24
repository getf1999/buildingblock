package com.getf.buildingblock.infrastucture.fastdev.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import com.getf.buildingblock.infrastucture.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder.ISqlBuilder;
import com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder.SqlHelper;
import lombok.var;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BaseDAO {
    @Resource
    protected ISqlBuilder sqlBuilder;

    @Resource
    protected SqlHelper sqlHelper;


    public JSONArray queryBySrcSql(String sqlSrc, FilterInfo filterInfo, List<String> ignoreFields) throws SQLException {
        var buildResult= sqlBuilder.buildQueryByFilterInfo(sqlSrc,filterInfo);
        var r= sqlHelper.execJSONArray(buildResult,ignoreFields);
        return r;
    }

    public JSONArray query(FastDevTableConfig.TableConfig tableConfig, FastDevTableConfig.TableConfig.CRUDConfig crudConfig, FilterInfo filterInfo) throws SQLException {
        var buildResult= sqlBuilder.buildQuery(tableConfig.getTableName(),filterInfo);
        var r= sqlHelper.execJSONArray(buildResult,crudConfig.getIgnoreFields());
        return r;
    }

    /**
     * 插入
     * @param tableConfig
     * @param jsonObject
     * @throws SQLException
     */
    public void insert(FastDevTableConfig.TableConfig tableConfig, FastDevTableConfig.TableConfig.CRUDConfig crudConfig,JSONObject jsonObject) throws SQLException {
        var sqlInfoParamMap= sqlBuilder.buildInsert(tableConfig.getTableName(),jsonObject,crudConfig.getIgnoreFields());
        sqlHelper.execNonQuery(sqlInfoParamMap);
    }

    public JSONObject getById(FastDevTableConfig.TableConfig tableConfig, FastDevTableConfig.TableConfig.CRUDConfig crudConfig,Long id) throws SQLException {
        var buildResult= sqlBuilder.buildGetById(tableConfig.getTableName(),tableConfig.getPrimaryKeyName(),id);
        var r= sqlHelper.execJSONArray(buildResult,crudConfig.getIgnoreFields());
        if(r.size()==0) return null;
        return r.getJSONObject(0);
    }

    public void update(FastDevTableConfig.TableConfig tableConfig, FastDevTableConfig.TableConfig.CRUDConfig crudConfig,JSONObject jsonObject) throws SQLException {
        var sqlInfoParamMap= sqlBuilder.buildUpdate(tableConfig.getTableName(),tableConfig.getPrimaryKeyName(),jsonObject,crudConfig.getIgnoreFields());
        sqlHelper.execNonQuery(sqlInfoParamMap);
    }
}
