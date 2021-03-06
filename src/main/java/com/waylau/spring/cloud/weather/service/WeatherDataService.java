package com.waylau.spring.cloud.weather.service;

import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * Weather Data Service.
 * 
 * @since 1.0.0 2017年11月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface WeatherDataService {
	/**
	 * 根据城市ID查询天气数据
	 * 
	 * @param cityId
	 * @return
	 */
	WeatherResponse getDataByCityId(String cityId);

	/**
	 * 根据城市名称查询天气数据
	 * 
	 * @param cityName
	 * @return
	 */
	WeatherResponse getDataByCityName(String cityName);


	/**
	 * 根据城市ID来同步天气
	 * @param cityId
	 */
	void syncDateByCityId(String cityId);


	/**
	 * 根据城市名称来同步天气
	 * @param cityName
	 */
	void syncDateByCityName(String cityName);
	
}
