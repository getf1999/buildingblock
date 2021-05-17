package com.getf.buildingblock.gateway.controller;

import com.getf.buildingblock.gateway.domain.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @Autowired
    TokenService tokenService;

    @Value("${test}")
    public String test;

    @RequestMapping("/test")
    public Object Test() {
        var r = tokenService.createToken(1L);
        return r;
    }
}
