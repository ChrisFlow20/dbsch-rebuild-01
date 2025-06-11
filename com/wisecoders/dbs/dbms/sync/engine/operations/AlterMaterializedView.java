package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterMaterializedView extends Alter {
  private String d() {
    return this.a.materializedViewDrop.c_();
  }
  
  private String e() {
    return this.a.materializedViewComment.c_();
  }
  
  public AlterMaterializedView(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (d() != null);
  }
  
  public boolean c() {
    return (e() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, MaterializedView paramMaterializedView) {
    AlterScript alterScript = new AlterScript(this.a.dbId);
    alterScript.add((new AlterStatement(paramSyncPair, paramMaterializedView.getScript(), false, true))
        .set(K.a, Dbms.quote(paramMaterializedView))
        .set(K.C, paramMaterializedView.ref())
        .set(K.D, paramMaterializedView.ref())
        .set(K.z, paramMaterializedView.ref())
        .set(K.l, paramMaterializedView.schema.ref()));
    if (paramMaterializedView.getComment() != null && this.a.alterMaterializedView.c())
      alterScript.add(this.a.alterMaterializedView.a(paramSyncPair, (MaterializedView)null, paramMaterializedView)); 
    return alterScript.setSortOrder(8000);
  }
  
  public AlterScript b(SyncPair paramSyncPair, MaterializedView paramMaterializedView) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          d()))
        .set(K.a, Dbms.quote(paramMaterializedView))
        .set(K.C, paramMaterializedView.ref())
        .set(K.D, paramMaterializedView.ref())
        .set(K.z, paramMaterializedView.ref())))
      .setSortOrder(300);
  }
  
  public AlterStatement a(SyncPair paramSyncPair, MaterializedView paramMaterializedView1, MaterializedView paramMaterializedView2) {
    return (new AlterStatement(paramSyncPair, e()))
      .set(K.a, Dbms.quote(paramMaterializedView2))
      .set(K.C, paramMaterializedView2.ref())
      .set(K.D, paramMaterializedView2.ref())
      .set(K.z, paramMaterializedView2.ref())
      .set(K.m, paramMaterializedView2.schema.getName())
      .set(K.n, paramMaterializedView2.schema.getCatalogName(paramSyncPair.getMapping()))
      .set(K.d, SqlUtil.escapeCommentQuotes(paramMaterializedView2.getComment()))
      .set(K.e, Dbms.get(paramMaterializedView2.getDbId()).escapeString(paramMaterializedView2.getComment()))
      .setSortOrder(a());
  }
}
