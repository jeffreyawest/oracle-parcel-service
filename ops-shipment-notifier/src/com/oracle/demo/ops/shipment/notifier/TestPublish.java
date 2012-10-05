/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import com.oracle.demo.ops.domain.Shipment;
import com.tangosol.net.NamedCache;

/**
 * @author cmlee
 */
public class TestPublish
{

  public static void main(String... args) throws Exception
  {

    Shipment ship = Utility.randomShipment();

    NamedCache cache = Utility.getCache(Constants.CACHE_NAME);

    cache.put(ship.getId(), ship);
  }
}
