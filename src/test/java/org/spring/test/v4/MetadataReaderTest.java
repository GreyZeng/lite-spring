package org.spring.test.v4;

import org.junit.Test;
import org.spring.beans.factory.annotation.AnnotationAttributes;
import org.spring.core.io.ClassPathResource;
import org.spring.core.type.AnnotationMetadata;
import org.spring.core.type.classreading.MetadataReader;
import org.spring.core.type.classreading.SimpleMetadataReader;
import org.spring.stereotype.Component;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author zenghui
 * 2020/8/2
 */
public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/spring/service/v4/UserService.class");

        MetadataReader reader = new SimpleMetadataReader(resource);
        //注意：不需要单独使用ClassMetadata
        //ClassMetadata clzMetadata = reader.getClassMetadata();
        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        assertEquals("userService", attributes.get("value"));

        //注：下面对class metadata的测试并不充分
        assertFalse(amd.isAbstract());
        assertFalse(amd.isFinal());
        assertEquals("org.spring.service.v4.UserService", amd.getClassName());

    }
}
