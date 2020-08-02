package org.spring.beans;

/**
 * @author zenghui
 * 2020/8/2
 */
public interface TypeConverter {
    // TODO 抽象出：TypeMismatchException
    <T> T convertIfNecessary(Object value, Class<T> requiredType);
}
