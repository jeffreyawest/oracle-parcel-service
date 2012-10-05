package com.oracle.demo.ops.web.beans.coherence;

import com.tangosol.net.*;

import java.util.ArrayList;
import java.util.Enumeration;
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
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Aug 9, 2011
 * Time: 9:59:01 AM
 */
public class CacheStatisticsBean
{
  private static final Logger logger = Logger.getLogger(CacheStatisticsBean.class.getName());

  private String cacheName;
  private int size;
  private String cacheServiceName;
  private String cacheServiceType;
  private Member oldestMember;
  private List<Member> serviceMembers;
  private List<String> cacheNames;

  public CacheStatisticsBean(String pCacheName)
  {
    logger.fine("Creating statistics for cache name=["+pCacheName+"]");
    cacheName = pCacheName;
    NamedCache cache = null;

    try
    {
      cache = getCache(pCacheName);
      if(cache != null)
      {
        size = cache.size();
        CacheService service = cache.getCacheService();

        cacheNames = new ArrayList<String>(7);

        Enumeration caches = service.getCacheNames();

        while(caches.hasMoreElements())
        {
          cacheNames.add((String) caches.nextElement());
        }

        ServiceInfo info = service.getInfo();

        cacheServiceName = info.getServiceName();
        cacheServiceType = info.getServiceType();
        oldestMember = info.getOldestMember();
        serviceMembers = new ArrayList<Member>();
        serviceMembers.addAll(info.getServiceMembers());
      }
      else
      {
        size = -1;
        cacheServiceName = "Not Found";
        cacheServiceType = "Not Found";
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      size = -2;
      cacheServiceName = "ERROR: "+e.getMessage();
      cacheServiceType = "ERROR: "+e.getMessage();
    }

  }

  private static NamedCache getCache(String cacheName)
  {
    CacheFactory.ensureCluster();
    return CacheFactory.getCache(cacheName);
  }

  public String getCacheName()
  {
    return cacheName;
  }

  public String getCacheServiceName()
  {
    return cacheServiceName;
  }

  public int getSize()
  {
    return size;
  }

  public String getCacheServiceType()
  {
    return cacheServiceType;
  }

  public Member getOldestMember()
  {
    return oldestMember;
  }

  public List<Member> getServiceMembers()
  {
    return serviceMembers;
  }

  public List<String> getCacheNames()
  {
    return cacheNames;
  }

  public void setCacheNames(List<String> cacheNames)
  {
    this.cacheNames = cacheNames;
  }
}
