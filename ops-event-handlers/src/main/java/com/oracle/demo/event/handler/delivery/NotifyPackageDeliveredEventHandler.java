package com.oracle.demo.event.handler.delivery;

import com.oracle.demo.event.handler.AbstractEventHandler;
import com.oracle.demo.ops.domain.ParcelEvent;

import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
 * Date: Feb 15, 2011
 * Time: 6:13:34 PM
 */
@Named
public class NotifyPackageDeliveredEventHandler
    extends AbstractEventHandler
{
  private static final Logger logger = Logger.getLogger(NotifyPackageDeliveredEventHandler.class.getName());

  protected void handleEventInternal(final ParcelEvent pEvent)
  {
    if (pEvent != null)
    {
      DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
      logger.info("Package ID=["
                  + pEvent.getParcelId()
                  + "] Delivered to location=[" + pEvent.getLocation()
                  + "] at Date=[" + dateFormat.format(pEvent.getEventDate().getTime())
                  + "] with message: " + pEvent.getMessage());
    }
    else
    {
      System.out.println("NotifyPackageDeliveredEventHandler.handleEventInternal: NULL EVENT");
    }
  }
}