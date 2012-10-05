package com.oracle.demo.ops.test;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/7/11
 * Time: 5:53 PM
 */
public class Tester
{
  public static final int THREAD_COUNT        = 1;
  public static final int MESSAGES_PER_THREAD = 1;

  public static void main(String[] args)
  {
    try
    {
      ArrayList<ShipmentCreatorThread> list = new ArrayList<ShipmentCreatorThread>(THREAD_COUNT);

      for (int x = 1; x <= THREAD_COUNT; x++)
      {
        ShipmentCreatorThread creatorThread = new ShipmentCreatorThread(MESSAGES_PER_THREAD, "Jeff West", "Davis Peden");
        Thread thread = new Thread(creatorThread, "ShipmentCreatorThread-" + x);
        //thread.start();
        list.add(creatorThread);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private static void log(String s)
  {
    System.out.println(s);
  }
}
