package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterForeignKey extends Alter {
  private String o() {
    return this.a.fkCreate.c_();
  }
  
  private String p() {
    return this.a.fkCreateInline.c_();
  }
  
  private String q() {
    return this.a.fkRename.c_();
  }
  
  private String r() {
    return this.a.fkComment.c_();
  }
  
  private String s() {
    return this.a.fkChangeComment.c_();
  }
  
  private String t() {
    return this.a.fkDropComment.c_();
  }
  
  private String u() {
    return this.a.fkDrop.c_();
  }
  
  private String v() {
    return this.a.fkDeleteCascade.c_();
  }
  
  private String w() {
    return this.a.fkDeleteRestrict.c_();
  }
  
  private String x() {
    return this.a.fkDeleteSetNull.c_();
  }
  
  private String y() {
    return this.a.fkDeleteNoAction.c_();
  }
  
  private String z() {
    return this.a.fkUpdateCascade.c_();
  }
  
  private String A() {
    return this.a.fkUpdateRestrict.c_();
  }
  
  private String B() {
    return this.a.fkUpdateSetNull.c_();
  }
  
  private String C() {
    return this.a.fkUpdateNoAction.c_();
  }
  
  public AlterForeignKey(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (o() != null);
  }
  
  public boolean c() {
    return (p() != null);
  }
  
  public boolean d() {
    return (q() != null);
  }
  
  public boolean e() {
    return (r() != null);
  }
  
  public boolean f() {
    return (u() != null);
  }
  
  public String a(ForeignKeyAction paramForeignKeyAction) {
    switch (AlterForeignKey$1.a[paramForeignKeyAction.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
    } 
    return 



      
      null;
  }
  
  public String b(ForeignKeyAction paramForeignKeyAction) {
    switch (AlterForeignKey$1.a[paramForeignKeyAction.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
    } 
    return 



      
      null;
  }
  
  public AlterScript a(SyncPair paramSyncPair, ForeignKey paramForeignKey) {
    AlterScript alterScript = new AlterScript(this.a.dbId);
    alterScript.add((new AlterStatement(paramSyncPair, o()))
        .set(K.o, paramForeignKey.getEntity().ref())
        .set(K.m, Dbms.quote(paramForeignKey.getEntity().getSchema()))
        .set(K.ae, paramForeignKey.getTargetEntity().ref())
        .set(K.a, Dbms.quote(paramForeignKey))
        .set(K.ac, this.a.identifierCommaList(paramForeignKey.getSourceAttributes()))
        .set(K.af, this.a.identifierCommaList(paramForeignKey.getTargetAttributes()))
        .set(K.ai, a(paramForeignKey.getDeleteAction()))
        .set(K.aj, b(paramForeignKey.getUpdateAction()))
        .set(K.an, paramForeignKey)
        .set(K.G, paramForeignKey.getOptions())
        .set(K.ao, paramSyncPair));
    alterScript.setSortOrder(6000);
    return alterScript;
  }
  
  public SimpleStatement a(ForeignKey paramForeignKey) {
    return (new SimpleStatement(p()))
      .set(K.o, paramForeignKey.getEntity().ref())
      .set(K.ae, paramForeignKey.getTargetEntity().ref())
      .set(K.m, Dbms.quote(paramForeignKey.getEntity().getSchema()))
      .set(K.a, Dbms.quote(paramForeignKey))
      .set(K.ac, this.a.identifierCommaList(paramForeignKey.getSourceAttributes()))
      .set(K.af, this.a.identifierCommaList(paramForeignKey.getTargetAttributes()))
      .set(K.ai, a(paramForeignKey.getDeleteAction()))
      .set(K.aj, b(paramForeignKey.getUpdateAction()))
      .set(K.G, paramForeignKey.getOptions());
  }
  
  public AlterScript a(SyncPair paramSyncPair, ForeignKey paramForeignKey1, ForeignKey paramForeignKey2) {
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          q()))
        .set(K.m, Dbms.quote(paramForeignKey1.getEntity().getSchema()))
        .set(K.o, paramForeignKey1.getEntity().ref())
        .set(K.a, Dbms.quote(paramForeignKey1))
        .set(K.g, Dbms.quote(paramForeignKey2))
        .set(K.h, paramForeignKey2));
  }
  
  public AlterStatement b(SyncPair paramSyncPair, ForeignKey paramForeignKey1, ForeignKey paramForeignKey2) {
    String str = r();
    if (paramForeignKey1 != null && paramForeignKey1.getComment() != null)
      str = (paramForeignKey2.getComment() != null) ? s() : t(); 
    return (new AlterStatement(paramSyncPair, str))
      .set(K.l, paramForeignKey2.getEntity().getSchema().ref())
      .set(K.m, Dbms.quote(paramForeignKey2.getEntity().getSchema()))
      .set(K.n, paramForeignKey2.getEntity().getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.o, paramForeignKey2.getEntity().ref())
      .set(K.p, paramForeignKey2.getEntity().getName())
      .set(K.a, paramForeignKey2.getName())
      .set(K.d, SqlUtil.escapeCommentQuotes(paramForeignKey2.getComment()))
      .set(K.e, Dbms.get(paramForeignKey2.getDbId()).escapeString(paramForeignKey2.getComment()))
      .setSortOrder(a());
  }
  
  public AlterScript b(SyncPair paramSyncPair, ForeignKey paramForeignKey) {
    AlterScript alterScript = new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, u())).set(K.m, Dbms.quote(paramForeignKey.getEntity().getSchema())).set(K.o, paramForeignKey.getEntity().ref()).set(K.a, Dbms.quote(paramForeignKey)).set(K.ac, this.a.identifierCommaList(paramForeignKey.getSourceAttributes())).set(K.an, paramForeignKey));
    alterScript.setSortOrder(100);
    return alterScript;
  }
  
  public boolean g() {
    return (v() != null);
  }
  
  public boolean h() {
    return (w() != null);
  }
  
  public boolean i() {
    return (y() != null);
  }
  
  public boolean j() {
    return (x() != null);
  }
  
  public boolean k() {
    return (z() != null);
  }
  
  public boolean l() {
    return (A() != null);
  }
  
  public boolean m() {
    return (C() != null);
  }
  
  public boolean n() {
    return (B() != null);
  }
}
