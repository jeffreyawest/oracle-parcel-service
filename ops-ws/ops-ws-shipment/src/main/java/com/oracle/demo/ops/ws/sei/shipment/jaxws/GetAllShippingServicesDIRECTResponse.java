
package com.oracle.demo.ops.ws.sei.shipment.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.demo.ops.domain.ShippingService;

@XmlRootElement(name = "getAllShippingServicesDIRECTResponse", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllShippingServicesDIRECTResponse", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
public class GetAllShippingServicesDIRECTResponse {

    @XmlElement(name = "return", namespace = "")
    private List<ShippingService> _return;

    /**
     * 
     * @return
     *     returns List<ShippingService>
     */
    public List<ShippingService> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<ShippingService> _return) {
        this._return = _return;
    }

}
