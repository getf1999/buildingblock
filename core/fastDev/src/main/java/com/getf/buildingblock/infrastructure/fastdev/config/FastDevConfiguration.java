package com.getf.buildingblock.infrastructure.fastdev.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.getf.buildingblock.infrastructure.fastdev.FastDevApplication;
import com.getf.buildingblock.infrastructure.fastdev.config.interceptor.OperationInfoInterceptor;
import com.getf.buildingblock.infrastructure.fastdev.dao.DefaultDAO;
import com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder.ISqlBuilder;
import com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder.MysqlBuilder;
import com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder.SqlHelper;
import com.getf.buildingblock.infrastructure.fastdev.manager.InterceptorManager;
import com.getf.buildingblock.infrastructure.fastdev.service.DefaultService;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FastDevTableConfig.class)
@ComponentScan(FastDevApplication.PACKAGE_NAME)
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

//    @ConditionalOnMissingBean
//    @Bean
//    public FastDevTableConfig getFastDevTableConfig(){
//        var r=new FastDevTableConfig();
//        r.setPublicInterceptorClassNames(new ArrayList<>());
//        r.getPublicInterceptorClassNames().add("com.getf.buildingblock.infrastructure.fastdev.config.interceptor.OperationInfoInterceptor");
////        r.setUseBlackList(true);
////        r.setList(new ArrayList<>());
////        r.getList().add("sys_user");
//        return r;
//    }

    @ConditionalOnMissingBean
    @Bean
    public ISqlBuilder getSqlBuilder(){
        return new MysqlBuilder();
    }

    @Bean
    public OperationInfoInterceptor getOperationInfoInterceptor(){
        return new OperationInfoInterceptor();
    }

    @Bean
    public InterceptorManager getInterceptorManager(FastDevTableConfig config, ApplicationContext applicationContext) throws Exception {
        var r= new InterceptorManager(config,applicationContext);
        return r;
    }

    @Bean
    public DefaultDAO getDefaultDAO(){
        return new DefaultDAO();
    }

    @Bean
    public DefaultService getDefaultService(){
        return new DefaultService();
    }

    @Bean
    public SqlHelper getSqlHelper(){
        return new SqlHelper();
    }
}