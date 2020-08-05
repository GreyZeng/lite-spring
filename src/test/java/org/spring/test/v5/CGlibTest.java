package org.spring.test.v5;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.spring.service.v5.UserService;
import org.spring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/5
 */
public class CGlibTest {
    @Test
    public void testCallBack() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new TransactionInterceptor());
        UserService userService = (UserService) enhancer.create();
        userService.placeOrder();
    }


    public static class TransactionInterceptor implements MethodInterceptor {
        TransactionManager txManager = new TransactionManager();

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            txManager.start();
            Object result = proxy.invokeSuper(obj, args);
            txManager.commit();
            return result;
        }
    }
}
