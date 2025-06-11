package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.StringUtil;

public class FunctionParameter {
  public final String a;
  
  public final int b;
  
  public final int c;
  
  public final String d;
  
  public final int e;
  
  public FunctionParameter(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3) {
    this.a = paramString1;
    this.b = paramInt1;
    this.d = paramString2;
    this.c = paramInt3;
    this.e = paramInt2;
  }
  
  public boolean a(FunctionParameter paramFunctionParameter) {
    return StringUtil.areEqual(this.d, paramFunctionParameter.d);
  }
}
