package com.oracle.demo.ops.web.beans.address;

import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.services.ejb.GeolocationService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.awt.event.ActionEvent;
import java.io.Serializable;

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
 * ****************************************************************************
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/27/12
 * Time: 10:43 PM
 */
@Named
@SessionScoped
public class AddressInputBean implements Serializable
{
  @EJB
  GeolocationService geoService;

  private Address address;

  public AddressInputBean()
  {
    reset();
  }

  public void reset()
  {
    address = new Address();
  }

  public void onPostalCodeValueChange(ValueChangeEvent event)
  {
    processPostalCode();
  }

  public void onValidatePostalCodeAction(ActionEvent event)
  {
    processPostalCode();
  }

  public void processPostalCode()
  {
    if (address.getPostalCode() == null)
    {
      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                                          "Postal code cannot be null!!",
                                          "Null Postal Code");

      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    else
    {
      PostalGeolocation geo = geoService.lookupByPostalCode(address.getPostalCode());

      if (geo != null)
      {
        address.setCity(geo.getCity());
        address.setState(geo.getState());
        address.setPostalCode(geo.getPostalCode());
      }
      else
      {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            "Postal code not found: [" + address.getPostalCode() + "]",
                                            "Postal Code not Found");

        FacesContext.getCurrentInstance().addMessage(null, msg);
        address.setPostalCode(null);
      }
    }
  }

  public Address getAddress()
  {
    return address;
  }

  public void setAddress(Address pAddress)
  {
    address = pAddress;
  }

}
