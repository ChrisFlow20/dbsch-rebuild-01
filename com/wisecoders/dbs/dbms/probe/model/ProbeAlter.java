package com.wisecoders.dbs.dbms.probe.model;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import java.sql.SQLException;

public class ProbeAlter extends ProbeOperation {
  private final AlterStatement a;
  
  public ProbeAlter(AlterStatement paramAlterStatement) {
    this.a = paramAlterStatement;
  }
  
  public String toString() {
    return this.a.toString();
  }
  
  public void a(Envoy paramEnvoy) {
    try {
      a(ProbeStatus.b);
      paramEnvoy.g(this.a.toString());
      a(ProbeStatus.c);
      paramEnvoy.p();
    } catch (SQLException sQLException) {
      a(sQLException.toString());
      a(ProbeStatus.d);
      throw sQLException;
    } 
  }
}
