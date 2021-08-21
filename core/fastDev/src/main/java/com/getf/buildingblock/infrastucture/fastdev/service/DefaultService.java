package com.getf.buildingblock.infrastucture.fastdev.service;

import com.getf.buildingblock.infrastructure.model.Result;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import com.getf.buildingblock.infrastucture.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastucture.fastdev.dao.DefaultDao;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service
public class DefaultService {
    @Resource
    private FastDevTableConfig config;

    @Resource
    private DefaultDao dao;

    public <T> Result<T> query(String tableName,Class<T> tClass) throws SQLException {
        if(isInBlackList(tableName)){
            return new Result(-100,"is in black list");
        }
        return new Result(dao.query(tableName));
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
                if(StringUtil.humpToLine(tableName).equals(elem.getName())){
                    return false;
                }
            }
        }
        return true;
    }
}
