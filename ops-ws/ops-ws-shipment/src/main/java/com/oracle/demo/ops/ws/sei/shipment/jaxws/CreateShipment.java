
package com.oracle.demo.ops.ws.sei.shipment.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.demo.ops.domain.CreateShipmentRequest;

@XmlRootElement(name = "createShipment", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createShipment", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
public class CreateShipment {

    @XmlElement(name = "arg0", namespace = "")
    private CreateShipmentRequest arg0;

    /**
     * 
     * @return
     *     returns CreateShipmentRequest
     */
    public CreateShipmentRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(CreateShipmentRequest arg0) {
        this.arg0 = arg0;
    }

}
