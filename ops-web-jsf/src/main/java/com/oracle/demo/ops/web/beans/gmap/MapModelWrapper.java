package com.oracle.demo.ops.web.beans.gmap;

import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.entitymanager.GeolocationManager;
import com.oracle.demo.ops.services.ejb.GeolocationService;
import org.primefaces.model.map.*;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * Time: 11:38 AM
 */

@Named
@RequestScoped
public class MapModelWrapper implements Serializable
{
  private static final Logger logger = Logger.getLogger(MapModelWrapper.class.getName());
  private static String US_CENTER = "39.50, -98.35";

  @EJB
  private GeolocationService geoService;

  private transient MapModel mMapModel;
  private int zoomLevel = 3;
  private String mMapCenter;


  public MapModelWrapper()
  {
    mMapCenter = US_CENTER;
  }

  public MapModel getMapModel()
  {
    return mMapModel;
  }

  public void setMapModel(MapModel pMapModel)
  {
    mMapModel = pMapModel;
  }

  public String getMapCenter()
  {
    return mMapCenter;
  }

  public void setMapCenter(String pMapCenter)
  {
    mMapCenter = pMapCenter;
  }

  public void update(Address pFromAddress, Address pToAddress)
  {
    List<PostalGeolocation> geoList = new ArrayList<PostalGeolocation>(2);

    PostalGeolocation fromGeo = geoService.lookupByPostalCode(pFromAddress.getPostalCode());
    PostalGeolocation toGeo = geoService.lookupByPostalCode(pToAddress.getPostalCode());

    geoList.add(fromGeo);
    geoList.add(toGeo);

    updateModel(geoList);
  }

  public void update(List<ParcelEvent> pParcelLog)
  {
    List<PostalGeolocation> geoList = new ArrayList<PostalGeolocation>(pParcelLog.size());

    logger.info("Size of Parcel Log: " + pParcelLog.size());

    for (ParcelEvent event : pParcelLog)
    {
      if (event == null)
      {
        continue;
      }

      if (event.getLocation() == null || event.getLocation().equals("United States"))
      {
        continue;
      }

      PostalGeolocation geo = geoService.lookupByPostalCode(event.getLocation());

      if (geo != null)
      {
        geoList.add(geo);
      }
      else
      {
        logger.log(Level.WARNING, "GEO for event location: " + event.getLocation() + " is null!");
      }
    }

    updateModel(geoList);
  }

  private void updateModel(List<PostalGeolocation> postalLocations)
  {
    if (mMapModel == null)
    {
      mMapModel = new DefaultMapModel();
    }

    logger.info("Size of locations: " + postalLocations.size());

    Polyline polyline = new Polyline();

    double maxLat = -1 * Double.MAX_VALUE;
    double minLat = Double.MAX_VALUE;

    double maxLng = -1 * Double.MAX_VALUE;
    double minLng = Double.MAX_VALUE;

    if (postalLocations.size() <= 0)
    {
      mMapCenter = US_CENTER;
    }
    else
    {
      for (PostalGeolocation geo : postalLocations)
      {
        if (geo == null)
        {
          continue;
        }

        double lat = geo.getLatitude();
        double lng = geo.getLongitude();

        if (lat > maxLat)
        {
          maxLat = lat;
        }

        if (lat < minLat)
        {
          minLat = lat;
        }

        if (maxLng < lng)
        {
          maxLng = lng;
        }

        if (lng < minLng)
        {
          minLng = lng;
        }

        LatLng coord = new LatLng(lat, lng);

        Marker marker = new Marker(coord, geo.getCity() + ", " + geo.getState());
        mMapModel.addOverlay(marker);

        polyline.getPaths().add(coord);
      }

      polyline.setStrokeWeight(4);
      polyline.setStrokeColor("#FF9900");
      polyline.setStrokeOpacity(0.7);

      mMapCenter = getMapCenter(maxLat, minLat, maxLng, minLng);

      mMapModel.addOverlay(polyline);

      if (false && postalLocations.size() > 3)
      {
        PostalGeolocation[] geolocations = postalLocations.toArray(new PostalGeolocation[postalLocations.size()]);
        PostalGeolocation lastGeo = geolocations[geolocations.length - 1];
        PostalGeolocation secondToLastGeo = null;

        for (int x = geolocations.length - 2; x >= 0; x--)
        {
          PostalGeolocation geolocation = geolocations[x];

          if (!(lastGeo.getPostalCode() == null)
              && !(geolocation.getPostalCode() == null)
              && !lastGeo.getPostalCode().equals(geolocation.getPostalCode()))
          {
            secondToLastGeo = geolocation;
            break;
          }
        }

        if (secondToLastGeo != null)
        {
          LatLng coordSecondToLast = new LatLng(secondToLastGeo.getLatitude(), secondToLastGeo.getLongitude());
          LatLng coordLast = new LatLng(lastGeo.getLatitude(), lastGeo.getLongitude());

          Polyline lastLine = new Polyline();
          lastLine.getPaths().add(coordSecondToLast);
          lastLine.getPaths().add(coordLast);

          lastLine.setStrokeWeight(4);
          lastLine.setStrokeColor("#00FF00");
          lastLine.setStrokeOpacity(1.0);

          mMapModel.addOverlay(lastLine);
          System.out.println("LAST LINE");
        }
      }
    }

    zoomLevel = 3;
  }

  private String getMapCenter(double maxLat, double minLat, double maxLng, double minLng)
  {
    double x = (minLat + maxLat) / (double) 2;
    double y = (maxLng + minLng) / (double) 2;

    return x + ", " + y;
  }

  public int getZoomLevel()
  {
    return zoomLevel;
  }

  public void setZoomLevel(int zoomLevel)
  {
    this.zoomLevel = zoomLevel;
  }
}
