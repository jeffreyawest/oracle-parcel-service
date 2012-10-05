package com.oracle.demo.jsf.converter;

import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.domain.ShippingServiceName;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.io.Serializable;

/**
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
