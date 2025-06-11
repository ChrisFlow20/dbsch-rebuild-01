package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterIndex extends Alter {
  private final IndexType b;
  
  private String f() {
    switch (AlterIndex$1.a[this.b.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
    } 
    return 







      
      this.a.indexCreate.c_();
  }
  
  private String g() {
    switch (AlterIndex$1.a[this.b.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
    } 
    return 







      
      null;
  }
  
  private String h() {
    switch (AlterIndex$1.a[this.b.ordinal()]) {
      case 1:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
    } 
    return 





      
      null;
  }
  
  private String i() {
    switch (AlterIndex$1.a[this.b.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
    } 
    return 







      
      this.a.indexRename.c_();
  }
  
  private String j() {
    switch (AlterIndex$1.a[this.b.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
    } 
    return 







      
      this.a.indexDrop.c_();
  }
  
  private String k() {
    switch (AlterIndex$1.a[this.b.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 9:
      
    } 
    return 



      
      null;
  }
  
  public AlterIndex(DbmsDef paramDbmsDef, IndexType paramIndexType) {
    super(paramDbmsDef);
    this.b = paramIndexType;
  }
  
  public boolean b() {
    return (f() != null);
  }
  
  public boolean c() {
    return (i() != null);
  }
  
  public boolean d() {
    return (j() != null);
  }
  
  public boolean e() {
    return (k() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Index paramIndex1, Index paramIndex2) {
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          i()))
        .set(K.l, paramIndex1.getEntity().getSchema().ref())
        .set(K.o, paramIndex1.getEntity().ref())
        .set(K.p, paramIndex1.getEntity().getName())
        .set(K.v, paramIndex1.ref())
        .set(K.w, Dbms.quote(paramIndex1))
        .set(K.g, Dbms.quote(paramIndex2))
        .set(K.h, paramIndex2));
  }
  
  public AlterScript a(SyncPair paramSyncPair, Index paramIndex) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          f()))
        .set(K.l, paramIndex.getEntity().getSchema().ref())
        .set(K.m, paramIndex.getEntity().getSchema().getName())
        .set(K.o, paramIndex.getEntity().ref())
        .set(K.p, paramIndex.getEntity().getName())
        .set(K.an, paramIndex)
        .set(K.v, paramIndex.ref())
        .set(K.w, Dbms.quote(paramIndex))
        .set(K.ac, this.a.indexColumnsWithOptions(paramIndex))
        .set(K.F, paramIndex.getSpecificationOptions())
        .set(K.G, paramIndex.getOptions())
        .set(K.ad, this.a.jsonIdentifierCommaList(paramIndex.getColumns()))))
      
      .setSortOrder((paramIndex.getSpecificationOptions() != null && paramIndex.getSpecificationOptions().toUpperCase().contains("CLUSTERED")) ? 
        4000 : 5000);
  }
  
  public SimpleStatement a(Index paramIndex) {
    return (new SimpleStatement(g()))
      .set(K.l, paramIndex.getEntity().getSchema().ref())
      .set(K.m, paramIndex.getEntity().getSchema().getName())
      .set(K.o, paramIndex.getEntity().ref())
      .set(K.p, paramIndex.getEntity().getName())
      .set(K.w, Dbms.quote(paramIndex))
      .set(K.v, paramIndex.ref())
      .set(K.F, paramIndex.getSpecificationOptions())
      .set(K.G, paramIndex.getOptions())
      .set(K.ac, this.a.indexColumnsWithOptions(paramIndex))
      .set(K.an, paramIndex);
  }
  
  public AlterStatement b(SyncPair paramSyncPair, Index paramIndex) {
    return (new AlterStatement(paramSyncPair, g()))
      .set(K.l, paramIndex.getEntity().getSchema().ref())
      .set(K.m, paramIndex.getEntity().getSchema().getName())
      .set(K.o, paramIndex.getEntity().ref())
      .set(K.p, paramIndex.getEntity().getName())
      .set(K.v, paramIndex.ref())
      .set(K.w, Dbms.quote(paramIndex))
      .set(K.F, paramIndex.getSpecificationOptions())
      .set(K.G, paramIndex.getOptions())
      .set(K.ac, this.a.indexColumnsWithOptions(paramIndex))
      .set(K.an, paramIndex);
  }
  
  public AlterScript c(SyncPair paramSyncPair, Index paramIndex) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          j()))
        .set(K.l, paramIndex.getEntity().getSchema().ref())
        .set(K.m, paramIndex.getEntity().getSchema().getName())
        .set(K.o, paramIndex.getEntity().ref())
        .set(K.p, paramIndex.getEntity().getName())
        .set(K.ac, this.a.indexColumnsWithOptions(paramIndex))
        .set(K.ad, this.a.jsonIdentifierCommaList(paramIndex.getColumns()))
        .set(K.v, paramIndex.ref())
        .set(K.w, Dbms.quote(paramIndex))
        .set(K.an, paramIndex)))
      .setSortOrder(200);
  }
  
  public AlterStatement b(SyncPair paramSyncPair, Index paramIndex1, Index paramIndex2) {
    return (new AlterStatement(paramSyncPair, k()))
      .set(K.m, paramIndex2.getEntity().getSchema().getName())
      .set(K.l, paramIndex2.getEntity().getSchema().ref())
      .set(K.n, paramIndex2.getEntity().getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.v, paramIndex2.ref())
      .set(K.w, paramIndex2.getName())
      .set(K.u, paramIndex2.getName())
      .set(K.o, paramIndex2.getEntity().ref())
      .set(K.p, paramIndex2.getEntity().getName())
      .set(K.d, SqlUtil.escapeCommentQuotes(paramIndex2.getComment()))
      .set(K.e, Dbms.get(paramIndex2.getDbId()).escapeString(paramIndex2.getComment()))
      .setSortOrder(a());
  }
  
  String d(SyncPair paramSyncPair, Index paramIndex) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, h())).set(K.ac, this.a.indexColumnsWithOptions(paramIndex)).set(K.F, paramIndex.getSpecificationOptions()).set(K.G, paramIndex.getOptions());
    return alterStatement.toString().trim();
  }
}
