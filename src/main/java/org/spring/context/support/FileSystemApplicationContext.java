package org.spring.context.support;

import org.spring.core.io.FileSystemResource;
import org.spring.core.io.Resource;

/**
 * @author zenghui
 * 2020/8/1
 */
public class FileSystemApplicationContext extends AbstractApplicationContext {
    public FileSystemApplicationContext(String configPath) {
        super(configPath);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
