package com.waylau.spring.cloud.weather.controller;

import com.waylau.spring.cloud.weather.service.CityDataService;
import com.waylau.spring.cloud.weather.service.WeatherReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面接口类
 */
@Api(value = "WeatherReportController", description = "城市天气预报rest接口")
@RestController
@RequestMapping("/report")
public class WeatherReportController {
  @Autowired
  private CityDataService cityDataService;

  @Autowired
  private WeatherReportService weatherReportService;

  @ApiOperation(value = "城市ID查询天气", notes = "通过城市ID查询天气,并返回展示页面")
  @ApiImplicitParam(name = "cityId", value = "城市Id", required = true, paramType = "path", dataType = "String")
  @GetMapping("/cityId/{cityId}")
  public ModelAndView getReportByCityId(@PathVariable("cityId") String cityId, Model model) throws Exception {
    model.addAttribute("title", "老卫的天气预报");
    model.addAttribute("cityId", cityId);
    model.addAttribute("cityList", cityDataService.listCity());
    model.addAttribute("report", weatherReportService.getDataByCityId(cityId));
    return new ModelAndView("weather/report", "reportModel", model);
  }
}
