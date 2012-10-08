package com.oracle.demo.ops.spring.rest.jmx;

import com.oracle.demo.ops.domain.Parcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Sep 23, 2011
 * Time: 5:58:05 PM
 */
@Controller
@RequestMapping(value = "/jmx")
public class WebLogicRESTJMXProxy
{
  @Autowired
  private WebLogicJMXWrapper proxy;

  @RequestMapping(value = "/SpringBean/MyString", method = RequestMethod.GET)
  @ResponseBody
  public String getProxyStringValue()
  {
    return proxy.getMyString();
  }

  @RequestMapping(value = "/att/{namespace}/{beanName}/{attName}", method = RequestMethod.GET)
  @ResponseBody
  public String getAttributeValue(@PathVariable("namespace") String namespace,
                                  @PathVariable("beanName") String beanName,
                                  @PathVariable("attName") String attName)
  {
    StringBuilder sb = new StringBuilder();

    ObjectNameWrapper beanObjectNameWrapper = new ObjectNameWrapper();
    beanObjectNameWrapper.setNamespace(namespace);
    beanObjectNameWrapper.setBeanName(beanName);

    sb.append(proxy.getAttributeValue(beanObjectNameWrapper, attName));

    return sb.toString();
  }

  @RequestMapping(value = "/att/{namespace}/{beanName}/{attName}", method = RequestMethod.PUT)
  @ResponseBody
  public String setAttributeValueFromPUT(@RequestBody String newValue,
                                         @PathVariable("namespace") String namespace,
                                         @PathVariable("beanName") String beanName,
                                         @PathVariable("attName") String attName)
  {
    ObjectNameWrapper beanObjectNameWrapper = new ObjectNameWrapper();
    beanObjectNameWrapper.setBeanName(beanName);
    beanObjectNameWrapper.setNamespace(namespace);

    StringBuilder sb = new StringBuilder();
    
    try
    {
      proxy.setAttributeValue(beanObjectNameWrapper, attName, newValue);
      sb.append("Success!");
    }
    catch (Exception e)
    {
      StringWriter sw = new StringWriter();
      PrintWriter writer = new PrintWriter(sw);
      e.printStackTrace(writer);
      writer.flush();
      sb.append(sw.toString());
    }

    return sb.toString();
  }

  @RequestMapping(value = "/att/{namespace}/{beanName}/{attName}/{newValue}", method = RequestMethod.GET)
  @ResponseBody
  public String setAttributeValueFromGET(@PathVariable("namespace") String namespace,
                                         @PathVariable("beanName") String beanName,
                                         @PathVariable("attName") String attName,
                                         @PathVariable("newValue") String newValue)
  {
    StringBuilder sb = new StringBuilder();

    ObjectNameWrapper beanObjectNameWrapper = new ObjectNameWrapper();
    beanObjectNameWrapper.setBeanName(beanName);
    beanObjectNameWrapper.setNamespace(namespace);

    try
    {
      proxy.setAttributeValue(beanObjectNameWrapper, attName, newValue);
      sb.append("Success!");
    }
    catch (Exception e)
    {
      StringWriter sw = new StringWriter();
      PrintWriter writer = new PrintWriter(sw);
      e.printStackTrace(writer);
      writer.flush();
      sb.append(sw.toString());
    }


    return sb.toString();
  }

}
