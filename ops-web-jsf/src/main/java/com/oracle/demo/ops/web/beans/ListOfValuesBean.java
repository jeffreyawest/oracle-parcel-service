package com.oracle.demo.ops.web.beans;

import com.oracle.demo.jsf.converter.ParcelStatusStringConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

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
 * User: jeffrey.a.west
 * Date: Mar 3, 2011
 * Time: 4:13:09 PM
 */
@Named(value = "ReferenceValueBean")
@ApplicationScoped
public class ListOfValuesBean
        implements Serializable
{
  static final long serialVersionUID = 42L;
  
  private static Map<String, Object> stateMap;


  static
  {
    stateMap = new TreeMap<String, Object>();
    stateMap.put("ALASKA", "AK");
    stateMap.put("Enter Postal Code", "");
    stateMap.put("Enter Postal Code ", null);
    stateMap.put("ALABAMA", "AL");
    stateMap.put("ARKANSAS", "AR");
    stateMap.put("AMERICAN SAMOA", "AS");
    stateMap.put("ARIZONA", "AZ");
    stateMap.put("CALIFORNIA", "CA");
    stateMap.put("COLORADO", "CO");
    stateMap.put("CONNECTICUT", "CT");
    stateMap.put("DIST. OF COLUMBIA", "DC");
    stateMap.put("WASHINGTON, DC", "DC");
    stateMap.put("DELAWARE", "DE");
    stateMap.put("FLORIDA", "FL");
    stateMap.put("GEORGIA", "GA");
    stateMap.put("GUAM", "GU");
    stateMap.put("HAWAII", "HI");
    stateMap.put("IOWA", "IA");
    stateMap.put("IDAHO", "ID");
    stateMap.put("ILLINOIS", "IL");
    stateMap.put("INDIANA", "IN");
    stateMap.put("KANSAS", "KS");
    stateMap.put("KENTUCKY", "KY");
    stateMap.put("LOUISIANA", "LA");
    stateMap.put("MASSACHUSETTS", "MA");
    stateMap.put("MARYLAND", "MD");
    stateMap.put("MAINE", "ME");
    stateMap.put("MARSHALL ISLANDS", "MH");
    stateMap.put("MICHIGAN", "MI");
    stateMap.put("MINNESOTA", "MN");
    stateMap.put("MISSOURI", "MO");
    stateMap.put("MISSISSIPPI", "MS");
    stateMap.put("MONTANA", "MT");
    stateMap.put("NORTH CAROLINA", "NC");
    stateMap.put("NORTH DAKOTA", "ND");
    stateMap.put("NEBRASKA", "NE");
    stateMap.put("NEW HAMPSHIRE", "NH");
    stateMap.put("NEW JERSEY", "NJ");
    stateMap.put("NEW MEXICO", "NM");
    stateMap.put("NEVADA", "NV");
    stateMap.put("NEW YORK", "NY");
    stateMap.put("OHIO", "OH");
    stateMap.put("OKLAHOMA", "OK");
    stateMap.put("OREGON", "OR");
    stateMap.put("PENNSYLVANIA", "PA");
    stateMap.put("PUERTO RICO", "PR");
    stateMap.put("PALAU", "PW");
    stateMap.put("RHODE ISLAND", "RI");
    stateMap.put("SOUTH CAROLINA", "SC");
    stateMap.put("SOUTH DAKOTA", "SD");
    stateMap.put("TENNESSEE", "TN");
    stateMap.put("TEXAS", "TX");
    stateMap.put("UTAH", "UT");
    stateMap.put("VIRGINIA", "VA");
    stateMap.put("VIRGIN ISLANDS", "VI");
    stateMap.put("VERMONT", "VT");
    stateMap.put("WASHINGTON", "WA");
    stateMap.put("WISCONSIN", "WI");
    stateMap.put("WEST VIRGINIA", "WV");
    stateMap.put("WYOMING", "WY");
  }

  public Map<String, Object> getParcelStatusMap()
  {
    return ParcelStatusStringConverter.getParcelStatusMap();
  }

  public Map<String, Object> getStateMap()
  {
    return stateMap;
  }

}
