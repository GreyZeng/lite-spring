package org.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author zenghui
 * 2020/8/8
 */
public interface Advice extends MethodInterceptor {
    Pointcut getPointcut();
}
