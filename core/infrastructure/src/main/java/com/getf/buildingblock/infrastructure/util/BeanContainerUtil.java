//package com.getf.buildingblock.infrastructure.util;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class BeanContainerUtil implements ApplicationContextAware {
//    private static ApplicationContext applicationContext;
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//    public synchronized void setApplicationContext(ApplicationContext applicationContext){
//        this.applicationContext = applicationContext;
//    }
//    public static Object getBean(String beanName) {
//        return applicationContext.getBean(beanName);
//    }
//
//    public static <T> T getBean(Class<T> cls) {
//        return applicationContext.getBean(cls);
//    }
//
//    public static Map<String,Object> getBeanOfType(Class cls){
//        return applicationContext.getBeansOfType(cls);
//    }
//}
