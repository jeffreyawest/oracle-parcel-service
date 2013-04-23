package com.oracle.demo.ops.web.beans.shipment;


import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.ejb.EJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
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
 * User: jeffrey.a.west
 * Date: Mar 3, 2011
 * Time: 5:03:08 PM
 */
public class ShipmentTableBean implements Serializable
{
  static final long serialVersionUID = 42L;
  
  @EJB
  protected transient ShipmentManager ShipmentManager;

  private LazyDataModel<Shipment> lazyModel;

  private Shipment selectedShipment;

  public ShipmentTableBean()
  {

    lazyModel = new LazyDataModel<Shipment>()
    {

      /**
       * Dummy implementation of loading a certain segment of data.
       * In a real application, this method should load data from a datasource
       */

      @Override
      public List<Shipment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
      {
        //logger.info("Loading the lazy car data between {0} and {1}", new Object[]{first, (first + pageSize)});
        //Sorting and Filtering information are not used for demo purposes just random dummy data is returned

        Collection<Shipment> col = ShipmentManager.findAllShipments();
        List<Shipment> lazyShipments = new ArrayList<Shipment>(col.size());
        lazyShipments.addAll(col);

        return lazyShipments;
      }
    };

    /**
     * In a real application, this number should be resolved by a projection query
     */
    lazyModel.setRowCount(10);
  }

  public LazyDataModel<Shipment> getLazyModel()
  {
    return lazyModel;
  }
}
