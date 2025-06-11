package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.sys.Log;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UpdateStatement extends SelectStatement {
  private int b;
  
  private Statement g;
  
  public UpdateStatement(Envoy paramEnvoy) {
    super(paramEnvoy);
  }
  
  public UpdateStatement(Envoy paramEnvoy, String paramString, Object... paramVarArgs) {
    super(paramEnvoy, paramString, paramVarArgs);
  }
  
  public void a() {
    this.f.f(this.c);
    Log.e("Execute Direct Update " + this.c);
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try {
      if (this.d.isEmpty()) {
        this.g = this.f.c().createStatement();
        Thread.currentThread().setContextClassLoader(this.g.getClass().getClassLoader());
        this.f.a(this.c, this.e);
        String str = this.c.toLowerCase();
        if ((Dbms.get(this.f.e())).runDDLUsingStatementExecute.b() && (str
          .startsWith("alter") || str.startsWith("create") || str.startsWith("drop") || str.startsWith("truncate") || str.startsWith("use"))) {
          this.g.execute(this.c);
          this.b = 0;
        } else {
          this.b = this.g.executeUpdate(this.c);
        } 
      } else {
        this.g = a(this.f.c());
        Thread.currentThread().setContextClassLoader(this.g.getClass().getClassLoader());
        this.f.a(this.c, this.e);
        this.b = ((PreparedStatement)this.g).executeUpdate();
      } 
      this.g.close();
      this.g = null;
    } catch (Throwable throwable) {
      this.f.a(throwable);
      throw throwable;
    } finally {
      Thread.currentThread().setContextClassLoader(classLoader);
    } 
  }
  
  public void m() {
    if (this.g == null)
      this.g = this.f.c().prepareStatement(this.c); 
    a((PreparedStatement)this.g);
    ((PreparedStatement)this.g).addBatch();
  }
  
  public void n() {
    this.g.executeBatch();
  }
  
  public int o() {
    return this.b;
  }
  
  public void close() {
    this.f.i();
    if (this.g != null) {
      this.g.close();
      this.g = null;
    } 
  }
}
