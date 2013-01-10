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
package com.oracle.demo.ops.rest.resource;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import org.codehaus.jettison.json.JSONObject;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
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
@Path("shipment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipmentResourceV1
{
  @Context
  private UriInfo context;

  @EJB
  protected ParcelManager parcelManager;

  @EJB
  protected ShipmentManager shipmentManager;

  /* ****************************************************************************************
    LIST ALL SHIPMENTS
   **************************************************************************************** */

  private Collection<Shipment> findAllShipments()
  {
    return shipmentManager.findAllShipments();
  }

  @GET
  @Path("/all")
  public Collection<Shipment> listShipments_default()
  {
    return findAllShipments();
  }

  @GET
  @Path("/all.json")
  public Collection<Shipment> listShipments_json()
  {
    return findAllShipments();
  }

  @GET
  @Path("/all.xml")
  @Produces(MediaType.APPLICATION_XML)
  public Collection<Shipment> listShipments_xml()
  {
    return findAllShipments();
  }

  /* ****************************************************************************************
    GET SHIPMENT
   **************************************************************************************** */

  private Shipment getShipmentById(int id)
  {
    return shipmentManager.findShipmentById(id);
  }

  @GET
  @Path("{id}")
  public Shipment getShipmentById_default(@PathParam("id") int id)
  {
    return getShipmentById(id);
  }

  @GET
  @Path("{id}.json")
  public Shipment getShipmentById_json(@PathParam("id") int id)
  {
    return getShipmentById(id);
  }

  @GET
  @Path("{id}.xml")
  @Produces(MediaType.APPLICATION_XML)
  public Shipment getShipmentById_xml(@PathParam("id") int id)
  {
    return getShipmentById(id);
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

  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Shipment putJXML(Shipment shipment)
  {
    return shipmentManager.createShipment(shipment);
  }

  /**
   * POST method for updating or creating an instance of ParcelServiceResource
   *
   * @param jaxbShipment representation for the resource
   * @return an HTTP response with content of the updated or created resource.
   */
  @POST
  @Path("/postJAXBElement")
  @Consumes("application/json")
  @Produces("application/json")
  public Shipment postJAXBElement(JAXBElement<Shipment> jaxbShipment)
  {

    System.out.println("Type: " + jaxbShipment.getClass().getName());

    return new Shipment();
  }

  @POST
  @Path("/postJSONObject")
  @Consumes("application/json")
  @Produces("application/json")
  public Shipment postJSONObject(JSONObject jaxbShipment)
  {

    System.out.println("Type: " + jaxbShipment.getClass().getName());

    return new Shipment();
  }

  @POST
  @Path("/postShipmentJSON")
  @Consumes("application/json")
  @Produces("application/json")
  public Shipment postShipmentJSON(Shipment jaxbShipment)
  {
    System.out.println("Type: " + jaxbShipment.getClass().getName());
    return shipmentManager.createShipment(jaxbShipment);
  }

  @POST
  @Path("/postShipmentXML")
  @Consumes("application/xml")
  @Produces("application/xml")
  public Shipment postShipmentXML(Shipment jaxbShipment)
  {

    System.out.println("Type: " + jaxbShipment.getClass().getName());
    return shipmentManager.createShipment(jaxbShipment);
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
}
