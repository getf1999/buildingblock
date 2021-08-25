package com.getf.buildingblock.infrastructure.fastdev.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.getf.buildingblock.infrastructure.fastdev.config.interceptor.OperationInfoInterceptor;
import com.getf.buildingblock.infrastructure.fastdev.controller.DefaultController;
import com.getf.buildingblock.infrastructure.fastdev.dao.DefaultDAO;
import com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder.ISqlBuilder;
import com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder.MysqlBuilder;
import com.getf.buildingblock.infrastructure.fastdev.dao.sql.builder.SqlHelper;
import com.getf.buildingblock.infrastructure.fastdev.manager.InterceptorManager;
import com.getf.buildingblock.infrastructure.fastdev.service.DefaultService;
import lombok.var;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Configuration
@EnableConfigurationProperties(FastDevTableConfig.class)
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
    public InterceptorManager getInterceptorManager(FastDevTableConfig config, ApplicationContext applicationContext) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return new InterceptorManager(config,applicationContext);
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

//    @Bean(initMethod = "beanInited")
//    public DefaultController getDefaultController(){
//        var r= new DefaultController();
//        return r;
//    }

//    @Bean
//    public void regionController(DefaultController controller,RequestMappingHandlerMapping requestMappingHandlerMapping) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//
//        //注册Controller
//        Method method=requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
//                getDeclaredMethod("detectHandlerMethods",Object.class);
//        method.setAccessible(true);
//        method.invoke(requestMappingHandlerMapping,controller.getClass().getName());
//    }
}