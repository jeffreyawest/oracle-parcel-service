package com.oracle.demo.ops.wldf.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
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
 * Date: Jul 5, 2011
 * Time: 6:06:07 PM
 */
@MessageDriven(
    messageListenerInterface = javax.jms.MessageListener.class,
    name = "WLDFJMSNotificationListener",
    mappedName = "com/oracle/demo/ops/jms/wldf/notification/jms",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/demo/ops/jms/wldf/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })

public class WLDFJMSNotificationListener
    implements MessageListener
{
  public void onMessage(Message message)
  {
    printMessageHeaders(message, this);

    System.out.println(message.getClass());

    if (message instanceof TextMessage)
    {
      TextMessage txtMsg = (TextMessage) message;
      try
      {
         String text = txtMsg.getText();
//        System.out.println(txtMsg.getText());
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }
    
    if (message instanceof MapMessage)
    {
      MapMessage map = (MapMessage) message;

      try
      {
        Enumeration names = map.getMapNames();

        while (names.hasMoreElements())
        {
          String name = (String) names.nextElement();
          System.out.println(name+"="+map.getString(name));
        }
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static final void printMessageHeaders(Message pMessge, Object pHandler)
  {
    StringWriter stringWriter = new StringWriter();

    PrintWriter writer = new PrintWriter(stringWriter);

    try
    {
      writer.println("===================================================");
      writer.println("MDB Event Handler class=[" + pHandler.getClass().getName() + "]");
      writer.println("");
      writer.println("JMS Message Id: " + pMessge.getJMSMessageID());

      for (Enumeration em = pMessge.getPropertyNames(); em.hasMoreElements();)
      {
        String property = (String) em.nextElement();
        writer.println("JMS Prop=[" + property + "] value=[" + pMessge.getStringProperty(property) + "]");
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

}
