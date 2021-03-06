package com.getf.buildingblock.infrastructure.fastdev.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastructure.fastdev.dao.DefaultDAO;
import com.getf.buildingblock.infrastructure.fastdev.manager.InterceptorManager;
import com.getf.buildingblock.infrastructure.model.filter.data.SearchInfo;
import com.getf.buildingblock.infrastructure.model.result.Result;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastructure.util.CollectionUtil;
import com.getf.buildingblock.infrastructure.util.IdUtil;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import lombok.var;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultService {
    @Resource
    private FastDevTableConfig config;

    @Resource
    private DefaultDAO dao;

    @Resource
    InterceptorManager interceptorManager;

    public Result query(String routeName, FilterInfo filterInfo) throws SQLException {
        var config=getTableConfig(routeName,5);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        if(config.getQueryConfig().isDisabled()){
            return new Result(-100,"operation disabled");
        }
        var interceptors=interceptorManager.getList(routeName);
        for(var elem:interceptors){
            elem.beforeQuery(filterInfo,config,0);
        }
        var data=dao.query(config,config.getQueryConfig(),filterInfo);
        var r=filterInfo.toListPageResult(data);

        for(var elem:interceptors){
            elem.afterQuery(data,r,config,0);
            if(!r.getSuccess()){
                return r;
            }
        }
        return r;
    }

    public Result<Long> add(String routeName, JSONObject jsonObject) throws SQLException {
        var config=getTableConfig(routeName,1);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        if(config.getAddConfig().isDisabled()){
            return new Result(-100,"operation disabled");
        }

        var interceptors=interceptorManager.getList(routeName);
        for(var elem:interceptors){
            var reuslt= elem.validation(jsonObject,1,config);
            if(!reuslt.getSuccess()) return reuslt;
        }

        var id=jsonObject.getLong(config.getPrimaryKeyName());
        if(id==null){
            id= IdUtil.newId();
            jsonObject.put(config.getPrimaryKeyName(),id);
        }
        for(var elem:interceptors){
            elem.beforeSave(jsonObject,1,config);
        }
        dao.insert(config,config.getAddConfig(),jsonObject);
        return new Result(id);
    }

    public Result getById(String routeName, Long id) throws SQLException {
        var config=getTableConfig(routeName,4);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        if(config.getGetConfig().isDisabled()){
            return new Result(-100,"operation disabled");
        }

        var r=dao.getById(config,config.getGetConfig(),id);
        var interceptors=interceptorManager.getList(routeName);
        for(var elem:interceptors){
            elem.afterGet(r,config);
        }
        return new Result(r);
    }

    public Result edit(String routeName, JSONObject jsonObject) throws SQLException {
        var config=getTableConfig(routeName,2);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        if(config.getEditConfig().isDisabled()){
            return new Result(-100,"operation disabled");
        }

        var interceptors=interceptorManager.getList(routeName);
        for(var elem:interceptors){
            var reuslt= elem.validation(jsonObject,2,config);
            if(!reuslt.getSuccess()) return reuslt;
        }

        for(var elem:interceptors){
            elem.beforeSave(jsonObject,2,config);
        }
        dao.update(config,config.getEditConfig(),jsonObject);
        return new Result();
    }

    public Result delete(String routeName,Long id) throws SQLException {
        var config=getTableConfig(routeName,3);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        if(config.getGetConfig().isDisabled()){
            return new Result(-100,"operation disabled");
        }
        var interceptors=interceptorManager.getList(routeName);
        for(var elem:interceptors){
            Result result= elem.validationDelete(id,config);
            if(!result.getSuccess()) return result;
        }
        dao.delete(config,config.getEditConfig(),id);
        return new Result();
    }



    public Result getTree(String routeName, Long ignoreId) throws SQLException {
        var config=getTableConfig(routeName,5);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        var treeConfig=config.getQueryTreeConfig();
        if(treeConfig.isDisabled()){
            return new Result(-100,"operation disabled");
        }
        FilterInfo filterInfo;
        if(ignoreId!=null){
            filterInfo=FilterInfo.createFilterInfo(config.getPrimaryKeyName(),ignoreId.toString(), SearchInfo.NEQ);
        }else{
            filterInfo=new FilterInfo();
        }
        var interceptors=interceptorManager.getList(routeName);
        for(var elem:interceptors){
            elem.beforeQuery(filterInfo,config,1);
        }
        var data=dao.search(config,config.getQueryTreeConfig(),filterInfo);

        var tree=toTree(
            data,0L,
            config.getPrimaryKeyName(),
            treeConfig.getParentFieldName()==null?"parentId":treeConfig.getParentFieldName(),
            treeConfig.getChildrenFieldName()==null?"children":treeConfig.getChildrenFieldName()
        );

        var r=new Result(tree);

        for(var elem:interceptors){
            elem.afterQuery(tree,null,config,0);
            if(!r.getSuccess()){
                return r;
            }
        }
        return r;
    }

    private JSONArray toTree(JSONArray srcData, Long parentId,String primaryKeyFieldName,String parentFieldName,String childrenFieldName){
        JSONArray r=new JSONArray();
        var childrenNode=srcData.stream().map(m->(JSONObject)m).filter(m-> Objects.equals(m.getLong(parentFieldName),parentId)).collect(Collectors.toList());
        for(var elem: childrenNode){
            elem.put(childrenFieldName,toTree(srcData,elem.getLong(primaryKeyFieldName),primaryKeyFieldName,parentFieldName,childrenFieldName));
            r.add(elem);
        }
        return r;
    }
    /**
     * ????????????
     * @param routeName
     * @param type 1 add 2 edit 3 delete 4 get 5 query 6 tree
     * @return
     */
    private FastDevTableConfig.TableConfig getTableConfig(String routeName,int type){
        FastDevTableConfig.TableConfig r=null;

        if(config.getTableConfigs()!=null){
            r= CollectionUtil.firstOrDefault(config.getTableConfigs(),m->routeName.equals(m.getRouteName()));
        }
        if(r==null){
            r=new FastDevTableConfig.TableConfig();
            r.setTableName(routeName);
        }
        if(StringUtil.isNullOrEmpty(r.getPrimaryKeyName())){
            r.setPrimaryKeyName("id");
        }
        switch (type){
            case 1:
                r.setAddConfig(initCRUDConfig(r.getAddConfig()));
            case 2:
                r.setEditConfig(initCRUDConfig(r.getEditConfig()));
            case 3:
                r.setDeleteConfig(initCRUDConfig(r.getDeleteConfig()));
            case 4:
                r.setGetConfig(initCRUDConfig(r.getGetConfig()));
            case 5:
                r.setQueryConfig(r.getQueryConfig()==null?new FastDevTableConfig.TableConfig.QueryConfig():r.getQueryConfig());
            case 6:
                r.setQueryTreeConfig(r.getQueryTreeConfig()==null?new FastDevTableConfig.TableConfig.QueryTreeConfig():r.getQueryTreeConfig());
        }
        return r;
    }

    /**
     * ???????????????
     * @param config
     * @return
     */
    private FastDevTableConfig.TableConfig.CRUDConfig initCRUDConfig(FastDevTableConfig.TableConfig.CRUDConfig config){
        if(config==null){
            return new FastDevTableConfig.TableConfig.CRUDConfig();
        }

        return config;
    }

    private Boolean isInBlackList(String tableName){
        if(config.getUseBlackList()==null||config.getUseBlackList()){
            if(config.getList()!=null){
                for(var elem:config.getList()){
                    if(StringUtil.humpToLine(tableName).equals(elem)){
                        return true;
                    }
                }
            }
            return false;
        }
        if(config.getList()!=null){
            for(var elem:config.getList()){
                if(StringUtil.humpToLine(tableName).equals(elem)){
                    return false;
                }
            }
        }
        if(config.getTableConfigs()!=null){
            for(var elem:config.getTableConfigs()){
                if(StringUtil.humpToLine(tableName).equals(elem.getTableName())){
                    return false;
                }
            }
        }
        return true;
    }
}
