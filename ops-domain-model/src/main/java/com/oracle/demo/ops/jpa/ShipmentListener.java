package com.oracle.demo.ops.jpa;
/*
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************

 */
/*
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************

 */
import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.Shipment;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ShipmentListener
{

  @PrePersist
  private void prePersist(Shipment shipment)
  {
    setParcelShipmentId(shipment);
  }

  @PreUpdate
  private void preUpdate(Shipment shipment)
  {
    setParcelShipmentId(shipment);
  }

  private void setParcelShipmentId(Shipment shipment)
  {
    for (Parcel parcel : shipment.getParcels())
    {
      if (parcel.getShipmentId() == 0)
      {
        parcel.setShipmentId(shipment.getId());
      }
    }
  }

}
