package com.oracle.demo.ops.web.beans.coherence;

import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.web.beans.OPSController;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//import com.oracle.demo.cache.listener.ShipmentMapListener;

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
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Jul 15, 2011
 * Time: 12:58:01 PM
 */
@Named
@SessionScoped
public class CoherenceControllerBean
    extends OPSController
{
  static final long serialVersionUID = 42L;
  private static final Logger logger = Logger.getLogger(CoherenceControllerBean.class.getName());

  private String inputCacheName;
  private List<ShippingService> shippingServicesList = new ArrayList(0);

  @Resource(mappedName = "com.oracle.demo.ops.jdbc.cluster-ds")
  private DataSource dataSource;

  //private ShipmentMapListener shipmentListener;

  public CoherenceControllerBean()
  {
    CacheFactory.ensureCluster();
  }

  public String queryShippingServicesCache()
  {
    logger.info("Querying ShippingService Cache");
    NamedCache cache = CacheFactory.getCache("ShippingService");
    logger.info("Cache Size Before: "+cache.size());

    shippingServicesList.clear();
    shippingServicesList.addAll(getShippingServiceManager().findAllShippingServices());

    logger.info("Cache Size After: "+cache.size());
    return "cacheManagement";
  }

  public String warmShippingServicesCache()
  {
    logger.info("Warming ShippingService Cache");
    NamedCache cache = CacheFactory.getCache("ShippingService");

    logger.info("Cache Size Before warm: "+cache.size());

    getShippingServiceManager().warmCache();

    logger.info("Cache Size After: "+cache.size());

    shippingServicesList.clear();
    shippingServicesList.addAll(getShippingServiceManager().findAllShippingServices());

    return "cacheManagement";
  }

  public List<ShippingService> getShippingServicesList()
  {
    return shippingServicesList;
  }


  public void performCacheLookupActionListener(ActionEvent event)
  {
    logger.info("Doing Cache Lookup name=["+inputCacheName+"]");

    NamedCache selectedCache = CacheFactory.getCache(inputCacheName);

    if(selectedCache == null)
    {
      getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Named Cache \"" + inputCacheName + "\" NOT found!", "Cache not found"));
    }
    else
    {
      getFacesContext().addMessage(null, new FacesMessage("Named Cache \""+inputCacheName+"\" found!"));
    }
  }

  public void addShipmentMapListenerActionListener(ActionEvent event)
  {
    logger.info("Adding Map Listener");
    NamedCache cache = CacheFactory.getCache("Shipment");
    //shipmentListener = new ShipmentMapListener();
    //cache.addMapListener(shipmentListener);

    getFacesContext().addMessage(null, new FacesMessage("Added message listener to Shipment Cache"));
  }

  @PreDestroy
  public void removeShipmentMapListenerOnDestroy()
  {
    try
    {
      removeShipmentMapListenerActionListener(null);
    }
    catch(Exception ignore)
    {
    }
  }

  public void removeShipmentMapListenerActionListener(ActionEvent event)
  {
//    if(shipmentListener != null)
//    {
//      logger.info("Removing Map Listener");
//      NamedCache cache = CacheFactory.getCache("Shipment");
//
//      //cache.removeMapListener(shipmentListener);
//      getFacesContext().addMessage(null, new FacesMessage("Removed message listener to Shipment Cache"));
//    }
  }

  public List<CacheStatisticsBean> getCacheStatistics()
  {
    String[] interesting_caches = {"Shipment",
                                   "Parcel",
                                   "ParcelEvent",
                                   "Address",
                                   "foo-bar",
                                   "ops-notification-cache"
    };

    List<CacheStatisticsBean> list = new ArrayList<CacheStatisticsBean>(interesting_caches.length);

    for(String cacheName : interesting_caches)
    {
      list.add(new CacheStatisticsBean(cacheName));
    }

    return list;
  }

  private NamedCache getCache(String cacheName)
  {
    return CacheFactory.getCache(cacheName);
  }

  public String getInputCacheName()
  {
    return inputCacheName;
  }

  public void setInputCacheName(String inputCacheName)
  {
    this.inputCacheName = inputCacheName;
  }
}
