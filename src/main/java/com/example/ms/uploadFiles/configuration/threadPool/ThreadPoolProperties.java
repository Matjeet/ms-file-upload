package com.example.ms.uploadFiles.configuration.threadPool;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {

    private PoolConfig io;
    private PoolConfig cpu;

    public static class PoolConfig {
        private int coreSize;
        private int maxSize;
        private int queueCapacity;
        private int keepAliveSeconds;
        private String threadNamePrefix;

        public int getCoreSize() {
            return coreSize;
        }

        public void setCoreSize(int coreSize) {
            this.coreSize = coreSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public int getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public void setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }

    public PoolConfig getIo() {
        return io;
    }

    public void setIo(PoolConfig io) {
        this.io = io;
    }

    public PoolConfig getCpu() {
        return cpu;
    }

    public void setCpu(PoolConfig cpu) {
        this.cpu = cpu;
    }
}
