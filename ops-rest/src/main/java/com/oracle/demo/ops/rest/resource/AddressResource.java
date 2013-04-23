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
 */
package com.oracle.demo.ops.rest.resource;

import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.services.ejb.AddressService;
import com.oracle.demo.ops.services.ejb.GeolocationService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 12/3/12
 * Time: 5:08 PM
 */
@Stateless
@Path("address")
@Produces({MediaType.APPLICATION_JSON})
public class AddressResource
{
  @EJB
  private AddressService addressService;

  @GET
  @Path("test")
  public Address test()
  {
    Address addr = new Address();

    addr.setAddressee("Jeffrey West");
    addr.setAddressLine1("4122 Network Circle");
    addr.setCity("Santa Clara");
    addr.setState("CA");
    addr.setPostalCode("99999");

    return addr;
  }

  @GET
  @Path("{id}")
  public Address findAddress(@PathParam("id") int id)
  {
    return addressService.getAddressById(id);
  }

  @POST
  @Path("/create")
  @Consumes("application/json")
  @Produces("application/json")
  public Address postShipmentJSON(Address address)
  {
    System.out.println("Type: " + address.getClass().getName());

    return address;
  }

}
