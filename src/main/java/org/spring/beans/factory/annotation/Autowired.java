package org.spring.beans.factory.annotation;

/**
 * @author zenghui
 * 2020/8/2
 */

import java.lang.annotation.*;

/**
 * @author zenghui
 * @date 2020/7/27
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
