package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterUserDataType extends Alter {
  private String e() {
    return this.a.userDataTypeDrop.c_();
  }
  
  private String f() {
    return this.a.userDataTypeRename.c_();
  }
  
  private String g() {
    return this.a.userDataTypeComment.c_();
  }
  
  public AlterUserDataType(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (e() != null);
  }
  
  public boolean c() {
    return (f() != null);
  }
  
  public boolean d() {
    return (g() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, UserDataType paramUserDataType) {
    AlterScript alterScript = new AlterScript(this.a.dbId);
    alterScript.add((new AlterStatement(paramSyncPair, paramUserDataType.getScript(), false, true))
        .set(K.t, paramUserDataType.ref())
        .set(K.a, paramUserDataType.getName())
        .set(K.l, paramUserDataType.schema.ref())
        .set(K.m, paramUserDataType.schema.getName()));
    if (paramUserDataType.getComment() != null && d())
      alterScript.add(b(paramSyncPair, (UserDataType)null, paramUserDataType)); 
    return alterScript.setSortOrder(900);
  }
  
  public AlterScript a(SyncPair paramSyncPair, UserDataType paramUserDataType1, UserDataType paramUserDataType2) {
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          f()))
        .set(K.t, paramUserDataType1.ref())
        .set(K.a, paramUserDataType1.getName())
        .set(K.l, paramUserDataType2.getEntity().getSchema().ref())
        .set(K.m, paramUserDataType2.getEntity().getSchema().getName())
        .set(K.al, paramUserDataType2.getScript())
        .set(K.g, paramUserDataType2.ref())
        .set(K.h, paramUserDataType2));
  }
  
  public AlterStatement b(SyncPair paramSyncPair, UserDataType paramUserDataType1, UserDataType paramUserDataType2) {
    return (new AlterStatement(paramSyncPair, g()))
      .set(K.m, paramUserDataType2.getEntity().getSchema().getName())
      .set(K.n, paramUserDataType2.getEntity().getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.l, paramUserDataType2.getEntity().getSchema().ref())
      .set(K.m, paramUserDataType2.getEntity().getSchema().getName())
      .set(K.t, paramUserDataType2.ref())
      .set(K.a, paramUserDataType2.getName())
      .set(K.al, paramUserDataType2.getScript())
      .set(K.d, SqlUtil.escapeCommentQuotes(paramUserDataType2.getComment()))
      .set(K.e, Dbms.get(paramUserDataType2.getDbId()).escapeString(paramUserDataType2.getComment()))
      .setSortOrder(a());
  }
  
  public AlterScript b(SyncPair paramSyncPair, UserDataType paramUserDataType) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          e()))
        .set(K.t, paramUserDataType.ref())
        .set(K.a, paramUserDataType.getName())
        .set(K.al, paramUserDataType.getScript())
        .set(K.l, paramUserDataType.schema.ref())
        .set(K.m, paramUserDataType.schema.getName())))
      .setSortOrder(400);
  }
}
