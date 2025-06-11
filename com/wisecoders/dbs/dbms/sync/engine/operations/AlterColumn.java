package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterColumn extends Alter {
  private String i() {
    return this.a.columnCreateInline.c_();
  }
  
  private String j() {
    return this.a.columnCreate.c_();
  }
  
  private String k() {
    return this.a.columnCreateComputedInline.c_();
  }
  
  private String l() {
    return this.a.columnCreateComputed.c_();
  }
  
  private String m() {
    return this.a.columnRename.c_();
  }
  
  private String n() {
    return this.a.columnComment.c_();
  }
  
  private String o() {
    return this.a.columnChangeComment.c_();
  }
  
  private String p() {
    return this.a.columnDropComment.c_();
  }
  
  private String q() {
    return this.a.columnSetDefault.c_();
  }
  
  private String r() {
    return this.a.columnRemoveDefault.c_();
  }
  
  private String s() {
    return this.a.columnModifyType.c_();
  }
  
  private String t() {
    return this.a.columnSetMandatory.c_();
  }
  
  private String u() {
    return this.a.columnDropMandatory.c_();
  }
  
  private String v() {
    return this.a.columnSetIdentity.c_();
  }
  
  private String w() {
    return this.a.columnDropIdentity.c_();
  }
  
  private String x() {
    return this.a.columnDrop.c_();
  }
  
  private String y() {
    return this.a.columnReorderFirst.c_();
  }
  
  private String z() {
    return this.a.columnReorderAfter.c_();
  }
  
  private String A() {
    return this.a.columnNotNull.c_();
  }
  
  private String B() {
    return this.a.columnNull.c_();
  }
  
  private String C() {
    return this.a.columnNullInline.c_();
  }
  
  private String D() {
    return this.a.columnUnsigned.c_();
  }
  
  private String E() {
    return this.a.columnIdentityOptions.c_();
  }
  
  private String F() {
    return this.a.columnDefault.c_();
  }
  
  private String G() {
    return this.a.pkInlineKeyword.c_();
  }
  
  public AlterColumn(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (i() != null);
  }
  
  public boolean c() {
    return (m() != null);
  }
  
  public boolean d() {
    return (x() != null);
  }
  
  public boolean e() {
    return (n() != null);
  }
  
  private void a(SimpleStatement paramSimpleStatement, Column paramColumn, boolean paramBoolean) {
    if (paramColumn == null)
      throw new NullPointerException(); 
    paramSimpleStatement.set(K.S, new SimpleStatement(paramColumn.isMandatory() ? A() : (paramBoolean ? null : C())));
  }
  
  private void a(SimpleStatement paramSimpleStatement, Column paramColumn) {
    if (paramColumn == null)
      throw new NullPointerException(); 
    paramSimpleStatement.set(K.S, new SimpleStatement(paramColumn.isMandatory() ? A() : B()));
  }
  
  private void b(SimpleStatement paramSimpleStatement, Column paramColumn) {
    if (paramColumn == null)
      throw new NullPointerException(); 
    boolean bool = (paramColumn.table instanceof Table && ((Table)paramColumn.table).getPrimaryKey() != null && (((Table)paramColumn.table).getPrimaryKey()).columns.contains(paramColumn) && (((Table)paramColumn.table).getPrimaryKey()).columns.size() == 1) ? true : false;
    paramSimpleStatement.set(K.W, paramColumn.getTypeString())
      .set(K.G, paramColumn.getOptions())
      .set(K.o, paramColumn.getEntity().ref())
      .set(K.R, paramColumn.hasDefaultValue() ? (

        
        new SimpleStatement(F())).set(K.R, paramColumn.getDefaultValue()).set(K.o, SqlUtil.removeDelimiters(paramColumn.getEntity().getName())).set(K.q, SqlUtil.removeDelimiters(paramColumn.ref())) : null)
      .set(K.T, paramColumn.isIdentity() ? new SimpleStatement(paramColumn.getIdentity()) : null)
      .set(K.X, bool ? G() : null)
      .set(K.d, SqlUtil.escapeCommentQuotes(paramColumn.getComment()))
      .set(K.e, Dbms.get(paramColumn.getDbId()).escapeString(paramColumn.getComment()))
      .set(K.k, paramColumn.getDefinition())
      .set(K.U, paramColumn.isIdentity() ? new SimpleStatement(paramColumn.getIdentity()) : (paramColumn.isMandatory() ? new SimpleStatement(A()) : new SimpleStatement(C())))
      .set(K.V, paramColumn.isUnsigned() ? new SimpleStatement(D()) : null);
  }
  
  private void a(SimpleStatement paramSimpleStatement, Column paramColumn1, Column paramColumn2) {
    if (paramColumn1 == null)
      throw new NullPointerException(); 
    b(paramSimpleStatement
        .set(K.ab, !paramColumn2.getTypeString().equals(paramColumn1.getTypeString()) ? paramColumn2.getTypeString() : null)
        .set(K.Y, paramColumn2.defaultValuesAreSimilar(paramColumn1) ? null : (


          
          new SimpleStatement(F())).set(K.R, paramColumn2.getDefaultValue()).set(K.o, paramColumn2.getEntity().getName()).set(K.q, paramColumn2.ref()))
        .set(K.Z, (paramColumn2.isIdentity() != paramColumn1.isIdentity()) ? new SimpleStatement(E()) : null)
        .set(K.aa, (paramColumn2.isUnsigned() != paramColumn1.isUnsigned()) ? new SimpleStatement(D()) : null), paramColumn2);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, m())).set(K.m, paramColumn1.getEntity().getSchema().getName()).set(K.p, paramColumn1.getEntity().getName()).set(K.o, paramColumn1.getEntity().ref()).set(K.q, paramColumn1.ref()).set(K.b, paramColumn1.getNameWithPath()).set(K.g, this.a.quote(paramColumn2.getName())).set(K.h, paramColumn2.getName()).set(K.c, paramColumn2.getNameWithPath()).set(K.ao, paramSyncPair).set(K.an, paramColumn2);
    b(alterStatement, paramColumn2);
    a(alterStatement, paramColumn2, false);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(600);
  }
  
  public AlterStatement b(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    String str = n();
    if (paramColumn1 != null && paramColumn1.getComment() != null)
      str = (paramColumn2.getComment() != null) ? o() : p(); 
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, str)).set(K.o, paramColumn2.getEntity().ref()).set(K.p, paramColumn2.getEntity().getName()).set(K.l, paramColumn2.getEntity().getSchema().ref()).set(K.n, paramColumn2.getEntity().getSchema().getCatalogName(paramSyncPair.getMapping())).set(K.m, paramColumn2.getEntity().getSchema().getName()).set(K.q, paramColumn2.ref()).set(K.r, SqlUtil.removeDelimiters(paramColumn2.getName())).set(K.d, SqlUtil.escapeCommentQuotes(paramColumn2.getComment())).set(K.e, Dbms.get(paramColumn2.getDbId()).escapeString(paramColumn2.getComment())).set(K.ao, paramSyncPair).set(K.an, paramColumn2);
    b(alterStatement, paramColumn2);
    a(alterStatement, paramColumn2, false);
    return alterStatement.setSortOrder(a());
  }
  
  public SimpleStatement a(Column paramColumn, boolean paramBoolean) {
    String str = paramColumn.ref();
    SimpleStatement simpleStatement = (new SimpleStatement((paramColumn.getSpec() == AttributeSpec.computed) ? k() : i())).set(K.q, str + str);
    b(simpleStatement, paramColumn);
    a(simpleStatement, paramColumn, (paramColumn.getSpec() == AttributeSpec.computed));
    simpleStatement.set(K.an, paramColumn);
    return simpleStatement;
  }
  
  public AlterScript a(SyncPair paramSyncPair, Column paramColumn) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, (paramColumn.getSpec() == AttributeSpec.computed) ? l() : j())).set(K.m, paramColumn.getEntity().getSchema().getName()).set(K.p, paramColumn.getEntity().getName()).set(K.o, paramColumn.getEntity().ref()).set(K.q, paramColumn.ref()).set(K.r, paramColumn.getName()).set(K.ao, paramSyncPair).set(K.an, paramColumn);
    b(alterStatement, paramColumn);
    a(alterStatement, paramColumn, (paramColumn.getSpec() == AttributeSpec.computed));
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(2000);
  }
  
  public AlterScript c(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, s())).set(K.m, paramColumn1.getEntity().getSchema().getName()).set(K.p, paramColumn1.getEntity().getName()).set(K.o, paramColumn1.getEntity().ref()).set(K.q, paramColumn1.ref()).set(K.r, paramColumn1.getName()).set(K.ao, paramSyncPair).set(K.an, paramColumn2).set(K.G, paramColumn1.getOptions());
    a(alterStatement, paramColumn1, paramColumn2);
    a(alterStatement, paramColumn2);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript d(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, t())).set(K.m, paramColumn1.getEntity().getSchema().getName()).set(K.p, paramColumn1.getEntity().getName()).set(K.o, paramColumn1.getEntity().ref()).set(K.q, paramColumn1.ref()).set(K.r, paramColumn1.getName()).set(K.an, paramColumn1).set(K.ao, paramSyncPair).set(K.G, paramColumn1.getOptions());
    a(alterStatement, paramColumn1, paramColumn2);
    a(alterStatement, paramColumn2);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript e(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, u())).set(K.m, paramColumn1.getEntity().getSchema().getName()).set(K.p, paramColumn1.getEntity().getName()).set(K.o, paramColumn1.getEntity().ref()).set(K.q, paramColumn1.ref()).set(K.r, paramColumn1.getName()).set(K.an, paramColumn1).set(K.ao, paramSyncPair);
    a(alterStatement, paramColumn1, paramColumn2);
    a(alterStatement, paramColumn2);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript f(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, v())).set(K.o, paramColumn1.getEntity().ref()).set(K.p, paramColumn1.getEntity().getName()).set(K.q, paramColumn1.ref()).set(K.r, paramColumn1.getName()).set(K.an, paramColumn2);
    a(alterStatement, paramColumn1, paramColumn2);
    a(alterStatement, paramColumn2);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript g(SyncPair paramSyncPair, Column paramColumn1, Column paramColumn2) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, w())).set(K.o, paramColumn1.getEntity().ref()).set(K.p, paramColumn1.getEntity().getName()).set(K.q, paramColumn1.ref()).set(K.r, paramColumn1.getName()).set(K.ao, paramSyncPair).set(K.an, paramColumn2);
    a(alterStatement, paramColumn1, paramColumn2);
    a(alterStatement, paramColumn2);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript b(SyncPair paramSyncPair, Column paramColumn) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, q())).set(K.o, paramColumn.getEntity().ref()).set(K.p, paramColumn.getEntity().getName()).set(K.q, paramColumn.ref()).set(K.r, paramColumn.getName()).set(K.ao, paramSyncPair).set(K.an, paramColumn);
    b(alterStatement, paramColumn);
    a(alterStatement, paramColumn, false);
    alterStatement.set(K.R, paramColumn.getDefaultValue());
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript c(SyncPair paramSyncPair, Column paramColumn) {
    AlterStatement alterStatement = (new AlterStatement(paramSyncPair, r())).set(K.o, paramColumn.getEntity().ref()).set(K.p, paramColumn.getEntity().getName()).set(K.q, paramColumn.ref()).set(K.r, paramColumn.getName()).set(K.ao, paramSyncPair).set(K.an, paramColumn);
    b(alterStatement, paramColumn);
    a(alterStatement, paramColumn, false);
    return (new AlterScript(this.a.dbId, alterStatement))
      .setSortOrder(500);
  }
  
  public AlterScript d(SyncPair paramSyncPair, Column paramColumn) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          x()))
        .set(K.m, paramColumn.getEntity().getSchema().getName())
        .set(K.p, paramColumn.getEntity().getName())
        .set(K.o, paramColumn.getEntity().ref())
        .set(K.q, paramColumn.ref())
        .set(K.r, paramColumn.getName())
        .set(K.b, paramColumn.getNameWithPath())
        .set(K.an, paramColumn)
        .set(K.ao, paramSyncPair)))
      .setSortOrder(400);
  }
  
  public SimpleStatement a(Column paramColumn1, Column paramColumn2) {
    SimpleStatement simpleStatement = new SimpleStatement((paramColumn1 == null) ? y() : z());
    a(simpleStatement, paramColumn2);
    b(simpleStatement, paramColumn2);
    simpleStatement.set(K.o, paramColumn2.table.ref())
      .set(K.q, paramColumn2.ref());
    if (paramColumn1 != null)
      simpleStatement.set(K.s, paramColumn1.ref()); 
    return simpleStatement;
  }
  
  public String f() {
    return E();
  }
  
  public boolean g() {
    return (D() != null);
  }
  
  public boolean h() {
    return (E() != null);
  }
}
