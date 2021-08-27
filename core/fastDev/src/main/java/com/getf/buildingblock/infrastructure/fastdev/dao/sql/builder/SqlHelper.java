package com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.getf.buildingblock.infrastructure.converter.ResultSetConverter;
import com.getf.buildingblock.infrastructure.data.SqlInfoParamMap;
import lombok.var;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;


public class SqlHelper {
    @Resource
    protected DruidDataSource druidDataSource;

    private PreparedStatement getPreparedStatementParams(DruidPooledConnection connection, SqlInfoParamMap sqlInfoParamMap) throws SQLException {
        var sqlInfo=sqlInfoParamMap.toSqlInfo();
        var r=connection.prepareStatement(sqlInfo.getSql());
        int i=1;
        for(var elem:sqlInfo.getParams()){
            r.setObject(i++,elem);
        }
        System.out.println(sqlInfo.getSql());
        return r;
    }

    public JSONArray execJSONArray(SqlInfoParamMap sqlInfoParamMap, List<String> ignoreFields) throws SQLException {
        DruidPooledConnection connection=null;
        PreparedStatement prepareStatement=null;
        ResultSet resultSet=null;
        try {
            connection = druidDataSource.getConnection();
            prepareStatement= getPreparedStatementParams(connection,sqlInfoParamMap);
            resultSet= prepareStatement.executeQuery();
            return ResultSetConverter.to(resultSet,ignoreFields);
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

    public <T> T execScalar(SqlInfoParamMap sqlInfoParamMap,Class<T> tClass) throws SQLException {
        DruidPooledConnection connection=null;
        PreparedStatement prepareStatement=null;
        ResultSet resultSet=null;
        try {
            connection = druidDataSource.getConnection();
            prepareStatement= getPreparedStatementParams(connection,sqlInfoParamMap);
            resultSet=prepareStatement.executeQuery();
            if(resultSet.next()){
                return (T)resultSet.getObject(1);
            }
            return null;
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

    public void execNonQuery(SqlInfoParamMap sqlInfoParamMap) throws SQLException {
        DruidPooledConnection connection=null;
        PreparedStatement prepareStatement=null;
        try {
            connection = druidDataSource.getConnection();
            prepareStatement= getPreparedStatementParams(connection,sqlInfoParamMap);
            prepareStatement.executeUpdate();
        }finally {
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
}
