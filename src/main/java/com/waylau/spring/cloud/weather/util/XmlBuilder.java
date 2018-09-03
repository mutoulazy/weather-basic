package com.waylau.spring.cloud.weather.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * xml工具类
 */
public class XmlBuilder {

  /**
   * 把xml配置文件中的数据转化为实体类
   */
  public static Object xmlStrToObject(String xmlStr, Class<?> clazz)
      throws JAXBException, IOException {
    JAXBContext context = JAXBContext.newInstance(clazz);

    // XML 转为对象的接口
    Unmarshaller unmarshaller = context.createUnmarshaller();

    Reader reader = new StringReader(xmlStr);
    Object xmlObject = unmarshaller.unmarshal(reader);

    if (null != reader) {
      reader.close();
    }

    return xmlObject;
  }
}
