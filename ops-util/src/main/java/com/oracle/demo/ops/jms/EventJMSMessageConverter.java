package com.oracle.demo.ops.jms;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.xml.MyMarshaller;

import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 12/6/11
 * Time: 10:55 AM
 */
@Named
public class EventJMSMessageConverter
{
  public ParcelEvent convertMessage(TextMessage message) throws JAXBException, JMSException
  {
    return (ParcelEvent) MyMarshaller.unmarshalEvent((TextMessage) message);
  }
}
