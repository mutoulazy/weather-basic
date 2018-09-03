package com.waylau.spring.cloud.weather.job;

import com.waylau.spring.cloud.weather.service.CityDataService;
import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.vo.City;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 天气数据定时更新任务
 */
public class WeatherDataSyncJob extends QuartzJobBean {
  private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);
  @Autowired
  private CityDataService cityDataService;
  @Autowired
  private WeatherDataService weatherDataService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    logger.info("WeatherDataSyncJob Begin!!");
    List<City> cityList = null;
    try {
      cityList = cityDataService.listCity();
    } catch (IOException e) {
      logger.error("文件打开读取错误", e);
    } catch (JAXBException e) {
      logger.error("json文件转换错误", e);
    }
    // 遍历城市ID获取天气
    for (City city : cityList) {
      String cityId = city.getCityId();
      logger.debug("WeatherDataSyncJob: cityId="+cityId);
      weatherDataService.syncDateByCityId(cityId);
    }

    logger.debug("WeatherDataSyncJob Done!!!");
  }
}
