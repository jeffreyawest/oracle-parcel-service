/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import com.oracle.demo.ops.domain.*;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.oracle.demo.ops.shipment.notifier.Constants.*;

/**
 * @author cmlee
 */
public class Utility
{

  private static final Logger logger = Logger.getLogger(Utility.class.getName());
  private static final Random rand = new Random();

  private static Map<String, NamedCache> cache = new HashMap<String, NamedCache>();

  public static NamedCache getCache(String s)
  {
    if(!cache.containsKey(s))
    {
      CacheFactory.ensureCluster();
      NamedCache c = CacheFactory.getCache(s);
      cache.put(s, c);
    }
    return (cache.get(s));
  }

  public static <T extends BaseEntity> T create(Class<T> baseEntityClass)
  {
    T obj = null;
    try
    {
      obj = baseEntityClass.newInstance();
    }
    catch(Exception e)
    {
      logger.log(Level.SEVERE, "Instantiating "+baseEntityClass.getName(), e);
      return (null);
    }
    obj.setId(rand.nextInt(5000));
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    obj.setCreatedDate(cal);
    obj.setModifiedDate(cal);
    return (obj);
  }

  public static int parseInteger(String s)
  {
    try
    {
      return (Integer.parseInt(s));
    }
    catch(NumberFormatException ex)
    {
      JOptionPane.showMessageDialog(null, ex.getMessage()
          , "Invalid number", JOptionPane.ERROR_MESSAGE);
      return (-1);
    }
  }

  public static Shipment randomShipment()
  {
    Shipment s = create(Shipment.class);
    s.setExternalReferenceId(generateKey(7));
    s.setFromAddress(randomAddress());
    s.setToAddress(randomAddress());
    s.setShippingServiceName(randomShippingService().getName());
    for(int i = 0; i<rand.nextInt(5)+1; i++)
    {
      Parcel p = create(Parcel.class);
      p.setShipmentId(rand.nextInt(5000));
      p.setContents(randomize(CONTENTS));
      p.setWidth(rand.nextInt(5)+1);
      p.setHeight(rand.nextInt(5)+1);
      p.setLength(rand.nextInt(5)+1);
      p.setWeight(rand.nextInt(5)+1);
      p.setParcelStatus(randomEnum(ParcelStatus.values()));
      s.getParcels().add(p);
    }
    return (s);
  }

  private static ShippingService randomShippingService()
  {
    return null;  //To change body of created methods use File | Settings | File Templates.
  }

  public static Address randomAddress()
  {
    Address a = create(Address.class);
    a.setAddressee(randomize(ADDRESSEE));
    if(rand.nextBoolean())
    {
      a.setAddressLine1(randomize(ADDRESS));
      a.setAddressLine2("");
    }
    else
    {
      a.setAddressLine1("");
      a.setAddressLine2(randomize(ADDRESS));
    }
    a.setCity(randomize(CITY));
    a.setPostalCode(generateKey(5));
    a.setState(randomize(STATE));
    return (a);
  }

  public static Image load(String res) throws IOException
  {
    ClassLoader cl = Utility.class.getClassLoader();
    return (ImageIO.read(cl.getResource(res)));
  }

  private static String randomize(final String[] vals)
  {
    return (vals[rand.nextInt(vals.length)]);
  }

  private static String generateKey(final int size)
  {
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i<size; i++)
    {
      int idx = rand.nextInt(ALPHANUMERIC.length());
      sb.append(ALPHANUMERIC.substring(idx, idx+1));
    }
    return (sb.toString());
  }

  private static <T extends Enum> T randomEnum(T[] values)
  {
    return (values[rand.nextInt(values.length)]);
  }

  public static void main(String... args) throws Exception
  {
    JAXBContext ctx = JAXBContext.newInstance(Shipment.class);
    Marshaller mar = ctx.createMarshaller();
    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    mar.setProperty(Marshaller.JAXB_FRAGMENT, true);
    for(int i = 0; i<10; i++)
    {
      Shipment s = randomShipment();
      mar.marshal(s, System.out);
      System.out.println("---------------------------");
    }
  }
}
