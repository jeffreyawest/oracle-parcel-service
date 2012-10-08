package com.oracle.demo.ops.web.beans.application;

import com.oracle.demo.jsf.validator.PostalCodeValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
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
