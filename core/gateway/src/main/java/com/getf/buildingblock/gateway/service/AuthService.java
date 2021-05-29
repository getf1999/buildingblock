package com.getf.buildingblock.gateway.service;

import com.getf.buildingblock.gateway.config.AuthServiceRequestInterceptor;
import com.getf.buildingblock.gateway.model.CheckTokenResult;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="auth",configuration = AuthServiceRequestInterceptor.class)
public interface AuthService {
    @RequestMapping(path="/oauth/check_token",method = RequestMethod.GET)
    public CheckTokenResult checkToken(@RequestParam("token") String token);
}
