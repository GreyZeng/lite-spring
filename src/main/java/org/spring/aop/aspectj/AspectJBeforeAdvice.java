package org.spring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.spring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/8
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory){
        super(adviceMethod,pointcut,adviceObjectFactory);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        //例如： 调用TransactionManager的start方法
        this.invokeAdviceMethod();
        Object o = mi.proceed();
        return o;
    }


}