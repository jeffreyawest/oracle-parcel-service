package com.oracle.demo.event.handler.parcelLog;

import com.oracle.demo.event.handler.AbstractEventHandler;
import com.oracle.demo.ops.domain.ParcelEvent;

import javax.inject.Named;

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