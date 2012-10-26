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

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.services.ejb.EventService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

/**
 * REST Web Service
 *
 * @author jeffrey.west
 */
@Stateless
@Path("2/parcel")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ParcelResourceV2
    extends RestResource
{
  @Context
  private UriInfo context;

  @EJB
  private ParcelManager parcelManager;

  @EJB
  private ParcelEventManager parcelEventManager;

  @EJB
  private EventService eventService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/ping")
  public Response ping()
  {
    return buildResponse(JSON_FORMAT, "pong");
  }

  @GET
  @Path(value = "/parcelManager")
  @Produces("text/html")
  public String testParcelManagerAvailable()
  {
    return String.format("ParcelManager: null:%s , class:%s",
                         parcelManager == null,
                         parcelManager == null ? "null" : parcelManager.getClass().getName());
  }

  @GET
  @Path("/all{path:.*}")
  public Response listParcels(@PathParam("path") String path)
  {
    Collection<Parcel> Parcels = parcelManager.findAllParcels();

    if (getFormat(path) == JSON_FORMAT)
    {
      return Response.status(200).type(MediaType.APPLICATION_JSON).entity(Parcels).build();
    }
    else
    {
      GenericEntity entity = new GenericEntity<Collection<Parcel>>(Parcels)
      {
      };
      return Response.status(200).type(MediaType.APPLICATION_XML).entity(entity).build();
    }
  }

  @GET
  @Path("{id}{path:.*}")
  public Response getParcelById(@PathParam("id") int id,
                                @PathParam("path") String path)
  {
    return buildResponse(getFormat(path), parcelManager.getParcelById(id));
  }

  @PUT
  @Path("/log/add.json")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ParcelEvent putLogEvent(ParcelEvent event)
  {
    eventService.sendEventToQueue(event);
    return event;
  }

  @POST
  @Path("/log/add.json")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ParcelEvent postLogEvent(ParcelEvent event)
  {
    eventService.sendEventToQueue(event);
    return event;
  }

  @POST
  @Path("/log/{id}/add.json")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response postLogEvent2(@PathParam("id") int id, ParcelEvent event)
  {
//    if (event.getParcelId() == id)
//    {
    event.setParcelId(id);
    eventService.sendEventToQueue(event);
    return Response.status(200).type(MediaType.APPLICATION_JSON).build();
//    }
//    else
//    {
//      return Response.status(500).type(MediaType.APPLICATION_JSON).entity(new Exception("ID does not match")).build();
//    }
  }

  @GET
  @Path("log/{id}{path:.*}")
  public Response getParcelEvents_default(@PathParam("id") int id,
                                          @PathParam("path") String path)
  {
    List<ParcelEvent> list = parcelEventManager.getParcelLogByParcelId(id);

    if (getFormat(path) == JSON_FORMAT)
    {
      return Response.status(200).type(MediaType.APPLICATION_JSON).entity(list).build();
    }
    else
    {
      GenericEntity entity = new GenericEntity<List<ParcelEvent>>(list)
      {
      };
      return Response.status(200).type(MediaType.APPLICATION_XML).entity(entity).build();
    }
  }

  @GET
  @Path("log/list.json")
  public Response getAllParcelEvents(@PathParam("path") String path)
  {
    List<ParcelEvent> list = parcelEventManager.getAllParcelEvents();

    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(list).build();
  }
}
