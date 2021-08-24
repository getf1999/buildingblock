package com.getf.buildingblock.infrastructure.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import lombok.var;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResultSetConverter {
    public static JSONArray to(ResultSet collection) throws SQLException {
        return to(collection,null);
    }
    public static JSONArray to(ResultSet collection, List<String> ignoreDbFields) throws SQLException {
        var metaData = collection.getMetaData();
        var columnsCount = metaData.getColumnCount();
        JSONArray r = new JSONArray();
        while (collection.next()) {
            JSONObject jsonObject = new JSONObject();
            for (var i = 1; i <= columnsCount; i++) {
                var columnName = metaData.getColumnLabel(i);
                if(ignoreDbFields!=null&&ignoreDbFields.stream().filter(m->columnName.equals(m)).findAny().orElse(null)!=null){
                    continue;
                }
                jsonObject.put(StringUtil.lineToHump(columnName), collection.getObject(columnName));
            }
            r.add(jsonObject);
        }
        return r;
    }
}
