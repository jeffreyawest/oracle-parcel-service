/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.Shipment;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.InvocableMap.EntryAggregator;
import com.tangosol.util.InvocableMapHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cmlee
 */
public class CalculateWeightAggregrator implements EntryAggregator, PortableObject
{

  private static final long serialVersionUID = 1L;

  @Override
  public Object aggregate(Set setEntries)
  {
    Map<String, Object> result = new HashMap<String, Object>();
    int total = 0;
    result.put(Constants.TOTAL_ENTRY, setEntries.size());
    for(Object o : setEntries)
    {
      InvocableMapHelper.SimpleEntry entry = (InvocableMapHelper.SimpleEntry) o;
      Shipment s = (Shipment) entry.getValue();
      for(Parcel p : s.getParcels())
      {
        total += p.getWeight();
      }
    }
    result.put(Constants.TOTAL_WEIGHT, total);
    return (result);
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
