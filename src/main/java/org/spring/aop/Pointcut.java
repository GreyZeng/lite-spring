package org.spring.aop;

/**
 * @author Grey
 * @date 2020/7/29
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}
