package org.spring.beans.factory.annotation;

import org.spring.beans.factory.config.AutowireCapableBeanFactory;
import org.spring.beans.factory.config.DependencyDescriptor;
import org.spring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author zenghui
 * 2020/8/2
 */
public class AutowiredFieldElement extends InjectionElement {
    boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) this.member;
    }

    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(desc);
            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable ex) {
            // TODO 异常处理 throw new BeanCreationException("Could not autowire field: " + field, ex);
            throw new RuntimeException("Could not autowire field: " + field);
        }
    }

}