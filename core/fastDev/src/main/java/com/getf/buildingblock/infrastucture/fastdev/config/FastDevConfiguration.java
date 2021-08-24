package com.getf.buildingblock.infrastucture.fastdev.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder.ISqlBuilder;
import com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder.MysqlBuilder;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@EnableConfigurationProperties(FastDevProperties.class)
public class FastDevConfiguration {
    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriverClassName;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String dataSourceUsername;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @ConditionalOnMissingBean
    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource r=new DruidDataSource();
        r.setDriverClassName(dataSourceDriverClassName);
        r.setUrl(dataSourceUrl);
        r.setUsername(dataSourceUsername);
        r.setPassword(dataSourcePassword);
        return r;
    }

    @ConditionalOnMissingBean
    @Bean
    public FastDevTableConfig getFastDevTableConfig(){
        var r=new FastDevTableConfig();
//        r.setUseBlackList(true);
//        r.setList(new ArrayList<>());
//        r.getList().add("sys_user");
        return r;
    }

    @ConditionalOnMissingBean
    @Bean
    public ISqlBuilder getSqlBuilder(){
        return new MysqlBuilder();
    }
}
/*spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver*/