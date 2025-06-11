package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class GeneralToResultStatement extends AbstractStatement {
  private final Envoy b;
  
  private final String c;
  
  private final Result d;
  
  private String e;
  
  private String f;
  
  public GeneralToResultStatement(Envoy paramEnvoy, String paramString, Result paramResult) {
    this.b = paramEnvoy;
    this.c = paramString;
    this.d = paramResult;
  }
  
  public void a() {
    String str1 = Dbms.get(this.b.e()).formatQueryForExecution(this.c);
    String str2 = str1.toLowerCase();
    Log.e("Execute query " + str1);
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try {
      if (str2.startsWith("commit")) {
        this.b.c().commit();
        this.b.j();
        this.e = "Commit done";
      } else if (str2.startsWith("rollback")) {
        this.b.c().rollback();
        this.b.j();
        this.e = "Rollback done";
      } else if (str2.startsWith("desc ") || str1.toLowerCase().startsWith("describe ")) {
        String str = str1.substring(str1.indexOf(' ') + 1);
        (new DescribeStatement(this.b, str, this.d)).a();
        this.e = "Describe done";
      } else {
        this.b.f(str2);
        Statement statement = this.b.a(this.d.p());
        try {
          boolean bool;
          Thread.currentThread().setContextClassLoader(statement.getClass().getClassLoader());
          statement.setFetchSize(10);
          this.b.d(str1);
          if (this.b.a.isMongo() && str2.contains(".insert")) {
            bool = true;
            statement.executeUpdate(str1);
            this.b.a.schemaChanged();
          } else if (str2.startsWith("create") || str2.startsWith("alter") || str2.startsWith("drop") || str2.startsWith("truncate")) {
            if ((Dbms.get(this.b.e())).runDDLUsingStatementExecute.b()) {
              bool = !statement.execute(str1) ? true : false;
            } else {
              bool = true;
              statement.executeUpdate(str1);
              this.b.a.schemaChanged();
            } 
          } else {
            this.b.a.delaySchemaChanged();
            bool = !statement.execute(str1) ? true : false;
          } 
          if (bool) {
            this.e = "Modified " + statement.getUpdateCount() + " rows";
          } else {
            try {
              ResultSet resultSet = statement.getResultSet();
              try {
                if (resultSet != null) {
                  this.d.x();
                  this.d.a(this.b.e(), resultSet);
                  this.e = this.d.E();
                  if (this.d.F() != null)
                    this.e = this.e + "Warnings: " + this.e; 
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
          } 
          SQLWarning sQLWarning = statement.getWarnings();
          while (sQLWarning != null) {
            this.f = (this.f == null) ? sQLWarning.getLocalizedMessage() : (this.f + "\n" + this.f);
            sQLWarning = sQLWarning.getNextWarning();
          } 
          this.b.m();
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
      } 
    } catch (Throwable throwable) {
      this.b.a(throwable);
      throw throwable;
    } finally {
      Thread.currentThread().setContextClassLoader(classLoader);
    } 
  }
  
  public String e() {
    return this.e;
  }
  
  public String f() {
    return this.f;
  }
  
  public String b() {
    return this.c;
  }
  
  public void close() {
    this.b.i();
  }
}
