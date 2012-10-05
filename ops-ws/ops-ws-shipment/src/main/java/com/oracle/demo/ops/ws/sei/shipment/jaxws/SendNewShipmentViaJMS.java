
package com.oracle.demo.ops.ws.sei.shipment.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.demo.ops.domain.SendNewShipmentViaJMSRequest;

@XmlRootElement(name = "sendNewShipmentViaJMS", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendNewShipmentViaJMS", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
public class SendNewShipmentViaJMS {

    @XmlElement(name = "arg0", namespace = "")
    private SendNewShipmentViaJMSRequest arg0;

    /**
     * 
     * @return
     *     returns SendNewShipmentViaJMSRequest
     */
    public SendNewShipmentViaJMSRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(SendNewShipmentViaJMSRequest arg0) {
        this.arg0 = arg0;
    }

}
