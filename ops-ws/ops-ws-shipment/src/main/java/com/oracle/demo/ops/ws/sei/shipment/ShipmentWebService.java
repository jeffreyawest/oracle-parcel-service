package com.oracle.demo.ops.ws.sei.shipment;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.services.ejb.ShipmentService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
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

 * User: jeffrey.a.west
 * Date: Feb 18, 2011
 * Time: 6:30:14 PM
 */
@WebService(name = "shipment",
            serviceName = "shipmentService",
            portName = "shipmentPort_jaxws",
            targetNamespace = "http://demo.oracle.com/ops/domain/services/shipment")
/*
when endpointInterface is not used an interface is implicitly defined
 */
public class ShipmentWebService
{
  @EJB
  private ShipmentService shipmentService;

  @WebMethod
  public CreateShipmentResponse createShipment(CreateShipmentRequest pRequest)
  {
    return shipmentService.createShipment(pRequest);
  }

  @WebMethod
  public SendNewShipmentViaJMSResponse sendNewShipmentViaJMS(SendNewShipmentViaJMSRequest pRequest)
  {
    return shipmentService.sendNewShipmentViaJMS(pRequest);
  }

  @WebMethod
  public GetShipmentByIdResponse getShipmentById(GetShipmentByIdRequest pRequest)
  {
    return shipmentService.getShipmentById(pRequest);
  }

  @WebMethod
  public GetShipmentByExternalIdResponse getShipmentByExternalId(GetShipmentByExternalIdRequest pRequest)
  {
    return shipmentService.getShipmentByExternalId(pRequest);
  }

}
