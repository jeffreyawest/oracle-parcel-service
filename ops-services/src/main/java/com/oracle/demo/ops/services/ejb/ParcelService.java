package com.oracle.demo.ops.services.ejb;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.Serializable;
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
 * Date: Feb 22, 2011
 * Time: 11:20:29 AM
 */

@Stateless (name = "ParcelServiceBean", mappedName = "ejb/ParcelService")
@LocalBean
public class ParcelService implements Serializable
{
  @EJB
  private ParcelManager parcelManager;

  @EJB
  private ParcelEventManager parcelEventManager;

  @EJB
  private ShipmentManager shipmentManager;

  @EJB
  private EventService EventService;


  public ParcelService()
  {
  }

  public GetParcelByIdResponse getParcelById(GetParcelByIdRequest pRequest)
  {
    //System.out.println("ParcelService.getParcelById");
    GetParcelByIdResponse resp = new GetParcelByIdResponse();
    resp.setResponseHeader(new WebServiceResponseHeader());    
    resp.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    resp.setParcel(parcelManager.getParcelById(pRequest.getParcelId()));
    return resp;
  }

  public AddParcelEventJMSPROXYResponse addParcelEventJMSPROXY(AddParcelEventJMSPROXYRequest pRequest)
  {
    EventService.sendEventToQueue(pRequest.getParcelEvent());

    AddParcelEventJMSPROXYResponse resp = new AddParcelEventJMSPROXYResponse();
    resp.setResponseHeader(new WebServiceResponseHeader());
    resp.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    resp.setParcelEvent(pRequest.getParcelEvent());
    return resp;
  }

  public void publishParcelEvent(com.oracle.demo.ops.domain.ParcelEvent event)
  {
    EventService.sendEventToQueue(event);
  }

  public GetParcelEventLogResponse getParcelEvents(GetParcelEventLogRequest pRequest)
  {
    //System.out.println("ParcelService.getParcelEvents");

    GetParcelEventLogResponse resp = new GetParcelEventLogResponse();
    List<com.oracle.demo.ops.domain.ParcelEvent> list = parcelEventManager.getParcelLogByParcelId(pRequest.getParcelId());
    resp.setResponseHeader(new WebServiceResponseHeader());
    resp.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    resp.getParcelEvents().addAll(list);
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
    return EventService;
  }

  public void setEventService(EventService eventService)
  {
    EventService = eventService;
  }


}
