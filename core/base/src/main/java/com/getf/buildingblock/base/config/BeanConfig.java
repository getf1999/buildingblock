package com.getf.buildingblock.base.config;

import com.getf.buildingblock.infrastructure.login.user.DebugLoginUserManager;
import com.getf.buildingblock.infrastructure.login.user.LoginUserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public LoginUserManager getLoginUserManager(){
        return new DebugLoginUserManager();
    }
}
