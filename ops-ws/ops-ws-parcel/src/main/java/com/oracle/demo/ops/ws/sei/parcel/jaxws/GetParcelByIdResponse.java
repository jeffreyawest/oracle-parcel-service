
package com.oracle.demo.ops.ws.sei.parcel.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getParcelByIdResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getParcelByIdResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class GetParcelByIdResponse {

    @XmlElement(name = "return", namespace = "")
    private com.oracle.demo.ops.domain.GetParcelByIdResponse _return;

    /**
     * 
     * @return
     *     returns GetParcelByIdResponse
     */
    public com.oracle.demo.ops.domain.GetParcelByIdResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.oracle.demo.ops.domain.GetParcelByIdResponse _return) {
        this._return = _return;
    }

}
