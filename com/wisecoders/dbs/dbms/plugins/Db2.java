package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db2 extends Dbms {
  public Db2() {
    super("Db2");
  }
  
  public Db2(String paramString) {
    super(paramString);
  }
  
  public String getMessageFromException(Throwable paramThrowable, Connector paramConnector) {
    String str = super.getMessageFromException(paramThrowable, paramConnector);
    try {
      if (paramConnector != null && paramThrowable != null) {
        Class<?> clazz = paramThrowable.getClass().getClassLoader().loadClass("com.ibm.db2.jcc.DB2Sqlca");
        Method method = paramThrowable.getClass().getDeclaredMethod("getSqlca", (Class[])null);
        if (clazz != null && method != null) {
          Object object = method.invoke(paramThrowable, (Object[])null);
          if (object != null) {
            Method method1 = clazz.getDeclaredMethod("getMessage", (Class[])null);
            if (method1 != null) {
              Object object1 = method1.invoke(object, (Object[])null);
              str = String.valueOf(object1) + "\n" + String.valueOf(object1);
            } 
          } 
        } 
      } 
    } catch (Throwable throwable) {
      Log.c("Cannot format Db2 exception: " + String.valueOf(throwable));
    } 
    return str;
  }
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    if (paramStatement != null && paramSQLException != null) {
      Class<?> clazz = paramSQLException.getClass().getClassLoader().loadClass("com.ibm.db2.jcc.DB2Statement");
      if (clazz != null) {
        Method method = clazz.getDeclaredMethod("getIDSSQLStatementOffSet", (Class[])null);
        if (method != null) {
          Object object = method.invoke(paramStatement, (Object[])null);
          if (object instanceof Integer)
            return ((Integer)object).intValue(); 
        } 
      } 
    } 
    return -1;
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    try {
      paramEnvoy.g("DELETE FROM explain_instance");
    } catch (SQLException sQLException) {
      paramEnvoy.g("CALL sysproc.sysinstallobjects('EXPLAIN','C',NULL,CURRENT SCHEMA)");
    } 
    paramEnvoy.g("DELETE FROM explain_instance");
    paramEnvoy.p();
    paramEnvoy.b("EXPLAIN PLAN FOR " + paramString, new Result());
    Result result = new Result();
    paramEnvoy.b((Dbms.get(this.dbId)).explainPlanView.c_(), result);
    result.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result);
    for (byte b = 0; b < result.A(); b++) {
      Number number1 = (Number)result.a(b, 0), number2 = (Number)result.a(b, 1), number3 = (Number)result.a(b, 5);
      String str1 = (String)result.a(b, 2), str2 = (String)result.a(b, 3), str3 = (String)result.a(b, 4);
      StringBuilder stringBuilder = new StringBuilder("");
      if (str1 != null)
        stringBuilder.append(str1).append(" "); 
      if (str2 != null)
        stringBuilder.append(str2).append("."); 
      if (str3 != null)
        stringBuilder.append(str3); 
      executionPlan.a((number1 != null) ? number1.intValue() : -1, 
          (number2 != null) ? number2.intValue() : -1, stringBuilder
          .toString(), str1, str3, 
          (number3 != null) ? number3.doubleValue() : -1.0D, result
          .j(b));
    } 
    return executionPlan;
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "GeneratedColumns");
    SelectStatement selectStatement = paramImporter.d.a("SELECT tabname, colname, generated FROM SYSCAT.COLUMNS where generated is not null and tabschema=?", new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1);
        String str2 = resultSet.getString(2);
        String str3 = resultSet.getString(3);
        Log.d("Import Generated columns " + str1 + " Column " + str2);
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Column column = table.getColumnByNameOrPath(str2);
          if (column != null)
            if ("A".equals(str3)) {
              column.setOptions("GENERATE ALWAYS AS IDENTITY");
            } else if ("D".equals(str3)) {
              column.setOptions("GENERATE BY DEFAULT AS IDENTITY");
            }  
        } 
        Log.j();
      } 
      if (selectStatement != null)
        selectStatement.close(); 
    } catch (Throwable throwable) {
      if (selectStatement != null)
        try {
          selectStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
