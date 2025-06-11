package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterSequence extends Alter {
  private String e() {
    return this.a.sequenceCreate.c_();
  }
  
  private String f() {
    return this.a.sequenceDrop.c_();
  }
  
  private String g() {
    return this.a.sequenceComment.c_();
  }
  
  private String h() {
    return this.a.sequenceChangeComment.c_();
  }
  
  private String i() {
    return this.a.sequenceDropComment.c_();
  }
  
  public AlterSequence(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (e() != null);
  }
  
  public boolean c() {
    return (g() != null);
  }
  
  public boolean d() {
    return (f() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Sequence paramSequence) {
    AlterScript alterScript = new AlterScript(this.a.dbId);
    alterScript.add((new AlterStatement(paramSyncPair, e()))
        .set(K.l, Dbms.quote(paramSequence.getSchema()))
        .set(K.y, Dbms.quote(paramSequence))
        .set(K.x, paramSequence.ref())
        .set(K.G, paramSequence.getOptions()));
    if (paramSequence.getComment() != null && c())
      alterScript.add(a(paramSyncPair, (Sequence)null, paramSequence)); 
    return alterScript.setSortOrder(800);
  }
  
  public AlterStatement a(SyncPair paramSyncPair, Sequence paramSequence1, Sequence paramSequence2) {
    String str = g();
    if (paramSequence1 != null && paramSequence1.getComment() != null)
      str = (paramSequence2.getComment() != null) ? h() : i(); 
    return (new AlterStatement(paramSyncPair, str))
      .set(K.m, paramSequence2.getSchema().getName())
      .set(K.n, paramSequence2.getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.l, paramSequence2.getSchema().ref())
      .set(K.x, paramSequence2.ref())
      .set(K.y, paramSequence2.getName())
      .set(K.d, SqlUtil.escapeCommentQuotes(paramSequence2.getComment()))
      .set(K.e, Dbms.get(paramSequence2.getDbId()).escapeString(paramSequence2.getComment()))
      .setSortOrder(a());
  }
  
  public AlterScript b(SyncPair paramSyncPair, Sequence paramSequence) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          f()))
        .set(K.y, Dbms.quote(paramSequence))
        .set(K.x, paramSequence.ref())))
      .setSortOrder(12000);
  }
}
