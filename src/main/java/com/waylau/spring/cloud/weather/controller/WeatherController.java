package com.waylau.spring.cloud.weather.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * Weather Controller.
 * 加入swagger2
 */
@Api(value = "WeatherController", description = "查询城市天气json数据接口")
@RestController
@RequestMapping("/weather")
public class WeatherController {

  @Autowired
  private WeatherDataService weatherDataService;

  @ApiOperation(value = "城市ID查询天气json数据", notes = "通过城市ID查询天气json数据")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "cityId", value = "城市ID", required = true, dataType = "string", paramType = "path")
  })
  @GetMapping("/cityId/{cityId}")
  public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId) {
    return weatherDataService.getDataByCityId(cityId);
  }

  @ApiOperation(value = "城市名称查询天气json数据", notes = "通过城市名称查询天气json数据")
  @ApiImplicitParam(name = "cityName", value = "城市名称", required = true, paramType = "path", dataType = "String")
  @GetMapping("/cityName/{cityName}")
  public WeatherResponse getWeatherByCityName(@PathVariable("cityName") String cityName) {
    return weatherDataService.getDataByCityName(cityName);
  }
}
