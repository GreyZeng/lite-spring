package org.spring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.spring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/8
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {


    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }


    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable t) {
            invokeAdviceMethod();
            throw t;
        }
    }

}