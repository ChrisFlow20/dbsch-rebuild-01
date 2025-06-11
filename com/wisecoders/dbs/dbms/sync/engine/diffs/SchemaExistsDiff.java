package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.TableDependency;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.schema.ViewDependency;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;

@DoNotObfuscate
public class SchemaExistsDiff extends AbstractExistsDiff {
  public SchemaExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Schema schema = (Schema)this.pair.getUnit(paramSyncSide.opposite());
    if (schema != null) {
      Project project = (Project)this.pair.getParent(paramSyncSide);
      Schema schema1 = project.createSchema(schema.getName(), schema.getCatalogName());
      schema1.setComment(schema.getComment());
      schema1.setCommentTags(schema.getCommentTags());
      schema1.setSpecificationOptions(schema.getSpecificationOptions());
      schema1.setVirtual((schema.isVirtual() || isMergeVirtual()));
      schema1.setOptions(schema.getOptions());
      schema1.setSyncPriority(schema.getSyncPriority());
      schema1.setPreScript(schema.getPreScript());
      schema1.setPostScript(schema.getPostScript());
      for (Sequence sequence1 : schema.sequences) {
        Sequence sequence2 = schema1.createSequence(sequence1.getName());
        sequence2.setOptions(sequence1.getOptions());
        sequence2.setComment(sequence1.getComment());
        sequence2.setCommentTags(sequence1.getCommentTags());
      } 
      for (Table table1 : schema.tables) {
        Table table2 = a(schema1, table1);
        if (paramGenericLayoutPane != null)
          paramGenericLayoutPane.a(table2); 
      } 
      for (View view1 : schema.views) {
        View view2 = schema1.createView(view1.getName());
        view2.setScript(view1.getScript());
        view2.setSyncPriority(view1.getSyncPriority());
        view2.setComment(view1.getComment());
        view2.setCommentTags(view1.getCommentTags());
        if (paramGenericLayoutPane != null)
          paramGenericLayoutPane.a(view2); 
      } 
      for (MaterializedView materializedView1 : schema.materializedViews) {
        MaterializedView materializedView2 = schema1.createMaterializedView(materializedView1.getName());
        materializedView2.setScript(materializedView1.getScript());
        materializedView2.setSyncPriority(materializedView1.getSyncPriority());
        materializedView2.setComment(materializedView1.getComment());
        materializedView2.setCommentTags(materializedView1.getCommentTags());
        if (paramGenericLayoutPane != null)
          paramGenericLayoutPane.a(materializedView2); 
      } 
      for (Trigger trigger1 : schema.triggers) {
        Trigger trigger2 = schema1.createTrigger(trigger1.getName());
        trigger2.setText(trigger1.getText());
        trigger2.setSyncPriority(trigger1.getSyncPriority());
        trigger2.setIsSystem(trigger1.isSystem());
        trigger2.setComment(trigger1.getComment());
        trigger2.setCommentTags(trigger1.getCommentTags());
      } 
      for (Procedure procedure1 : schema.procedures) {
        Procedure procedure2 = schema1.createProcedure(procedure1.getName());
        procedure2.setText(procedure1.getText());
        procedure2.setSyncPriority(procedure1.getSyncPriority());
        procedure2.setIsSystem(procedure1.isSystem());
        procedure2.setComment(procedure1.getComment());
        procedure2.setCommentTags(procedure1.getCommentTags());
      } 
      for (Function function1 : schema.functions) {
        Function function2 = schema1.createFunction(function1.getName());
        function2.setText(function1.getText());
        function2.setSyncPriority(function1.getSyncPriority());
        function2.setIsSystem(function1.isSystem());
        function2.setComment(function1.getComment());
        function2.setCommentTags(function1.getCommentTags());
      } 
      for (UserDataType userDataType1 : schema.userDataTypes) {
        UserDataType userDataType2 = schema1.createUserDataType(userDataType1.getName());
        userDataType2.setScript(userDataType1.getScript());
        userDataType2.setSyncPriority(userDataType1.getSyncPriority());
        userDataType2.setComment(userDataType1.getComment());
        userDataType2.setCommentTags(userDataType1.getCommentTags());
      } 
    } 
  }
  
  public void mergeFinal(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Schema schema = (Schema)this.pair.getUnit(paramSyncSide.opposite());
    if (schema != null) {
      Project project = (Project)this.pair.getParent(paramSyncSide);
      Schema schema1 = project.getSimilarSchema(schema);
      if (schema1 != null)
        for (Table table : schema.tables) {
          for (ForeignKey foreignKey : table.foreignKeys)
            a(schema1, foreignKey); 
        }  
    } 
    super.mergeFinal(paramSyncSide, paramGenericLayoutPane);
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Schema schema1 = (Schema)this.pair.getUnit(paramSyncSide.opposite()), schema2 = (Schema)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (schema2 != null && !SyncUtil.c(schema2)) {
      TableDependency tableDependency1 = new TableDependency(schema2.tables, (paramBoolean && dbms.alterForeignKey.c()));
      for (Table table : schema2.tables) {
        for (ForeignKey foreignKey : table.foreignKeys) {
          if (!SyncUtil.c(foreignKey) && !tableDependency1.getInlineForeignKeys().contains(foreignKey)) {
            alterScript.addAll(dbms.alterForeignKey.a(this.pair, foreignKey));
            if (foreignKey.getComment() != null)
              alterScript.add(dbms.alterForeignKey.b(this.pair, null, foreignKey)); 
          } 
        } 
      } 
      if (schema1 == null || !StringUtil.areEqual(schema1.getCatalogName(), schema2.getCatalogName()) || !StringUtil.areEqual(dbms.defaultSchema.c_(), schema2.getName()))
        alterScript.addAll(dbms.alterSchema.a(this.pair, schema2)); 
      for (UserDataType userDataType : schema2.userDataTypes)
        alterScript.addAll(dbms.alterUserDataType.a(this.pair, userDataType)); 
      for (Sequence sequence : schema2.sequences)
        alterScript.addAll(dbms.alterSequence
            .a(this.pair, sequence)); 
      TableDependency tableDependency2 = new TableDependency(schema2.tables, (paramBoolean && dbms.alterForeignKey.c()));
      for (Table table : tableDependency2.getTablesInCreationOrder()) {
        if (!table.isVirtual())
          alterScript.addAll(dbms.alterTable.a(this.pair, table, tableDependency2.getInlineForeignKeys())); 
      } 
      ViewDependency viewDependency1 = new ViewDependency(schema2.views);
      for (View view : viewDependency1.getViewsInCreationOrder())
        alterScript.addAll(dbms.alterView.a(this.pair, view)); 
      ViewDependency viewDependency2 = new ViewDependency(schema2.materializedViews);
      for (View view : viewDependency2.getViewsInCreationOrder())
        alterScript.addAll(dbms.alterMaterializedView.a(this.pair, (MaterializedView)view)); 
      for (Trigger trigger : schema2.triggers)
        alterScript.addAll(dbms.alterPlSql.a(this.pair, trigger)); 
      for (Rule rule : schema2.rules)
        alterScript.addAll(dbms.alterPlSql.a(this.pair, rule)); 
      for (Function function : schema2.functions)
        alterScript.addAll(dbms.alterPlSql.a(this.pair, function)); 
      for (Procedure procedure : schema2.procedures)
        alterScript.addAll(dbms.alterPlSql.a(this.pair, procedure)); 
    } 
    if (schema1 != null)
      alterScript.addAll(dbms.alterSchema.b(this.pair, schema1)); 
    return alterScript;
  }
}
