package com.oracle.demo.jsf.validator;

import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.entitymanager.GeolocationManager;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

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
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/27/12
 * Time: 9:16 PM
 */

@Named
@ApplicationScoped
public class PostalCodeValidator implements javax.faces.validator.Validator
{
  @EJB
  private GeolocationManager geoManager;

  @Override
  public void validate(FacesContext pFacesContext, UIComponent pUIComponent, Object o) throws ValidatorException
  {
    if (o instanceof String)
    {
      PostalGeolocation geo = geoManager.findByPostalCode((String) o);

      if (geo == null)
      {
        pFacesContext.addMessage(null,
                                 new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                  "Postal Code not found: [" + o + "]", "Postal Code Not Found!"));
      }
    }
    else
    {
      pFacesContext.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Postal Code cannot be validated! Object Class=[" + o.getClass().getName() + "] toString=[" + String.valueOf(o) + "]",
                                                "Postal Code Not valid!"));
    }
  }
}
