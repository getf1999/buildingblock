package com.getf.buildingblock.infrastucture.fastdev.service;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.model.Result;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastructure.util.CollectionUtil;
import com.getf.buildingblock.infrastructure.util.IdUtil;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import com.getf.buildingblock.infrastucture.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastucture.fastdev.dao.DefaultDAO;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service
public class DefaultService {
    @Resource
    private FastDevTableConfig config;

    @Resource
    private DefaultDAO dao;

    public <T> Result<T> query(String routeName, FilterInfo filterInfo, Class<T> tClass) throws SQLException {
        var config=getTableConfig(routeName);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }

        return new Result(dao.query(config,initCRUDConfig(config.getQueryConfig()),filterInfo));
    }

    public Result<Long> add(String routeName, JSONObject jsonObject) throws SQLException {
        var config=getTableConfig(routeName);
        if(isInBlackList(config.getTableName())){
            return new Result(-100,"is in black list");
        }
        if(StringUtil.isNullOrEmpty(config.getPrimaryKeyName())){
            config.setPrimaryKeyName("id");
        }

        var crudConfig=initCRUDConfig(config.getAddConfig());
        var id=jsonObject.getLong(config.getPrimaryKeyName());
        if(id==null){
            id= IdUtil.newId();
            jsonObject.put(config.getPrimaryKeyName(),id);
        }
        dao.insert(config,initCRUDConfig(config.getAddConfig()),jsonObject);
        return new Result(id);
    }

    /**
     * 获取配置
     * @param routeName
     * @return
     */
    private FastDevTableConfig.TableConfig getTableConfig(String routeName){
        FastDevTableConfig.TableConfig r=null;

        if(config.getTableConfigs()!=null){
            r= CollectionUtil.firstOrDefault(config.getTableConfigs(),m->m.getRouteName().equals(routeName));
        }
        if(r==null){
            r=new FastDevTableConfig.TableConfig();
            r.setTableName(routeName);
        }
        if(StringUtil.isNullOrEmpty(r.getPrimaryKeyName())){
            r.setPrimaryKeyName("id");
        }
        return r;
    }

    /**
     * 初始化配置
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
