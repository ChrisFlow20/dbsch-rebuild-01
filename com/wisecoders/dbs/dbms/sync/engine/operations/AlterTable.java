package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Table$TableType;
import com.wisecoders.dbs.sys.SqlUtil;
import java.util.List;

public class AlterTable extends Alter {
  private String f() {
    return this.a.tableCreate.c_();
  }
  
  private String g() {
    return this.a.tableCreatePartition.c_();
  }
  
  private String h() {
    return this.a.tableRename.c_();
  }
  
  private String i() {
    return this.a.childEntityRename.c_();
  }
  
  private String j() {
    return this.a.tableDrop.c_();
  }
  
  private String k() {
    return this.a.tableComment.c_();
  }
  
  private String l() {
    return this.a.tableChangeComment.c_();
  }
  
  private String m() {
    return this.a.tableDropComment.c_();
  }
  
  public AlterTable(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (f() != null);
  }
  
  public boolean c() {
    return (h() != null);
  }
  
  public boolean d() {
    return (j() != null);
  }
  
  public boolean e() {
    return (k() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Table paramTable, List paramList) {
    AlterScript alterScript1 = new AlterScript(this.a.dbId);
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool1 = false;
    Index index1 = paramTable.getIndexByType(IndexType.PARTITION);
    for (Column column : paramTable.getAttributes()) {
      boolean bool = (this.a.partitionIsColumnDefinition.b() && index1 != null && index1.columns.contains(column)) ? true : false;
      if (!bool && !column.isVirtual() && (column.getSpec() == AttributeSpec.normal || column.getSpec() == AttributeSpec.computed)) {
        stringBuilder.append(bool1 ? ",\n" : "\n");
        stringBuilder.append("\t").append(this.a.alterColumn.a(column, true));
        bool1 = true;
      } 
    } 
    Index index2 = null, index3 = null, index4 = null, index5 = null, index6 = null, index7 = null;
    boolean bool2 = false, bool3 = false, bool4 = false;
    for (Index index : paramTable.getIndexes()) {
      if (!index.isVirtual()) {
        SimpleStatement simpleStatement;
        switch (AlterTable$1.a[index.getType().ordinal()]) {
          case 1:
            index3 = index;
          case 2:
            index4 = index;
          case 3:
            index5 = index;
          case 4:
            index6 = index;
          case 5:
            index7 = index;
          case 6:
            index2 = index;
            simpleStatement = this.a.alterPrimaryKey.a(index);
            if (this.a.pkInlineKeyword.p() && index.columns.size() == 1) {
              bool2 = true;
              continue;
            } 
            if (!simpleStatement.isEmpty()) {
              if (bool1)
                stringBuilder.append(",\n"); 
              stringBuilder.append("\t").append(simpleStatement);
              bool1 = true;
              bool2 = true;
            } 
          case 7:
            simpleStatement = this.a.alterUniqueKey.b(paramSyncPair, index);
            if (!simpleStatement.isEmpty()) {
              if (bool1)
                stringBuilder.append(",\n"); 
              stringBuilder.append("\t").append(simpleStatement);
              bool1 = true;
              bool3 = true;
            } 
          case 8:
            simpleStatement = this.a.alterUniqueIndex.b(paramSyncPair, index);
            if (!simpleStatement.isEmpty()) {
              if (bool1)
                stringBuilder.append(",\n"); 
              stringBuilder.append("\t").append(simpleStatement);
              bool1 = true;
              bool4 = true;
            } 
        } 
      } 
    } 
    if (index3 != null && !index3.isVirtual()) {
      AlterStatement alterStatement = this.a.alterPartition.b(paramSyncPair, index3);
      if (!alterStatement.isEmpty()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(alterStatement);
        bool1 = true;
      } 
    } 
    if (index4 != null && !index4.isVirtual()) {
      AlterStatement alterStatement = this.a.alterSort.b(paramSyncPair, index4);
      if (!alterStatement.isEmpty()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(alterStatement);
        bool1 = true;
      } 
    } 
    if (index5 != null && !index5.isVirtual()) {
      AlterStatement alterStatement = this.a.alterCluster.b(paramSyncPair, index5);
      if (!alterStatement.isEmpty()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(alterStatement);
        bool1 = true;
      } 
    } 
    if (index6 != null && !index6.isVirtual()) {
      AlterStatement alterStatement = this.a.alterIndex1.b(paramSyncPair, index6);
      if (!alterStatement.isEmpty()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(alterStatement);
        bool1 = true;
      } 
    } 
    if (index7 != null && !index7.isVirtual()) {
      AlterStatement alterStatement = this.a.alterIndex2.b(paramSyncPair, index7);
      if (!alterStatement.isEmpty()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(alterStatement);
        bool1 = true;
      } 
    } 
    for (ForeignKey foreignKey : paramTable.getRelations()) {
      if (this.a.alterForeignKey.c() && paramList != null && paramList.contains(foreignKey) && !foreignKey.isVirtual()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(this.a.alterForeignKey.a(foreignKey));
        bool1 = true;
      } 
    } 
    for (Constraint constraint : paramTable.constraints) {
      if (this.a.alterConstraint.b() && !constraint.isVirtual()) {
        if (bool1)
          stringBuilder.append(",\n"); 
        stringBuilder.append("\t").append(this.a.alterConstraint.a(constraint));
        bool1 = true;
      } 
    } 
    stringBuilder.append("\n");
    if (paramTable.getPreScript() != null)
      alterScript1.add((new AlterStatement(paramSyncPair, paramTable.getPreScript())).set(K.o, paramTable.ref())); 
    alterScript1.add((new AlterStatement(paramSyncPair, (paramTable.getType() == Table$TableType.e) ? g() : f()))
        .set(K.o, paramTable.ref())
        .set(K.p, paramTable.getName())
        .set(K.m, paramTable.schema.getName())
        .set(K.i, stringBuilder.toString())
        .set(K.an, paramTable)
        .set(K.G, paramTable.getOptions())
        .set(K.d, paramTable.getComment())
        .set(K.e, this.a.escapeString(paramTable.getComment()))
        .set(K.F, paramTable.getSpecificationOptions())
        .set(K.L, (index2 != null) ? this.a.alterPrimaryKey.d(paramSyncPair, index2) : null)
        .set(K.M, (index3 != null) ? this.a.alterPartition.d(paramSyncPair, index3) : null)
        .set(K.N, (index4 != null) ? this.a.alterSort.d(paramSyncPair, index4) : null)
        .set(K.O, (index5 != null) ? this.a.alterCluster.d(paramSyncPair, index5) : null)
        .set(K.P, (index6 != null) ? this.a.alterIndex1.d(paramSyncPair, index6) : null)
        .set(K.Q, (index7 != null) ? this.a.alterIndex2.d(paramSyncPair, index7) : null)
        .set(K.ao, paramSyncPair));
    if (paramTable.getPostScript() != null)
      alterScript1.add((new AlterStatement(paramSyncPair, paramTable.getPostScript())).set(K.o, paramTable.ref()).set(K.p, paramTable.getName())); 
    if (!this.a.alterConstraint.b())
      for (Constraint constraint : paramTable.constraints) {
        alterScript1.addAll(this.a.alterConstraint.a(paramSyncPair, constraint).resetSortOrder());
        if (constraint.getComment() != null && this.a.alterConstraint.f())
          alterScript1.add(this.a.alterConstraint.b(paramSyncPair, null, constraint).setSortOrder(a())); 
      }  
    AlterScript alterScript2 = new AlterScript(alterScript1.dbId);
    for (Index index : paramTable.getIndexes()) {
      if (!index.isVirtual())
        switch (AlterTable$1.a[index.getType().ordinal()]) {
          case 6:
            if (!bool2)
              alterScript2.addAll(this.a.alterPrimaryKey.a(paramSyncPair, index)); 
          case 7:
            if (!bool3)
              alterScript2.addAll(this.a.alterUniqueKey.a(paramSyncPair, index)); 
          case 8:
            if (!bool4)
              alterScript2.addAll(this.a.alterUniqueIndex.a(paramSyncPair, index)); 
          case 9:
            alterScript2.addAll(this.a.alterNormalIndex.a(paramSyncPair, index));
        }  
    } 
    alterScript2.sort();
    alterScript2.resetSortOrder();
    alterScript1.addAll(alterScript2);
    for (Index index : paramTable.getIndexes()) {
      if (index.getComment() != null && !index.isVirtual())
        switch (AlterTable$1.a[index.getType().ordinal()]) {
          case 6:
            if (this.a.alterPrimaryKey.e())
              alterScript1.add(this.a.alterPrimaryKey.b(paramSyncPair, null, index)); 
          case 7:
            if (this.a.alterUniqueKey.e())
              alterScript1.add(this.a.alterUniqueKey.b(paramSyncPair, null, index)); 
          case 8:
            if (this.a.alterUniqueIndex.e())
              alterScript1.add(this.a.alterUniqueIndex.b(paramSyncPair, null, index)); 
          case 9:
            if (this.a.alterNormalIndex.e())
              alterScript1.add(this.a.alterNormalIndex.b(paramSyncPair, null, index)); 
        }  
    } 
    if (paramTable.getComment() != null && this.a.alterTable.e() && !paramTable.isVirtual())
      alterScript1.add(this.a.alterTable.b(paramSyncPair, (Table)null, paramTable).setSortOrder(a())); 
    for (Column column : paramTable.getAttributes()) {
      if (!column.isVirtual() && column.getComment() != null && this.a.alterColumn.e())
        alterScript1.add(this.a.alterColumn.b(paramSyncPair, null, column).setSortOrder(a())); 
    } 
    for (Constraint constraint : paramTable.constraints) {
      if (constraint.getComment() != null && this.a.alterConstraint.f() && !constraint.isVirtual())
        alterScript1.add(this.a.alterConstraint.b(paramSyncPair, null, constraint).setSortOrder(a())); 
    } 
    for (ForeignKey foreignKey : paramTable.getRelations()) {
      if (this.a.alterForeignKey.c() && paramList != null && paramList.contains(foreignKey) && 
        foreignKey.getComment() != null && this.a.alterForeignKey.e() && !foreignKey.isVirtual())
        alterScript1.add(this.a.alterForeignKey.b(paramSyncPair, null, foreignKey).setSortOrder(a())); 
    } 
    alterScript1.setSortOrder((paramTable.getSyncPriority() != -1) ? paramTable.getSyncPriority() : 1000);
    return alterScript1;
  }
  
  public AlterScript a(SyncPair paramSyncPair, Table paramTable1, Table paramTable2) {
    return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          h()))
        .set(K.l, paramTable1.getSchema().ref())
        .set(K.m, paramTable1.getSchema().getName())
        .set(K.o, paramTable1.ref())
        .set(K.p, paramTable1.getName())
        .set(K.f, paramTable2.ref())
        .set(K.g, Dbms.quote(paramTable2))
        .set(K.h, paramTable2)
        .set(K.an, paramTable2))).setSortOrder(600);
  }
  
  public AlterScript a(SyncPair paramSyncPair, ChildEntity paramChildEntity1, ChildEntity paramChildEntity2) {
    String str1 = paramChildEntity1.ownerColumn.getNameWithPath();
    paramChildEntity1.ownerColumn.rename(paramChildEntity2.getName());
    String str2 = paramChildEntity1.ownerColumn.getNameWithPath();
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          i()))
        .set(K.m, paramChildEntity1.getEntity().getSchema().getName())
        .set(K.p, paramChildEntity1.getEntity().getName())
        .set(K.o, paramChildEntity1.getEntity().ref())
        .set(K.q, paramChildEntity1.ref())
        .set(K.b, str1)
        .set(K.g, paramChildEntity2.getName())
        .set(K.c, str2)
        .set(K.ao, paramSyncPair)
        .set(K.an, paramChildEntity2));
  }
  
  public AlterStatement b(SyncPair paramSyncPair, Table paramTable1, Table paramTable2) {
    String str = k();
    if (paramTable1 != null && paramTable1.getComment() != null)
      str = (paramTable2.getComment() != null) ? l() : m(); 
    return (new AlterStatement(paramSyncPair, str))
      .set(K.o, paramTable2.ref())
      .set(K.p, paramTable2.getName())
      .set(K.l, paramTable2.getSchema().ref())
      .set(K.m, paramTable2.getSchema().getName())
      .set(K.n, paramTable2.getSchema().getCatalogName(paramSyncPair.getMapping()))
      .set(K.d, SqlUtil.escapeCommentQuotes(paramTable2.getComment()))
      .set(K.e, Dbms.get(paramTable2.getDbId()).escapeString(paramTable2.getComment()))
      .set(K.ao, paramSyncPair)
      .set(K.an, paramTable2)
      .setSortOrder(a());
  }
  
  public AlterScript a(SyncPair paramSyncPair, Table paramTable) {
    return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
          j()))
        .set(K.l, paramTable.schema.ref())
        .set(K.m, paramTable.schema.getName())
        .set(K.o, paramTable.ref())
        .set(K.p, paramTable.getName()));
  }
}
