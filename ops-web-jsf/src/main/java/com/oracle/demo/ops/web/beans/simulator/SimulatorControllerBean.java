package com.oracle.demo.ops.web.beans.simulator;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.web.beans.OPSController;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
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
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Jul 27, 2011
 * Time: 10:06:13 AM
 */

@Named
@SessionScoped
public class SimulatorControllerBean
    extends OPSController
{
  private static final Logger logger = Logger.getLogger(SimulatorControllerBean.class.getName());

  static final long serialVersionUID = 42L;
  private static final String CACHE_NAME = "ops-notification-cache";

  private int inputNumberOfShipments = 1;
  private int inputNumberOfParcelsPerShipment = 1;
  private long inputShipmentDelay = 500;
  private long inputParcelEventDelay = 100;
  private int currentShipmentNumber;

  public void beginSimulation(ActionEvent event)
  {
    final List<Shipment> listOfShipments = new ArrayList<Shipment>(inputNumberOfShipments);

    for (int x = 1; x <= inputNumberOfShipments; x++)
    {
      currentShipmentNumber = x;
      logger.info("Creating Shipment " + currentShipmentNumber + " of " + inputNumberOfShipments);

      Shipment shipment = new Shipment();

      shipment.setFromAddress(createFromAddress());
      shipment.setToAddress(createToAddress());
      shipment.setShippingServiceName(ShippingServiceName.BASIC);
      shipment.getParcels().addAll(createParcels(inputNumberOfParcelsPerShipment));

      Shipment finalShipment = createShipment(shipment);

      listOfShipments.add(finalShipment);

      logger.info("Finished creating Shipment " + currentShipmentNumber);

      if (inputNumberOfShipments > 1)
      {
        logger.info("Sleeping for Shipment Delay: " + inputShipmentDelay + "ms");
        sleep(inputShipmentDelay);
      }
    }

    int counter = 0;

    for (Shipment shipment : listOfShipments)
    {
      counter++;
      logger.info("Simulating events for Shipment " + counter + " of " + inputNumberOfShipments);

      getEventService().simulateEvents(shipment);
    }
  }

  private void sleep(long pDelay)
  {
    try
    {
      Thread.sleep(pDelay);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }


  private Shipment createShipment(Shipment shipment)
  {
//    getShipmentManager().createShipment(shipment);
    CreateShipmentRequest request = new CreateShipmentRequest();
    request.setShipment(shipment);

    CreateShipmentResponse response = getShipmentService().createShipment(request);

    logger.info("Created Shipment ID=" + response.getShipment().getId());

    getFacesContext().addMessage(null, new FacesMessage("Created Shipment ID:" + response.getShipment().getId(), ""));

    for (Parcel parcel : response.getShipment().getParcels())
    {
      getFacesContext().addMessage(null, new FacesMessage("Created Parcel ID:" + parcel.getId(), ""));
    }


    return response.getShipment();
  }

  private Collection<? extends Parcel> createParcels(int pNumberOfParcel)
  {
    List<Parcel> list = new ArrayList<Parcel>(pNumberOfParcel);

    for (int x = 1; x <= pNumberOfParcel; x++)
    {
      logger.info("Creating Parcel " + x + " of " + inputNumberOfParcelsPerShipment + " for shipment " + currentShipmentNumber + " of " + inputNumberOfShipments);

      Parcel p = createParcel(x);
      list.add(p);
    }

    return list;
  }

  private Parcel createParcel(int parcelNumber)
  {
    Parcel parcel = new Parcel();
    parcel.setContents("WebLogic Awesome Sauce - Parcel #" + parcelNumber);
    parcel.setHeight(1);
    parcel.setLength(2);
    parcel.setWidth(3);
    parcel.setWeight(5);
    parcel.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);

    return parcel;
  }

  private Address createToAddress()
  {
    Address address = new Address();
    address.setAddressee("Jeff West");
    address.setAddressLine1("123 Main Street");
    address.setAddressLine2("Example Line 2");
    address.setCity("Kinston");
    address.setState("NC");
    address.setPostalCode("28501");
    return address;
  }

  private Address createFromAddress()
  {
    Address address = new Address();
    address.setAddressee("Example Recipient Addressee");
    address.setAddressLine1("321 Main Street");
    address.setAddressLine2("Example Line 2");
    address.setCity("Redwood Shores");
    address.setState("CA");
    address.setPostalCode("54321");

    return address;
  }

  public int getInputNumberOfShipments()
  {
    return inputNumberOfShipments;
  }

  public void setInputNumberOfShipments(int inputNumberOfShipments)
  {
    this.inputNumberOfShipments = inputNumberOfShipments;
  }

  public int getInputNumberOfParcelsPerShipment()
  {
    return inputNumberOfParcelsPerShipment;
  }

  public void setInputNumberOfParcelsPerShipment(int inputNumberOfParcelsPerShipment)
  {
    this.inputNumberOfParcelsPerShipment = inputNumberOfParcelsPerShipment;
  }

  public long getInputShipmentDelay()
  {
    return inputShipmentDelay;
  }

  public void setInputShipmentDelay(long inputShipmentDelay)
  {
    this.inputShipmentDelay = inputShipmentDelay;
  }

  public long getInputParcelEventDelay()
  {
    return inputParcelEventDelay;
  }

  public void setInputParcelEventDelay(long inputParcelEventDelay)
  {
    this.inputParcelEventDelay = inputParcelEventDelay;
  }

}
