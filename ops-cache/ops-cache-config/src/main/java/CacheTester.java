import com.tangosol.net.*;

import java.util.Enumeration;

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
