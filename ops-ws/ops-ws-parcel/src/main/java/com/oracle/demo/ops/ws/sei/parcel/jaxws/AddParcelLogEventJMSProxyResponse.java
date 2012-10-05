
package com.oracle.demo.ops.ws.sei.parcel.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.demo.ops.domain.AddParcelLogEventJMSPROXYResponse;

@XmlRootElement(name = "addParcelLogEventJMSProxyResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addParcelLogEventJMSProxyResponse", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class AddParcelLogEventJMSProxyResponse {

    @XmlElement(name = "return", namespace = "")
    private AddParcelLogEventJMSPROXYResponse _return;

    /**
     * 
     * @return
     *     returns AddParcelLogEventJMSPROXYResponse
     */
    public AddParcelLogEventJMSPROXYResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(AddParcelLogEventJMSPROXYResponse _return) {
        this._return = _return;
    }

}
