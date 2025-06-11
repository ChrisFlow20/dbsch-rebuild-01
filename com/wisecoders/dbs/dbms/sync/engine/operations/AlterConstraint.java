package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterConstraint extends Alter {
  private String g() {
    return this.a.constraintCreate.c_();
  }
  
  private String h() {
    return this.a.constraintCreateInline.c_();
  }
  
  private String i() {
    return this.a.constraintRename.c_();
  }
  
  private String j() {
    return this.a.constraintDrop.c_();
  }
  
  private String k() {
    return this.a.constraintComment.c_();
  }
  
  public AlterConstraint(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (h() != null);
  }
  
  public boolean c() {
    return (g() != null);
  }
  
  public boolean d() {
    return (i() != null);
  }
  
  public boolean e() {
    return (j() != null);
  }
  
  public boolean f() {
    return (k() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Constraint paramConstraint) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          g()))
        .set(K.l, paramConstraint.getEntity().getSchema().ref())
        .set(K.m, Dbms.quote(paramConstraint.getEntity().getSchema()))
        .set(K.o, paramConstraint.getEntity().ref())
        .set(K.a, paramConstraint.ref())
        .set(K.W, (paramConstraint.getType() != null) ? paramConstraint.getType() : "CHECK")
        .set(K.u, Dbms.quote(paramConstraint))
        .set(K.G, paramConstraint.getOptions())
        .set(K.i, paramConstraint.getText())))
      .setSortOrder(3000);
  }
  
  public SimpleStatement a(Constraint paramConstraint) {
    return (new SimpleStatement(h()))
      .set(K.l, paramConstraint.getEntity().getSchema().ref())
      .set(K.m, Dbms.quote(paramConstraint.getEntity().getSchema()))
      .set(K.o, paramConstraint.getEntity().ref())
      .set(K.a, paramConstraint.ref())
      .set(K.W, (paramConstraint.getType() != null) ? paramConstraint.getType() : "CHECK")
      .set(K.u, Dbms.quote(paramConstraint))
      .set(K.G, paramConstraint.getOptions())
      .set(K.i, paramConstraint.getText());
  }
  
  public AlterScript a(SyncPair paramSyncPair, Constraint paramConstraint1, Constraint paramConstraint2) {
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          i()))
        .set(K.u, Dbms.quote(paramConstraint1))
        .set(K.a, paramConstraint1.ref())
        .set(K.l, paramConstraint2.getEntity().getSchema().ref())
        .set(K.m, Dbms.quote(paramConstraint1.getEntity().getSchema()))
        .set(K.o, paramConstraint2.getEntity().ref())
        .set(K.g, Dbms.quote(paramConstraint2))
        .set(K.h, paramConstraint2));
  }
  
  public AlterScript b(SyncPair paramSyncPair, Constraint paramConstraint) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          j()))
        .set(K.l, paramConstraint.getEntity().getSchema().ref())
        .set(K.m, Dbms.quote(paramConstraint.getEntity().getSchema()))
        .set(K.o, paramConstraint.getEntity().ref())
        .set(K.a, paramConstraint.ref())
        .set(K.u, Dbms.quote(paramConstraint))))
      .setSortOrder(100);
  }
  
  public AlterStatement b(SyncPair paramSyncPair, Constraint paramConstraint1, Constraint paramConstraint2) {
    return (new AlterStatement(paramSyncPair, k()))
      .set(K.l, paramConstraint2.getEntity().getSchema().ref())
      .set(K.m, paramConstraint2.getEntity().getSchema().getName())
      .set(K.n, paramConstraint2.getEntity().getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.o, paramConstraint2.getEntity().ref())
      .set(K.a, paramConstraint2.ref())
      .set(K.u, Dbms.quote(paramConstraint2))
      .set(K.d, SqlUtil.escapeCommentQuotes(paramConstraint2.getComment()))
      .set(K.e, Dbms.get(paramConstraint2.getDbId()).escapeString(paramConstraint2.getComment()))
      .setSortOrder(a());
  }
}
