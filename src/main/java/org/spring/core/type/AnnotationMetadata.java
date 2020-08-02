package org.spring.core.type;

import org.spring.beans.factory.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author zenghui
 * 2020/8/2
 */
public interface AnnotationMetadata extends ClassMetadata {

    /**
     * 注解类型
     *
     * @return
     */
    Set<String> getAnnotationTypes();

    /**
     * 是否存在某个注解
     *
     * @param annotationType
     * @return
     */
    boolean hasAnnotation(String annotationType);

    /**
     * 注解属性
     *
     * @param annotationType
     * @return
     */
    AnnotationAttributes getAnnotationAttributes(String annotationType);
}