package com.oracle.demo.ops.jms;

import javax.jms.JMSException;
import javax.jms.Message;
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
 * Time: 6:15:13 PM
 */
public class JMSUtils
{
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
