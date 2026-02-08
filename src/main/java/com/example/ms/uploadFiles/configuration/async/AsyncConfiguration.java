package com.example.ms.uploadFiles.configuration.async;

import com.example.ms.uploadFiles.configuration.threadPool.ThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class AsyncConfiguration implements AsyncConfigurer {

    private final ThreadPoolProperties properties;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AsyncConfiguration(ThreadPoolProperties properties) {
        this.properties = properties;
    }

    @Override
    public Executor getAsyncExecutor() {
        return ioBoundTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("Async error in method: {}", method.getName());
            ex.printStackTrace();
        };
    }

    @Bean(name = "ioBoundTaskExecutor")
    public ThreadPoolTaskExecutor ioBoundTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getIo().getCoreSize());
        executor.setMaxPoolSize(properties.getIo().getMaxSize());
        executor.setQueueCapacity(properties.getIo().getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getIo().getKeepAliveSeconds());
        executor.setThreadNamePrefix(properties.getIo().getThreadNamePrefix());

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setAllowCoreThreadTimeOut(true);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }

    @Bean(name = "cpuBoundTaskExecutor")
    public ThreadPoolTaskExecutor cpuBoundTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCpu().getCoreSize());
        executor.setMaxPoolSize(properties.getCpu().getMaxSize());
        executor.setQueueCapacity(properties.getCpu().getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getCpu().getKeepAliveSeconds());
        executor.setThreadNamePrefix(properties.getCpu().getThreadNamePrefix());

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setAllowCoreThreadTimeOut(false);

        executor.initialize();
        return executor;
    }
}
