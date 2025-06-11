package com.wisecoders.dbs.loader.model;

import java.util.HashMap;
import java.util.Map;

public class LoaderErrorSample {
  public final int a;
  
  public final Map b = new HashMap<>();
  
  public LoaderErrorSample(int paramInt, Map paramMap) {
    this.a = paramInt;
    this.b.putAll(paramMap);
  }
}
