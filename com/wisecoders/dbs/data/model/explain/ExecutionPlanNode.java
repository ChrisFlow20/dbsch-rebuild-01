package com.wisecoders.dbs.data.model.explain;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;

public class ExecutionPlanNode {
  public final ExecutionPlanNode d;
  
  private final Object[] a;
  
  public List e = new ArrayList();
  
  public final String f;
  
  public final String g;
  
  public String h;
  
  public int i;
  
  public int j;
  
  private double b;
  
  private int c;
  
  private int k;
  
  public ExecutionPlanNode(String paramString1, String paramString2) {
    this.f = paramString1;
    this.d = null;
    this.g = null;
    this.a = null;
    this.h = paramString2;
  }
  
  public ExecutionPlanNode(ExecutionPlanNode paramExecutionPlanNode, String paramString1, String paramString2, String paramString3, double paramDouble, Object[] paramArrayOfObject) {
    this.f = paramString1;
    this.d = paramExecutionPlanNode;
    this.g = paramString2;
    this.a = paramArrayOfObject;
    this.h = paramString3;
    this.b = paramDouble;
    this.c = 1;
    this.k = 2;
    paramExecutionPlanNode.e.add(this);
  }
  
  public void a(int paramInt) {
    this.i = paramInt;
  }
  
  public int b() {
    return this.i;
  }
  
  public String toString() {
    return this.f;
  }
  
  public boolean c() {
    return StringUtil.isFilledTrim(this.h);
  }
  
  protected int a(int paramInt, double paramDouble) {
    int i = paramInt;
    int j;
    for (j = this.e.size() - 1; j > -1; j--) {
      ExecutionPlanNode executionPlanNode = this.e.get(j);
      i = executionPlanNode.a(i, paramDouble);
    } 
    if (c()) {
      j = i + (int)(this.b * paramDouble);
      this.c = i;
      this.k = j;
      return j;
    } 
    this.c = paramInt;
    this.k = i;
    return i;
  }
  
  protected int b(int paramInt) {
    for (int i = this.e.size() - 1; i > -1; i--) {
      ExecutionPlanNode executionPlanNode = this.e.get(i);
      paramInt = executionPlanNode.b(paramInt);
    } 
    if (c()) {
      this.j = paramInt;
      paramInt++;
    } 
    return paramInt;
  }
  
  public int d() {
    return this.j;
  }
  
  public void a(double paramDouble) {
    this.b = paramDouble;
  }
  
  public double e() {
    return this.b;
  }
  
  public String f() {
    return this.h;
  }
  
  public int g() {
    return this.c;
  }
  
  public int h() {
    return this.k;
  }
}
