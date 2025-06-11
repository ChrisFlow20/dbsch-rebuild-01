package com.wisecoders.dbs.dbms.probe.model;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import java.util.ArrayList;
import java.util.List;

public class ProbeTransaction {
  private final String a;
  
  private final List b = new ArrayList();
  
  public ProbeTransaction(String paramString) {
    this.a = paramString;
  }
  
  public void a(AlterScript paramAlterScript) {
    for (AlterStatement alterStatement : paramAlterScript.statements)
      this.b.add(new ProbeAlter(alterStatement)); 
  }
  
  public void a(ProbeSync paramProbeSync) {
    this.b.add(paramProbeSync);
  }
  
  public String toString() {
    return this.a;
  }
  
  public void a(Envoy paramEnvoy) {
    try {
      for (ProbeOperation probeOperation : this.b) {
        probeOperation.a(paramEnvoy);
        paramEnvoy.p();
      } 
    } catch (Exception exception) {
      paramEnvoy.q();
    } 
  }
}
