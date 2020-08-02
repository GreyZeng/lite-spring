package org.spring.core.type.classreading;

import org.spring.core.type.AnnotationMetadata;
import org.spring.core.type.ClassMetadata;

/**
 * @author zenghui
 * 2020/8/2
 */
public interface MetadataReader {


    /**
     * Read basic class metadata for the underlying class.
     */
    ClassMetadata getClassMetadata();

    /**
     * Read full annotation metadata for the underlying class,
     * including metadata for annotated methods.
     */
    AnnotationMetadata getAnnotationMetadata();
}
