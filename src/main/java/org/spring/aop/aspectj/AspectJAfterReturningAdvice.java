package org.spring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.spring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/8
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice{

    public AspectJAfterReturningAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory){
        super(adviceMethod,pointcut,adviceObjectFactory);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        Object o = mi.proceed();
        //例如：调用TransactionManager的commit方法
        this.invokeAdviceMethod();
        return o;
    }

}
