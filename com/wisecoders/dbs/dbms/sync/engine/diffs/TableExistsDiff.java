package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.ArrayList;
import java.util.Iterator;

@DoNotObfuscate
public class TableExistsDiff extends AbstractExistsDiff {
  public TableExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Table table = (Table)this.pair.getUnit(paramSyncSide.opposite());
    if (table != null) {
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      Table table1 = a(schema, table);
      if (paramGenericLayoutPane != null)
        paramGenericLayoutPane.b(table1); 
    } 
  }
  
  public void mergeFinal(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Table table = (Table)this.pair.getUnit(paramSyncSide.opposite());
    if (table != null) {
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      Table table1 = schema.getTable(table.getName());
      if (table1 != null)
        for (ForeignKey foreignKey1 : table.getRelations()) {
          ForeignKey foreignKey2 = (ForeignKey)table1.foreignKeys.getByName(foreignKey1.getName());
          if (foreignKey2 == null) {
            foreignKey2 = table1.createRelation(table1.schema.nameFinder.a(foreignKey1.getName()));
          } else {
            foreignKey2.getSourceAttributes().clear();
            foreignKey2.getTargetAttributes().clear();
          } 
          Schema schema1 = schema.project.getSchema(foreignKey1.getTargetEntity().getSchema().getCatalogName(), foreignKey1.getTargetEntity().getSchema().getName());
          if (schema1 == null)
            schema1 = schema; 
          Table table2 = (Table)schema1.tables.getByName(foreignKey1.getTargetEntity().getName());
          if (table2 != null) {
            foreignKey2.setTargetEntity(table2);
            foreignKey2.setDeleteAction(foreignKey1.getDeleteAction());
            foreignKey2.setUpdateAction(foreignKey1.getUpdateAction());
            foreignKey2.setRelationType(foreignKey1.getRelationType());
            foreignKey2.setComment(foreignKey1.getComment());
            foreignKey2.setCommentTags(foreignKey1.getCommentTags());
            foreignKey2.setVirtual(foreignKey1.isVirtual());
            for (Iterator<Column> iterator1 = foreignKey1.getSourceAttributes().iterator(), iterator2 = foreignKey1.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
              Column column1 = iterator1.next();
              Column column2 = iterator2.next();
              Column column3 = (column1 != null) ? (Column)table1.columns.getByName(column1.getName()) : null;
              Column column4 = (column2 != null) ? (Column)table2.columns.getByName(column2.getName()) : null;
              if (column4 != null && (column3 != null || table1.is(UnitProperty.f).booleanValue()))
                foreignKey2.addColumns(column3, column4); 
            } 
          } 
        }  
    } 
    super.mergeFinal(paramSyncSide, paramGenericLayoutPane);
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Table table1 = (Table)this.pair.getUnit(paramSyncSide.opposite()), table2 = (Table)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (table2 != null && !SyncUtil.c(table2)) {
      ArrayList<ForeignKey> arrayList = new ArrayList();
      for (ForeignKey foreignKey : table2.getRelations()) {
        if (!SyncUtil.c(foreignKey)) {
          if (paramBoolean && this.pair.dependency != null && this.pair.dependency
            
            .getInlineForeignKeys().contains(foreignKey)) {
            arrayList.add(foreignKey);
            continue;
          } 
          alterScript.addAll(dbms.alterForeignKey.a(this.pair, foreignKey));
          if (foreignKey.getComment() != null && dbms.alterForeignKey.e())
            alterScript.add(dbms.alterForeignKey.b(this.pair, null, foreignKey)); 
        } 
      } 
      alterScript.addAll((Dbms.get(paramString)).alterTable.a(this.pair, table2, arrayList));
    } 
    if (table1 != null && !SyncUtil.c(table1))
      alterScript.addAll((Dbms.get(paramString)).alterTable.a(this.pair, table1)); 
    return alterScript;
  }
  
  public Table getTable() {
    return (Table)this.pair.getUnit();
  }
}
