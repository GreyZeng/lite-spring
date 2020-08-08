package org.spring.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spring.aop.aspectj.AspectJAfterReturningAdvice;
import org.spring.aop.aspectj.AspectJBeforeAdvice;
import org.spring.aop.aspectj.AspectJExpressionPointcut;
import org.spring.aop.config.AopConfig;
import org.spring.aop.framework.AopConfigSupport;
import org.spring.aop.framework.CglibProxyFactory;
import org.spring.service.v5.UserService;
import org.spring.tx.TransactionManager;
import org.spring.util.MessageTracker;

import java.util.List;

/**
 * @author zenghui
 * 2020/8/8
 */
public class CglibAopProxyTest {
    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;

    @Before
    public  void setup() throws Exception{
        MessageTracker.clearMsgs();

        TransactionManager tx = new TransactionManager();
        String expression = "execution(* org.spring.service.v5.*.placeOrder(..))";
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        beforeAdvice = new AspectJBeforeAdvice(
                TransactionManager.class.getMethod("start"),
                pc,
                tx);

        afterAdvice = new AspectJAfterReturningAdvice(
                TransactionManager.class.getMethod("commit"),
                pc,
                tx);

    }

    @Test
    public void testGetProxy(){

        AopConfig config = new AopConfigSupport();

        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new UserService());


        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        UserService proxy = (UserService)proxyFactory.getProxy();

        proxy.placeOrder();


        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        proxy.toString();
    }
}
