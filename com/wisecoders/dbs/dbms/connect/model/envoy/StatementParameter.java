package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.schema.DataType;

public class StatementParameter {
  public final Object a;
  
  public final int b;
  
  public final DataType c;
  
  public StatementParameter(Object paramObject, int paramInt) {
    this.a = paramObject;
    this.c = null;
    this.b = paramInt;
  }
  
  public StatementParameter(Object paramObject, DataType paramDataType) {
    this.a = paramObject;
    this.c = paramDataType;
    this.b = paramDataType.getJavaType();
  }
  
  public String toString() {
    return String.valueOf(this.a);
  }
}
