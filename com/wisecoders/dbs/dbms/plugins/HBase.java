package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Schema;
import java.util.ArrayList;
import java.util.List;

public class HBase extends Dbms {
  public HBase() {
    super("HBase");
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    Result result = new Result();
    paramEnvoy.b("EXPLAIN " + paramString, result);
    result.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result);
    int i = result.A();
    for (byte b = 0; b < i; b++) {
      Object[] arrayOfObject = result.j(b);
      String str = (String)arrayOfObject[0];
      executionPlan.a(b, -1, str, "", "", (1 / i), arrayOfObject);
    } 
    return executionPlan;
  }
  
  public List importFks(Importer paramImporter, Schema paramSchema) {
    return new ArrayList();
  }
}
