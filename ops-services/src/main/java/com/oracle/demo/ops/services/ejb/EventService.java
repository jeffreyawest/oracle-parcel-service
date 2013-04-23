package com.oracle.demo.ops.services.ejb;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.xml.MyMarshaller;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.WLSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.jms.*;
import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * **************************************************************************** * User: jeffrey.a.west
 * Date: 4/4/11
 * Time: 10:57 AM
 */

@Stateless(name = "EventServiceBean", mappedName = "ejb/EventService")
@LocalBean
public class EventService implements Serializable
{
  private static final Logger logger = Logger.getLogger(EventService.class.getName());

  @Resource(name = Constants.CONNECTION_FACTORY_JNDI, type = ConnectionFactory.class)
  private ConnectionFactory connectionFactory;

  @Resource(name = Constants.EVENT_QUEUE_JNDI, type = Queue.class)
  private Queue queue;

  @Resource(name = Constants.EVENT_TOPIC_JNDI, type = Topic.class)
  private Topic topic;

  private static final String[] EVENT_LOCATIONS = {"57625",
                                                   "87101",
                                                   "50302",
                                                   "82523",
                                                   "73401",
                                                   "57625",
                                                   "57625"};

  private long EVENT_SIMULATION_INTERVAL = 2500;

  private WLConnection connection;
  private WLSession session;
  private WLMessageProducer queueProducer;
  private WLMessageProducer topicProducer;

  @PostConstruct
  public void initialize()
  {
    try
    {
      connection = (WLConnection) connectionFactory.createConnection();
      session = (WLSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      queueProducer = (WLMessageProducer) session.createProducer(queue);
      topicProducer = (WLMessageProducer) session.createProducer(topic);
    }
    catch (JMSException e)
    {
      if (connection != null)
      {
        try
        {
          connection.close();
        }
        catch (JMSException f)
        {
          e.printStackTrace();
        }
      }
    }
  }

  @PreDestroy
  public void closeConnection()
  {
    try
    {
      if (connection != null)
      {
        connection.close();
      }
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  public void sendEventToQueue(final ParcelEvent event)
  {
    try
    {
//      queueProducer.setUnitOfOrder(getUnitOfOrder(event));
      queueProducer.send(session.createTextMessage(MyMarshaller.marshallObjectToString(event)));
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

  private String getUnitOfOrder(ParcelEvent event)
  {
    return "ParcelId_" + event.getParcelId();
  }

  public void publishEventToTopic(final com.oracle.demo.ops.domain.ParcelEvent event)
  {
    try
    {
      String eventXmlString = MyMarshaller.marshallObjectToString(event);
      TextMessage newMessage = session.createTextMessage(eventXmlString);

      setJmsMessageProperties(event, newMessage);
//      topicProducer.setUnitOfOrder(getUnitOfOrder(event));
      topicProducer.send(newMessage);
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

  private void setJmsMessageProperties(final com.oracle.demo.ops.domain.ParcelEvent event, final TextMessage newMessage)
      throws
      JMSException
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


  public void simulateEvents(Shipment shipment)
  {
    int counter = 0;
    for (Parcel parcel : shipment.getParcels())
    {
      counter++;
      logger.info("Simulating events for Parcel " + counter + " of " + shipment.getParcels().size());
      simulateEvents(parcel, shipment.getFromAddress(), shipment.getToAddress());
    }
  }

  private void simulateEvents(Parcel parcel, Address pStartingAddress, Address pDestinationAddress)
  {

    long sendDelay = EVENT_SIMULATION_INTERVAL;

    ParcelEvent parcelEvent;
//    parcelEvent = createEvent("Billing Information Received",
//                                          ParcelStatus.BILLING_INFO_RECEIVED,
//                                          Calendar.getInstance(),
//                                          "United States",
//                                          parcel.getId());
//    // do not delay first event
//    sendEventToQueue(parcelEvent, sendDelay);


    parcelEvent = createEvent("Origin Scan",
                              ParcelStatus.PARCEL_RECEIVED,
                              Calendar.getInstance(),
                              pStartingAddress.getPostalCode(),
                              parcel.getId());
    sendEventToQueue(parcelEvent, sendDelay);


    parcelEvent = createEvent("Departure Scan",
                              ParcelStatus.IN_TRANSIT,
                              Calendar.getInstance(),
                              pStartingAddress.getPostalCode(),
                              parcel.getId());
    sendDelay += EVENT_SIMULATION_INTERVAL;
    sendEventToQueue(parcelEvent, sendDelay);


    for (int x = 0; x < EVENT_LOCATIONS.length - 1; x++)
    {
      parcelEvent = createEvent("Arrival Scan",
                                ParcelStatus.IN_TRANSIT,
                                Calendar.getInstance(),
                                EVENT_LOCATIONS[x],
                                parcel.getId());
      sendDelay += EVENT_SIMULATION_INTERVAL;
      sendEventToQueue(parcelEvent, sendDelay);


      parcelEvent = createEvent("Departure Scan",
                                ParcelStatus.IN_TRANSIT,
                                Calendar.getInstance(),
                                EVENT_LOCATIONS[x],
                                parcel.getId());
      sendDelay += EVENT_SIMULATION_INTERVAL;
      sendEventToQueue(parcelEvent, sendDelay);

    }

    parcelEvent = createEvent("Arrival Scan",
                              ParcelStatus.IN_TRANSIT,
                              Calendar.getInstance(),
                              pDestinationAddress.getPostalCode(),
                              parcel.getId());
    sendDelay += EVENT_SIMULATION_INTERVAL;
    sendEventToQueue(parcelEvent, sendDelay);


    parcelEvent = createEvent("On Vehicle for Delivery Today",
                              ParcelStatus.IN_TRANSIT,
                              Calendar.getInstance(),
                              pDestinationAddress.getPostalCode(),
                              parcel.getId());
    sendDelay += EVENT_SIMULATION_INTERVAL;
    sendEventToQueue(parcelEvent, sendDelay);


    parcelEvent = createEvent("Delivered",
                              ParcelStatus.DELIVERED,
                              Calendar.getInstance(),
                              pDestinationAddress.getPostalCode(),
                              parcel.getId());
    sendDelay += EVENT_SIMULATION_INTERVAL;
    sendEventToQueue(parcelEvent, sendDelay);
  }

  private void sendEventToQueue(ParcelEvent pParcelEvent, long pDelay)
  {
    try
    {
      String eventString = MyMarshaller.marshallObjectToString(pParcelEvent);
      TextMessage textMessage = session.createTextMessage(eventString);

//      long deliveryDate = getDeliveryDate(pDelay);

      queueProducer.setTimeToDeliver(pDelay);
      queueProducer.send(textMessage);
      queueProducer.setTimeToDeliver(0);
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

  private long getDeliveryDate(long pDelay)
  {
    return new Date().getTime() + pDelay;
  }

  private ParcelEvent createEvent(String message, ParcelStatus status, Calendar date, String location, int parcelId)
  {
    ParcelEvent event = new ParcelEvent();
    event.setMessage(message);
    event.setParcelStatus(status);
    event.setEventDate(date);
    event.setLocation(location);
    event.setParcelId(parcelId);

    return event;
  }

}