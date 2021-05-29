package com.getf.buildingblock.auth.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @RequestMapping("/oauth/test")
    public Object test(HttpServletRequest request) {
        var tmp=request.getHeader("Authorization");
        return "1";
    }
}
