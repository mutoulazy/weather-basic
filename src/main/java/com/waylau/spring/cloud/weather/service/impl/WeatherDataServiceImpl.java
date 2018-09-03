package com.waylau.spring.cloud.weather.service.impl;

import com.waylau.spring.cloud.weather.service.WeatherDataService;
import java.io.IOException;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * WeatherDataService 实现.
 * 加入redis缓存
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";
	private static final long TIME_OUT = 1800L; // 1800s
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		return this.doGetWeahter(uri);
	}

	@Override
	public void syncDateByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		this.saveWeatherData(uri);
	}

	@Override
	public void syncDateByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		this.saveWeatherData(uri);
	}

	/**
	 * 通过url从天气接口获取天气json数据，并添加本地缓存
	 * @param uri
	 * @return
	 */
	private WeatherResponse doGetWeahter(String uri) {
		String key = uri;
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;
		String strBody = null;
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

		// 先检测缓存，有缓存就直接在缓存中取数据
		if (stringRedisTemplate.hasKey(key)) {
			logger.debug("从redis缓存中获取数据");
			strBody = ops.get(key);
		} else {
			logger.debug("redis 中没有缓存，从接口取数据存入缓存");
			ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

			// 从接口中获取数据
			if (respString.getStatusCodeValue() == 200) {
				strBody = respString.getBody();
			}

			// 添加进入redis缓存中
			ops.set(uri, strBody, TIME_OUT, TimeUnit.SECONDS);
		}

		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			logger.error("转换成天气接收类失败", e);
		}
		
		return resp;
	}

	/**
	 * 把天气数据存入到redis缓存
	 * @param uri
	 */
	private void saveWeatherData(String uri) {
		String key = uri;
		String strBody = null;
		ValueOperations<String, String>  ops = stringRedisTemplate.opsForValue();

		// 调用服务接口来获取
		ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		}

		// 数据写入缓存
		ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
	}

}
