package org.spring.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Grey
 * 2020/7/31
 */
public class FileSystemResource implements Resource {
    private final File file;

    public FileSystemResource(String path) {
        this.file = new File(path);
    }

    /**
     * 将文件转换为流
     *
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
