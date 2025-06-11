package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import java.util.Iterator;

public abstract class AbstractExistsDiff extends AbstractDiff {
  public static final String exist = "EXIST";
  
  public static final String missed = "MISSING";
  
  private boolean b = false;
  
  public AbstractExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return (this.pair.getUnit(paramSyncSide) != null) ? "EXIST" : "MISSING";
  }
  
  public String getOperationString(SyncSide paramSyncSide) {
    return (this.pair.getUnit(paramSyncSide) == null) ? "Create" : "Drop";
  }
  
  public OperationType getOperationType(SyncSide paramSyncSide) {
    return (this.pair.getUnit(paramSyncSide) == null) ? OperationType.a : OperationType.b;
  }
  
  public void mergeFinal(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    AbstractUnit abstractUnit1 = this.pair.getUnit(paramSyncSide.opposite()), abstractUnit2 = this.pair.getUnit(paramSyncSide), abstractUnit3 = this.pair.getParent(paramSyncSide);
    if (abstractUnit2 != null && abstractUnit1 == null) {
      abstractUnit2.markForDeletion();
      if (abstractUnit3 != null)
        abstractUnit3.refresh(); 
    } 
  }
  
  protected void a(Schema paramSchema, ForeignKey paramForeignKey) {
    Table table = paramSchema.getTable(paramForeignKey.getEntity().getName());
    Schema schema = paramSchema;
    if (paramForeignKey.getTargetEntity() != null) {
      if (paramForeignKey.getEntity().getSchema() != paramForeignKey.getTargetEntity().getSchema())
        schema = paramSchema.project.getSimilarSchema(paramForeignKey.getTargetEntity().getSchema()); 
      if (schema != null) {
        Table table1 = schema.getTable(paramForeignKey.getTargetEntity().getName());
        if (table != null && table1 != null) {
          ForeignKey foreignKey = table.createRelation(paramForeignKey.getName());
          foreignKey.setTargetEntity(table1);
          foreignKey.setDeleteAction(paramForeignKey.getDeleteAction());
          foreignKey.setUpdateAction(paramForeignKey.getUpdateAction());
          foreignKey.setComment(paramForeignKey.getComment());
          foreignKey.setCommentTags(paramForeignKey.getCommentTags());
          foreignKey.setOptions(paramForeignKey.getOptions());
          foreignKey.setLogical(paramForeignKey.getLogical());
          foreignKey.setVirtual((paramForeignKey.isVirtual() || isMergeVirtual()));
          for (Iterator<Column> iterator1 = paramForeignKey.getSourceAttributes().iterator(), iterator2 = paramForeignKey.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
            Column column1 = iterator1.next();
            Column column2 = iterator2.next();
            Column column3 = (column1 != null) ? table.getColumnByNameOrPath(column1.getNameWithPath()) : null;
            Column column4 = (column2 != null) ? (Column)table1.columns.getByName(column2.getName()) : null;
            if (column4 != null && (column3 != null || table1.is(UnitProperty.f).booleanValue()))
              foreignKey.addColumns(column3, column4); 
          } 
        } 
      } 
    } 
  }
  
  protected Table a(Schema paramSchema, Table paramTable) {
    Table table = paramSchema.createTable(paramTable.getName());
    table.setComment(paramTable.getComment());
    table.setCommentTags(paramTable.getCommentTags());
    table.setType(paramTable.getType());
    table.setSyncPriority(paramTable.getSyncPriority());
    table.setOptions(paramTable.getOptions());
    table.setPreScript(paramTable.getPreScript());
    table.setPostScript(paramTable.getPostScript());
    table.setSpecificationOptions(paramTable.getSpecificationOptions());
    table.setVirtual((paramTable.isVirtual() || isMergeVirtual()));
    Object object = paramTable.getUnitProperty(UnitProperty.a);
    if (object != null)
      table.setUnitProperty(UnitProperty.a, object); 
    for (Column column1 : paramTable.getAttributes()) {
      Column column2 = table.createColumn(column1.getName(), column1.getDataType());
      column2.cloneFrom(column1);
    } 
    for (Index index1 : paramTable.getIndexes()) {
      Index index2 = table.createIndex(index1.getName());
      index2.setSpecificationOptions(index1.getSpecificationOptions());
      index2.setOptions(index1.getOptions());
      index2.setComment(index1.getComment());
      index2.setCommentTags(index1.getCommentTags());
      index2.setType(index1.getType());
      index2.setVirtual(index1.isVirtual());
      for (Column column1 : index1.getColumns()) {
        Column column2 = table.getColumnByNameOrPath(column1.getNameWithPath());
        if (column2 != null)
          index2.addColumn(column2); 
        index2.setColumnOptions(column2, index1.getColumnOptions(column1));
      } 
    } 
    for (Constraint constraint1 : paramTable.constraints) {
      Constraint constraint2 = table.createConstraint(constraint1.getName());
      constraint2.setText(constraint1.getText());
      constraint2.setType(constraint1.getType());
      constraint2.setComment(constraint1.getComment());
      constraint2.setCommentTags(constraint1.getCommentTags());
      constraint2.setOptions(constraint1.getOptions());
    } 
    return table;
  }
  
  public void setMergeVirtual(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean isMergeVirtual() {
    return this.b;
  }
}
