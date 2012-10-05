package com.oracle.demo.ops.jms;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.xml.MyMarshaller;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import java.util.Enumeration;
import java.util.Hashtable;

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
 * Date: Feb 20, 2011
 * Time: 7:57:04 AM
 */
public class StandaloneJmsMessageSender
{
  private InitialContext context;

  ConnectionFactory jmsConnectionFactory;
  Destination       jmsDestination;
  Connection        conn;
  Session           session;
  MessageProducer   messageProducer;


  public StandaloneJmsMessageSender(String pURL,
                                    String pUsername,
                                    String pPassword,
                                    String connectionFactoryName,
                                    String destinationJNDI)
          throws NamingException
  {
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, Constants.WL_INITIAL_CONTEXT);
    h.put(Context.PROVIDER_URL, pURL);
    h.put(Context.SECURITY_PRINCIPAL, pUsername);
    h.put(Context.SECURITY_CREDENTIALS, pPassword);

    context = new InitialContext(h);
    jmsConnectionFactory = (ConnectionFactory) context.lookup(connectionFactoryName);
    jmsDestination = (Destination) context.lookup(destinationJNDI);

    try
    {
      conn = jmsConnectionFactory.createConnection();
      session = conn.createSession(false, 0);
      messageProducer = session.createProducer(jmsDestination);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  public StandaloneJmsMessageSender(String connectionFactoryJNDI, String destinationJNDI) throws NamingException
  {
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, Constants.WL_INITIAL_CONTEXT);

    context = new InitialContext(h);
    jmsConnectionFactory = (ConnectionFactory) context.lookup(connectionFactoryJNDI);
    jmsDestination = (Destination) context.lookup(destinationJNDI);

    try
    {
      conn = jmsConnectionFactory.createConnection();
      session = conn.createSession(false, 0);
      messageProducer = session.createProducer(jmsDestination);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }

  }

  public void forwardShipmentSAFE(Shipment pShipment)
  {
    try
    {
      forwardShipment(pShipment);
    }
    catch (NamingException e)
    {
      e.printStackTrace();
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

  public void forwardShipment(Shipment pShipment) throws NamingException, JMSException, JAXBException
  {
    try
    {
      String eventXmlString = MyMarshaller.marshallObjectToString(pShipment);
      TextMessage newMessage = session.createTextMessage(eventXmlString);

      messageProducer.send(newMessage);
      //System.out.println("Shipment sent!");
    }
    finally
    {
//      if (messageProducer != null)
//      { messageProducer.close(); }
//      if (session != null)
//      { session.close(); }
    }
  }


  public void forwardEventSAFE(ParcelEvent event)
  {
    try
    {
      forwardEvent(event);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
    catch (NamingException e)
    {
      e.printStackTrace();
    }
  }


  public void forwardEvent(ParcelEvent event) throws JAXBException, JMSException, NamingException
  {
    try
    {
      String eventXmlString = MyMarshaller.marshallObjectToString(event);
      TextMessage newMessage = session.createTextMessage(eventXmlString);

      setJmsMessageProperties(event, newMessage);
      messageProducer.send(newMessage);
    }
    finally
    {
//      messageProducer.close();
//      session.close();
    }
  }

  private void setJmsMessageProperties(ParcelEvent event, TextMessage newMessage)
          throws JMSException
  {
    newMessage.setStringProperty("location", event.getLocation());

    if (event.getEventDate() != null)
    {
      newMessage.setStringProperty("eventDate", event.getEventDate().toString());
    }

    newMessage.setStringProperty("parcelId", String.valueOf(event.getParcelId()));

    if (event.getParcelStatus() != null)
    {
      newMessage.setStringProperty("parcelStatus", event.getParcelStatus().value());
    }
  }

  public void forwardEventMessage(TextMessage message) throws JAXBException, JMSException, NamingException
  {
    ParcelEvent event = MyMarshaller.unmarshalEvent(message);


    try
    {
      TextMessage newMessage = session.createTextMessage(message.getText());

      for (Enumeration em = message.getPropertyNames(); em.hasMoreElements();)
      {
        String property = (String) em.nextElement();
        newMessage.setObjectProperty(property, message.getObjectProperty(property));
      }

      setJmsMessageProperties(event, newMessage);
      messageProducer.send(newMessage);
    }
    finally
    {
//      messageProducer.close();
//      session.close();
    }
  }
}
