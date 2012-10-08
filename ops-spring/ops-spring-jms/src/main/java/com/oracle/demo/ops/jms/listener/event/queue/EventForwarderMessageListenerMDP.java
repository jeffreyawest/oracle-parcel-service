package com.oracle.demo.ops.jms.listener.event.queue;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.services.ejb.EventService;
import com.oracle.demo.ops.xml.MyMarshaller;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

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
 * Date: Feb 15, 2011
 * Time: 9:58:46 PM
 */
public class EventForwarderMessageListenerMDP
        implements MessageListener
{
  private EventService eventService;

  @Autowired
  public void setForwarder(EventService eventForwarder)
  {
    this.eventService = eventForwarder;
  }

  @Override
  public void onMessage(Message message)
  {
    System.out.println(this.getClass().getName() + ":: Forwarding message...");

    if (message instanceof TextMessage)
    {
      try
      {
        ParcelEvent event = MyMarshaller.unmarshalEvent((TextMessage) message);
        eventService.publishEventToTopic(event);
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
      catch (JAXBException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      System.out.println("EventQueueListener NOT TEXT MESSSAGE");
    }
  }
}