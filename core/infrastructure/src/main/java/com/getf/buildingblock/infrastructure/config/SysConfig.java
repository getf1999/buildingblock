package com.getf.buildingblock.infrastructure.config;

import com.getf.buildingblock.infrastructure.login.user.DebugLoginUserManager;
import com.getf.buildingblock.infrastructure.login.user.LoginUserManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SysConfig {
    @Bean
    @ConditionalOnMissingBean
    public LoginUserManager getLoginUserManager(){
        return new DebugLoginUserManager();
    }
}
