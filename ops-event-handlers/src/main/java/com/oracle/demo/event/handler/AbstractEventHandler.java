package com.oracle.demo.event.handler;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;

import javax.ejb.EJB;
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
 * Time: 10:19:26 AM
 */
public abstract class AbstractEventHandler
{
  private static final Logger logger = Logger.getLogger(AbstractEventHandler.class.getName());

  @EJB(beanName = "ParcelManagerBean")
  protected ParcelManager parcelManager;

  @EJB(beanName="ParcelEventManagerBean")
  protected ParcelEventManager parcelEventManager;

  @EJB(beanName="ShipmentManagerBean")
  protected ShipmentManager shipmentManager;

  private long messageCount;
  private long exceptionCount;
  
  private String lastException;

  public void handleEvent(ParcelEvent pEvent)
      throws
      Exception
  {
    messageCount++;

    handleEventInternal(pEvent);
  }

  protected abstract void handleEventInternal(ParcelEvent pEvent)
      throws
      Exception;

  public void setParcelManager(ParcelManager parcelManager)
  {
    this.parcelManager = parcelManager;
  }

  public void setShipmentManager(ShipmentManager shipmentManager)
  {
    this.shipmentManager = shipmentManager;
  }

  public void setParcelEventManager(ParcelEventManager parcelEventManager)
  {
    this.parcelEventManager = parcelEventManager;
  }

  public long getMessageCount()
  {
    return messageCount;
  }

  public void setMessageCount(long messageCount)
  {
    this.messageCount = messageCount;
  }
}
