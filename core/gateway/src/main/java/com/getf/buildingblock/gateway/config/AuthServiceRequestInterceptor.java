package com.getf.buildingblock.gateway.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Enumeration;

@Configuration
public class AuthServiceRequestInterceptor implements RequestInterceptor {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        var text=clientId+":"+clientSecret;
        byte[] bytes= text.getBytes("UTF-8");
        var base64Str= Base64.getEncoder().encodeToString(bytes);
        requestTemplate.header("Authorization", "Basic "+base64Str);
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes == null) {
//            return;
//        }
//
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String name = headerNames.nextElement();
//                Enumeration<String> values = request.getHeaders(name);
//                while (values.hasMoreElements()) {
//                    String value = values.nextElement();
//                    requestTemplate.header("Authorization", value);
//                }
//            }
//        }
    }
}
