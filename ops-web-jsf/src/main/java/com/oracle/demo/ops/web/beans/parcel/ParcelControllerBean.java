package com.oracle.demo.ops.web.beans.parcel;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.web.beans.OPSController;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
 * User: jeffrey.a.west
 * Date: Feb 23, 2011
 * Time: 4:48:06 PM
 */
@Named
@SessionScoped
public class ParcelControllerBean
    extends OPSController
{
  static final long serialVersionUID = 42L;
  private static final Logger logger = Logger.getLogger(ParcelControllerBean.class.getName());

//  @ManagedProperty(value = "#{ShipmentControllerBean}")
//  private ShipmentControllerBean shipmentController;

  private Parcel parcel;
  private List<Parcel> parcelArray;

  private ParcelEvent parcelEvent;
  private List<ParcelEvent> parcelEventArray;

  private int inputParcelId;
  private int inputParcelEventId;
  private String inputParcelLocation;
  private String inputParcelContents;

  private int inputFirstResult = 0;
  private int inputMaxResults = 10;
  private List<ParcelEvent> parcelLog;

  public ParcelControllerBean()
  {
    clearValues();
  }

  private void clearValues()
  {
    parcelLog = new ArrayList<ParcelEvent>(7);
    parcel = new Parcel();
    parcelEvent = new ParcelEvent();
    parcelEvent.setEventDate(Calendar.getInstance());
  }

  public String updateParcelEventPageEventListener(ActionEvent event)
  {
    logger.info("first=[" + inputFirstResult + "] max=[" + inputMaxResults + "]");

    parcelLog = getParcelEventManager().getAllParcelEventsPaged(inputFirstResult, inputMaxResults);
    return "success";
  }

  public void publishLogEventEventListener(ActionEvent event)
  {
    logger.info("publishLogEvent");
    parcelEvent.setParcelId(inputParcelId);
    getParcelService().publishParcelEvent(parcelEvent);

    clearValues();
  }

  public void onParcelSelect(ActionEvent event)
  {
    System.out.println("ParcelControllerBean.onParcelSelect");
    System.out.println("Event:: " + event.getSource());
  }


  public void parcelIdValueChangeListener(ValueChangeEvent event)
  {
    try
    {
      Thread.sleep(2000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    inputParcelId = (Integer) event.getNewValue();

    logger.info("parcelIdValueChangeListener:: ParcelId=" + inputParcelId);

    Parcel temp = getParcelManager().getParcelById(inputParcelId);

    if (temp != null)
    {
      logger.info("Setting Parcel");
      parcel = temp;
      parcelEvent.setParcelId(inputParcelId);
      parcelLog = getParcelEventManager().getParcelLogByParcelId(inputParcelId);
    }
    else
    {
      logger.info("Cannot find parcel!");
    }
  }

  public String queryEventsByParcelId()
  {

////    NamedCache namedCache = CacheFactory.getCacheFactoryBuilder().getConfigurableCacheFactory(
////        ParcelEvent.class.getClassLoader()).ensureCache("ParcelEvent", Parcel.class.getClassLoader());
////    Set set = namedCache.entrySet((new EqualsFilter(new ReflectionExtractor("getParcelId"), inputParcelId)));
//    logger.info("result size=" + set.size());
//    logger.info("result" + set.toString());
//    namedCache.release();


    parcelEventArray = getParcelEventManager().getParcelLogByParcelId(inputParcelId);

    return "parcelevent/displayParcelEventArray";
  }

  public String queryEventById()
  {
    parcelEvent = getParcelEventManager().getParcelEventById(inputParcelEventId);

    return "parcelevent/displayParcelEvent";
  }

  public String queryEventsByLocation()
  {
    parcelEventArray = getParcelEventManager().getParcelEventsByLocation(inputParcelLocation);

    return "parcelevent/displayParcelEventArray";
  }

  public void valueChangeListener(ValueChangeEvent event)
  {
    logger.info("valueChangeListener");
  }

  public String queryParcelsByContents()
  {
    parcelArray = getParcelManager().queryParcelsByContents(inputParcelContents);

    return "/parcel/displayParcelArray";
  }

  public String queryParcelsByParcelId()
  {
    Parcel temp = getParcelManager().getParcelById(inputParcelId);

    if (temp != null)
    {
      logger.info("Setting Parcel");

      parcel = temp;
      //parcelEvent.setParcelId(inputParcelId);
      //parcelLog = getParcelEventManager().queryParcelLogByParcelId(inputParcelId);

      //logger.info("Parcel Event Count:" + parcelLog.size());
    }
    else
    {
      logger.info("Cannot find parcel!");
    }

    return "/app/parcel/displayParcel";
  }

  public List<ParcelEvent> getTestParcelLog()
  {
    return getParcelEventManager().getParcelLogByParcelId(getTestParcel().getId());
  }

  public Parcel getTestParcel()
  {
    return getParcelManager().findAllParcels().iterator().next();
  }

  public List<Parcel> getAllParcels()
  {
    List<Parcel> list = new ArrayList<Parcel>();
    list.addAll(getParcelManager().findAllParcels());

    return list;
  }

//  public void setShipmentController(ShipmentControllerBean shipmentController)
//  {
//    this.shipmentController = shipmentController;
//  }

  public List<ParcelEvent> getAllParcelEvents()
  {
    return getParcelEventManager().getAllParcelEvents();
  }

  public int getInputParcelId()
  {
    return inputParcelId;
  }

  public void setInputParcelId(int inputParcelId)
  {
    this.inputParcelId = inputParcelId;
  }

  public int getInputFirstResult()
  {
    return inputFirstResult;
  }

  public void setInputFirstResult(int inputFirstResult)
  {
    this.inputFirstResult = inputFirstResult;
  }

  public int getInputMaxResults()
  {
    return inputMaxResults;
  }

  public void setInputMaxResults(int inputMaxResults)
  {
    this.inputMaxResults = inputMaxResults;
  }

  public List<ParcelEvent> getParcelLog()
  {
    if (parcelLog == null)
    {
      parcelLog = new ArrayList<ParcelEvent>();
    }

    return parcelLog;
  }

  public void setParcelLog(List<ParcelEvent> parcelLog)
  {
    this.parcelLog = parcelLog;
  }

  public Parcel getParcel()
  {
    return parcel;
  }

  public void setParcel(Parcel parcel)
  {
    System.out.println("SET PARCEL ID=" + parcel.getId());

    this.parcel = parcel;
  }

  public ParcelEvent getParcelEvent()
  {
    return parcelEvent;
  }

  public void setParcelEvent(ParcelEvent parcelEvent)
  {
    this.parcelEvent = parcelEvent;
  }

  public int getInputParcelEventId()
  {
    return inputParcelEventId;
  }

  public void setInputParcelEventId(int inputParcelEventId)
  {
    this.inputParcelEventId = inputParcelEventId;
  }

  public List<ParcelEvent> getParcelEventArray()
  {
    return parcelEventArray;
  }

  public void setParcelEventArray(List<ParcelEvent> parcelEventArray)
  {
    this.parcelEventArray = parcelEventArray;
  }

  public String getInputParcelLocation()
  {
    return inputParcelLocation;
  }

  public void setInputParcelLocation(String inputParcelLocation)
  {
    this.inputParcelLocation = inputParcelLocation;
  }

  public String getInputParcelContents()
  {
    return inputParcelContents;
  }

  public void setInputParcelContents(String inputParcelContents)
  {
    this.inputParcelContents = inputParcelContents;
  }

  public List<Parcel> getParcelArray()
  {
    return parcelArray;
  }

  public void setParcelArray(List<Parcel> parcelArray)
  {
    this.parcelArray = parcelArray;
  }

}
