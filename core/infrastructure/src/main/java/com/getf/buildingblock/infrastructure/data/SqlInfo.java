package com.getf.buildingblock.infrastructure.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SqlInfo {
    private String sql;
    //private List<Object> params;
    private List<Object> params;

    public SqlInfo(String sql){
        this.sql=sql;
    }

    public SqlInfo(String sql,List<Object> params){
        this.sql=sql;
        this.params=params;
    }
}
