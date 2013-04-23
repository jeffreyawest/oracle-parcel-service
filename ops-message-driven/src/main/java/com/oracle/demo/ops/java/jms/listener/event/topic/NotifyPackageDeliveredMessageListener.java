package com.oracle.demo.ops.java.jms.listener.event.topic;

import com.oracle.demo.event.handler.delivery.NotifyPackageDeliveredEventHandler;
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
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * **************************************************************************** * User: jeffrey.a.west
 * Date: Feb 19, 2011
 * Time: 9:01:08 AM
 */
@MessageDriven(
    messageListenerInterface = javax.jms.MessageListener.class,
    name = "NotifyPackageDeliveredMDB",
    mappedName = Constants.EVENT_TOPIC_JNDI,
    activationConfig = {
        @ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = Constants.CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "parcelStatus = 'DELIVERED'"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "topicMessagesDistributionMode", propertyValue = "One-Copy-Per-Application"),
        @ActivationConfigProperty(propertyName = "distributedDestinationConnection", propertyValue = "LocalOnly")
    })
public class NotifyPackageDeliveredMessageListener
    extends AbstractEventMessageListener
{
  public NotifyPackageDeliveredMessageListener()
  {
    eventHandler = new NotifyPackageDeliveredEventHandler();
  }
}
