/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * *************************************************************************** */

package com.oracle.demo.ops.rest;


import com.oracle.demo.ops.entitymanager.ShipmentManager;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sbutton
 */
public class TestServlet
        extends HttpServlet
{
  @EJB(beanName="ShipmentManagerBean")
  ShipmentManager shipmentManager;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    try
    {

      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet TestServlet</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");

      out.printf("<p>ShipmentManager: null:%s , class:%s</p>",
              shipmentManager == null,
              shipmentManager == null ? "null" : shipmentManager.getClass().getName());


      try
      {
        out.printf("<p>ShipmentManager: findShipmentById(1)=%s</p>", shipmentManager.findShipmentById(1));
      }
      catch (Exception e)
      {
        out.printf("<p>ShipmentManager: findShipmentById(1)<br/><pre><code>%s</code></pre></p>", e.getMessage());
      }

      try
      {
        InitialContext ic = new InitialContext();
        ShipmentManager sm = (ShipmentManager) ic.lookup("java:comp/env/ejb/ShipmentManager");
        out.printf("<p>Lookup ShipmentManager: null:%s , class:%s</p>",
                sm == null,
                sm == null ? "null" : sm.getClass().getName());
      }
      catch (Exception e)
      {
        out.printf("<p>Lookup ShipmentManager: <br/><pre><code>%s</code></pre></p>", e.getMessage());
      }


      out.println("</body>");
      out.println("</html>");

    }
    finally
    {
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

}
