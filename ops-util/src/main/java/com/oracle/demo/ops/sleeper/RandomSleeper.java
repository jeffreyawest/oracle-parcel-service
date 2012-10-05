package com.oracle.demo.ops.sleeper;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/8/11
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomSleeper
{
  public static void main(String[] args)
  {
    for(int x=0; x<10; x++)
    {
      sleepOnceOutOf(1, 10, 1000);
    }
  }

  public static void sleepOnceOutOf(int value, int outOf, long sleepTime)
  {
    int randomInt = new Random().nextInt(outOf);
    if (randomInt == value)
    {
      try
      {
        double percent = new Random().nextDouble();

        long randomSleep = (long) (percent * (double) sleepTime);
        System.out.println(Thread.currentThread().getName() + " is sleeping for [" + randomSleep + "]!!");
        Thread.sleep(randomSleep);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void sleepLessThan(int value, int outOf, long sleepTime)
  {
    int randomInt = new Random().nextInt(outOf);

    if (randomInt < value)
    {
      try
      {
        long randomSleep = (1 / (new Random().nextInt(100))) * sleepTime;
        System.out.println(Thread.currentThread().getName() + " is sleeping!!");
        Thread.sleep(randomSleep);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
}
