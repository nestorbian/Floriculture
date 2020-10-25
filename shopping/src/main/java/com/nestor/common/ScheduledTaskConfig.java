package com.nestor.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 配置spring定时任务的线程池
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/24
 */
@Configuration
public class ScheduledTaskConfig {

    /**
     * 配置spring定时任务的线程池
     *
     * @param
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
     * @date : 2020/10/24 21:35
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
        threadPoolTaskScheduler.setThreadNamePrefix("schedule-pool-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }

}
