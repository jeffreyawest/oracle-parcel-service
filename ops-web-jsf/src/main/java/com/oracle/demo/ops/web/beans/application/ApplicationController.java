package com.oracle.demo.ops.web.beans.application;

import com.oracle.demo.jsf.validator.PostalCodeValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/27/12
 * Time: 9:23 PM
 */

@Named
@ApplicationScoped
public class ApplicationController
{
  @Inject
  private PostalCodeValidator postalValidator;


  public PostalCodeValidator getPostalValidator()
  {
    return postalValidator;
  }
}
