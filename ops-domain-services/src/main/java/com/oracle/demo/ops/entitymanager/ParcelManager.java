package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelStatus;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
 */
@Stateless (name = "ParcelManagerBean", mappedName = "ejb/ParcelManager")
@LocalBean
public class ParcelManager implements java.io.Serializable
{
  private static final Logger logger = Logger.getLogger(ParcelManager.class.getName());

  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public Parcel updateParcelStatusById(int id, ParcelStatus status)
  {
    logger.finest("ParcelManager.updateParcelStatusById(int=[" + id + "], ParcelStatus=[" + status + "])");

    Parcel parcel = em.find(Parcel.class, id);

    if (parcel == null)
    {
      System.out.println("Unable to find parcel!!");
      // TODO: Throw  exception or just return null?
      return null;
    }

    parcel.setParcelStatus(status);

    return parcel;
  }
  @SuppressWarnings(value="unchecked")
  public List<Parcel> findAllParcels()
  {
    logger.finest("ParcelManager.findAllParcels() - enter");
    return em.createNamedQuery("Parcel.findAll").getResultList();
  }

  public List<Parcel> findByShipmentId(int pShipmentId)
  {
    Query q = em.createNamedQuery("Parcel.findByShipmentId");
    q.setParameter("shipmentId", pShipmentId);

    return q.getResultList();
  }

  public Parcel getParcelById(int parcelId)
  {
    logger.finest(this.getClass() + " Method=[getParcelById] id=[" + parcelId + "]");
    Parcel parcel = null;

    try
    {
      parcel = em.find(Parcel.class, parcelId);
    }
    catch (Exception e)
    {
      logger.log(Level.WARNING, "Error retrieving parcel with ID=[" + parcelId + "]: " + e.getMessage(), e);
      e.printStackTrace();
    }

    if(parcel == null)
    {
      logger.log(Level.WARNING, "Unable to find parcel with id=[" + parcelId + "]");
    }
    else
    {
      logger.info("Found parcel with id=[" + parcelId + "]");     
    }

    return parcel;
  }

  public Parcel createParcel(Parcel parcel)
  {
    em.persist(parcel);

    com.oracle.demo.ops.domain.ParcelEvent event = new com.oracle.demo.ops.domain.ParcelEvent();
    event.setParcelId(parcel.getId());
    event.setLocation("United States");
    event.setMessage("Billing Information Received");
    event.setEventDate(Calendar.getInstance());
    event.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
    em.persist(event);

    return parcel;
  }

  public EntityManager getEm()
  {
    return em;
  }

  public void setEm(EntityManager em)
  {
    this.em = em;
  }

  @SuppressWarnings(value="unchecked")
  public List<Parcel> queryParcelsByContents(String inputParcelContents)
  {
    return em.createQuery("Select p from Parcel p where p.contents=?1").setParameter(1, inputParcelContents).getResultList();
  }

}
