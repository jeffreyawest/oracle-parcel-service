package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Address;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.Serializable;

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
 * User: jeffrey.a.west
 * Date: Sep 23, 2011
 * Time: 10:29:31 AM
 */
@Stateless (name = "AddressManagerBean", mappedName = "ejb/AddressManager")
@LocalBean
public class AddressManager implements Serializable
{
  public Address findAddressById(int pId)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
