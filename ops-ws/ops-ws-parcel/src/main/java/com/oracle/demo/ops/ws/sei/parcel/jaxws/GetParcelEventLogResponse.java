
package com.oracle.demo.ops.ws.sei.parcel.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getParcelEventLogResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getParcelEventLogResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class GetParcelEventLogResponse {

    @XmlElement(name = "return", namespace = "")
    private com.oracle.demo.ops.domain.GetParcelEventLogResponse _return;

    /**
     * 
     * @return
     *     returns GetParcelEventLogResponse
     */
    public com.oracle.demo.ops.domain.GetParcelEventLogResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.oracle.demo.ops.domain.GetParcelEventLogResponse _return) {
        this._return = _return;
    }

}
