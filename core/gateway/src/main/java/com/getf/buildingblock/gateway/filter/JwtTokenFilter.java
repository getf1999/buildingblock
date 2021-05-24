package com.getf.buildingblock.gateway.filter;

import com.getf.buildingblock.infrastructure.PathUtil;
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

@Configuration
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${gateway.whiteListUrls}")
    private String whiteListUrls;

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
            httpServletResponse.setStatus(401);
            httpServletResponse.addHeader("WWW-Authenticate",getWWWAuthenticate("invalid_token","token cannot be empty"));
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write("{\"message\":\"token cannot be empty\",\"code\":-1}");
        }
    }

    private String getWWWAuthenticate(String error,String errorDescription){
        String r="error=\"%s\", error_description=\"%s\"";
        r=String.format(r,error,errorDescription);
        return r;
    }
}
