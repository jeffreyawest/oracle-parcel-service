package com.oracle.demo.ops.java.jms.listener.shipment.queue;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.CreateShipmentRequest;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.services.ejb.ShipmentService;
import com.oracle.demo.ops.xml.MyMarshaller;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
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
 * Date: Feb 20, 2011
 * Time: 7:45:55 AM
 */

@MessageDriven(
    messageListenerInterface = javax.jms.MessageListener.class,
    name = "NewShipmentListenerMDB",
    mappedName = Constants.SHIPMENT_QUEUE_JNDI,
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = Constants.CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class NewShipmentListener
    implements MessageListener
{
  @EJB
  private ShipmentService shipmentService;

  @Override
  public void onMessage(Message message)
  {
    if (message instanceof TextMessage)
    {
      try
      {
        Shipment shipment = MyMarshaller.unmarshallShipment((TextMessage) message);
        CreateShipmentRequest req = new CreateShipmentRequest();
        req.setShipment(shipment);
        shipmentService.createShipment(req);
      }
      catch (JAXBException e)
      {
        e.printStackTrace();
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }
  }
}
