package com.oracle.demo.ops.spring.rest.parcel;

import com.oracle.demo.ops.domain.Parcel;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/13/11
 * Time: 12:15 PM
 */
public interface ParcelService
{
  public Parcel getParcelById(int id);
}
