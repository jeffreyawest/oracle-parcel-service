package com.oracle.demo.ops.rest.v1;
/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * *************************************************************************** */

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.services.ejb.EventService;
import com.oracle.demo.ops.services.ejb.ParcelService;
import com.oracle.demo.ops.services.ejb.ShipmentService;
import com.oracle.demo.ops.entitymanager.ShippingServiceManager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.List;

@Stateless
@Path("shipingservice")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ShipingServiceResource {
    @Context
  private UriInfo context;

  @EJB
  private ParcelService parcelService;
  @EJB
  private EventService eventService;
  @EJB
  private ShipmentService shipmentService;
  @EJB
  private ParcelManager parcelManager;
  @EJB
  private ParcelEventManager parcelEventManager;
  @EJB
  private ShipmentManager shipmentManager;
  
   @EJB
  private ShippingServiceManager shippingServiceManager;

  public ShipingServiceResource() {
    }

  @GET
  @Path("/")
  public Collection<ShippingService> getAllShippingServices()
  {
    return shippingServiceManager.findAllShippingServices();
  }

    
}
