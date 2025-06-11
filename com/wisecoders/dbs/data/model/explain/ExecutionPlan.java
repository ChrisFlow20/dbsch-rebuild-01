package com.wisecoders.dbs.data.model.explain;

import com.wisecoders.dbs.data.model.result.Result;
import java.util.ArrayList;
import java.util.List;

public class ExecutionPlan extends ExecutionPlanNode {
  public final List a = new ArrayList();
  
  public final String[] b;
  
  public final Class[] c;
  
  public ExecutionPlan(Result paramResult) {
    super("Execution Plan", null);
    int i = paramResult.C();
    this.b = new String[i];
    this.c = new Class[i];
    for (byte b = 0; b < i; b++) {
      this.b[b] = paramResult.k(b);
      this.c[b] = paramResult.f(b);
    } 
  }
  
  public ExecutionPlan(String[] paramArrayOfString, Class[] paramArrayOfClass) {
    super("Execution Plan", null);
    this.b = paramArrayOfString;
    this.c = paramArrayOfClass;
  }
  
  public void a(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, double paramDouble, Object[] paramArrayOfObject) {
    ExecutionPlanNode executionPlanNode1 = this;
    for (ExecutionPlanNode executionPlanNode : this.a) {
      if (executionPlanNode.b() == paramInt2)
        executionPlanNode1 = executionPlanNode; 
    } 
    ExecutionPlanNode executionPlanNode2 = new ExecutionPlanNode(executionPlanNode1, paramString1, paramString2, paramString3, paramDouble, paramArrayOfObject);
    executionPlanNode2.a(paramInt1);
    this.a.add(executionPlanNode2);
  }
  
  public void a(int paramInt, String paramString1, String paramString2, String paramString3, double paramDouble, Object[] paramArrayOfObject) {
    ExecutionPlanNode executionPlanNode1 = null;
    for (int i = this.a.size() - 1; i > -1; i--) {
      ExecutionPlanNode executionPlanNode = this.a.get(i);
      if (executionPlanNode1 == null && executionPlanNode.b() < paramInt)
        executionPlanNode1 = executionPlanNode; 
    } 
    if (executionPlanNode1 == null)
      executionPlanNode1 = this; 
    ExecutionPlanNode executionPlanNode2 = new ExecutionPlanNode(executionPlanNode1, paramString1, paramString2, paramString3, paramDouble, paramArrayOfObject);
    executionPlanNode2.a(paramInt);
    this.a.add(executionPlanNode2);
  }
  
  private int i() {
    byte b = 0;
    for (ExecutionPlanNode executionPlanNode : this.a) {
      if (executionPlanNode.c())
        b++; 
    } 
    return b;
  }
  
  private double j() {
    double d = 0.0D;
    for (ExecutionPlanNode executionPlanNode : this.a) {
      if (executionPlanNode.c() && executionPlanNode.e() > 0.0D)
        d += executionPlanNode.e(); 
    } 
    return (i() > 0) ? (d / i()) : 0.0D;
  }
  
  private void k() {
    double d = (j() == 0.0D) ? 1.0D : j();
    for (ExecutionPlanNode executionPlanNode : this.a) {
      if (executionPlanNode.c() && executionPlanNode.e() <= 0.0D)
        executionPlanNode.a(d); 
    } 
  }
  
  public void a() {
    k();
    double d = (i() > 0) ? (100.0D / i() * j()) : 1.0D;
    a(0, d);
    b(0);
  }
}
