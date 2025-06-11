package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterView extends Alter {
  private String d() {
    return this.a.viewDrop.c_();
  }
  
  private String e() {
    return this.a.viewComment.c_();
  }
  
  public AlterView(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (d() != null);
  }
  
  public boolean c() {
    return (e() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, View paramView) {
    AlterScript alterScript = new AlterScript(this.a.dbId);
    alterScript.add((new AlterStatement(paramSyncPair, paramView.getScript(), false, true))
        .set(K.a, Dbms.quote(paramView))
        .set(K.B, Dbms.quote(paramView))
        .set(K.A, paramView.ref())
        .set(K.C, paramView.ref())
        .set(K.D, paramView.ref())
        .set(K.l, paramView.schema.ref())
        .set(K.m, Dbms.quote(paramView.schema)));
    if (paramView.getComment() != null && c())
      alterScript.add(a(paramSyncPair, (View)null, paramView)); 
    return alterScript.setSortOrder(8000);
  }
  
  public AlterScript b(SyncPair paramSyncPair, View paramView) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          d()))
        .set(K.A, paramView.ref())
        .set(K.C, paramView.ref())
        .set(K.D, paramView.ref())
        .set(K.m, Dbms.quote(paramView.schema))
        .set(K.a, Dbms.quote(paramView))
        .set(K.B, Dbms.quote(paramView))))
      .setSortOrder(300);
  }
  
  public AlterStatement a(SyncPair paramSyncPair, View paramView1, View paramView2) {
    return (new AlterStatement(paramSyncPair, e()))
      .set(K.l, paramView2.schema.ref())
      .set(K.m, paramView2.schema.getName())
      .set(K.n, paramView2.schema.getCatalogName(paramSyncPair.getMapping()))
      .set(K.a, Dbms.quote(paramView2))
      .set(K.B, Dbms.quote(paramView2))
      .set(K.A, paramView2.ref())
      .set(K.C, paramView2.ref())
      .set(K.D, paramView2.ref())
      .set(K.d, SqlUtil.escapeCommentQuotes(paramView2.getComment()))
      .set(K.e, Dbms.get(paramView2.getDbId()).escapeString(paramView2.getComment()))
      .setSortOrder(a());
  }
}
