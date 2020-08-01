package org.spring.context.support;

import org.spring.core.io.ClassPathResource;
import org.spring.core.io.Resource;

/**
 * @author zenghui
 * 2020/8/1
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String configPath) {
        super(configPath);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);
    }
}
