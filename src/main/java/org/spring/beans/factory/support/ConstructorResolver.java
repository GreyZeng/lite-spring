package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.ConstructorArgument;
import org.spring.beans.SimpleTypeConverter;
import org.spring.beans.TypeConverter;
import org.spring.beans.factory.BeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author zenghui
 * 2020/8/2
 */
public class ConstructorResolver {
    private final BeanFactory beanFactory;

    public ConstructorResolver(BeanFactory factory) {
        this.beanFactory = factory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {

        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;
        try {
            // TODO 这里要考虑指定ClassLoader
            //beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());

            beanClass = Thread.currentThread().getContextClassLoader().loadClass(bd.getBeanClassName());

        } catch (ClassNotFoundException e) {
            // TODO 封装异常 throw new BeanCreationException(bd.getID(), "Instantiation of bean failed, can't resolve class", e);
            throw new RuntimeException(bd.getID() + "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument cargs = bd.getConstructorArgument();
        TypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {

            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if (parameterTypes.length != cargs.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];

            boolean result = this.valuesMatchTypes(parameterTypes,
                    cargs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);

            if (result) {
                constructorToUse = candidates[i];
                break;
            }

        }


        //找不到一个合适的构造函数
        if (constructorToUse == null) {
            // TODO throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
            throw new RuntimeException(bd.getID() + "can't find a apporiate constructor");

        }


        try {
            // 找到了一个合适的构造函数，则用这个构造函数初始化Bean对象初始化Bean对象
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            // TODO throw new BeanCreationException(bd.getID(), "can't find a create instance using " + constructorToUse);        }
            throw new RuntimeException(bd.getID() + "can't find a create instance using " + constructorToUse);
        }


    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     TypeConverter typeConverter) {


        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try {
                //获得真正的值
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                //如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
                //如果转型失败，则抛出异常。说明这个构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                //转型成功，记录下来
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                // TODO 封装异常
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
