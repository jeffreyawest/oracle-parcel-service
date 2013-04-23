package com.oracle.demo.ops.web.beans;

import com.oracle.demo.ops.entitymanager.*;
import com.oracle.demo.ops.services.ejb.EventService;
import com.oracle.demo.ops.services.ejb.GeolocationService;
import com.oracle.demo.ops.services.ejb.ParcelService;
import com.oracle.demo.ops.services.ejb.ShipmentService;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.logging.Logger;

/*
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
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Jul 28, 2011
 * Time: 7:30:50 AM
 */
public class OPSController
    implements Serializable
{
  static final long serialVersionUID = 1001L;
  private static final Logger logger = Logger.getLogger(OPSController.class.getName());

  private static final boolean USE_LOCAL = true;

  private static final String EJB_PARCEL_MANAGER_LOCAL_JNDI = "ejb/ParcelManager";
  private static final String EJB_PARCEL_LOG_EVENT_MANAGER_LOCAL_JNDI = "ejb/ParcelEventManager";
  private static final String EJB_SHIPMENT_MANAGER_LOCAL_JNDI = "ejb/ShipmentManager";
  private static final String EJB_SHIPPING_SERVICE_MANAGER_LOCAL_JNDI = "ejb/ShippingServiceManager";
  private static final String EJB_SHIPMENT_SERVICE_LOCAL_JNDI = "ejb/ShipmentService";
  private static final String EJB_PARCEL_SERVICE_LOCAL_JNDI = "ejb/ParcelService";
  private static final String EJB_EVENT_SERVICE_LOCAL_JNDI = "ejb/EventService";
  private static final String EJB_GEOLOCATION_MANAGER_LOCAL_JNDI = "ejb/GeolocationManager";
  private static final String EJB_GEOLOCATION_SERVICE_LOCAL_JNDI = "ejb/GeolocationService";

  @EJB
  private ShippingServiceManager shippingServiceManager;

  @EJB
  private GeolocationService geolocationService;

  @EJB
  private GeolocationManager geolocationManager;

  @EJB
  private ParcelManager parcelManager;

  @EJB
  private ParcelEventManager parcelLogEventManager;

  @EJB
  private ShipmentManager shipmentManager;

  @EJB
  private ShipmentService shipmentService;

  @EJB
  private ParcelService parcelService;

  @EJB
  private EventService eventService;


  @SuppressWarnings("unchecked") // Because of forced cast on (T).
  public static <T> T lookup(Class<T> ejbClass)
  {
    String jndiName = "java:comp/env/" + ejbClass.getSimpleName();

    try
    {
      // Do not use ejbClass.cast(). It will fail on local/remote interfaces.
      return (T) new InitialContext().lookup(jndiName);
    }
    catch (NamingException e)
    {
      throw new IllegalArgumentException(
          String.format("Cannot find EJB class %s in JNDI %s", ejbClass, jndiName), e);
    }
  }

  public GeolocationService getGeolocationService()
  {
    if (geolocationService == null)
    {
      geolocationService = (GeolocationService) refreshEJB(EJB_GEOLOCATION_SERVICE_LOCAL_JNDI);
    }
    return geolocationService;
  }

  public GeolocationManager getGeolocationManager()
  {
    if (geolocationManager == null)
    {
      geolocationManager = (GeolocationManager) refreshEJB(EJB_GEOLOCATION_MANAGER_LOCAL_JNDI);
    }
    return geolocationManager;
  }

  public ParcelManager getParcelManager()
  {
    if (parcelManager == null)
    {
      parcelManager = (ParcelManager) refreshEJB(EJB_PARCEL_MANAGER_LOCAL_JNDI);
    }
    return parcelManager;
  }

  public ParcelEventManager getParcelEventManager()
  {
    if (parcelLogEventManager == null)
    {
      parcelLogEventManager = (ParcelEventManager) refreshEJB(EJB_PARCEL_LOG_EVENT_MANAGER_LOCAL_JNDI);
    }
    return parcelLogEventManager;
  }

  public ShipmentManager getShipmentManager()
  {
    if (shipmentManager == null)
    {
      shipmentManager = (ShipmentManager) refreshEJB(EJB_SHIPMENT_MANAGER_LOCAL_JNDI);
    }
    return shipmentManager;
  }

  public ShippingServiceManager getShippingServiceManager()
  {
    if (shippingServiceManager == null)
    {
      shippingServiceManager = (ShippingServiceManager) refreshEJB(EJB_SHIPPING_SERVICE_MANAGER_LOCAL_JNDI);
    }
    return shippingServiceManager;
  }

  public ShipmentService getShipmentService()
  {
    if (shipmentService == null)
    {
      shipmentService = (ShipmentService) refreshEJB(EJB_SHIPMENT_SERVICE_LOCAL_JNDI);
    }
    return shipmentService;
  }

  public ParcelService getParcelService()
  {
    if (parcelService == null)
    {
      parcelService = (ParcelService) refreshEJB(EJB_PARCEL_SERVICE_LOCAL_JNDI);
    }
    return parcelService;
  }

  public EventService getEventService()
  {
    if (eventService == null)
    {
      eventService = (EventService) refreshEJB(EJB_EVENT_SERVICE_LOCAL_JNDI);
    }
    return eventService;
  }

  public static Object refreshEJB(String jndi)
  {
    if (USE_LOCAL)
    {
      jndi = "java:comp/env/" + jndi;
    }

    logger.info("////////////// looking up JNDI:" + jndi);

    InitialContext ctx = null;
    try
    {
      ctx = new InitialContext();
      return ctx.lookup(jndi);
    }
    catch (NamingException e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public HttpSession getHttpSession()
  {
    return (HttpSession) getFacesContext().getExternalContext().getSession(false);
  }

  public FacesContext getFacesContext()
  {
    FacesContext ctx = FacesContext.getCurrentInstance();

    //ctx.get

    return ctx;
  }

  public void setParcelManager(ParcelManager parcelManager)
  {
    this.parcelManager = parcelManager;
  }

  public void setShipmentManager(ShipmentManager ShipmentManager)
  {
    this.shipmentManager = ShipmentManager;
  }

  public void setShipmentService(ShipmentService shipmentService)
  {
    this.shipmentService = shipmentService;
  }

  public void setParcelService(ParcelService parcelService)
  {
    this.parcelService = parcelService;
  }

  public void setEventService(EventService eventService)
  {
    this.eventService = eventService;
  }

}
