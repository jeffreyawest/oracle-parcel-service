package com.oracle.demo.ops.ejb;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
 * **************************************************************************** * Created with IntelliJ IDEA.
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
