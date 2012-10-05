package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.PostalGeolocation;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;
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
 * Date: Sep 23, 2011
 * Time: 10:29:41 AM
 */
@Stateless (name = "GeolocationManagerBean", mappedName = "ejb/GeolocationManager")
@LocalBean
public class GeolocationManager implements Serializable
{
  private static final Logger logger = Logger.getLogger(GeolocationManager.class.getName());

  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public PostalGeolocation findByPostalCode(final String pPostalCode)
  {
    return em.find(PostalGeolocation.class, pPostalCode);
  }

  public int getGeoDataSize()
  {
    return em.createQuery("Select geo from PostalGeolocation geo order by geo.postalCode")
             .getResultList().size();
  }

  public List<PostalGeolocation> findByCityState(String pCity, String pState)
  {
    Query q = em.createNamedQuery("PostalGeolocation.findByCityState");

    q.setParameter("city", pCity);
    q.setParameter("state", pState);

    return q.getResultList();
  }

  public void addNewRecord(PostalGeolocation pGeo)
  {
    em.persist(pGeo);
  }

  public void loadDataFromStream(InputStream inputStream)
  {
    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      System.out.println(reader.readLine());

      String line = null;
      while ((line = reader.readLine()) != null)
      {
        line = line.replaceAll("\"", "");

        StringTokenizer st = new StringTokenizer(line, ",");

        if (st.countTokens() != 7)
        {
          continue;
        }
        try
        {
          //"zip","city","state","latitude","longitude","timezone","dst"
          PostalGeolocation geo = new PostalGeolocation();
          geo.setPostalCode(st.nextToken()); //zip
          geo.setCity(st.nextToken()); //city
          geo.setState(st.nextToken()); //state

          String strLat = st.nextToken(); //lat
          geo.setLatitude(Double.parseDouble(strLat));

          String strLng = st.nextToken(); //lat
          geo.setLongitude(Double.parseDouble(strLng)); //lng
          em.persist(geo);
        }
        catch (Exception e)
        {
          System.out.println("Unable to parse line: " + line);
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}