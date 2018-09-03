package com.waylau.spring.cloud.weather.job;

import com.waylau.spring.cloud.weather.vo.City;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 天气数据定时更新任务
 */
public class WeatherDataSyncJob extends QuartzJobBean {
  private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    logger.info("WeatherDataSyncJob 开始");

    List<City> cityList = null;
  }
}
