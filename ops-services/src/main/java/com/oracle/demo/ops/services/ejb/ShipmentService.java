package com.oracle.demo.ops.services.ejb;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.entitymanager.ShippingServiceManager;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.Collection;

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
 * Date: Feb 16, 2011
 * Time: 4:39:48 PM
 */

@Stateless (name = "ShipmentServiceBean", mappedName = "ejb/ShipmentService")
@LocalBean
public class ShipmentService implements Serializable
{
  @EJB
  private ParcelManager parcelManager;

  @EJB
  private ShipmentManager shipmentManager;

  @EJB
  private ShippingServiceManager shippingServiceManager;

  @EJB
  private ShipmentJMSClient shipmentJmsSender;

  @EJB
  private EventService eventService;

  public ShipmentService()
  {
  }

  public CreateShipmentResponse createShipment(CreateShipmentRequest pRequest)
  {
    Shipment shipment = shipmentManager.createShipment(pRequest.getShipment());

    if (pRequest.isSimulateEvents())
    {
      eventService.simulateEvents(shipment);
    }

    CreateShipmentResponse response = new CreateShipmentResponse();
    response.setResponseHeader(new WebServiceResponseHeader());
    response.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
    response.setShipment(shipment);
    return response;
  }

  public SendNewShipmentViaJMSResponse sendNewShipmentViaJMS(SendNewShipmentViaJMSRequest pRequest)
  {
    SendNewShipmentViaJMSResponse response = new SendNewShipmentViaJMSResponse();
    response.setResponseHeader(new WebServiceResponseHeader());
    response.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());

    try
    {
      shipmentJmsSender.publishShipment(pRequest.getShipment());
      response.getResponseHeader().setRequestSuccess("TRUE");
      response.setShipment(pRequest.getShipment());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      response.getResponseHeader().setResponseMessage(e.getMessage());
      response.getResponseHeader().setRequestSuccess("FALSE");
    }

    return response;
  }

  public GetShipmentByIdResponse getShipmentById(GetShipmentByIdRequest pRequest)
  {
    Shipment shipment = shipmentManager.findShipmentById(pRequest.getShipmentId());

    if (shipment != null)
    {
      GetShipmentByIdResponse response = new GetShipmentByIdResponse();
      response.setResponseHeader(new WebServiceResponseHeader());
      response.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
//      System.out.println("returning shipment ID=[" + shipment.getId() + "]");
      response.setShipment(shipment);
      return response;
    }
    else
    {
      throw new RuntimeException("Shipment is NULL!");
    }
  }

  public GetShipmentByExternalIdResponse getShipmentByExternalId(GetShipmentByExternalIdRequest pRequest)
  {
    Shipment shipment = shipmentManager.findShipmentByExternalId(pRequest.getExternalReferenceId());

    if (shipment != null)
    {
      GetShipmentByExternalIdResponse response = new GetShipmentByExternalIdResponse();
      response.setResponseHeader(new WebServiceResponseHeader());
      response.getResponseHeader().setRequestHeader(pRequest.getRequestHeader());
      response.setShipment(shipment);
//      System.out.println("returning shipment ID=[" + shipment.getId() + "] externalId=[" + shipment.getExternalReferenceId() + "]");
      return response;
    }
    else
    {
      throw new RuntimeException("Shipment is NULL!");
    }
  }

  public Collection<ShippingService> getAllShippingServices(boolean direct)
  {
    if (direct)
    {
      shippingServiceManager.warmCache();
    }

    return shippingServiceManager.findAllShippingServices();
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

  public ShippingServiceManager getShippingServiceManager()
  {
    return shippingServiceManager;
  }

  public void setShippingServiceManager(ShippingServiceManager shippingServiceManager)
  {
    this.shippingServiceManager = shippingServiceManager;
  }

  public ShipmentJMSClient getShipmentJmsSender()
  {
    return shipmentJmsSender;
  }

  public void setShipmentJmsSender(ShipmentJMSClient shipmentJmsSender)
  {
    this.shipmentJmsSender = shipmentJmsSender;
  }
}