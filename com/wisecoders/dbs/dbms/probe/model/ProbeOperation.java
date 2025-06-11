package com.wisecoders.dbs.dbms.probe.model;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;

public abstract class ProbeOperation {
  private String a;
  
  private ProbeStatus b = ProbeStatus.a;
  
  public void a(String paramString) {
    this.a = paramString;
  }
  
  public boolean a() {
    return (this.a != null);
  }
  
  public String b() {
    return this.a;
  }
  
  public void a(ProbeStatus paramProbeStatus) {
    this.b = paramProbeStatus;
  }
  
  public ProbeStatus c() {
    return this.b;
  }
  
  public abstract void a(Envoy paramEnvoy);
}
