/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * @author cmlee
 */
public class CityModel implements ComboBoxModel
{

  private Object selected = null;

  @Override
  public void setSelectedItem(Object anItem)
  {
    selected = anItem;
  }

  @Override
  public Object getSelectedItem()
  {
    return (selected);
  }

  @Override
  public int getSize()
  {
    return (Constants.CITY.length);
  }

  @Override
  public Object getElementAt(int index)
  {
    return (Constants.CITY[index]);
  }

  @Override
  public void addListDataListener(ListDataListener l)
  {
  }

  @Override
  public void removeListDataListener(ListDataListener l)
  {
  }

}
