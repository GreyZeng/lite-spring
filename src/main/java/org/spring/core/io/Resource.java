package org.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Grey
 * 2020/7/31
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
