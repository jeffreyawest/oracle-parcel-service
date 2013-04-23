package com.oracle.demo.event.handler;

import com.oracle.demo.ops.domain.ParcelEvent;

import javax.inject.Named;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * **************************************************************************** * Created by IntelliJ IDEA.
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
