package com.oracle.demo.ops.spring.rest;

import com.oracle.demo.ops.domain.Parcel;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/13/11
 * Time: 10:25 PM
 */
public class TestClient
{

  public static void main(String[] args)
  {
    meth();
  }

  public static void spring()
  {
    String uri = "http://10.0.0.51:7101/ops-spring-rest/rest-api/1/parcel/{id}";

    RestTemplate template = new RestTemplate();

  }

  public static void jakarta()
  {

    try
    {
      String uri = "http://10.0.0.51:7101/ops-spring-rest/rest-api/1/parcel/3";

      HttpClient httpClient = new HttpClient();

      GetMethod get = new GetMethod(uri);
      //get.getRequestHeader("Accept", "application/json");
      httpClient.executeMethod(get);

      System.out.println(get.getResponseBodyAsString());

      ObjectMapper mapper = new ObjectMapper();

      Parcel p;

      p = mapper.readValue(get.getResponseBodyAsString(), Parcel.class);
      System.out.println(p.getContents());

      if (HttpStatus.SC_CREATED == get.getStatusCode())
      {
        Header location = get.getRequestHeader("Location");
        if (location != null)
        {
          System.out.println("Created new booking at :" + location.getValue());
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void meth()
  {
    String uri = "http://10.0.0.51:7101/ops-spring-rest/rest-api/1/parcel/3";

    Client client = Client.create();

    WebResource webResource = client.resource(uri);
    long start = System.currentTimeMillis();
    Parcel p = webResource.accept(MediaType.APPLICATION_XML).get(Parcel.class);
    long stop = System.currentTimeMillis();
    System.out.println((stop-start));

    start=System.currentTimeMillis();
    p = webResource.accept(MediaType.APPLICATION_XML).get(Parcel.class);
    stop = System.currentTimeMillis();
    System.out.println((stop-start));


    //ClientResponse response = webResource.path("1").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);

// jaxb object
//    CustomerType entity = response.getEntity(CustomerType.class);
//    logger.info(entity.getCity());
  }

}
