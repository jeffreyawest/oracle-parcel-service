//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.02 at 08:14:42 AM PDT 
//


package com.oracle.demo.ops.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingServiceName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ShippingServiceName">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BASIC"/>
 *     &lt;enumeration value="EXPEDITED"/>
 *     &lt;enumeration value="OVERNIGHT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ShippingServiceName", namespace = "http://demo.oracle.com/ops/domain/schema/shipping")
@XmlEnum
public enum ShippingServiceName {

    BASIC,
    EXPEDITED,
    OVERNIGHT;

    public String value() {
        return name();
    }

    public static ShippingServiceName fromValue(String v) {
        return valueOf(v);
    }

}
