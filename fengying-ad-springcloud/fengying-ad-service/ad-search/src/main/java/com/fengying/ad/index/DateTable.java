package com.fengying.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DateTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class,Object> dateTableMap=new ConcurrentHashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DateTable.applicationContext=applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("all")
    private static <T> T bean(String beanName){
        return (T)applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz){
        T instance=(T)dateTableMap.get(clazz);
        if(instance!=null){
            return instance;
        }
        dateTableMap.put(clazz,bean(clazz));
        return (T)dateTableMap.get(clazz);
    }

    @SuppressWarnings("all")
    private static <T> T bean(Class clazz){
        return (T)applicationContext.getBean(clazz);
    }
}
