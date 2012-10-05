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
