import com.tangosol.net.*;

import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/30/12
 * Time: 11:34 PM
 */
public class CacheTester
{
  public static void main(String[] args)
  {
    try
    {
      Cluster cohCluster = CacheFactory.ensureCluster();
      Enumeration serviceNames = cohCluster.getServiceNames();

      while (serviceNames.hasMoreElements())
      {
        String name = (String) serviceNames.nextElement();
        System.out.println("Service Name: " + name);
        ServiceInfo info = cohCluster.getServiceInfo(name);
        Service service = cohCluster.getService(name);
        System.out.println("Type: " + info.getServiceType());
      }

      System.out.println("Connected to cluster: " + cohCluster.getClusterName());
      NamedCache cache = CacheFactory.getCache("geolocation");

      if (cache == null)
      {
        System.out.println("NULL CACHE!");
      }
      else
      {
        System.out.println("CACHE NOT NULL");
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
