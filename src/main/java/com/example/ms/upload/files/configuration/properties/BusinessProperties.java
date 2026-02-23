package com.example.ms.upload.files.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "business")
public class BusinessProperties {

    private File file;

    public static class File {
        private String tempDir;

        public String getTempDir() {
            return tempDir;
        }

        public void setTempDir(String tempDir) {
            this.tempDir = tempDir;
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
