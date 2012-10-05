package com.oracle.demo.ops.web.beans.services;

import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.domain.ShippingServiceName;
import com.oracle.demo.ops.web.beans.OPSController;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: Oct 11, 2011
 * Time: 11:54:52 AM
 */
@Named
@SessionScoped
public class ServiceControllerBean
    extends OPSController
{
  private static final Logger logger = Logger.getLogger(ServiceControllerBean.class.getName());

  @Resource(mappedName = "com.oracle.demo.ops.jdbc.cluster-ds")
  private DataSource dataSource;

  private List<ShippingService> allServices = new ArrayList<ShippingService>();
  private ShippingService newShippingService = new ShippingService();
  private ShippingService selectedShippingService;

  public List<ShippingService> getAllServices()
  {
    allServices.clear();
    allServices.addAll(getShippingServiceManager().findAllShippingServices());
    return allServices;
  }

  public void editShippingServiceActionListener(ActionEvent event)
  {
    getShippingServiceManager().updateService(selectedShippingService);
  }

  public void addShippingServiceActionListener(ActionEvent event)
  {
    addService(newShippingService);
    newShippingService = new ShippingService();
  }

  private void addService(ShippingService pService)
  {
    Connection conn = null;
    PreparedStatement stmt = null;

    try
    {
      conn = dataSource.getConnection();
      stmt = conn.prepareStatement("INSERT INTO SHIPPINGSERVICE (ID, NAME, DESCRIPTION) VALUES (?,?,?)");
      stmt.setString(1, pService.getName().toString());
      stmt.setString(2, pService.getName().toString());
      stmt.setString(3, pService.getDescription());
      stmt.executeUpdate();
      conn.commit();
      conn.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if(conn != null)
      {
        try
        {
          conn.close();
        }
        catch(SQLException e)
        {
          e.printStackTrace();
        }
      }

    }
  }


  public List<ShippingService> getShippingServicesListTopLink()
  {
    return getShippingServiceManager().findAllShippingServices();
  }

  public void selectShippingServiceListener(ActionEvent event)
  {
    logger.info(String.valueOf(event.getComponent().getAttributes().get("action")));

    UIComponent tmpComponent = event.getComponent();

    while(null != tmpComponent && !(tmpComponent instanceof UIData))
    {
      tmpComponent = tmpComponent.getParent();
    }

    if(tmpComponent != null)
    {
      final Object tmpRowData = ((UIData) tmpComponent).getRowData();

      if(tmpRowData instanceof ShippingService)
      {
        selectedShippingService = (ShippingService) tmpRowData;

        logger.info("Selected Shipping Service: " + selectedShippingService.getName());
      }
      else
      {
        logger.info(this.getClass() + "::selectShippingServiceListener::Wrong Class!!===" + tmpComponent.getClass());
      }
    }
  }

  public void warmCacheActionListner(ActionEvent event)
  {
    logger.info("WARMING!!");
    logger.info("WARMING!!");
    getShippingServiceManager().warmCache();
  }

  public String warmCacheAction()
  {
    getShippingServiceManager().warmCache();

    return "/app/shippingServices/listServices";
  }

  public List<ShippingService> getShippingServicesListSQL()
  {
    List<ShippingService> list = new ArrayList<ShippingService>();

    Connection conn = null;
    Statement stmt = null;

    try
    {
      conn = dataSource.getConnection();
      stmt = conn.createStatement();

      ResultSet rs = stmt.executeQuery("select * from shippingservice");

      while(rs.next())
      {
        ShippingService svc = new ShippingService();
        svc.setCost(rs.getDouble("COST"));
        svc.setDescription(rs.getString("DESCRIPTION"));
        svc.setName(ShippingServiceName.fromValue(rs.getString("NAME")));
        list.add(svc);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if(stmt != null)
      {
        try
        {
          stmt.close();
        }
        catch(SQLException ignore)
        {

        }
      }

      if(conn != null)
      {
        try
        {
          conn.close();
        }
        catch(SQLException ignore)
        {
        }
      }


    }

    return list;
  }

  public void setAllServices(List<ShippingService> allServices)
  {
    this.allServices = allServices;
  }

  public ShippingService getNewShippingService()
  {
    return newShippingService;
  }

  public void setNewShippingService(ShippingService newShippingService)
  {
    this.newShippingService = newShippingService;
  }

  public ShippingService getSelectedShippingService()
  {
    return selectedShippingService;
  }

  public void setSelectedShippingService(ShippingService selectedShippingService)
  {
    this.selectedShippingService = selectedShippingService;
  }
}
