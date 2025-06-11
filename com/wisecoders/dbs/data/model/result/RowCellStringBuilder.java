package com.wisecoders.dbs.data.model.result;

public class RowCellStringBuilder {
  private final StringBuilder a = new StringBuilder();
  
  private final String b;
  
  private final String c;
  
  private int d = 0;
  
  private int e = 0;
  
  public RowCellStringBuilder() {
    this.b = "\t";
    this.c = "\n";
  }
  
  public RowCellStringBuilder(String paramString1, String paramString2) {
    this.b = paramString1;
    this.c = paramString2;
  }
  
  public void a(Object paramObject) {
    if (this.d > 0)
      this.a.append(this.b); 
    if (paramObject != null)
      this.a.append(paramObject.toString()); 
    this.d++;
  }
  
  public void a() {
    this.d = 0;
    this.a.append(this.c);
  }
  
  public String toString() {
    return this.a.toString();
  }
}
