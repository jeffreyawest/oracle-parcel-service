package com.oracle.demo.ops.entitymanager;
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
 */
import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelStatus;
import com.oracle.demo.ops.domain.Shipment;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Stateless (name = "ShipmentManagerBean", mappedName = "ejb/ShipmentManager")
@LocalBean
public class ShipmentManager implements Serializable
{
  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public Shipment findShipmentById(int id)
  {
    return em.find(Shipment.class, id);
  }

  public Shipment findShipmentByExternalId(String id)
  {
    return (Shipment) em.createNamedQuery("Shipment.findByExternalReferenceId").setParameter("id", id).getSingleResult();
  }

  @SuppressWarnings(value = "unchecked")
  public Collection<Shipment> findAllShipments()
  {
    return em.createNamedQuery("Shipment.findAll").getResultList();
  }

  @SuppressWarnings(value = "unchecked")
  public Collection<Shipment> findAllShipmentsPaged(int pFirstResult, int pMaxResults)
  {
    Query q = em.createNamedQuery("Shipment.findAll");
    q.setFirstResult(pFirstResult);
    q.setMaxResults(pMaxResults);

    return q.getResultList();
  }

  public Shipment createShipment(Shipment shipment)
  {

    List<Parcel> parcels = shipment.getParcels();

    for (Parcel parcel : parcels)
    {
      em.persist(parcel);

      com.oracle.demo.ops.domain.ParcelEvent event = new com.oracle.demo.ops.domain.ParcelEvent();
      event.setParcelId(parcel.getId());
      event.setLocation("ORIGIN");
      event.setMessage("New Parcel");
      event.setEventDate(Calendar.getInstance());
      event.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);

      em.merge(event);
    }

    em.persist(shipment);
    return shipment;
  }


}