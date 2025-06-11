package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.result.ResultColumn;

public class Tuple {
  public final ResultColumn a;
  
  public final String b;
  
  public final boolean c;
  
  public Object d;
  
  public Tuple(String paramString, Object paramObject, boolean paramBoolean) {
    this.a = null;
    this.c = paramBoolean;
    this.b = paramString;
    this.d = paramObject;
  }
  
  public Tuple(ResultColumn paramResultColumn, Object paramObject) {
    this.a = paramResultColumn;
    this.c = false;
    this.b = paramResultColumn.a;
    this.d = paramObject;
  }
  
  public void a(Object paramObject) {
    this.d = paramObject;
  }
  
  public String toString() {
    return String.valueOf(this.b);
  }
}
