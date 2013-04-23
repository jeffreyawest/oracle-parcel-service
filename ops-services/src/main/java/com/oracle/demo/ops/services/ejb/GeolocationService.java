package com.oracle.demo.ops.services.ejb;

import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.entitymanager.GeolocationManager;
import com.oracle.demo.ops.geo.GoogleGeocodeResponse;
import com.tangosol.net.*;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

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
 * **************************************************************************** * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/28/12
 * Time: 8:21 PM
 */
@Stateless(name = "GeolocationServiceBean", mappedName = "ejb/GeolocationService")
public class GeolocationService
{
  private static final String GOOGLE_URL = "http://maps.googleapis.com/maps/api/geocode/json";

  private static final boolean CACHE_ENABLED = true;
  private static final String CACHE_NAME = "geolocation";

  @EJB
  private GeolocationManager geoManager;

  private GoogleGeocodeResponse lookupAddressFromGoogle(String address) throws IOException
  {
    URL url = new URL(GOOGLE_URL + "?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=false");

    URLConnection conn = url.openConnection();
    String responseString = null;

    if (conn != null)
    {
      try
      {
        InputStream in = null;
        ByteArrayOutputStream out;

        try
        {
          in = conn.getInputStream();
          out = new ByteArrayOutputStream();
          IOUtils.copy(in, out);
        }
        catch (Exception e)
        {
          throw new IOException("Unable to lookup Address=[" + address + "] using URL=[" + GOOGLE_URL + "]");
        }

        responseString = out.toString();
//      System.out.println(responseString);

        ObjectMapper mapper = new ObjectMapper();

        // (note: can also use more specific type, like ArrayNode or ObjectNode!)
        GoogleGeocodeResponse response = mapper.readValue(responseString, GoogleGeocodeResponse.class);

        return response;
      }
      catch (RuntimeException e)
      {
        System.out.println("Google Response: " + responseString);
        throw e;
      }
    }
    else
    {
      throw new IOException("Unable to lookup Address=[" + address + "] using URL=[" + GOOGLE_URL + "]");
    }

  }


  public PostalGeolocation lookupByPostalCode(String pPostalCode)
  {
    NamedCache cache = null;

    if (CACHE_ENABLED)
    {
      try
      {
        cache = CacheFactory.getCache(CACHE_NAME);
        PostalGeolocation fromCache = null;

        if (cache != null)
        {
          fromCache = (PostalGeolocation) cache.get(pPostalCode);

          if (fromCache != null)
          {
            return fromCache;
          }
        }
        else
        {
          System.out.println("Unable to find CACHE");
        }

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    PostalGeolocation location;

    try

    {
      GoogleGeocodeResponse response = lookupAddressFromGoogle(pPostalCode+", USA");

      location = mapGoogleResponseToPostalGeo(response);

      if (cache != null)
      {
        cache.put(pPostalCode, location);
      }

      if (location != null)
      {
        return location;
      }
    }

    catch (Exception e)
    {
      e.printStackTrace();
    }

    //fallback
    return geoManager.findByPostalCode(pPostalCode);
  }

  private PostalGeolocation mapGoogleResponseToPostalGeo(GoogleGeocodeResponse response)
  {
    if (response != null
        && response.getStatus() != null
        && response.getStatus().equals("OK"))
    {
      PostalGeolocation location = new PostalGeolocation();
      List<GoogleGeocodeResponse.Results.Address_Components> addressComponents;

      for (GoogleGeocodeResponse.Results result : response.getResults())
      {
        addressComponents = result.getAddress_components();

        for (GoogleGeocodeResponse.Results.Address_Components addressComponent : addressComponents)
        {

          for (String str : addressComponent.getTypes())
          {
            if (str.equalsIgnoreCase("locality"))
            {
              location.setCity(addressComponent.getShort_name());
            }
            else if (str.equalsIgnoreCase("administrative_area_level_1"))
            {
              location.setState(addressComponent.getShort_name());
            }
            else if (str.equalsIgnoreCase("postal_code"))
            {
              location.setPostalCode(addressComponent.getShort_name());
            }
          }

        }

        location.setLatitude(result.getGeometry().getLocation().getLat());
        location.setLongitude(result.getGeometry().getLocation().getLng());
      }

      return location;
    }

    return null;
  }
}
