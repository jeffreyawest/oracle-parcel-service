package com.oracle.demo.ops.web.beans.shipment;

import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.web.beans.OPSController;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
 * Time: 5:28:27 PM
 */
@Named
@SessionScoped
public class ShipmentControllerBean
    extends OPSController
{
  static final long serialVersionUID = 42L;
  private List<ShippingService> shippingServiceList = new ArrayList(0);
  private List<Shipment> shipmentList;
  private Shipment selectedShipment;

  private String inputShipmentId;
  private String queryTitle;
  private String inputExternalId;
  private String inputZip;
  private String inputState;

  public List<ShippingService> getAllShippingServices()
  {
    shippingServiceList.clear();
    shippingServiceList.addAll(getShippingServiceManager().findAllShippingServices());
    return shippingServiceList;
  }

  public List<Shipment> getAllShipments()
  {
    List<Shipment> list = new ArrayList<Shipment>();
    list.addAll(getShipmentManager().findAllShipments());
    return list;
  }

  public void growl()
  {
    FacesContext context = FacesContext.getCurrentInstance();

    context.addMessage(null, new FacesMessage("Grrrrr!", "Grrrrrr!"));
  }

  public String queryShipmentsByShipmentId()
  {
    selectedShipment = getShipmentManager().findShipmentById(Integer.parseInt(inputShipmentId));

    return "displayShipment";
  }

  public String queryShipmentsByShipmentIdREST()
  {
    try
    {
      String uriString = "http://127.0.0.1:7101/ops-rest-ws/rest-api/1/shipment/" + inputShipmentId;

      URL url = new URL(uriString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/xml");

      JAXBContext jc = JAXBContext.newInstance(Shipment.class);
      InputStream data = connection.getInputStream();
      Shipment shipment = (Shipment) jc.createUnmarshaller().unmarshal(data);

      connection.disconnect();
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }
    catch (ProtocolException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }


    return null;
  }

  public String queryByExternalId()
  {
    selectedShipment = getShipmentManager().findShipmentByExternalId(inputExternalId);

    return "displayShipment";
  }

  public String queryByDestinationZip()
  {
    return "success";
  }

  public String queryByState()
  {
    return "success";
  }

  public String getInputShipmentId()
  {
    return inputShipmentId;
  }

  public void setInputShipmentId(String inputShipmentId)
  {
    this.inputShipmentId = inputShipmentId;
  }

  public String getInputExternalId()
  {
    return inputExternalId;
  }

  public void setInputExternalId(String inputExternalId)
  {
    this.inputExternalId = inputExternalId;
  }

  public String getInputZip()
  {
    return inputZip;
  }

  public void setInputZip(String inputZip)
  {
    this.inputZip = inputZip;
  }

  public String getInputState()
  {
    return inputState;
  }

  public void setInputState(String inputState)
  {
    this.inputState = inputState;
  }

  public List<Shipment> getShipmentList()
  {
    return shipmentList;
  }

  public void setShipmentList(List<Shipment> shipmentList)
  {
    this.shipmentList = shipmentList;
  }

  public String getQueryTitle()
  {
    return queryTitle;
  }

  public void setQueryTitle(String queryTitle)
  {
    this.queryTitle = queryTitle;
  }

  public Shipment getSelectedShipment()
  {
    return selectedShipment;
  }

  public void setSelectedShipment(Shipment selectedShipment)
  {
    this.selectedShipment = selectedShipment;
  }
}
