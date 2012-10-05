package com.oracle.demo.ops.web.beans.session;

import com.oracle.demo.ops.web.beans.OPSController;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * Date: Jul 27, 2011
 * Time: 10:06:52 AM
 */

@Named
@SessionScoped
public class SessionControllerBean
    extends OPSController
{
  static final long serialVersionUID = 42L;
  private static final Logger logger = Logger.getLogger(SessionControllerBean.class.getName());

  private String userMode = CUSTOMER_MODE;
  private static final String CUSTOMER_MODE = "customer";
  private static final String ADMIN_MODE = "admin";

  private String wlFullSessionId;
  private String wlSessionId;
  private String wlSessionPrimaryJvmId;
  private String wlSessionSecondaryJvmId;
  private String wlSessionCreatedDate;

  // JSESSIONID=SESSION_ID!PRIMARY_JVMID_HASH!SECONDARY_JVM_HASH!CREATION_TIME

  public List<Map.Entry> getSessionAttributes()
  {
    List<Map.Entry> atts = new ArrayList<Map.Entry>();

    Enumeration<String> attributes = getHttpSession().getAttributeNames();

    logger.info("Session Attributes:");
    logger.info("======================================");
    while (attributes.hasMoreElements())
    {
      String att = attributes.nextElement();
      Object value = getHttpSession().getAttribute(att);
      atts.add(new AbstractMap.SimpleEntry(att, value));
      logger.info("Att name=[" + att + "] ObjectType=[" + value.getClass() + "] value=[" + value + "]");
    }
    logger.info("======================================");

    return atts;
  }

  public String getWlSessionId()
  {
    if (wlSessionId == null)
    {
      parseSessionId(getHttpSession().getId());
    }

    return wlSessionId;
  }

  private void parseSessionId(String pId)
  {
    StringTokenizer st = new StringTokenizer(pId, "!");
    int count = st.countTokens();
    wlFullSessionId = pId;

    logger.info("Session ID: " + pId);
    try
    {
      while (st.hasMoreTokens())
      {
        wlSessionId = st.nextToken();
        wlSessionPrimaryJvmId = st.nextToken();

        if (count == 4)
        {
          wlSessionSecondaryJvmId = st.nextToken();
        }
        else
        {
          wlSessionSecondaryJvmId = "None";
        }

        String date = st.nextToken();
        StringBuilder sb = new StringBuilder(date);
        long dateMillis = Long.parseLong(date);
        Date theDate = new Date(dateMillis);
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        sb.append(" (").append(format.format(theDate)).append(')');

        wlSessionCreatedDate = sb.toString();
      }
    }
    catch (Exception e)
    {
      logger.info(e.getClass().getSimpleName() + " exception while parsing Session ID: " + pId);
      logger.log(Level.WARNING, e.getClass().getSimpleName() + " exception while parsing Session ID: " + pId, e);
    }

  }

  public String getWlSessionCreatedDate()
  {
    if (wlSessionCreatedDate == null)
    {
      parseSessionId(getHttpSession().getId());
    }

    return wlSessionCreatedDate;
  }

  public String getWlSessionPrimaryJvmId()
  {
    if (wlSessionPrimaryJvmId == null)
    {
      parseSessionId(getHttpSession().getId());
    }

    return wlSessionPrimaryJvmId;
  }

  public String getWlSessionSecondaryJvmId()
  {
    if (wlSessionSecondaryJvmId == null)
    {
      parseSessionId(getHttpSession().getId());
    }

    return wlSessionSecondaryJvmId;
  }


  public String getWebLogicServerName()
  {
    return System.getProperty("weblogic.Name");
  }


  public String invalidateSession()
  {
    logger.info("INVALIDATING SESSION!! ID=[" + getWlSessionId() + "]");

    Enumeration<String> attributes = getHttpSession().getAttributeNames();
    logger.info("Session Attributes:");
    logger.info("======================================");
    while (attributes.hasMoreElements())
    {
      String att = attributes.nextElement();
      Object value = getHttpSession().getAttribute(att);
      logger.info("Att name=[" + att + "] ObjectType=[" + value.getClass() + "] value=[" + value + "]");
    }
    logger.info("======================================");

    getHttpSession().invalidate();
    return "/index";
  }


  public void invalidateSessionActionListener(ActionEvent event)
  {
    invalidateSession();
    getFacesContext().addMessage(null, new FacesMessage("Session Invalidated", "User Request"));
  }

  public String getUserMode()
  {
    return userMode;
  }

  public void setUserMode(String pUserMode)
  {
    userMode = pUserMode;
  }

  public void onSwitchUserMode(ActionEvent event)
  {
    switchUserMode();
  }

  public String changeUserModeAction()
  {
    switchUserMode();

    return "";
  }

  private void switchUserMode()
  {
    if (userMode.equals(CUSTOMER_MODE))
    {
      userMode = ADMIN_MODE;
    }
    else
    {
      userMode = CUSTOMER_MODE;
    }

    logger.info("Switching modes!  New Mode: " + userMode);
  }

  public String getWlFullSessionId()
  {
    if (wlFullSessionId == null)
    {
      parseSessionId(getHttpSession().getId());
    }

    return wlFullSessionId;
  }
}
