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
package com.oracle.demo.ops.rest.v2;

import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.services.ejb.EventService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
 * REST Web Service
 *
 * @author sbutton
 */
@Stateless
@Path("2/shipment")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class ShipmentResourceV2
        extends RestResource
{
  @Context
  private UriInfo context;

  @EJB
  private ParcelManager parcelManager;

  @EJB
  private ShipmentManager shipmentManager;

  @EJB
  private ParcelEventManager parcelEventManager;

  @EJB
  private EventService eventService;

  @GET
  @Path("/all{path:.*}")
  public Response listShipments(@PathParam("path") String path)
  {
    Collection<Shipment> shipments = shipmentManager.findAllShipments();

    if (getFormat(path) == JSON_FORMAT)
    {
      return Response.status(200).type(MediaType.APPLICATION_JSON).entity(shipments).build();
    }
    else
    {
      GenericEntity entity = new GenericEntity<Collection<Shipment>>(shipments)
      {
      };
      return Response.status(200).type(MediaType.APPLICATION_XML).entity(entity).build();
    }
  }

  @GET
  @Path("{id}{path:.*}")
  public Response getShipmentById(@PathParam("id") int id,
                                  @PathParam("path") String path)
  {
    return buildResponse(getFormat(path), shipmentManager.findShipmentById(id));
  }

  @GET
  @Path("/prototype.json")
  @Produces("application/json")
  public Shipment getShipmentPrototype()
  {
    Shipment shipment = new Shipment();
    Address blankAddr = new Address();

    blankAddr.setAddressee("");
    blankAddr.setAddressLine1("");
    blankAddr.setAddressLine2("");
    blankAddr.setCity("");
    blankAddr.setState("");
    blankAddr.setPostalCode("");

    shipment.setFromAddress(blankAddr);
    shipment.setToAddress(blankAddr);
    shipment.getParcels().clear();

    return shipment;
  }

  /**
   * PUT method for updating or creating an instance of ParcelServiceResource
   *
   * @param shipment representation for the resource
   * @return an HTTP response with content of the updated or created resource.
   */

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Shipment putJSON(Shipment shipment)
  {
    return shipmentManager.createShipment(shipment);
  }

  /**
   * POST method for updating or creating an instance of ParcelServiceResource
   *
   * @param shipment representation for the resource
   * @return an HTTP response with content of the updated or created resource.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Shipment postJSON(Shipment shipment)
  {
    return shipmentManager.createShipment(shipment);
  }
}
