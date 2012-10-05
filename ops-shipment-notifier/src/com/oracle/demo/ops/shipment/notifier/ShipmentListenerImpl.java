/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import com.oracle.demo.ops.domain.Shipment;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.Filter;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;

import java.io.IOException;

/**
 * @author cmlee
 */
public class ShipmentListenerImpl implements MapListener, Filter, PortableObject
{

  @Override
  public void entryInserted(MapEvent evt)
  {
    Shipment ship = (Shipment) evt.getNewValue();
    display(ship, ShipmentFrame.ShipmentType.New);
    System.out.println("-------------> inserted id = "+ship.getId());
  }

  @Override
  public void entryUpdated(MapEvent evt)
  {
    Shipment ship = (Shipment) evt.getNewValue();
    display(ship, ShipmentFrame.ShipmentType.Updated);
    System.out.println("-------------> updated id = "+ship.getId());
  }

  @Override
  public void entryDeleted(MapEvent evt)
  {
    Shipment ship = (Shipment) evt.getOldValue();
    System.out.println("-------------> deleted id = "+ship.getId());
  }

  private void display(Shipment s, ShipmentFrame.ShipmentType t)
  {
    ShipmentFrame f = new ShipmentFrame(s, t);
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }

  @Override
  public boolean evaluate(Object o)
  {
    MapEvent evt = (MapEvent) o;
    return ((MapEvent.ENTRY_UPDATED == evt.getId())
            || (MapEvent.ENTRY_INSERTED == evt.getId()));
  }

  @Override
  public void readExternal(PofReader in) throws IOException
  {
  }

  @Override
  public void writeExternal(PofWriter out) throws IOException
  {
  }
}
