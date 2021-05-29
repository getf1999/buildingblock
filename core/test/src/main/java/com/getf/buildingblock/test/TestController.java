package com.getf.buildingblock.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/auth/test1")
    public String test1(){
        return "1";
    }
}
