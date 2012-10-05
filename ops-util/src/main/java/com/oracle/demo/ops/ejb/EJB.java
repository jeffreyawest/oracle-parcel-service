package com.oracle.demo.ops.ejb;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/23/12
 * Time: 11:50 AM
 */
public class EJB
{
  private static final String EJB_PREFIX;

  static
  {
    try
    {
      String appName = (String) new InitialContext().lookup("java:app/AppName");
      EJB_PREFIX = "java:global/" +  appName+ "/";
    }
    catch (NamingException e)
    {
      throw new ExceptionInInitializerError(e);
    }
  }

  private EJB()
  {
  }

  @SuppressWarnings("unchecked")
  public static <T> T lookup(Class<T> ejbClass)
  {
    String jndiName = EJB_PREFIX + ejbClass.getSimpleName();

    try
    {
      return (T) new InitialContext().lookup(jndiName);
    }
    catch (NamingException e)
    {
      throw new IllegalArgumentException(
          String.format("Cannot find EJB class %s with JNDI %s", ejbClass, jndiName), e);
    }
  }
}
