package com.getf.buildingblock.infrastucture.fastdev.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import lombok.var;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DefaultDao {
    @Resource
    DruidDataSource druidDataSource;

    public JSONArray query(String tableName) throws SQLException {
        DruidPooledConnection connection=null;
        PreparedStatement prepareStatement=null;
        ResultSet resultSet=null;
        try {
            connection = druidDataSource.getConnection();
            prepareStatement= connection.prepareStatement("select * from "+tableName);

            resultSet= prepareStatement.executeQuery();
            return toJSONArray(resultSet);
        }finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                }catch (SQLException e){

                }
            }
            if(prepareStatement!=null){
                try {
                    prepareStatement.close();
                }catch (SQLException e){

                }
            }
            if(connection!=null){
                try {
                    connection.close();
                }catch (SQLException e){

                }
            }
        }
    }

    private JSONArray toJSONArray(ResultSet resultSet) throws SQLException {
        var metaData=resultSet.getMetaData();
        var columnsCount=metaData.getColumnCount();
        JSONArray r=new JSONArray();
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            for(var i=1;i<=columnsCount;i++){
                var columnName=metaData.getColumnLabel(i);
                jsonObject.put(StringUtil.lineToHump(columnName),resultSet.getObject(columnName));
            }
            r.add(jsonObject);
        }
        return r;
    }
}
