package com.oracle.demo.ops.java.jms.listener.event.topic;

import com.oracle.demo.event.handler.parcelLog.UpdateParcelLogEventHandler;
import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.java.jms.listener.event.AbstractEventMessageListener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

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
 * Time: 9:01:08 AM
 */

// http://download.oracle.com/docs/cd/E17904_01/web.1111/e15493/dist_topics.htm

@MessageDriven(
    messageListenerInterface = javax.jms.MessageListener.class,
    name = "AddParcelEventMDB",
    mappedName = Constants.EVENT_TOPIC_JNDI,
    activationConfig = {
        @ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = Constants.CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "topicMessagesDistributionMode", propertyValue = "One-Copy-Per-Application"),
        @ActivationConfigProperty(propertyName = "distributedDestinationConnection", propertyValue = "LocalOnly")
    })

public class AddParcelLogMessageListener
    extends AbstractEventMessageListener
{
  public AddParcelLogMessageListener()
  {
    eventHandler = new UpdateParcelLogEventHandler();
  }
}