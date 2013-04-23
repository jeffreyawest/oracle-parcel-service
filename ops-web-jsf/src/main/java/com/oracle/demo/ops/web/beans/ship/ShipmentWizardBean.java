package com.oracle.demo.ops.web.beans.ship;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.web.beans.OPSController;
import com.oracle.demo.ops.web.beans.address.AddressInputBean;
import com.oracle.demo.ops.web.beans.gmap.MapModelWrapper;
import com.oracle.demo.ops.web.beans.track.ParcelTrackingControllerBean;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
 * Date: Feb 26, 2011
 * Time: 1:30:28 PM
 */
@Named
@SessionScoped
public class ShipmentWizardBean
    extends OPSController
{
  static final long serialVersionUID = 42L;

  private static final String CACHE_NAME = "ops-notification-cache";

  private Address toAddress;
  private Address fromAddress;
  private List<Parcel> parcelList;

  private String parcelContents;
  private int parcelWeight;
  private int parcelHeight;
  private int parcelLength;
  private int parcelWidth;

  private String selectedServiceName = "BASIC";
  private String testString = "foo";

  private boolean skip;
  private boolean simulateEvents = true;

  private Shipment finalShipment;

  @Inject
  private ParcelTrackingControllerBean parcelTrackingBean;

  @Inject
  private AddressInputBean addressInputBean;

  @Inject
  private MapModelWrapper mMapModelWrapper;

  private Marker marker;

  private static transient Logger logger = Logger.getLogger(ShipmentWizardBean.class.getName());

  public ShipmentWizardBean()
  {
    mMapModelWrapper = new MapModelWrapper();
  }

  @PostConstruct
  public void initialize()
  {
    try
    {
      reset();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public String finish()
  {
    System.out.println("Submitting order toAddressee=[" + toAddress.getAddressee()
                       + "] fromAddressee=[" + fromAddress.getAddressee()
                       + "] parcel count=[" + parcelList
                       + "] service name=[" + getSelectedService().getName().toString() + "]");

    Shipment shipment = new Shipment();
    shipment.setFromAddress(fromAddress);
    shipment.setToAddress(toAddress);
    shipment.setShippingServiceName(getSelectedService().getName());
    shipment.getParcels().addAll(parcelList);

    finalShipment = getShipmentManager().createShipment(shipment);

    for (Parcel parcel : finalShipment.getParcels())
    {
      getParcelEventManager().billingInformationReceived(parcel);
    }


      if (simulateEvents)
      {
        getEventService().simulateEvents(finalShipment);
      }

    System.out.println("Submitted!");


    FacesMessage msg = new FacesMessage("Shipment ID:" + finalShipment.getId(), "Shipment ID:" + finalShipment.getId());
    FacesContext.getCurrentInstance().addMessage(null, msg);

    reset();

    parcelTrackingBean.setInputShipmentId(finalShipment.getId());
    parcelTrackingBean.trackShipment();

    return "/app/track/displayTracking";
  }

  public MapModelWrapper getToFromMapModel()
  {
    return mMapModelWrapper;
  }

  private void reset()
  {
    parcelList = new ArrayList<Parcel>();
    toAddress = new Address();
    fromAddress = new Address();
  }

  public void selectService(ValueChangeEvent pEvent)
  {
    System.out.println("Service Selected!! new value=[" + pEvent.getNewValue()
                       + "] event=" + pEvent.toString());
  }

  public String deleteParcelAction(ActionEvent event)
  {
    Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();

    Parcel selectedParcel = (Parcel) params.get("parcel");

    parcelList.remove(selectedParcel);

    return null;
  }

  public void addParcelActionListener(ActionEvent actionEvent)
  {
/*
    System.out.println("addParcelActionListener contents=[" + parcelContents + "] " +
                       "weight=[" + parcelWeight + "] " +
                       "height=[" + parcelHeight + "] " +
                       "width=[" + parcelWidth + "] " +
                       "length=[" + parcelLength + "]");
*/

    Parcel p = new Parcel();

    p.setContents(parcelContents);
    p.setWeight(parcelWeight);
    p.setHeight(parcelHeight);
    p.setWidth(parcelWidth);
    p.setLength(parcelLength);

    parcelContents = null;
    parcelWeight = 0;
    parcelHeight = 0;
    parcelWidth = 0;
    parcelLength = 0;

    parcelList.add(p);
  }

  public String onFlowProcess(FlowEvent event)
  {
    if (skip)
    {
      skip = false;   //reset in case user goes back
      return "confirm";
    }
    else
    {
      String oldStep = event.getOldStep();
//      System.out.println("old: " + oldStep);

      if (oldStep.equalsIgnoreCase("from_tab"))
      {
        fromAddress = addressInputBean.getAddress();
//        System.out.println("fromAddress: " + fromAddress.getAddressee());
        addressInputBean.reset();
      }
      else if (oldStep.equalsIgnoreCase("to_tab"))
      {
        toAddress = addressInputBean.getAddress();
        if (fromAddress == null)
        {
          System.out.println("from null");
        }
        if (toAddress == null)
        {
          System.out.println("to null");
        }
        mMapModelWrapper.update(fromAddress, toAddress);
        addressInputBean.reset();
      }

      return event.getNewStep();
    }
  }


  public Address getToAddress()
  {
    return toAddress;
  }

  public void setToAddress(Address toAddress)
  {
    this.toAddress = toAddress;
  }

  public Address getFromAddress()
  {
    return fromAddress;
  }

  public void setFromAddress(Address fromAddress)
  {
    this.fromAddress = fromAddress;
  }

  public List<Parcel> getParcelList()
  {
    return parcelList;
  }

  public void setParcelList(List<Parcel> parcelList)
  {
    this.parcelList = parcelList;
  }

  public String getParcelContents()
  {
    return parcelContents;
  }

  public void setParcelContents(String parcelContents)
  {
    this.parcelContents = parcelContents;
  }

  public int getParcelWeight()
  {
    return parcelWeight;
  }

  public void setParcelWeight(int parcelWeight)
  {
    this.parcelWeight = parcelWeight;
  }

  public int getParcelHeight()
  {
    return parcelHeight;
  }

  public void setParcelHeight(int parcelHeight)
  {
    this.parcelHeight = parcelHeight;
  }

  public int getParcelLength()
  {
    return parcelLength;
  }

  public void setParcelLength(int parcelLength)
  {
    this.parcelLength = parcelLength;
  }

  public int getParcelWidth()
  {
    return parcelWidth;
  }

  public void setParcelWidth(int parcelWidth)
  {
    this.parcelWidth = parcelWidth;
  }

  public Shipment getFinalShipment()
  {
    return finalShipment;
  }

  public void setFinalShipment(Shipment finalShipment)
  {
    this.finalShipment = finalShipment;
  }

  public List<ShippingService> getShippingServiceList()
  {
    getShippingServiceManager().warmCache();
    return getShippingServiceManager().findAllShippingServices();
  }

  public static Logger getLogger()
  {
    return logger;
  }

  public static void setLogger(final Logger logger)
  {
    ShipmentWizardBean.logger = logger;
  }

  public String getSelectedServiceName()
  {
    return selectedServiceName;
  }

  public void setSelectedServiceName(String pSelectedServiceName)
  {
    if (pSelectedServiceName != null)
    {
      selectedServiceName = pSelectedServiceName;
    }
  }

  public ShippingService getSelectedService()
  {
    return getShippingServiceManager().findShippingServiceByName(selectedServiceName);
  }

  public boolean getSimulateEvents()
  {
    return simulateEvents;
  }

  public void setSimulateEvents(boolean pSimulateEvents)
  {
    simulateEvents = pSimulateEvents;
  }

  public MapModelWrapper getMapModelWrapper()
  {
    mMapModelWrapper.update(fromAddress, toAddress);
    return mMapModelWrapper;
  }

  public void setMapModelWrapper(MapModelWrapper pMapModelWrapper)
  {
    mMapModelWrapper = pMapModelWrapper;
  }

  public Marker getMarker()
  {
    return marker;
  }

  public void setMarker(Marker pArker)
  {
    marker = pArker;
  }

  public void onMarkerSelect(OverlaySelectEvent event)
  {
    marker = (Marker) event.getOverlay();
  }

}