package com.oracle.demo.jsf.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
 * User: jeffrey.a.west
 * Date: Feb 24, 2011
 * Time: 1:02:22 PM
 */
@FacesConverter(forClass = java.util.Calendar.class)
public class CalendarConverter
        implements Converter
{
  public static final DateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");

  @Override
  public Object getAsObject(FacesContext facesContext,
                            UIComponent uiComponent,
                            String param)
  {
    try
    {
      return format.parse(param);
    }
    catch (Exception exception)
    {
      throw new ConverterException(exception);
    }
  }

  @Override
  public String getAsString(FacesContext facesContext,
                            UIComponent uiComponent,
                            Object obj)
  {
    try
    {
      Calendar cal = (Calendar) obj;
      return format.format(cal.getTime());
    }
    catch (Exception exception)
    {
      throw new ConverterException(exception);
    }
  }
}