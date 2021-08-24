package com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.data.SqlInfoParamMap;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastructure.model.filter.data.SearchInfo;
import com.getf.buildingblock.infrastructure.model.filter.data.SortInfo;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

public class MysqlBuilder implements ISqlBuilder{
    // region query
    @Override
    public SqlInfoParamMap buildQueryByFilterInfo(String srcSql, FilterInfo filterInfo) {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT * FROM (").append(srcSql).append(") T");
        srcSql= sb.toString();
        SqlInfoParamMap r= buildSearch(srcSql,filterInfo);
        var sql=buildOrderBy(r.getSql(),filterInfo);
        sql=buildPaging(sql,filterInfo);
        r.setSql(sql);
        return r;
    }

    @Override
    public SqlInfoParamMap buildQuery(String tableName, FilterInfo filterInfo) {
        var sql= "`"+ tableName+"`";
        return buildQueryByFilterInfo(sql,filterInfo);
    }

    @Override
    public String getDisambiguationSql(String str) {
        return "`"+str+"`";
    }

    private SqlInfoParamMap buildSearch(String srcSql,FilterInfo filterInfo){
        var searchInfos=filterInfo.getSearchInfos();
        if(searchInfos==null||searchInfos.size()==0) return new SqlInfoParamMap(srcSql);
        StringBuilder sb=new StringBuilder();
        sb.append(srcSql).append(" WHERE ");

        Map<String,Object> params=new HashMap<>();
        List<String> searchAndParts=new ArrayList<>();
        for(var searchInfo:searchInfos){
            StringBuilder partSb=new StringBuilder();

            String searchType=searchInfo.getSearchType();
            if(StringUtil.isNullOrEmpty(searchType)){
                searchType=SearchInfo.LIKE;
            }

            if(SearchInfo.EQ.equals(searchType)&&searchInfo.getValues().size()>1){
                partSb.append("`").append(StringUtil.humpToLine(searchInfo.getFieldName())).append("`");
                partSb.append(" IN ('");
                partSb.append(String.join("','",searchInfo.getValues().stream().map(m->m.replace("\\'","\\'\\'")).collect(Collectors.toList())));
                partSb.append("')");
            }else{
                List<String> searchOrParts=new ArrayList<>();
                for(var value:searchInfo.getValues()){
                    StringBuilder partOrSb=new StringBuilder();
                    partOrSb.append("`").append(StringUtil.humpToLine(searchInfo.getFieldName())).append("` ");
                    partOrSb.append(searchType);
                    var randomID=getRandomID();
                    if(SearchInfo.LIKE.equals(searchType)){
                        partOrSb.append(" CONCAT('%',@").append(randomID).append(",'%')");
                    }else{
                        partOrSb.append(" @").append(randomID);
                    }
                    params.put(randomID,value);
                    searchOrParts.add(partOrSb.toString());
                }
                partSb.append("(").append(String.join(" OR ",searchOrParts)).append(")");
            }
            searchAndParts.add(partSb.toString());
        }
        sb.append(String.join(" AND ",searchAndParts));
        return new SqlInfoParamMap(sb.toString(),params);
    }

    private String buildOrderBy(String srcSql,FilterInfo filterInfo){
        if(filterInfo.getSortInfos()==null||filterInfo.getSortInfos().size()==0){
            return srcSql;
        }
        List<String> sortStrList=new ArrayList<>();
        for(var elem:filterInfo.getSortInfos()){
            sortStrList.add(StringUtil.humpToLine(elem.getFieldName())+" "+(elem.getDesc()?"DESC":""));
        }
        return srcSql+" ORDER BY "+String.join(",",sortStrList);
    }

    private String buildPaging(String srcSql,FilterInfo filterInfo){
        int pageSize=filterInfo.getPageSize();
        if(pageSize<=0){
            return srcSql;
        }
        int pageIndex=filterInfo.getPageIndex();
        if(pageIndex<1){
            pageIndex=1;
        }

        StringBuilder r=new StringBuilder();
        r.append(srcSql).append(" LIMIT ").append((pageIndex-1)*pageSize).append(",").append(pageSize);
        return r.toString();
    }

    // endregion query

    @Override
    public SqlInfoParamMap buildInsert(String tableName, JSONObject jsonObject, List<String> ignoreFields) {
        Map<String,Object> params=new HashMap<>();
        List<String> insertFieldParts=new ArrayList<>();
        List<String> insertValueParts=new ArrayList<>();
        for(var elem:jsonObject.keySet()){
            if(ignoreFields!=null&& ignoreFields.contains(elem)){
               continue;
            }
            var randKey=getRandomID();
            insertFieldParts.add("`"+StringUtil.humpToLine(elem)+"`");
            insertValueParts.add("@"+randKey);
            params.put(randKey,jsonObject.get(elem));
        }
        StringBuilder sb=new StringBuilder();
        sb.append("INSERT INTO `").append(tableName).append("` (").append(String.join(",",insertFieldParts)).append(") VALUES (").append(String.join(",",insertValueParts)).append(")");
        return new SqlInfoParamMap(sb.toString(),params);
    }

    @Override
    public SqlInfoParamMap buildGetById(String tableName,String primaryKeyName,Long value) {
        var sql="SELECT * FROM `"+tableName+"` WHERE `"+primaryKeyName+"`=@id";
        Map<String,Object> params=new HashMap<>();
        params.put(primaryKeyName,value);
        return new SqlInfoParamMap(sql,params);
    }

    @Override
    public SqlInfoParamMap buildUpdate(String tableName,String primaryKeyName, JSONObject jsonObject, List<String> ignoreFields) {
        Map<String,Object> params=new HashMap<>();
        StringBuilder sb=new StringBuilder();
        sb.append("UPDATE `").append(tableName).append("` SET " );
        for(var elem:jsonObject.keySet()){
            if(ignoreFields!=null&& ignoreFields.contains(elem)||elem.equals(primaryKeyName)){
                continue;
            }
            var value=jsonObject.get(elem);
            if(value==null) continue;
            sb.append("`").append(StringUtil.humpToLine(elem)).append("`=@");
            var randKey=getRandomID();
            sb.append(randKey).append(",");
            params.put(randKey,jsonObject.get(elem));
        }
        sb.deleteCharAt(sb.length()-1);
        var randKey=getRandomID();
        sb.append(" WHERE `").append(primaryKeyName).append("`=@").append(randKey);
        params.put(randKey,jsonObject.get(primaryKeyName));
        return new SqlInfoParamMap(sb.toString(),params);
    }

    private String getRandomID(){
        return "A"+UUID.randomUUID().toString().replaceAll("-", "");
    }
}
