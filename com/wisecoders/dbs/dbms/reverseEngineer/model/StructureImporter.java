package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Log;

public class StructureImporter {
  public final String b;
  
  public final Envoy c;
  
  private int a;
  
  private int d;
  
  public StructureImporter(Schema paramSchema, Envoy paramEnvoy) {
    this.b = paramEnvoy.e();
    this.c = paramEnvoy;
    paramEnvoy.b(false);
    a(paramSchema);
  }
  
  public boolean b() {
    return false;
  }
  
  public void a() {
    this.a = Math.min(++this.a, this.d);
  }
  
  public void a(String paramString) {}
  
  public void a(Schema paramSchema) {
    Log.c("Start importing schema '" + String.valueOf(paramSchema) + "' structure.");
    this.d = 4;
    Dbms dbms = Dbms.get(this.b);
    try {
      dbms.loadDbVersion(this, paramSchema.project);
    } catch (Exception exception) {
      Log.b(exception);
    } 
    a("MongoDb".equals(dbms.dbId) ? "List Collections..." : "List Tables ...");
    dbms.listTableAndViewsNames(this, paramSchema);
    try {
      a("List Procedures ...");
      dbms.listProcedureNames(this, paramSchema);
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    try {
      a("List Functions ...");
      dbms.listFunctionNames(this, paramSchema);
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    try {
      a("List Triggers ...");
      dbms.listTriggerNames(this, paramSchema);
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    try {
      a("List Rules ...");
      dbms.listRuleNames(this, paramSchema);
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    try {
      a("List Sequences ...");
      dbms.listSequences(this, paramSchema);
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    Log.k();
    Log.c("Finish importing schema '" + String.valueOf(paramSchema) + "' structure.");
  }
  
  public int c() {
    return this.a;
  }
  
  public int d() {
    return this.d;
  }
}
