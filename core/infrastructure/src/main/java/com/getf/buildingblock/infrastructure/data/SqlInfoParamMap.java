package com.getf.buildingblock.infrastructure.data;

import lombok.Data;
import lombok.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SqlInfoParamMap {
    private String sql;
    //private List<Object> params;
    private Map<String,Object> params;

    public SqlInfoParamMap(String sql){
        this.sql=sql;
    }

    public SqlInfoParamMap(String sql,Map<String,Object> params){
        this.sql=sql;
        this.params=params;
    }

    public SqlInfo toSqlInfo(){
        boolean flagQuot=false;
        boolean flagQuot2=false;
        boolean flagAt=false;
        var chars= sql.toCharArray();
        List<String> names=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        StringBuilder resultSql=new StringBuilder();
        for(char elem:chars){
            if(!flagAt&&elem!='@') {
                resultSql.append(elem);
            }
            if(flagQuot){
                if(elem!='\'') continue;
            }
            if(flagQuot2){
                if(elem!='`') continue;
            }
            if(elem=='\''){
                flagQuot=!flagQuot;
            }
            if(elem=='`'){
                flagQuot2=!flagQuot2;
            }

            if(flagAt){
                if(elem>='0'&&elem<='9'||elem>='A'&&elem<='Z'||elem>='a'&&elem<='z'){
                    sb.append(elem);
                }else{
                    names.add(sb.toString());
                    sb.delete(0,sb.length());
                    resultSql.append('?').append(elem);
                    flagAt=false;
                }
            }

            if(elem=='@'){
                flagAt=true;
            }
        }
        if(sb.length()>0){
            names.add(sb.toString());
            resultSql.append('?');
        }

        SqlInfo r=new SqlInfo(resultSql.toString(),new ArrayList());
        for(var elem:names){
            r.getParams().add(params.get(elem));
        }

        return r;
    }
}
