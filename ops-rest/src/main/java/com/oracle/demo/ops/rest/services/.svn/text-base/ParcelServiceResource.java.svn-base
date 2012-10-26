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
package com.oracle.demo.ops.rest.services;

import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * REST Web Service
 *
 * @author sbutton
 */
@Stateless
@Path("parcelService")
public class ParcelServiceResource
    extends javax.ws.rs.core.Application
{
  @Context

  private UriInfo context;

  @EJB
  private ShipmentManager shipmentManager;

  @EJB
  private ParcelManager parcelManager;


  /**
   * Creates a new instance of ParcelServiceResource
   */
  public ParcelServiceResource()
  {
  }

  /**
   * Retrieves representation of an instance of com.oracle.demo.ops.rest.services.ParcelServiceResource
   *
   * @return an instance of java.lang.String
   */
  @GET
  @Produces("application/xml")
  public String getXml()
  {
    return "<shipments><shipment><name>order1</name></shipment></shipments>";
  }

  @GET
  @Path(value = "shipment/test")
  @Produces("text/html")
  public String testShipmentManagerAvailable()
  {
    return String.format("XXX ShipmentManager: null:%s , class:%s",
                         shipmentManager == null,
                         shipmentManager == null ? "null" : shipmentManager.getClass().getName());
  }

  @GET
  @Path(value = "parcel/test")
  @Produces("text/html")
  public String testParcelManagerAvailable()
  {
    return String.format("ParcelManagerLocal: null:%s , class:%s",
                         parcelManager == null,
                         parcelManager == null ? "null" : parcelManager.getClass().getName());
  }

  @GET
  @Path("/shipment/list")
  @Produces("application/json")
  public Collection<Shipment> listShipments()
  {
    ShipmentManager sm = shipmentManager;
    return sm.findAllShipments();
  }

  @GET
  @Path("/shipment/track/{id}")
  @Produces("application/json")
  public Shipment locateShipment(@PathParam("id") int id)
  {
    ShipmentManager sm = shipmentManager;
    return sm.findShipmentById(id);
  }

  /* ========== PARCEL OPERATIONS ========== */

  @GET
  @Path("/parcel/list")
  @Produces("application/json")
  public Collection<Parcel> listParcels()
  {
    return parcelManager.findAllParcels();
  }

  @POST
  @Path("/parcel/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Parcel createParcel(Parcel parcel)
  {
    System.out.printf("/parcel/create:#Parcel: %s, %s, %s, %s, %s\n",
                      parcel,
                      parcel.getContents(),
                      parcel.getWeight(),
                      parcel.getHeight(),
                      parcel.getLength());

    return parcelManager.createParcel(parcel);
  }

  /*
  * Call this from the command line as follows:
  *
  * wget -v http://localhost:7001/ops-rest-ws/resources/parcelService/shipment/create1
  * --header 'Content-Type: application/json'
  * --post-file /tmp/shipment4.json
  *
  */

  @POST
  @Path("/shipment/create1")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Shipment createShipmentFromWizard(Shipment shipment)
  {

    if (shipment == null)
    {
      System.out.println("Shipment is NULL");
    }
    System.out.printf("/shipment/create1#Shipment: %s [%s], To: %s, From: %s, Parcel: %s\n",
                      shipment,
                      shipment.getShippingServiceName(),
                      shipment.getToAddress(),
                      shipment.getFromAddress(),
                      shipment.getParcels());
    return shipmentManager.createShipment(shipment);
  }


  @POST
  @Path("/shipment/create")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Shipment createShipmentFromWizard(
      // toAddress
      @FormParam("to_addressLine1") String to_addressLine1,
      @FormParam("to_addressLine2") String to_addressLine2,
      @FormParam("to_city") String to_city,
      @FormParam("to_state") String to_state,
      @FormParam("to_postalCode") String to_postalCode,
      // fromAddress
      @FormParam("from_addressLine1") String from_addressLine1,
      @FormParam("from_addressLine2") String from_addressLine2,
      @FormParam("from_city") String from_city,
      @FormParam("from_state") String from_state,
      @FormParam("from_postalCode") String from_postalCode,
      // Parcel
      @FormParam("parcel_contents") String parcel_contents,
      @FormParam("parcel_weight") String parcel_weight,
      @FormParam("parcel_height") String parcel_height,
      @FormParam("parcel_width") String parcel_width,
      @FormParam("parcel_length") String parcel_length
                                          )
  {

    Address toAddress = new Address();
    toAddress.setAddressLine1(to_addressLine1);
    toAddress.setAddressLine2(to_addressLine2);
    toAddress.setCity(to_city);
    toAddress.setState(to_state);
    toAddress.setPostalCode(to_postalCode);

    Address fromAddress = new Address();
    fromAddress.setAddressLine1(from_addressLine1);
    fromAddress.setAddressLine2(from_addressLine2);
    fromAddress.setCity(from_city);
    fromAddress.setState(from_state);
    fromAddress.setPostalCode(from_postalCode);

    Parcel parcel = new Parcel();
    parcel.setContents(parcel_contents);
    parcel.setWeight(Integer.parseInt(parcel_weight));
    parcel.setHeight(Integer.parseInt(parcel_height));
    parcel.setLength(Integer.parseInt(parcel_length));

    //ShipmentManager shipmentManager = shipmentManager;
    //Shipment shipment = shipmentManager.createShipment(toAddress, fromAddress, parcel);
    Shipment shipment = new Shipment();
    shipment.setToAddress(toAddress);
    shipment.setFromAddress(fromAddress);
    shipment.getParcels().add(parcel);
    return shipmentManager.createShipment(shipment);

  }

  /**
   * PUT method for updating or creating an instance of ParcelServiceResource
   *
   * @param content representation for the resource
   * @return an HTTP response with content of the updated or created resource.
   */
  @PUT
  @Consumes("application/xml")
  public void putXml(String content)
  {
  }

  /*
  * ============ Helper methods ============
  */

}
