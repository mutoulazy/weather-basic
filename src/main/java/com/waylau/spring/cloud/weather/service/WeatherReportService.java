package com.waylau.spring.cloud.weather.service;

import com.waylau.spring.cloud.weather.vo.Weather;

/**
 * report服务接口
 */
public interface WeatherReportService {

  /**
   * 根据城市id查询天气
   * @param cityId
   * @return
   */
  Weather getDataByCityId(String cityId);
}
