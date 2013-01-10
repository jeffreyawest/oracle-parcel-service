package com.oracle;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
 * Created with IntelliJ IDEA because its awesome.
 * User: jeffreyawest
 * Date: 12/3/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateless
@Path("person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource
{
  @GET
  @Path("get")
  public Person getTestPerson()
  {
    Person jeff = new Person();
    jeff.setFirstName("Jeff");
    jeff.setLastName("West");
    jeff.setId(0);

    return jeff;
  }

  @POST
  @Path("post")
  public Person testPost(Person pPerson)
  {
    System.out.println("Works");
    return pPerson;
  }
}
