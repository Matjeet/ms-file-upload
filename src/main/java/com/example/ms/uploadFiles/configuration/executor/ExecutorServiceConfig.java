package com.example.ms.uploadFiles.configuration.executor;

import com.example.ms.uploadFiles.configuration.threadPool.ThreadPoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ExecutorServiceConfig {

    @Bean(name  = "cpuBoundExecutorService")
    public ExecutorService cpuBoundExecutorService(ThreadPoolProperties properties) {
        ThreadPoolProperties.PoolConfig cpuConfig = properties.getCpu();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                cpuConfig.getCoreSize(),
                cpuConfig.getMaxSize(),
                cpuConfig.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(cpuConfig.getQueueCapacity()),
                new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName(cpuConfig.getThreadNamePrefix() + threadNumber.getAndIncrement());
                        thread.setPriority(Thread.NORM_PRIORITY);
                        thread.setDaemon(false);
                        return thread;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy()
        );

        executor.allowCoreThreadTimeOut(false);

        return executor;
    }
}
