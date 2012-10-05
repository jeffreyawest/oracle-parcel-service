package com.oracle.demo.event.handler;

import com.oracle.demo.ops.domain.ParcelEvent;

import javax.inject.Named;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/4/11
 * Time: 11:46 AM
 */
@Named
public class DoNothingEventHandler
        extends AbstractEventHandler
{
  @Override
  protected void handleEventInternal(ParcelEvent pEvent)
  {
  }
}
