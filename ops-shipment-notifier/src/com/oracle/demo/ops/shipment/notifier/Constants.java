/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

/**
 * @author cmlee
 */
public class Constants
{

  public static final String CACHE_NAME = "ops-notification-cache";
  public static final String SHIPMENT_CACHE = "Shipment";

  public static final String LINK = "com/oracle/demo/ops/shipment/notifier/link.png";
  public static final String LINK_BREAK = "com/oracle/demo/ops/shipment/notifier/link_break.png";

  public static final String FILTER = "filter";
  public static final String TOTAL_WEIGHT = "totalWeight";
  public static final String TOTAL_ENTRY = "totalEntries";

  public static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  public static final String[] ADDRESSEE = {
      "Peter Parker", "Fred Flintstone", "Tyrion Lannister", "Zhang Wu Ji", "Hajime Kindaichi"
  };
  public static final String[] ADDRESS = {
      "112 1/2 Beacon Street", "4 Privet Drive", "1060 West Addison Street", "742 Evergreen Terrace", "10 Downing Street", "Wu Dang Mountain"
  };
  public static final String[] CITY = {
      "Boston", "Surrey", "Chicago", "Springfield", "London"
  };
  public static final String[] STATE = {
      "Massachusetts", "Surrey County", "Illinois", "California", "London", "Hubei"
  };
  public static final String[] CONTENTS = {"Books", "DVD", "Lego blocks", "Boardgames", "Sushi", "Runners", "Leather jacket", "Tu Long sword"};

  public static final String NEWLINE = System.getProperty("line.separator");
}
