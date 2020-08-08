package org.spring.aop.config;

import org.spring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zenghui
 * 2020/8/8
 */
public interface AopConfig {
    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();


    boolean isInterfaceProxied(Class<?> intf);


    List<Advice> getAdvices();


    void addAdvice(Advice advice);

    List<Advice> getAdvices(Method method/*,Class<?> targetClass*/);

    void setTargetObject(Object obj);

}
