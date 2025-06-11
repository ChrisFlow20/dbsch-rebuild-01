package com.wisecoders.dbs.data.model.result;

import java.math.BigDecimal;

public class ResultArrayRow implements ResultRow {
  public final Object[] a;
  
  private boolean b = false;
  
  public ResultArrayRow(int paramInt) {
    this.a = new Object[paramInt];
  }
  
  public Object a(ResultColumn paramResultColumn) {
    return (paramResultColumn != null && paramResultColumn.d < this.a.length) ? this.a[paramResultColumn.d] : null;
  }
  
  public void a(ResultColumn paramResultColumn, Object paramObject) {
    if (paramResultColumn != null && paramResultColumn.d < this.a.length) {
      if (paramObject instanceof BigDecimal) {
        BigDecimal bigDecimal = (BigDecimal)paramObject;
        if (bigDecimal.doubleValue() == bigDecimal.intValue())
          paramObject = Integer.valueOf(bigDecimal.intValue()); 
      } 
      if (paramObject instanceof java.util.Map || paramObject instanceof java.util.List)
        this.b = true; 
      this.a[paramResultColumn.d] = paramObject;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("Array Row [");
    for (Object object : this.a)
      stringBuilder.append("'").append(object).append("', "); 
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  public boolean a() {
    return this.b;
  }
  
  public boolean a(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    return ResultRow.a(this.a, paramString, paramBoolean1, paramBoolean2);
  }
}
