package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.ParcelStatus;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

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
 * Date: Sep 23, 2011
 * Time: 10:29:41 AM
 */

@Stateless(name = "ParcelEventManagerBean", mappedName = "ejb/ParcelEventManager")
@LocalBean
public class ParcelEventManager implements Serializable
{
  private static final Logger logger = Logger.getLogger(ParcelManager.class.getName());

  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public ParcelEvent addParcelEvent(final com.oracle.demo.ops.domain.ParcelEvent pEvent)
  {
    logger.finest("Adding ParcelEvent: " + pEvent);
    em.persist(pEvent);
    return pEvent;
  }

  @SuppressWarnings(value = "unchecked")
  public List<com.oracle.demo.ops.domain.ParcelEvent> getParcelLogByParcelId(int pParcelId)
  {
    logger.finest("ParcelManager.getParcelLogByParcelId(int=[" + pParcelId + "])");
    return em.createNamedQuery("ParcelEvent.findByParcelId").setParameter("id", pParcelId).getResultList();
  }

  @SuppressWarnings(value = "unchecked")
  public List<com.oracle.demo.ops.domain.ParcelEvent> getParcelLogByParcelIdPaged(int pParcelId, int pFirstResult, int pMaxResults)
  {
    Query q = em.createNamedQuery("ParcelEvent.findByParcelId");
    q.setParameter("id", pParcelId);
    q.setFirstResult(pFirstResult);
    q.setMaxResults(pMaxResults);
    return q.getResultList();
  }

  @SuppressWarnings(value = "unchecked")
  public List<com.oracle.demo.ops.domain.ParcelEvent> getAllParcelEvents()
  {
    return em.createNamedQuery("ParcelEvent.findAll").getResultList();
  }

  @SuppressWarnings(value = "unchecked")
  public List<com.oracle.demo.ops.domain.ParcelEvent> getAllParcelEventsPaged(int pFirstResult, int pMaxResults)
  {
    Query q = em.createNamedQuery("ParcelEvent.findAll");
    q.setFirstResult(pFirstResult);
    q.setMaxResults(pMaxResults);
    return q.getResultList();
  }

  public ParcelEvent getParcelEventById(final int id)
  {
    logger.info("ParcelEventManager.getParcelEventById(int=[" + id + "])");

    return em.find(ParcelEvent.class, id);
  }

  @SuppressWarnings(value = "unchecked")
  public List<ParcelEvent> getParcelEventsByLocation(final String inputParcelLocation)
  {
    logger.info("ParcelEventManager.getParcelEventsByLocation(String=[" + inputParcelLocation + "])");

    Query q = em.createQuery("Select event from ParcelEvent event where event.location=?1");
    q.setParameter(1, inputParcelLocation);

    return q.getResultList();
  }

  public void billingInformationReceived(Parcel parcel)
  {
    ParcelEvent parcelEvent = new ParcelEvent();
    parcelEvent.setEventDate(Calendar.getInstance());
    parcelEvent.setLocation("United States");
    parcelEvent.setMessage("Billing Information Received");
    parcelEvent.setParcelId(parcel.getId());
    parcelEvent.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);

    em.persist(parcelEvent);
  }

}
