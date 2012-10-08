package com.oracle.demo.ops.spring.rest.parcel;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/13/11
 * Time: 12:15 PM
 */
@Controller
@RequestMapping(value = "/parcel")
public class ParcelServiceController
{
  @Autowired
  ParcelManager parcelManager;


  @InitBinder
  public void initBinder(WebDataBinder binder)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Parcel getParcelById1(@PathVariable("id") int id)
  {
    return parcelManager.getParcelById(id);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public Collection<Parcel> getParcelById2(@PathVariable("id") int id)
  {
    return parcelManager.findAllParcels();
  }
}
