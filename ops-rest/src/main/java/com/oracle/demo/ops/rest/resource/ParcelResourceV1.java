package com.oracle.demo.ops.rest.resource;
/**
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
 * **************************************************************************** */

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.ParcelStatus;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.services.ejb.EventService;
import com.oracle.demo.ops.services.ejb.ParcelService;
import com.oracle.demo.ops.services.ejb.ShipmentService;
import java.util.Calendar;

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
@Path("parcel")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ParcelResourceV1
{
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

  public ParcelResourceV1()
  {

  }

  @PostConstruct
  public void foo()
  {
  }

  @GET
  @Path("/ping")
  public String ping()
  {
    return "pong";
  }

  @GET
  @Path(value = "/validate")
  @Produces(MediaType.TEXT_HTML)
  public Response testParcelManagerAvailable()
  {
    boolean error = false;

    StringBuilder errorMessage = new StringBuilder();

    try
    {
      System.out.println(Constants.CONNECTION_FACTORY_FOREIGN_JNDI);

      if (parcelManager == null)
      {
        error = true;
        errorMessage.append("Parcel Manager EJB is NULL!  ");
      }

      if (eventService == null)
      {
        error = true;
        errorMessage.append("Event Client EJB is NULL!! ");
      }
    }
    catch (Exception e)
    {
      return Response.status(500).type(MediaType.APPLICATION_JSON).entity(e).build();
    }

    if (error)
    {
      return Response.status(500).type(MediaType.APPLICATION_JSON).entity(new Exception(errorMessage.toString())).build();
    }

    return Response.status(200).type(MediaType.TEXT_HTML).entity("<html><body>OK!</body></html>").build();
  }

  /* ****************************************************************************************
    LIST ALL PARCELS
   **************************************************************************************** */
  private Collection<Parcel> listParcels()
  {
    return parcelManager.findAllParcels();
  }

  @GET
  @Path("/")
  public Collection<Parcel> listParcels_json()
  {
    return listParcels();
  }

  /* ****************************************************************************************
    GET PARCEL
   **************************************************************************************** */

  private Parcel getParcelById(int id)
  {
    return parcelManager.getParcelById(id);
  }

  // http://localhost:7001/app/parcel/1
  @GET
  @Path("{id}")
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Parcel getParcelById_default(@PathParam("id") int id)
  {
    return getParcelById(id);
  }

  @GET
  @Path("{id}.json")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public Parcel getParcelById_json(@PathParam("id") int id)
  {
    return getParcelById(id);
  }

  @GET
  @Path("{id}.xml")
  @Produces(MediaType.TEXT_XML)
  public Parcel getParcelById_xml(@PathParam("id") int id)
  {
    return getParcelById(id);
  }

  /* ****************************************************************************************
    PARCEL LOG
   **************************************************************************************** */

  private List<ParcelEvent> getParcelEvents(int id)
  {
    return parcelEventManager.getParcelLogByParcelId(id);
  }

  @GET
  @Path("{id}/log")
  public List<ParcelEvent> getParcelEvents_default(@PathParam("id") int id)
  {
    return getParcelEvents(id);
  }

  @GET
  @Path("{id}/log.json")
  public List<ParcelEvent> getParcelEvents_json(@PathParam("id") int id)
  {
    return getParcelEvents(id);
  }

  @GET
  @Path("{id}/log.xml")
  @Produces(MediaType.APPLICATION_XML)
  public List<ParcelEvent> getParcelEvents_xml(@PathParam("id") int id)
  {
    return getParcelEvents(id);
  }

  
  
  @GET
  @Path("all/log")
  public List<ParcelEvent> getAllParcelEvents(@PathParam("path") String path)
  {
    List<ParcelEvent> list = parcelEventManager.getAllParcelEvents();

    return list;
  }

  @POST
  @Path("/log/{id}/add.json")
  public Response postLogEvent2(@PathParam("id") int id, ParcelEvent event)
  {
    if (event.getParcelId() == id)
    {
      event.setParcelId(id);
      eventService.sendEventToQueue(event);
      return Response.status(200).type(MediaType.APPLICATION_JSON).build();
    }
    else
    {
      return Response.status(500).type(MediaType.APPLICATION_JSON).entity(new Exception("ID on object [" + event.getParcelId() + "] does not match id on URL [" + id + "]")).build();
    }
  }

  @POST
  @Path("/log/prototype.json")
  public Response parcelLogPrototype()
  {
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(new ParcelEvent()).build();
  }
}

 