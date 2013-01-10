package com.oracle.demo.ops.services.ejb;

import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.entitymanager.AddressManager;
import com.oracle.demo.ops.entitymanager.GeolocationManager;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

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
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateless(name = "AddressServiceBean", mappedName = "ejb/AddressService")
@LocalBean
public class AddressService
{
  @EJB
  private AddressManager addressManager;

  public Address getAddressById(int pId)
  {
    return addressManager.findAddressById(pId);
  }

}
