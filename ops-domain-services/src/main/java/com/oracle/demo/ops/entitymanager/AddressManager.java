package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Address;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
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

 * User: jeffrey.a.west
 * Date: Sep 23, 2011
 * Time: 10:29:31 AM
 */
@Stateless (name = "AddressManagerBean", mappedName = "ejb/AddressManager")
@LocalBean
public class AddressManager implements Serializable
{
  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public Address findAddressById(int pId)
  {
    return em.find(Address.class, pId);
  }
}
