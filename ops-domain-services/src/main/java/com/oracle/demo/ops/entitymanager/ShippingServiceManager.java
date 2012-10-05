package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.domain.ShippingServiceName;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

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
 * Date: Sep 15, 2011
 * Time: 10:03:33 AM
 */

@LocalBean
@Stateless(name = "ShippingServiceManagerBean", mappedName = "ejb/ShippingServiceManager")
public class ShippingServiceManager implements Serializable
{
  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  boolean initialized = false;

  @SuppressWarnings(value = "unchecked")
  public List<ShippingService> findAllShippingServices()
  {
    warmCache();
    return em.createNamedQuery("ShippingService.findall").getResultList();
  }

  public void addShippingService(ShippingService newShippingService)
  {
    em.persist(newShippingService);
  }

  public void updateService(ShippingService selectedShippingService)
  {
    em.merge(selectedShippingService);
  }

  public boolean warmCache()
  {
    if (!initialized)
    {
      try
      {
        insertOrUpdateShippingService(ShippingServiceName.BASIC, "Basic Service", 10.00, 3);
        insertOrUpdateShippingService(ShippingServiceName.EXPEDITED, "Expedited Service", 25, 2);
        insertOrUpdateShippingService(ShippingServiceName.OVERNIGHT, "Overnight Service", 100, 1);
        initialized = true;
        em.flush();
      }
      catch (Exception e)
      {
//        e.printStackTrace();
      }
    }

    return true;
  }

  private void insertOrUpdateShippingService(ShippingServiceName pName, String pDescrition, double pCost, int pDeliveryEstimate)
  {
    ShippingService service = findShippingServiceByName(pName);

    if (service == null)
    {
      service = new ShippingService();
      service.setName(pName);
      service.setCost(pCost);
      service.setDescription(pDescrition);
      service.setDeliveryEstimate(pDeliveryEstimate);
      em.merge(service);
    }

  }

  public ShippingService findShippingServiceByName(ShippingServiceName pName)
  {
    return em.find(ShippingService.class, pName);
  }

  public ShippingService findShippingServiceByName(String pName)
  {
    if(pName == null)
    {
      return null;
    }

    return findShippingServiceByName(ShippingServiceName.fromValue(pName));
  }
}
