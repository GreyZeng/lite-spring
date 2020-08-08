package org.spring.beans.factory;

/**
 * @author zenghui
 * 2020/8/9
 */
public interface FactoryBean<T> {


    T getObject() throws Exception;

    Class<?> getObjectType();

}