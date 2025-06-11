package com.wisecoders.dbs.loader.engine;

public class JSONParseException extends RuntimeException {
  final String a;
  
  final int b;
  
  public String getMessage() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n");
    stringBuilder.append(this.a);
    stringBuilder.append("\n");
    for (byte b = 0; b < this.b; b++)
      stringBuilder.append(" "); 
    stringBuilder.append("^");
    return stringBuilder.toString();
  }
  
  public JSONParseException(String paramString, int paramInt) {
    this.a = paramString;
    this.b = paramInt;
  }
  
  public JSONParseException(String paramString, int paramInt, Throwable paramThrowable) {
    super(paramThrowable);
    this.a = paramString;
    this.b = paramInt;
  }
  
  public int a() {
    return this.b;
  }
}
