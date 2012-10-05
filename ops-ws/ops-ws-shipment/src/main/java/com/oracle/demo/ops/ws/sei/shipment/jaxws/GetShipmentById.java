
package com.oracle.demo.ops.ws.sei.shipment.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.demo.ops.domain.GetShipmentByIdRequest;

@XmlRootElement(name = "getShipmentById", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getShipmentById", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
public class GetShipmentById {

    @XmlElement(name = "arg0", namespace = "")
    private GetShipmentByIdRequest arg0;

    /**
     * 
     * @return
     *     returns GetShipmentByIdRequest
     */
    public GetShipmentByIdRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(GetShipmentByIdRequest arg0) {
        this.arg0 = arg0;
    }

}
