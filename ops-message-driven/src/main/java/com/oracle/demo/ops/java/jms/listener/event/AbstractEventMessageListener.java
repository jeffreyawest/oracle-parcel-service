package com.oracle.demo.ops.java.jms.listener.event;

import com.oracle.demo.event.handler.AbstractEventHandler;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.jms.EventJMSMessageConverter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

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
 * Date: Feb 19, 2011
 * Time: 8:57:04 AM
 */
public class AbstractEventMessageListener
    implements MessageListener
{
  public static final boolean DEBUG = false;
  public static final boolean DISABLE = false;

  @EJB
  protected ParcelManager parcelManager;

  @EJB
  protected ParcelEventManager parcelEventManager;

  @EJB
  protected ShipmentManager shipmentManager;

  protected AbstractEventHandler eventHandler;

  @Inject
  protected EventJMSMessageConverter messageConverter;

  @PostConstruct
  public void init()
  {
    messageConverter = new EventJMSMessageConverter();

    if (eventHandler != null)
    {
      // wire in the EJB's top the Event Handler
      eventHandler.setParcelEventManager(parcelEventManager);
      eventHandler.setParcelManager(parcelManager);
      eventHandler.setShipmentManager(shipmentManager);
    }
  }

  /**
   * Concrete event handlers will implement this method.
   */
  @Override
  public void onMessage(Message message)
  {
    if(DISABLE)
    {
      return;
    }

    if (DEBUG)
    {
      System.out.println("AbstractEventHandler.onMessage()..." + this.getClass().getName());
      StringWriter stringWriter = new StringWriter();

      PrintWriter writer = new PrintWriter(stringWriter);

      try
      {
        writer.println("===================================================");
        writer.println("MDB Event Handler class=[" + this.getClass().getName() + "]");
        writer.println("");
        writer.println("JMS Message Id: " + message.getJMSMessageID());

        for (Enumeration em = message.getPropertyNames(); em.hasMoreElements();)
        {
          String property = (String) em.nextElement();
          writer.println("JMS Prop=[" + property + "] value=[" + message.getStringProperty(property) + "]");
        }

        writer.println("===================================================");
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }

      writer.flush();
      System.out.println(stringWriter.toString());
    }

    if (message instanceof TextMessage)
    {
      try
      {

        ParcelEvent event = messageConverter.convertMessage((TextMessage) message);
        this.eventHandler.handleEvent(event);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    else

    {
      System.out.println("NON-TEXT-MESSAGE RECEIVED");
    }
  }
}