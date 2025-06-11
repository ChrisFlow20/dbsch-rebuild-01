package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;

public class Mimer extends Dbms {
  public Mimer() {
    super("Mimer");
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    Result result1 = new Result();
    paramEnvoy.b("explain " + paramString, result1);
    result1.q();
    Result result2 = new Result();
    result2.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result2);
    for (byte b = 0; b < result2.A(); b++) {
      String str1 = (String)result2.a(b, result2.a("OPERATION"));
      String str2 = (String)result2.a(b, result2.a("TABLE"));
      if (str2 == null)
        str2 = (String)result2.a(b, result2.a("INDEX")); 
      StringBuilder stringBuilder = new StringBuilder(" ");
      if (str1 != null)
        stringBuilder.append(str1).append(" "); 
      if (str2 != null)
        stringBuilder.append(str2); 
      executionPlan.a(b, 
          Integer.parseInt(String.valueOf(result2.a(b, result2.a("PARENT")))), stringBuilder
          .toString(), str1, str2, -1.0D, result2.j(b));
    } 
    return executionPlan;
  }
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    Class<?> clazz = paramSQLException.getClass().getClassLoader().loadClass("com.mimer.jdbc.SQLException");
    if (clazz != null) {
      Method method = clazz.getDeclaredMethod("getErrorPosition", (Class[])null);
      if (method != null) {
        Object object = method.invoke(paramSQLException, (Object[])null);
        if (object instanceof Integer)
          return Integer.valueOf(((Integer)object).intValue()).intValue() - 1; 
      } 
    } 
    return -1;
  }
}
