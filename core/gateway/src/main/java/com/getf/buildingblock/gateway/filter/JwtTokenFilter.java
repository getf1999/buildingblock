package com.getf.buildingblock.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.gateway.service.AuthService;
import com.getf.buildingblock.infrastructure.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${gateway.whiteListUrls}")
    private String whiteListUrls;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if(whiteListUrls!=null) {
            var whiteLists = whiteListUrls.split(",");
            for (var elem : whiteLists) {
                if (PathUtil.wildcardEquals(elem, httpServletRequest.getServletPath())) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }

        String authorization=httpServletRequest.getHeader("Authorization");
        if(authorization==null){
            display401(httpServletResponse,"invalid_token","token cannot be empty");
            return;
        }

        String[] authorizationSplitResult=authorization.split(" ");
        if(authorizationSplitResult.length!=2){
            display401(httpServletResponse,"invalid_token","illegal token" + authorization);
            return;
        }

        if(!"Bearer".equals(authorizationSplitResult[0])){
            display401(httpServletResponse,"invalid_token","must be bearer token");
            return;
        }
        var checkResult= authService.checkToken(authorizationSplitResult[1]);
        if(!checkResult.isActive()){
            display401(httpServletResponse,checkResult.getError(),checkResult.getErrorDescription());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        return;
    }

    private void display401(HttpServletResponse httpServletResponse,String error,String errorDescription) throws IOException {
        httpServletResponse.setStatus(401);
        httpServletResponse.addHeader("WWW-Authenticate",getWWWAuthenticate(error,errorDescription));
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map<String,Object> rMap=new HashMap<>();
        rMap.put("message",errorDescription);
        rMap.put("code",-1);
        var jsonStr=JSONObject.toJSONString(rMap);
        httpServletResponse.getWriter().write(jsonStr);
    }

    private String getWWWAuthenticate(String error,String errorDescription){
        String r="error=\"%s\", error_description=\"%s\"";
        r=String.format(r,error,errorDescription);
        return r;
    }
}
