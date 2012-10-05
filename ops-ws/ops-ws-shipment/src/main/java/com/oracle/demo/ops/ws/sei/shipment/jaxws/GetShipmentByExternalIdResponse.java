
package com.oracle.demo.ops.ws.sei.shipment.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getShipmentByExternalIdResponse", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getShipmentByExternalIdResponse", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
public class GetShipmentByExternalIdResponse {

    @XmlElement(name = "return", namespace = "")
    private com.oracle.demo.ops.domain.GetShipmentByExternalIdResponse _return;

    /**
     * 
     * @return
     *     returns GetShipmentByExternalIdResponse
     */
    public com.oracle.demo.ops.domain.GetShipmentByExternalIdResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.oracle.demo.ops.domain.GetShipmentByExternalIdResponse _return) {
        this._return = _return;
    }

}
