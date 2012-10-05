/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import com.oracle.demo.ops.domain.Parcel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

/**
 * @author cmlee
 */
public class ParcelModel implements TableModel
{

  private static final int SHIPMENT_ID = 0;
  private static final int CONTENTS = 1;
  private static final int STATUS = 2;

  private final List<Parcel> parcels;

  public ParcelModel(List<Parcel> parcels)
  {
    this.parcels = parcels;
  }

  @Override
  public int getRowCount()
  {
    return (parcels.size());
  }

  @Override
  public int getColumnCount()
  {
    return (3);
  }

  @Override
  public String getColumnName(int columnIndex)
  {
    switch(columnIndex)
    {
      case SHIPMENT_ID:
        return ("Shipment Id");
      case CONTENTS:
        return ("Contents");
      case STATUS:
      default:
        return ("Status");
    }
  }

  @Override
  public Class<?> getColumnClass(int columnIndex)
  {
    return (String.class);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return (false);
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    Parcel p = parcels.get(rowIndex);
    switch(columnIndex)
    {
      case SHIPMENT_ID:
        return (Integer.toString(p.getShipmentId()));
      case CONTENTS:
        return (p.getContents());
      case STATUS:
      default:
        return (p.getParcelStatus().name());
    }
  }

  public Parcel getValueAt(int rowIndex)
  {
    return (parcels.get(rowIndex));
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex)
  {
  }

  @Override
  public void addTableModelListener(TableModelListener l)
  {
  }

  @Override
  public void removeTableModelListener(TableModelListener l)
  {
  }

}
