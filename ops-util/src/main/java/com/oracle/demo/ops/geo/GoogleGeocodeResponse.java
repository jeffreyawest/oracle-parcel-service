package com.oracle.demo.ops.geo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/29/12
 * Time: 10:37 AM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodeResponse
{
  private List<Results> results;
  private String status;

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String pStatus)
  {
    status = pStatus;
  }

  public List<Results> getResults()
  {
    return results;
  }

  public void setResults(List<Results> pResults)
  {
    results = pResults;
  }

  @JsonIgnoreProperties (ignoreUnknown = true)
  public static class Results
  {
    private List<Address_Components> address_components;
    private Geometry geometry;

    public Geometry getGeometry()
    {
      return geometry;
    }

    public void setGeometry(Geometry geometry)
    {
      this.geometry = geometry;
    }

    public List<Address_Components> getAddress_components()
    {
      return address_components;
    }

    public void setAddress_components(List<Address_Components> pAddress_components)
    {
      address_components = pAddress_components;
    }

    @JsonIgnoreProperties (ignoreUnknown = true)
    public static class Geometry
    {
      private Location location;

      public Location getLocation()
      {
        return location;
      }

      public void setLocation(Location pLocation)
      {
        location = pLocation;
      }

      public static class Location
      {
        private double lat;
        private double lng;

        public double getLat()
        {
          return lat;
        }

        public void setLat(double pLat)
        {
          lat = pLat;
        }

        public double getLng()
        {
          return lng;
        }

        public void setLng(double pLng)
        {
          lng = pLng;
        }
      }
    }

    public static class Address_Components
    {
      private String long_name;
      private String short_name;
      private List<String> types;

      public String getLong_name()
      {
        return long_name;
      }

      public void setLong_name(String pLong_name)
      {
        long_name = pLong_name;
      }

      public String getShort_name()
      {
        return short_name;
      }

      public void setShort_name(String pShort_name)
      {
        short_name = pShort_name;
      }

      public List<String> getTypes()
      {
        return types;
      }

      public void setTypes(List<String> pTypes)
      {
        types = pTypes;
      }
    }

  }

}