package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterSchema extends Alter {
  private String f() {
    return this.a.schemaCreate.c_();
  }
  
  private String g() {
    return this.a.schemaRename.c_();
  }
  
  private String h() {
    return this.a.schemaDrop.c_();
  }
  
  private String i() {
    return this.a.schemaComment.c_();
  }
  
  private String j() {
    return this.a.schemaChangeComment.c_();
  }
  
  private String k() {
    return this.a.schemaDropComment.c_();
  }
  
  public AlterSchema(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (f() != null);
  }
  
  public boolean c() {
    return (g() != null);
  }
  
  public boolean d() {
    return (h() != null);
  }
  
  public boolean e() {
    return (i() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Schema paramSchema) {
    AlterScript alterScript = new AlterScript(this.a.dbId);
    if (paramSchema.getPreScript() != null)
      alterScript.add((new AlterStatement(paramSyncPair, paramSchema.getPreScript())).set(K.l, paramSchema.ref())); 
    alterScript.add((new AlterStatement(paramSyncPair, 
          f()))
        .set(K.m, Dbms.quote(paramSchema))
        .set(K.n, paramSchema.getCatalogName())
        .set(K.l, paramSchema.ref())
        .set(K.F, paramSchema.getSpecificationOptions())
        .set(K.G, paramSchema.getOptions()));
    if (paramSchema.getPostScript() != null)
      alterScript.add((new AlterStatement(paramSyncPair, paramSchema.getPostScript())).set(K.l, paramSchema.ref())); 
    return alterScript.setSortOrder(700);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Schema paramSchema1, Schema paramSchema2) {
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          g()))
        .set(K.m, Dbms.quote(paramSchema1))
        .set(K.n, paramSchema1.getCatalogName())
        .set(K.l, paramSchema1.ref())
        .set(K.g, paramSchema2.ref())
        .set(K.h, paramSchema2));
  }
  
  public AlterScript b(SyncPair paramSyncPair, Schema paramSchema) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          h()))
        .set(K.m, Dbms.quote(paramSchema))
        .set(K.n, paramSchema.getCatalogName())
        .set(K.l, paramSchema.ref())))
      .setSortOrder(400);
  }
  
  public AlterStatement b(SyncPair paramSyncPair, Schema paramSchema1, Schema paramSchema2) {
    String str = i();
    if (paramSchema1 != null && paramSchema1.getComment() != null)
      str = (paramSchema2.getComment() != null) ? j() : k(); 
    return (new AlterStatement(paramSyncPair, str))
      .set(K.l, paramSchema2.getSchema().ref())
      .set(K.m, paramSchema2.getSchema().getName())
      .set(K.n, paramSchema2.getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.a, paramSchema2.getSchema().getName())
      .set(K.d, SqlUtil.escapeCommentQuotes(paramSchema2.getComment()))
      .set(K.e, Dbms.get(paramSchema2.getDbId()).escapeString(paramSchema2.getComment()));
  }
}
