package com.oracle.demo.event.handler.parcelLog;

import com.oracle.demo.event.handler.AbstractEventHandler;
import com.oracle.demo.ops.domain.ParcelEvent;

import javax.inject.Named;

/*
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
 */
@Named
public class UpdateParcelLogEventHandler
    extends AbstractEventHandler
{
  protected void handleEventInternal(ParcelEvent pEvent)
  {
    //System.out.println("UpdateParcelLog.handleEvent");

    parcelEventManager.addParcelEvent(pEvent);
    System.out.println("ParcelEvent! Location=[" + pEvent.getLocation() + "] Message=[" + pEvent.getMessage() + "]");
  }
}