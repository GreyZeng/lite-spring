package org.spring.beans.factory.annotation;


import org.spring.beans.BeanDefinition;
import org.spring.core.type.AnnotationMetadata;

/**
 * @author zenghui
 * @Date 2020/7/27
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}

