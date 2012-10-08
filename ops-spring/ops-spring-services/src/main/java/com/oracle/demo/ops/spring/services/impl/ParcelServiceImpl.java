package com.oracle.demo.ops.spring.services.impl;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.domain.GetParcelByIdResponse;
import com.oracle.demo.ops.domain.GetParcelEventLogRequest;
import com.oracle.demo.ops.domain.GetParcelEventLogResponse;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.WebServiceResponseHeader;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.services.ejb.EventService;

import java.util.List;

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
 * Date: Sep 23, 2011
 * Time: 11:55:58 AM
 */

public class ParcelServiceImpl
{
  private ParcelManager parcelManager;

  private ParcelEventManager parcelEventManager;

  private ShipmentManager shipmentManager;

  private EventService eventService;


  public ParcelServiceImpl()
  {
  }

  public GetParcelByIdResponse getParcelById(GetParcelByIdRequest pRequest)
  {
    //System.out.println("ParcelServiceImpl.getParcelById");
    GetParcelByIdResponse resp = new GetParcelByIdResponse();
    resp.setResponseHeader(new WebServiceResponseHeader());
    resp.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    resp.setParcel(parcelManager.getParcelById(pRequest.getParcelId()));
    return resp;
  }

  public AddParcelLogEventJMSPROXYResponse addParcelEventJMSPROXY(AddParcelLogEventJMSPROXYRequest pRequest)
  {
    eventService.sendEventToQueue(pRequest.getParcelLogEvent());

    AddParcelLogEventJMSPROXYResponse resp = new AddParcelLogEventJMSPROXYResponse();
    resp.setResponseHeader(new WebServiceResponseHeader());
    resp.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    resp.setParcelLogEvent (pRequest.getParcelLogEvent ());
    return resp;
  }

  public void publishParcelEvent(ParcelEvent event)
  {
    eventService.sendEventToQueue(event);
  }

  public GetParcelEventLogResponse getParcelEvents(GetParcelEventLogRequest pRequest)
  {
    //System.out.println("ParcelServiceImpl.getParcelEvents");

    GetParcelEventLogResponse resp = new GetParcelEventLogResponse();
    List<ParcelEvent> list = parcelEventManager.getParcelLogByParcelId(pRequest.getParcelId());
    resp.setResponseHeader(new WebServiceResponseHeader());
    resp.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    resp.getParcelLogEvents().addAll(list);
    return resp;
  }

  public ParcelManager getParcelManager()
  {
    return parcelManager;
  }

  public void setParcelManager(ParcelManager parcelManager)
  {
    this.parcelManager = parcelManager;
  }

  public ShipmentManager getShipmentManager()
  {
    return shipmentManager;
  }

  public void setShipmentManager(ShipmentManager shipmentManager)
  {
    this.shipmentManager = shipmentManager;
  }

  public EventService getEventService()
  {
    return eventService;
  }

  public void setEventService(EventService eventService)
  {
    eventService = eventService;
  }

  public ParcelEventManager getParcelEventManager()
  {
    return parcelEventManager;
  }

  public void setParcelEventManager(ParcelEventManager parcelEventManager)
  {
    this.parcelEventManager = parcelEventManager;
  }
}
