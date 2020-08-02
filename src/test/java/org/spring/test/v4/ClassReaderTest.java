package org.spring.test.v4;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.spring.beans.factory.annotation.AnnotationAttributes;
import org.spring.core.io.ClassPathResource;
import org.spring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.spring.core.type.classreading.ClassMetadataReadingVisitor;
import org.spring.service.v4.UserService;
import org.spring.stereotype.Component;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.objectweb.asm.ClassReader.SKIP_DEBUG;

/**
 * @author zenghui
 * 2020/8/2
 */
public class ClassReaderTest {
    @Test
    public void testGetClassMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/spring/service/v4/UserService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor, SKIP_DEBUG);
        assertFalse(visitor.isAbstract());
        assertFalse(visitor.isInterface());
        assertFalse(visitor.isFinal());
        assertEquals(UserService.class.getName(), visitor.getClassName());
        assertEquals(Object.class.getName(), visitor.getSuperClassName());
        assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnotation() throws Exception {
        ClassPathResource resource = new ClassPathResource("org/spring/service/v4/UserService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, SKIP_DEBUG);
        String annotation = Component.class.getName();
        assertTrue(visitor.hasAnnotation(annotation));
        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
        assertEquals("userService", attributes.get("value"));

    }
}
