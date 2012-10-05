package com.oracle.demo.cache.trigger;

import com.oracle.demo.ops.domain.Shipment;
import com.tangosol.util.MapTrigger;

import java.io.Serializable;
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
 * Time: 8:44:53 AM
 */
public class ShipmentMapTrigger
    implements MapTrigger,
               Serializable
{
  private static final Logger logger = Logger.getLogger(ShipmentMapTrigger.class.getName());

  public void process(Entry entry)
  {

    Shipment shipment = (Shipment) entry.getValue();

    logger.info("Shipment Trigger! Entry ID=[" + entry.getKey() + "] Shipment ID=[" + shipment.getId() + "]");
  }
}
