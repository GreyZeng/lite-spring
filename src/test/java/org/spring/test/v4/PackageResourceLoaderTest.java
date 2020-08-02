package org.spring.test.v4;

import org.junit.Test;
import org.spring.core.io.Resource;
import org.spring.core.io.support.PackageResourceLoader;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author zenghui
 * 2020/8/2
 */
public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.spring.dao.v4");
        assertEquals(3, resources.length);
    }
}
