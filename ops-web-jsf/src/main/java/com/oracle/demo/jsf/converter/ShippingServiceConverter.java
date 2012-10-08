package com.oracle.demo.jsf.converter;

import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.domain.ShippingServiceName;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
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
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/23/12
 * Time: 1:06 AM
 */

@FacesConverter(forClass = com.oracle.demo.ops.domain.ShippingServiceName.class)
public class ShippingServiceConverter implements Converter, Serializable
{
  @Override
  public Object getAsObject(FacesContext pFacesContext, UIComponent pUIComponent, String s)
  {
    if(s != null)
    {
      return ShippingServiceName.fromValue(s);
    }
    else
    {
      return null;
    }
  }

  @Override
  public String getAsString(FacesContext pFacesContext, UIComponent pUIComponent, Object o)
  {
    if (o instanceof ShippingService)
    {
      ShippingService service = (ShippingService) o;
      return service.getName().toString();
    }

    return String.valueOf(o);
  }
}
