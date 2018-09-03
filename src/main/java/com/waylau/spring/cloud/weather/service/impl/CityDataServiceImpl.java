package com.waylau.spring.cloud.weather.service.impl;

import com.waylau.spring.cloud.weather.service.CityDataService;
import com.waylau.spring.cloud.weather.util.XmlBuilder;
import com.waylau.spring.cloud.weather.vo.City;
import com.waylau.spring.cloud.weather.vo.CityList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * CityDataService实现类
 */
@Service
public class CityDataServiceImpl implements CityDataService {

  /**
   * 读取本地xml文件把xml文件内数据转化为citylist对象
   * @return
   * @throws Exception
   */
  @Override
  public List<City> listCity() throws Exception {
    // 读取xml文件
    Resource resource = new ClassPathResource("citylist.xml");
    BufferedReader br = new BufferedReader(
        new InputStreamReader(resource.getInputStream(), "utf-8"));
    StringBuffer stringBuffer = new StringBuffer();
    String line = "";

    while ((line =br.readLine()) != null) {
      stringBuffer.append(line);
    }

    br.close();

    // 把xml对象转化为citylist对象
    CityList cityList = (CityList) XmlBuilder.xmlStrToObject(stringBuffer.toString(), CityList.class);
    return cityList.getCityList();
  }
}
