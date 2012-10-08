package com.oracle.demo.ops.spring.rest.shipment;

import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/13/11
 * Time: 8:40 PM
 */
@Controller
public class ShipmentController
{
  @Autowired
  ShipmentManager shipmentManager;

  @InitBinder
  public void initBinder(WebDataBinder binder)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }

  @RequestMapping(value = "/1/shipment/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Shipment getShipmentById(@PathVariable("id") int id)
  {
    Shipment shipment = shipmentManager.findShipmentById(id);
    return shipment;
  }

  @RequestMapping("/2/shipment/{id}")
  public ModelAndView getAllItems(@PathVariable("id") int id)
  {
    Shipment shipment = shipmentManager.findShipmentById(id);
    return new ModelAndView("shipment", "shipment", shipment);
  }

}