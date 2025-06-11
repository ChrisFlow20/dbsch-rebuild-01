package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectToResultStatement extends AbstractStatement {
  private final String b;
  
  private final Result c;
  
  private String d;
  
  private int e;
  
  private final Envoy f;
  
  public SelectToResultStatement(Envoy paramEnvoy, String paramString, Result paramResult, int paramInt) {
    this.f = paramEnvoy;
    this.b = paramString;
    this.c = paramResult;
    this.e = paramInt;
  }
  
  public void a() {
    String str1 = Dbms.get(this.f.e()).formatQueryForExecution(this.b);
    String str2 = str1.toLowerCase();
    Log.e("Execute query " + str1);
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    this.f.f(str2);
    try {
      Statement statement = this.f.a(false);
      try {
        Thread.currentThread().setContextClassLoader(statement.getClass().getClassLoader());
        statement.setFetchSize(10);
        this.f.d(str1);
        if (statement.execute(str1)) {
          try {
            ResultSet resultSet = statement.getResultSet();
            try {
              if (resultSet != null) {
                if (this.e != 0)
                  while (this.e-- > 0 && resultSet.next()); 
                this.c.x();
                this.c.a(this.f.e(), resultSet);
                this.d = this.c.E();
              } 
              if (resultSet != null)
                resultSet.close(); 
            } catch (Throwable throwable) {
              if (resultSet != null)
                try {
                  resultSet.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
          } catch (SQLException sQLException) {
            Log.b(sQLException);
            throw sQLException;
          } 
        } else {
          this.d = "Modified " + statement.getUpdateCount() + " rows.";
        } 
        this.f.m();
        if (statement != null)
          statement.close(); 
      } catch (Throwable throwable) {
        if (statement != null)
          try {
            statement.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } finally {
      Thread.currentThread().setContextClassLoader(classLoader);
    } 
  }
  
  public String e() {
    return this.d;
  }
  
  public String b() {
    return this.b;
  }
  
  public void close() {
    this.f.i();
  }
}
