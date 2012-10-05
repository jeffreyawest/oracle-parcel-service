/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.demo.ops.shipment.notifier;

import com.tangosol.net.NamedCache;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cmlee
 */
public class OpsShipmentNotifier implements ActionListener, ItemListener
{

  public static final String ADD = "Add";
  public static final String LOAD = "Load";
  public static final String QUERY = "Query";
  public static final String PROCESS = "Process";
  public static final String ENABLE_LISTENER = "Enable Listener";
  public static final String EXIT = "Exit";

  private TrayIcon trayIcon = null;

  private NamedCache opsCache = null;
  private final ShipmentListenerImpl opsListener;
  private CheckboxMenuItem listenerMI;

  public OpsShipmentNotifier()
  {
    opsListener = new ShipmentListenerImpl();
    opsCache = Utility.getCache(Constants.CACHE_NAME);
  }

  private PopupMenu createPopupMenu()
  {
    PopupMenu pop = new PopupMenu();
    MenuItem mi = new MenuItem(ADD);
    mi.addActionListener(this);
    pop.add(mi);

    mi = new MenuItem(LOAD);
    mi.addActionListener(this);
    pop.add(mi);

    mi = new MenuItem(QUERY);
    mi.addActionListener(this);
    pop.add(mi);

    mi = new MenuItem(PROCESS);
    mi.addActionListener(this);
    pop.add(mi);

    pop.add(new MenuItem("-"));

    listenerMI = new CheckboxMenuItem(ENABLE_LISTENER);
    listenerMI.addItemListener(this);
    pop.add(listenerMI);

    pop.add(new MenuItem("-"));

    mi = new MenuItem(EXIT);
    mi.addActionListener(this);
    pop.add(mi);

    return (pop);
  }

  public void setTrayIcon(TrayIcon t)
  {
    trayIcon = t;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {

    String cmd = e.getActionCommand();
    JFrame f = null;

    if(ADD.equals(cmd))
    {
      f = new AddShipmentFrame(opsCache);
    }

    else if(LOAD.equals(cmd))
    {
      f = new LoaderFrame();
    }

    else if(QUERY.equals(cmd))
    {
      f = new QueryFrame();
    }

    else if(PROCESS.equals(cmd))
    {
      f = new ProcessFrame();
    }

    else if(EXIT.equals(cmd))
    {
      SystemTray.getSystemTray().remove(trayIcon);
      System.exit(0);
    }

    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }

  @Override
  public void itemStateChanged(ItemEvent e)
  {
    if(listenerMI.getState())
    {
      opsCache.addMapListener(opsListener, opsListener, false);
    }
    else
    {
      opsCache.removeMapListener(opsListener, opsListener);
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException
  {

    if(!SystemTray.isSupported())
    {
      JOptionPane.showMessageDialog(null
          , "SystemTray is not supported on your system."
          , "Not supported", JOptionPane.CLOSED_OPTION);
      System.exit(-1);
    }

    OpsShipmentNotifier opsNotifier = new OpsShipmentNotifier();

    ClassLoader cl = OpsShipmentNotifier.class.getClassLoader();
    TrayIcon tray = new TrayIcon(ImageIO.read(
        cl.getResourceAsStream("com/oracle/demo/ops/shipment/notifier/oracle.png"))
        , "OPS notifier", opsNotifier.createPopupMenu());
    opsNotifier.setTrayIcon(tray);


    try
    {
      SystemTray.getSystemTray().add(tray);
    }
    catch(AWTException ex)
    {
      Logger.getLogger(OpsShipmentNotifier.class.getName()).log(
          Level.SEVERE, "main", ex);
    }
  }
}
