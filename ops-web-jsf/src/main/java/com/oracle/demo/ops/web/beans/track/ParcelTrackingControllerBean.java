package com.oracle.demo.ops.web.beans.track;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.web.beans.OPSController;
import com.oracle.demo.ops.web.beans.gmap.MapModelWrapper;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
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
public class ParcelTrackingControllerBean
    extends OPSController
{
  static final long serialVersionUID = 42L;
  private static final Logger logger = Logger.getLogger(ParcelTrackingControllerBean.class.getName());

  private Shipment shipment;
  private Parcel parcel;
  private ParcelEvent selectedParcelEvent;
  private List<ParcelEvent> parcelLog;

  @Inject
  private MapModelWrapper mMapModelWrapper;

  private int inputParcelId;
  private Marker marker;

  private String mInputExternalId;
  private int mInputShipmentId;

  public ParcelTrackingControllerBean()
  {
  }

  @PostConstruct
  private void init()
  {
//    mMapModelWrapper = new MapModelWrapper();
    clearValues();
  }

  public String trackShipment()
  {
    shipment = getShipmentManager().findShipmentById(mInputShipmentId);

    if (shipment == null)
    {
      addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipment not found!", null));
      return "";
    }

    parcel = shipment.getParcels().get(0);
    parcelLog = getParcelEventManager().getParcelLogByParcelId(parcel.getId());

    updateMapModel(parcelLog);

    return "displayTracking";
  }

  public String trackParcel()
  {
    parcel = getParcelManager().getParcelById(inputParcelId);

    if (parcel == null)
    {
      addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Parcel not found!", null));
      return "";
    }

    shipment = getShipmentManager().findShipmentById(parcel.getShipmentId());
    mInputShipmentId = shipment.getId();
    parcelLog = getParcelEventManager().getParcelLogByParcelId(inputParcelId);

    updateMapModel(parcelLog);

    return "displayTracking";
  }

  public String trackShipmentByExternalId()
  {
    shipment = getShipmentManager().findShipmentByExternalId(mInputExternalId);

    if (shipment == null)
    {
      addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipment not found!", null));
      return "";
    }

    if (shipment.getParcels().size() > 0)
    {
      parcel = shipment.getParcels().get(0);
      parcelLog = getParcelEventManager().getParcelLogByParcelId(parcel.getId());

      updateMapModel(parcelLog);
    }

    return "displayTracking";
  }


  private void updateMapModel(List<ParcelEvent> pParcelLog)
  {
    mMapModelWrapper.update(pParcelLog);
  }

  private void clearValues()
  {
    parcelLog = new ArrayList<ParcelEvent>(7);
    parcel = new Parcel();
    selectedParcelEvent = new ParcelEvent();
    selectedParcelEvent.setEventDate(Calendar.getInstance());
  }

  public Parcel getParcel()
  {
    return parcel;
  }

  public void setParcel(Parcel pParcel)
  {
    parcel = pParcel;
  }

  public ParcelEvent getSelectedParcelEvent()
  {
    return selectedParcelEvent;
  }

  public void setSelectedParcelEvent(ParcelEvent pSelectedParcelEvent)
  {
    selectedParcelEvent = pSelectedParcelEvent;
  }

  public List<ParcelEvent> getParcelLog()
  {
    return parcelLog;
  }

  public void setParcelLog(List<ParcelEvent> pParcelLog)
  {
    parcelLog = pParcelLog;
  }

  public void onMarkerSelect(OverlaySelectEvent event)
  {
    marker = (Marker) event.getOverlay();
  }

  public MapModelWrapper getMapModelWrapper()
  {
    return mMapModelWrapper;
  }

  public void setMapModelWrapper(MapModelWrapper pMapModelWrapper)
  {
    mMapModelWrapper = pMapModelWrapper;
  }

  public int getInputParcelId()
  {
    return inputParcelId;
  }

  public void setInputParcelId(int pInputParcelId)
  {
    inputParcelId = pInputParcelId;
  }

  public void onPolylineSelect(OverlaySelectEvent event)
  {
    addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Polyline Selected", null));
  }

  public void addMessage(FacesMessage message)
  {
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

  public Shipment getShipment()
  {
    return shipment;
  }

  public void setShipment(Shipment pShipment)
  {
    shipment = pShipment;
  }

  public Marker getMarker()
  {
    return marker;
  }

  public void setInputExternalId(String inputExternalId)
  {
    mInputExternalId = inputExternalId;
  }

  public String getInputExternalId()
  {
    return mInputExternalId;
  }

  public int getInputShipmentId()
  {
    return mInputShipmentId;
  }

  public void setInputShipmentId(int pInputShipmentId)
  {
    mInputShipmentId = pInputShipmentId;
  }

  public Parcel getNewParcel()
  {
    return parcel;
  }

  public void setNewParcel(Parcel pNewParcel)
  {
    parcel = pNewParcel;
    shipment = getShipmentManager().findShipmentById(shipment.getId());
    parcelLog = getParcelEventManager().getParcelLogByParcelId(parcel.getId());
    updateMapModel(parcelLog);
  }

  public void updatePoll()
  {
    shipment = getShipmentManager().findShipmentById(shipment.getId());
    parcelLog = getParcelEventManager().getParcelLogByParcelId(parcel.getId());
    updateMapModel(parcelLog);
  }

  public void onSelectNewParcel(ActionEvent event)
  {
    CommandButton button = (CommandButton) event.getSource();
  }

  public List<Parcel> getParcels()
  {
    return getParcelManager().findByShipmentId(mInputShipmentId);
  }

}
