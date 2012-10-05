//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.02 at 08:14:42 AM PDT 
//


package com.oracle.demo.ops.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetParcelEventLogResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetParcelEventLogResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseHeader" type="{http://demo.oracle.com/ops/domain/services/common}WebServiceResponseHeaderType"/>
 *         &lt;element name="ParcelLogEvent" type="{http://demo.oracle.com/ops/domain/schema/shipping}ParcelEventType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetParcelEventLogResponseType", namespace = "http://demo.oracle.com/ops/domain/services/parcel", propOrder = {
    "responseHeader",
    "parcelLogEvents"
})
@XmlRootElement(name = "GetParcelEventLogResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class GetParcelEventLogResponse
    implements Serializable
{

    private final static long serialVersionUID = 678L;
    @XmlElement(name = "ResponseHeader", required = true)
    protected WebServiceResponseHeader responseHeader;
    @XmlElement(name = "ParcelLogEvent", required = true)
    protected List<ParcelEvent> parcelLogEvents;

    /**
     * Gets the value of the responseHeader property.
     * 
     * @return
     *     possible object is
     *     {@link WebServiceResponseHeader }
     *     
     */
    public WebServiceResponseHeader getResponseHeader() {
        return responseHeader;
    }

    /**
     * Sets the value of the responseHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceResponseHeader }
     *     
     */
    public void setResponseHeader(WebServiceResponseHeader value) {
        this.responseHeader = value;
    }

    /**
     * Gets the value of the parcelLogEvents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parcelLogEvents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParcelLogEvents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParcelEvent }
     * 
     * 
     */
    public List<ParcelEvent> getParcelLogEvents() {
        if (parcelLogEvents == null) {
            parcelLogEvents = new ArrayList<ParcelEvent>();
        }
        return this.parcelLogEvents;
    }

}
