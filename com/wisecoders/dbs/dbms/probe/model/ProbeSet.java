package com.wisecoders.dbs.dbms.probe.model;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import java.util.ArrayList;
import java.util.List;

public abstract class ProbeSet {
  public final String a;
  
  public final String b;
  
  private final List c = new ArrayList();
  
  public ProbeSet(String paramString1, String paramString2) {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public void a(SyncPair paramSyncPair, String paramString) {
    ProbeTransaction probeTransaction = new ProbeTransaction(paramString);
    probeTransaction.a(paramSyncPair.generateCommitScript(this.a, null, SyncSide.right, true));
    this.c.add(probeTransaction);
  }
  
  public void a(ProbeSync paramProbeSync, String paramString) {
    ProbeTransaction probeTransaction = new ProbeTransaction(paramString);
    probeTransaction.a(paramProbeSync);
    this.c.add(probeTransaction);
  }
  
  public String toString() {
    return this.b;
  }
  
  public void a(Envoy paramEnvoy) {
    for (ProbeTransaction probeTransaction : this.c)
      probeTransaction.a(paramEnvoy); 
  }
}
