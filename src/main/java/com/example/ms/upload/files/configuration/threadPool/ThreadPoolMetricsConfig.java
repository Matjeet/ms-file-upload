package com.example.ms.upload.files.configuration.threadPool;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolMetricsConfig {

    @Bean
    public MeterBinder ioPoolMetrics(ThreadPoolTaskExecutor ioBoundTaskExecutor) {
        return meterRegistry -> {
            ThreadPoolExecutor executor = ioBoundTaskExecutor.getThreadPoolExecutor();

            Gauge.builder("thread.pool.io.active.count", executor, ThreadPoolExecutor::getActiveCount)
                    .description("Total number of active threads in IO")
                    .register(meterRegistry);

            Gauge.builder("thread.pool.io.queue.size", executor, e -> executor.getQueue().size())
                    .description("Maximum number of queue threads in IO")
                    .register(meterRegistry);

            Gauge.builder("thread.pool.oi.pool.size", executor, ThreadPoolExecutor::getPoolSize)
                    .description("Maximum number of pool threads in IO")
                    .register(meterRegistry);

            Gauge.builder("thread.pool.io.core.size", executor, ThreadPoolExecutor::getCorePoolSize)
                    .description("Maximum core of pool threads in IO")
                    .register(meterRegistry);

            Gauge.builder("thread.pool.io.max.size", executor, ThreadPoolExecutor::getMaximumPoolSize)
                    .description("Maximum pool size of pool threads in IO")
                    .register(meterRegistry);
        };
    }

    @Bean
    public MeterBinder cpuPoolMetrics(ThreadPoolTaskExecutor cpuBoundTaskExecutor) {
        return meterRegistry -> {
          ThreadPoolExecutor executor = cpuBoundTaskExecutor.getThreadPoolExecutor();

          Gauge.builder("thread.pool.cpu.active.count", executor, ThreadPoolExecutor::getActiveCount)
                  .description("Total number of active threads in CPU")
                  .register(meterRegistry);

          Gauge.builder("thread.pool.cpu.queue.size", executor, e -> e.getQueue().size())
                  .description("Maximum number of queue threads in CPU")
                  .register(meterRegistry);
        };
    }
}
