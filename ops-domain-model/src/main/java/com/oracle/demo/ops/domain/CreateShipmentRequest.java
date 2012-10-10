//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.10 at 11:11:09 AM PDT 
//


package com.oracle.demo.ops.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateShipmentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateShipmentRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestHeader" type="{http://demo.oracle.com/ops/domain/services/common}WebServiceRequestHeaderType"/>
 *         &lt;element name="Shipment" type="{http://demo.oracle.com/ops/domain/schema/shipping}ShipmentType"/>
 *         &lt;element name="SimulateEvents" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateShipmentRequestType", propOrder = {
    "requestHeader",
    "shipment",
    "simulateEvents"
})
@XmlRootElement(name = "CreateShipmentRequest")
public class CreateShipmentRequest
    implements Serializable
{

    private final static long serialVersionUID = 678L;
    @XmlElement(name = "RequestHeader", required = true)
    protected WebServiceRequestHeader requestHeader;
    @XmlElement(name = "Shipment", required = true)
    protected Shipment shipment;
    @XmlElement(name = "SimulateEvents")
    protected boolean simulateEvents;

    /**
     * Gets the value of the requestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link WebServiceRequestHeader }
     *     
     */
    public WebServiceRequestHeader getRequestHeader() {
        return requestHeader;
    }

    /**
     * Sets the value of the requestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceRequestHeader }
     *     
     */
    public void setRequestHeader(WebServiceRequestHeader value) {
        this.requestHeader = value;
    }

    /**
     * Gets the value of the shipment property.
     * 
     * @return
     *     possible object is
     *     {@link Shipment }
     *     
     */
    public Shipment getShipment() {
        return shipment;
    }

    /**
     * Sets the value of the shipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Shipment }
     *     
     */
    public void setShipment(Shipment value) {
        this.shipment = value;
    }

    /**
     * Gets the value of the simulateEvents property.
     * 
     */
    public boolean isSimulateEvents() {
        return simulateEvents;
    }

    /**
     * Sets the value of the simulateEvents property.
     * 
     */
    public void setSimulateEvents(boolean value) {
        this.simulateEvents = value;
    }

}
