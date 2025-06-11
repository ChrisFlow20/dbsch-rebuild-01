package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;

public class HSql extends Dbms {
  public HSql() {
    super("HSql");
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    Envoy envoy = paramConnector.startEnvoy("HSql create database");
    try {
      paramConnector.setInstance(paramString1);
      envoy.p();
      if (envoy != null)
        envoy.close(); 
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    paramConnector.closeAllEnvoysAndSsh();
  }
}
