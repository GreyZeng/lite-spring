package org.spring.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Grey
 * 2020/7/31
 */
public class ClassPathResource implements Resource {
    private String path;

    public ClassPathResource(String path) {
        this.path = path;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.path);

        if (is == null) {
            throw new FileNotFoundException(path + " cannot be opened");
        }
        return is;
    }
}
