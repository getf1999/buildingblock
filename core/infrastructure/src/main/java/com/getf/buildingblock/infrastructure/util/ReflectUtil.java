package com.getf.buildingblock.infrastructure.util;

import lombok.var;

import java.lang.reflect.*;

public abstract class ReflectUtil {
    /**
     * 获取泛型类型
     * @param obj
     * @return
     */
    public static Class getGenericClass(Object obj){
        Type type = obj.getClass().getGenericSuperclass();
        if( type instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType)type;
            Type claz = pType.getActualTypeArguments()[0];
            if(claz instanceof Class ){
                return (Class) claz;
            }
        }
        return null;
    }

    /**
     * 获取泛型类型
     * @param obj
     * @return
     */
    public static Type[] getGenericTypes(Object obj){
        Type type = obj.getClass().getGenericSuperclass();
        if( type instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType)type;
            return pType.getActualTypeArguments();
        }
        return null;
    }

    /**
     * 设置属性
     * @param obj
     * @param propertyName 不要带set 例 setId 直接传id
     * @param value
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void setProperty(Object obj, String propertyName,Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var method= obj.getClass().getMethod("set"+StringUtil.firstCharToUp(propertyName));
        method.invoke(obj,value);
    }

    /**
     * 获取属性值
     * @param obj
     * @param propertyName 不要带get 例 getId 直接传id
     * @return
     */
    public static Object setProperty(Object obj,String propertyName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var method=obj.getClass().getMethod("get"+StringUtil.firstCharToUp(propertyName));
        return method.invoke(obj);
    }
}
