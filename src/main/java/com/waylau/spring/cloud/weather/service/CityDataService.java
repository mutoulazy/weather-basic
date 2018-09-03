package com.waylau.spring.cloud.weather.service;

import com.waylau.spring.cloud.weather.vo.City;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;

public interface CityDataService {

  /**
   * 获取City列表
   */
  List<City> listCity() throws JAXBException, IOException;
}
