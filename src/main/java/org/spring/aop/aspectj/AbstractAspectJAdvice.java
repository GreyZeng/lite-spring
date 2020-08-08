package org.spring.aop.aspectj;

import org.spring.aop.Advice;
import org.spring.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/8
 */
public abstract class AbstractAspectJAdvice implements Advice {


    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected Object adviceObject;



    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 Object adviceObject){

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObject = adviceObject;
    }


    public void invokeAdviceMethod() throws  Throwable{

        adviceMethod.invoke(adviceObject);
    }
    @Override
    public Pointcut getPointcut(){
        return this.pointcut;
    }
    public Method getAdviceMethod() {
        return adviceMethod;
    }
}