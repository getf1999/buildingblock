package com.getf.buildingblock.infrastructure.fastdev.controller;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.fastdev.service.DefaultService;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
public class DefaultController {
    @Resource
    DefaultService service;
    @Resource
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostMapping("/{routeName}/list")
    public Object query(@PathVariable("routeName") String routeName, @RequestBody FilterInfo filterInfo) throws SQLException {
        var r=service.query(routeName,filterInfo);
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

    @GetMapping("/{routeName}/{id}")
    public Object getById(@PathVariable("routeName") String routeName,@PathVariable("id") Long id) throws SQLException {
        var r=service.getById(routeName,id);
        if(r.getCode()==-100){
            return ResponseEntity.notFound().build();
        }
        return r;
    }

    @PutMapping("/{routeName}")
    public Object edit(@PathVariable("routeName") String routeName, @RequestBody JSONObject jsonObject) throws SQLException {
        var r=service.edit(routeName,jsonObject);
        if(r.getCode()==-100){
            return ResponseEntity.notFound().build();
        }
        return r;
    }

    @DeleteMapping("/{routeName}/{id}")
    public Object delete(@PathVariable("routeName") String routeName,@PathVariable("id") Long id) throws SQLException {
        var r=service.delete(routeName,id);
        if(r.getCode()==-100){
            return ResponseEntity.notFound().build();
        }
        return r;
    }


//    private void beanInited() throws Exception {
//        dynamicLoadUtils.registerController("DefaultController");
//    }
}
