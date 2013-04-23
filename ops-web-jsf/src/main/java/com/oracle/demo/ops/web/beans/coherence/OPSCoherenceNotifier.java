package com.oracle.demo.ops.web.beans.coherence;

import com.oracle.demo.ops.domain.Shipment;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.NamedCache;

import java.util.Map;
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
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************
 */

public class OPSCoherenceNotifier {

	private static final Logger logger = Logger.getLogger(OPSCoherenceNotifier.class.getName());
	private static final String CACHE_NAME = "ops-notification-cache";

	public static void publish(Shipment shipment) {
		Cluster cluster = CacheFactory.ensureCluster();
/*
		logger.info("----------------------------------------------------");
		Service svc = cluster.ensureService("OPSDistributedCache"
			, CacheService.TYPE_DISTRIBUTED);
		logger.log(Level.INFO, "Service name: " + svc.getInfo().getServiceName());
		logger.log(Level.INFO, "Serializer name: " + svc.getSerializer().getClass().getName());
*/
		NamedCache cache = CacheFactory.getCache(CACHE_NAME);
		logger.log(Level.INFO, "Publishing shipment: id = {0}", shipment.getId());
		logger.log(Level.INFO, "Cache name: " + cache.getCacheName());
		logger.log(Level.INFO, "Service name: " + cache.getCacheService().getInfo().getServiceName());
		cache.put(shipment.getId(), shipment);
		logger.log(Level.INFO, "After adding to cache");
	}

	public static void publish(Map<Integer, Shipment> shipmentList) {
		CacheFactory.ensureCluster();
		NamedCache cache = CacheFactory.getCache(CACHE_NAME);
		logger.log(Level.INFO, "Bulk publishing shipments: {0}", shipmentList.size());
		cache.putAll(shipmentList);
	}
}
