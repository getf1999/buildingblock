package com.getf.buildingblock.infrastucture.fastdev.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastucture.fastdev.service.DefaultService;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
public class DefaultController {
    @Resource
    DefaultService service;



    @PostMapping("/{routeName}/list")
    public Object query(@PathVariable("routeName") String routeName, @RequestBody FilterInfo filterInfo) throws SQLException {
        var r=service.query(routeName,filterInfo,JSONArray.class);
        if(r.getCode()==-100){
            return ResponseEntity.notFound().build();
        }
        return r;
    }

    @PostMapping("/{routeName}")
    public Object add(@PathVariable("routeName") String routeName, @RequestBody JSONObject jsonObject) throws SQLException {
        var r=service.add(routeName,jsonObject);
        if(r.getCode()==-100){
            return ResponseEntity.notFound().build();
        }
        return r;
    }
}
