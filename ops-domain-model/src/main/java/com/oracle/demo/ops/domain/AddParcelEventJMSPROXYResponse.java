//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.03 at 05:26:51 PM PST 
//

/*
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

 */
package com.oracle.demo.ops.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddParcelEventJMSPROXYResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddParcelEventJMSPROXYResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseHeader" type="{http://demo.oracle.com/ops/domain/services/common}WebServiceResponseHeaderType"/>
 *         &lt;element name="ParcelEvent" type="{http://demo.oracle.com/ops/domain/schema/shipping}ParcelEventType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddParcelEventJMSPROXYResponseType", namespace = "http://demo.oracle.com/ops/domain/services/parcel", propOrder = {
    "responseHeader",
    "parcelEvent"
})
@XmlRootElement(name = "AddParcelEventJMSPROXYResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class AddParcelEventJMSPROXYResponse
    implements Serializable
{

    private final static long serialVersionUID = 678L;
    @XmlElement(name = "ResponseHeader", required = true)
    protected WebServiceResponseHeader responseHeader;
    @XmlElement(name = "ParcelEvent", required = true)
    protected ParcelEvent parcelEvent;

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
     * Gets the value of the parcelEvent property.
     * 
     * @return
     *     possible object is
     *     {@link ParcelEvent }
     *     
     */
    public ParcelEvent getParcelEvent() {
        return parcelEvent;
    }

    /**
     * Sets the value of the parcelEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParcelEvent }
     *     
     */
    public void setParcelEvent(ParcelEvent value) {
        this.parcelEvent = value;
    }

}
