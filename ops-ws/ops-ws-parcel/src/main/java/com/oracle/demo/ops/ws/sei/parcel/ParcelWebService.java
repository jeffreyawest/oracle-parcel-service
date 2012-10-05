package com.oracle.demo.ops.ws.sei.parcel;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.services.ejb.ParcelService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

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
 * Date: Feb 18, 2011
 * Time: 6:30:14 PM
 */
@WebService(name = "parcel",
            serviceName = "parcelService",
            portName = "parcelPort_jaxws",            
            targetNamespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class ParcelWebService
{
  @EJB
  private ParcelService parcelService;

  @WebMethod
  public GetParcelEventLogResponse getParcelEventLog(GetParcelEventLogRequest pRequest)
  {
    return parcelService.getParcelEvents(pRequest);
  }

  @WebMethod
  public AddParcelLogEventJMSPROXYResponse addParcelLogEventJMSProxy(AddParcelLogEventJMSPROXYRequest pRequest)
  {
    return parcelService.addParcelEventJMSPROXY(pRequest);
  }

  @WebMethod
  public GetParcelByIdResponse getParcelById(GetParcelByIdRequest pRequest)
  {
    return parcelService.getParcelById(pRequest);
  }
}