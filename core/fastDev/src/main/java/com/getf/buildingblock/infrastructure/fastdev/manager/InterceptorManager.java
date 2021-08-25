package com.getf.buildingblock.infrastructure.fastdev.manager;

import com.getf.buildingblock.infrastructure.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastructure.fastdev.config.interceptor.Interceptor;
import lombok.var;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class InterceptorManager {

    List<Interceptor> publicInterceptors;
    Map<String,List<Interceptor>> interceptors;

    @Resource
    ApplicationContext applicationContext;

    public InterceptorManager(FastDevTableConfig config, ApplicationContext applicationContext) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.applicationContext=applicationContext;
        if(config.getPublicInterceptorClassNames()!=null){
            publicInterceptors=getPublicInterceptorsByClassNames(config.getPublicInterceptorClassNames());
            publicInterceptors.sort(Comparator.comparing(m->m.getOrder()));
        }else{
            publicInterceptors=new ArrayList<>();
        }
        interceptors=new HashMap<>();
        if(config.getTableConfigs()==null||config.getTableConfigs().size()==0) return;


        for(var elem:config.getTableConfigs()){
            var tmpInterceptors=getPublicInterceptorsByClassNames(elem.getInterceptorClassNames());
            if(elem.getIgnorePublicInterceptorClassNames()!=null){
                var tmpPublicInterceptors=publicInterceptors.stream().filter(m->{
                    return elem.getIgnorePublicInterceptorClassNames().stream().filter(m1->!m.getClass().getName().equals(m1)).findAny().orElse(null)==null;
                }).collect(Collectors.toList());
                tmpInterceptors.addAll(tmpPublicInterceptors);
            }
            tmpInterceptors.sort(Comparator.comparing(m->m.getOrder()));
            interceptors.put(elem.getRouteName(),tmpInterceptors);
        }
    }

    public List<Interceptor> getList(String routeName){
        if(interceptors.containsKey(routeName)) return interceptors.get(routeName);
        return publicInterceptors;
    }

    private Interceptor getInterceptorByClassName(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        var cls= Class.forName(className);
        return (Interceptor)applicationContext.getBean(cls);
    }

    private List<Interceptor> getPublicInterceptorsByClassNames(List<String> classNames) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        List<Interceptor> r= new ArrayList<>();
        if(classNames!=null&&classNames.size()>0) {
            for (var elem : classNames) {
                r.add(getInterceptorByClassName((elem)));
            }
        }
        return r;
    }
}
