
package com.oracle.demo.ops.ws.sei.parcel.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.demo.ops.domain.AddParcelLogEventJMSPROXYRequest;

@XmlRootElement(name = "addParcelLogEventJMSProxy", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addParcelLogEventJMSProxy", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
public class AddParcelLogEventJMSProxy {

    @XmlElement(name = "arg0", namespace = "")
    private AddParcelLogEventJMSPROXYRequest arg0;

    /**
     * 
     * @return
     *     returns AddParcelLogEventJMSPROXYRequest
     */
    public AddParcelLogEventJMSPROXYRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(AddParcelLogEventJMSPROXYRequest arg0) {
        this.arg0 = arg0;
    }

}
