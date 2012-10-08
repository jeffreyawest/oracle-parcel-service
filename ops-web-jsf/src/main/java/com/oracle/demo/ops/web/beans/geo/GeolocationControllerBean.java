package com.oracle.demo.ops.web.beans.geo;

import com.oracle.demo.ops.domain.PostalGeolocation;
import com.oracle.demo.ops.services.ejb.GeolocationService;
import com.oracle.demo.ops.web.beans.OPSController;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.io.IOException;

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
 * Date: 9/21/12
 * Time: 10:11 AM
 */
@Named
@SessionScoped
public class GeolocationControllerBean extends OPSController
{
  private PostalGeolocation lookupResult;
  private String inputPostalCode;

  private UploadedFile file;
  private Integer progress;
  private boolean abortLoad;

  public void handleFileUpload(FileUploadEvent event)
  {
    file = event.getFile();

    FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public int getGeoDataSize()
  {
    return getGeolocationManager().getGeoDataSize();
  }

  public String loadGeoData()
  {
    try
    {
      getGeolocationManager().loadDataFromStream(file.getInputstream());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return "geoDataSummary";
  }

  public String lookupGeo()
  {
    GeolocationService service = getGeolocationService();

    lookupResult = service.lookupByPostalCode(inputPostalCode);

    return "lookupResult";
  }

  public PostalGeolocation getLookupResult()
  {
    return lookupResult;
  }

  public void setLookupResult(PostalGeolocation pLookupResult)
  {
    lookupResult = pLookupResult;
  }

  public String getInputPostalCode()
  {
    return inputPostalCode;
  }

  public void setInputPostalCode(String pInputPostalCode)
  {
    inputPostalCode = pInputPostalCode;
  }

  public Integer getProgress()
  {
    return progress;
  }

  public void cancelGeodataLoad()
  {
    progress = null;
    abortLoad = true;
  }

  public void onComplete()
  {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Geodata Load Completed", "Geodata Load Completed"));
  }

  public void loadGeoDataActionListener(ActionEvent event)
  {
    System.out.println("GeolocationControllerBean.loadGeoDataActionListener");
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Starting Geodata Load", "Starting Geodata Load"));
    loadGeoData();
  }

  public void noop()
  {
    System.out.println("NOOP");
  }
}
