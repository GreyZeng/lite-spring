package org.spring.test.v1;

import org.junit.Test;
import org.spring.core.io.ClassPathResource;
import org.spring.core.io.FileSystemResource;
import org.spring.core.io.Resource;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * @author Grey
 * 2020/7/31
 */
public class ResourceTest {
    @Test
    public void testClassPathResource() throws Exception {
        Resource r = new ClassPathResource("bean-v1.xml");
        try (InputStream is = r.getInputStream()) {
            assertNotNull(is);
        }
    }

    @Test
    public void testFileSystemResource() throws Exception {
        Resource r = new FileSystemResource("src\\test\\resources\\bean-v1.xml");
        try (InputStream is = r.getInputStream()) {
            assertNotNull(is);
        }

    }
}
