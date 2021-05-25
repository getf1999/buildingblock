package com.getf.buildingblock.gateway.service;

import com.getf.buildingblock.gateway.model.CheckTokenResult;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "oauth",url = "http://localhost:9997")
public interface AuthService {
    @GetMapping(path="/oauth/check_token")
    @Headers({"Authorization:Basic YXBwOnNzc3M="})
    public CheckTokenResult checkToken(String token);
}
