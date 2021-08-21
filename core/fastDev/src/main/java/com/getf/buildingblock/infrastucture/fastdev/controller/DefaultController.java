package com.getf.buildingblock.infrastucture.fastdev.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastucture.fastdev.dao.DefaultDao;
import com.getf.buildingblock.infrastucture.fastdev.service.DefaultService;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class DefaultController {
    @Resource
    DefaultService service;


    @RequestMapping("/{tableName}/list")
    public Object query(@PathVariable("tableName") String tableName) throws SQLException {
        var r=service.query(tableName,JSONArray.class);
        if(r.getCode()==-100){
            return ResponseEntity.notFound().build();
        }
        return r.getData();
    }
}
