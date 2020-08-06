package org.spring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.spring.aop.MethodMatcher;
import org.spring.aop.aspectj.AspectJExpressionPointcut;
import org.spring.service.v5.UserService;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/5
 */
public class PointcutTest {
    @Test
    public void testPointcut() throws NoSuchMethodException {
        String expression = "execution(* org.spring.service.v5.*.getAccountDao(..))";
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);
        MethodMatcher mm = pc.getMethodMatcher();
        {
            Class<?> targetClass = UserService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertFalse(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertTrue(mm.matches(method2));
        }

        {
            Class<?> targetClass = org.spring.service.v4.UserService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }
    }
}
