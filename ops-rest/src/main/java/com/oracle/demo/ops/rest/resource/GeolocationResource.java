package com.oracle.demo.ops.rest.resource;
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
import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.services.ejb.GeolocationService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 10/1/12
 * Time: 11:53 PM
 */
@Stateless
@Path("geolocation")
@Produces({MediaType.APPLICATION_JSON})
public class GeolocationResource
{
  @EJB
  private GeolocationService geoService;

  @GET
  @Path("/{postalCode}")
  public PostalGeolocation searchPostalCode(@PathParam(value = "postalCode") String postalCode)
  {
    return geoService.lookupByPostalCode(postalCode);
  }

}
