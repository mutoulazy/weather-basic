package com.waylau.spring.cloud.weather.config;

import com.waylau.spring.cloud.weather.job.WeatherDataSyncJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz Configuration
 */
@Configuration
public class QuartzConfiguration {
  private static final int TIME = 1800;

  /**
   * 天气任务的JobDetail
   * @return
   */
  @Bean
  public JobDetail weatherDataSyncJobDetail() {
    return JobBuilder.newJob(WeatherDataSyncJob.class).withIdentity("weatherDataSyncJob").storeDurably().build();
  }

  /**
   * 天气任务的Trigger
   * @return
   */
  @Bean
  public Trigger weatherDataSyncTrigger() {
    SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInSeconds(TIME).repeatForever();
    return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
        .withIdentity("weatherDataSyncTrigger").withSchedule(schedBuilder).build();
  }
}
