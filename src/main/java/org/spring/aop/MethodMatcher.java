package org.spring.aop;

import java.lang.reflect.Method;

/**
 * @author Grey
 * @date 2020/7/29
 */
public interface MethodMatcher {
    boolean matches(Method method/*, Class<?> targetClass*/);
}
