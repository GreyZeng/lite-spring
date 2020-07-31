package org.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Grey
 * 2020/7/31
 */
public class ClassPathResource implements Resource {
    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // TODO 异常处理
        // TODO ClassLoader处理
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(this.path);
    }
}
