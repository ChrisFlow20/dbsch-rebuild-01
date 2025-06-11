package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Line;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.store.XMLWriter;
import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryAggregate;
import com.wisecoders.dbs.query.model.items.QueryFilter;
import com.wisecoders.dbs.query.model.items.QueryOperationColumn;
import com.wisecoders.dbs.query.model.items.QueryOrderBy;
import com.wisecoders.dbs.query.model.items.QueryRelation;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.schema.AbstractFunction;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.FunctionParameter;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.ProjectType;
import com.wisecoders.dbs.schema.PxKey;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Table$TableType;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

@DoNotObfuscate
public class ProjectStore {
  public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
  
  public ProjectStore(Project paramProject, Writer paramWriter) {
    XMLWriter xMLWriter = new XMLWriter(paramWriter);
    paramProject.refresh();
    xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    xMLWriter.a(PxKey.project)
      .e(PxKey.name, paramProject.getName())
      .e(PxKey.database, paramProject.getDbId());
    if (paramProject.getType() == ProjectType.a) {
      xMLWriter.e(PxKey.id, paramProject.getKey());
    } else {
      xMLWriter.e(PxKey.type, paramProject.getType());
    } 
    xMLWriter.b(PxKey.sync_filter, paramProject.getSyncFilter());
    xMLWriter.b(PxKey.sync_init_script, paramProject.getSyncInitScript());
    xMLWriter.b(PxKey.ddl_pre_script, paramProject.getDDLPreScript());
    xMLWriter.b(PxKey.ddl_post_script, paramProject.getDDLPostScript());
    saveComment(paramProject, xMLWriter);
    for (Schema schema : paramProject.schemas) {
      xMLWriter.a(PxKey.schema)
        .e(PxKey.name, schema.getName())
        .e(PxKey.catalogname, schema.getCatalogName());
      if (schema.isVirtual())
        xMLWriter.c(PxKey.virtual); 
      xMLWriter.e(PxKey.spec, schema.getSpecificationOptions())
        .e(PxKey.options, schema.getOptions());
      if (schema.getSyncPriority() != -1)
        xMLWriter.e(PxKey.sync_priority, Integer.valueOf(schema.getSyncPriority())); 
      xMLWriter.b(PxKey.pre_script, schema.getPreScript());
      xMLWriter.b(PxKey.post_script, schema.getPostScript());
      saveComment(schema, xMLWriter);
      for (UserDataType userDataType : schema.userDataTypes) {
        xMLWriter.a(PxKey.udt).e(PxKey.name, userDataType);
        xMLWriter.e(PxKey.udt_java_type, Integer.valueOf(userDataType.getJavaType()));
        xMLWriter.e(PxKey.udt_precision, userDataType.getPrecision());
        if (userDataType.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        if (userDataType.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(userDataType.getSyncPriority())); 
        a(xMLWriter, userDataType.columns);
        xMLWriter.b(PxKey.udt_script, userDataType.getScript());
        saveComment(userDataType, xMLWriter);
        xMLWriter.b(PxKey.udt);
      } 
      for (Table table : schema.tables) {
        xMLWriter.a(PxKey.table)
          .e(PxKey.name, table)
          .e(PxKey.prior, table.getUnitProperty(UnitProperty.a));
        if (table.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(table.getSyncPriority())); 
        if (table.getType() != Table$TableType.a)
          xMLWriter.e(PxKey.type, table.getType()); 
        if (table.getGeneratorRowsCount() > -1)
          xMLWriter.e(PxKey.generator_rows, Integer.valueOf(table.getGeneratorRowsCount())); 
        if (table.getGeneratorOrder() > -1)
          xMLWriter.e(PxKey.generator_order, Integer.valueOf(table.getGeneratorOrder())); 
        if (table.getRowCount() > -1L)
          xMLWriter.e(PxKey.row_count, Long.valueOf(table.getRowCount())); 
        if (table.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        xMLWriter.e(PxKey.spec, table.getSpecificationOptions());
        saveComment(table, xMLWriter);
        a(xMLWriter, table.columns);
        for (Index index : table.indexes) {
          xMLWriter.a(PxKey.index)
            .e(PxKey.name, index)
            .e(PxKey.unique, index.getType())
            .e(PxKey.spec, index.getSpecificationOptions())
            .e(PxKey.options, index.getOptions());
          if (index.isVirtual())
            xMLWriter.c(PxKey.virtual); 
          saveComment(index, xMLWriter);
          for (Column column : index.getColumns())
            xMLWriter.a(PxKey.column).e(PxKey.name, column).e(PxKey.options, index.getColumnOptions(column)).b(PxKey.column); 
          xMLWriter.b(PxKey.index);
        } 
        for (Constraint constraint : table.constraints) {
          xMLWriter.a(PxKey.constraint)
            .e(PxKey.name, constraint.getName())
            .e(PxKey.type, constraint.getType());
          if (constraint.isVirtual())
            xMLWriter.c(PxKey.virtual); 
          xMLWriter.e(PxKey.options, constraint.getOptions())
            .b(PxKey.string, constraint.getText());
          saveComment(constraint, xMLWriter);
          xMLWriter.b(PxKey.constraint);
        } 
        a(xMLWriter, table);
        xMLWriter.b(PxKey.options, table.getOptions());
        xMLWriter.b(PxKey.pre_script, table.getPreScript());
        xMLWriter.b(PxKey.post_script, table.getPostScript());
        xMLWriter.b(PxKey.table);
      } 
      for (View view : schema.views) {
        xMLWriter.a(PxKey.view).e(PxKey.name, view);
        if (view.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        if (view.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(view.getSyncPriority())); 
        saveComment(view, xMLWriter);
        xMLWriter.b(PxKey.view_script, view.getScript());
        for (Column column : view.getAttributes()) {
          xMLWriter.a(PxKey.column).e(PxKey.name, column).e(PxKey.type, column.getDataType());
          if (column.getToDoFlag() > -1)
            xMLWriter.e(PxKey.todo, Short.valueOf(column.getToDoFlag())); 
          saveComment(column, xMLWriter);
          xMLWriter.b(PxKey.column);
        } 
        a(xMLWriter, view);
        xMLWriter.b(PxKey.view);
      } 
      for (MaterializedView materializedView : schema.materializedViews) {
        xMLWriter.a(PxKey.materialized_view).e(PxKey.name, materializedView);
        if (materializedView.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        if (materializedView.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(materializedView.getSyncPriority())); 
        saveComment(materializedView, xMLWriter);
        xMLWriter.b(PxKey.view_script, materializedView.getScript());
        for (Column column : materializedView.getAttributes()) {
          xMLWriter.a(PxKey.column).e(PxKey.name, column).e(PxKey.type, column.getDataType());
          if (column.getToDoFlag() > -1)
            xMLWriter.e(PxKey.todo, Short.valueOf(column.getToDoFlag())); 
          saveComment(column, xMLWriter);
          xMLWriter.b(PxKey.column);
        } 
        a(xMLWriter, materializedView);
        xMLWriter.b(PxKey.materialized_view);
      } 
      for (Sequence sequence : schema.sequences) {
        xMLWriter.a(PxKey.sequence)
          .e(PxKey.name, sequence.getName());
        if (sequence.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        xMLWriter.e(PxKey.options, sequence.getOptions());
        saveComment(sequence, xMLWriter);
        xMLWriter.b(PxKey.sequence);
      } 
      for (Procedure procedure : schema.procedures) {
        xMLWriter.a(PxKey.procedure).e(PxKey.name, procedure.getName())
          .e(PxKey.id, procedure.getKey())
          .e(PxKey.isSystem, Boolean.valueOf(procedure.isSystem()))
          .a(PxKey.params_known, procedure.isParametersKnown());
        if (procedure.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        if (procedure.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(procedure.getSyncPriority())); 
        xMLWriter.b(PxKey.string, procedure.getText());
        saveComment(procedure, xMLWriter);
        a(xMLWriter, procedure);
        xMLWriter.b(PxKey.procedure);
      } 
      for (Function function : schema.functions) {
        xMLWriter.a(PxKey.function).e(PxKey.name, function.getName())
          .e(PxKey.id, function.getKey())
          .e(PxKey.isSystem, Boolean.valueOf(function.isSystem()))
          .a(PxKey.params_known, function.isParametersKnown());
        if (function.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        if (function.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(function.getSyncPriority())); 
        xMLWriter.b(PxKey.string, function.getText());
        saveComment(function, xMLWriter);
        a(xMLWriter, function);
        xMLWriter.b(PxKey.function);
      } 
      for (Trigger trigger : schema.triggers) {
        xMLWriter.a(PxKey.trigger).e(PxKey.name, trigger.getName()).e(PxKey.table, trigger.getOwningTable())
          .e(PxKey.id, trigger.getKey())
          .e(PxKey.isSystem, Boolean.valueOf(trigger.isSystem()));
        if (trigger.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        if (trigger.getSyncPriority() != -1)
          xMLWriter.e(PxKey.sync_priority, Integer.valueOf(trigger.getSyncPriority())); 
        xMLWriter.b(PxKey.string, trigger.getText());
        saveComment(trigger, xMLWriter);
        xMLWriter.b(PxKey.trigger);
      } 
      for (Rule rule : schema.rules) {
        xMLWriter.a(PxKey.rule)
          .e(PxKey.name, rule.getName())
          .e(PxKey.table, rule.getOwningTable())
          .e(PxKey.id, rule.getKey());
        if (rule.isVirtual())
          xMLWriter.c(PxKey.virtual); 
        xMLWriter.b(PxKey.string, rule.getText());
        saveComment(rule, xMLWriter);
        xMLWriter.b(PxKey.rule);
      } 
      xMLWriter.b(PxKey.schema);
    } 
    for (Layout layout : paramProject.layouts) {
      xMLWriter.a(PxKey.layout)
        .e(PxKey.name, layout.getName())
        .e(PxKey.id, layout.getKey())
        .e(PxKey.hide_fks, layout.diagram.getHiddenRelationNames());
      if (layout.isConfirmed())
        xMLWriter.c(PxKey.confirmed); 
      if (layout.diagram.isJoinedRouting())
        xMLWriter.c(PxKey.joined_routing); 
      if (layout.diagram.isShowDeducedFks())
        xMLWriter.c(PxKey.deduced_fks); 
      if (layout.diagram.isShowDataType())
        xMLWriter.c(PxKey.show_column_type); 
      if (layout.diagram.isShowSchemaName())
        xMLWriter.c(PxKey.show_schema_name); 
      if (layout.diagram.isShowPageBorders())
        xMLWriter.c(PxKey.show_page_borders); 
      if (layout.diagram.isShowPhysicalName())
        xMLWriter.c(PxKey.show_physical_name); 
      if (layout.diagram.isShowPhysicalDictionaryName())
        xMLWriter.c(PxKey.show_physical_dictionary_name); 
      xMLWriter.e(PxKey.show_relation, layout.diagram.getLineTextType());
      saveComment(layout, xMLWriter);
      for (Depict depict : layout.getDepicts()) {
        xMLWriter.a(PxKey.entity)
          .e(PxKey.schema, depict.getEntity().getSchema().getNameWithCatalog())
          .e(PxKey.name, depict.getEntity().getEntity());
        if (depict.getEntity() instanceof ChildEntity)
          xMLWriter.e(PxKey.column, ((ChildEntity)depict.getEntity()).ownerColumn.getNameWithPath()); 
        xMLWriter.e(PxKey.color, ColorUtil.b(depict.getColor()))
          .e(PxKey.x, Integer.valueOf(depict.getPosition().e()))
          .e(PxKey.y, Integer.valueOf(depict.getPosition().f()));
        if (depict.hasHiddenAttributes()) {
          boolean bool = true;
          for (Attribute attribute : depict.getVisibleAttributes()) {
            xMLWriter.a(PxKey.column).e(PxKey.name, attribute.getName()).b(PxKey.column);
            bool = false;
          } 
          if (bool)
            xMLWriter.c(PxKey.hide_all); 
        } 
        for (Callout callout : layout.diagram.callouts) {
          if (callout.b == depict && !callout.g())
            a(xMLWriter, callout, (callout.c != depict.getEntity())); 
        } 
        xMLWriter.b(PxKey.entity);
      } 
      for (Callout callout : layout.diagram.callouts) {
        if (callout.b == null && !callout.g())
          a(xMLWriter, callout, false); 
      } 
      for (Shape shape : layout.diagram.shapes)
        a(xMLWriter, layout.diagram, shape); 
      for (Group group : layout.diagram.groups) {
        xMLWriter.a(PxKey.group)
          .e(PxKey.name, group)
          .e(PxKey.color, ColorUtil.b(group.getColor()));
        saveComment(group, xMLWriter);
        for (Depict depict : group.getDepicts()) {
          xMLWriter.a(PxKey.entity)
            .e(PxKey.schema, depict.entity.getSchema().getNameWithCatalog())
            .e(PxKey.name, depict.getEntity().getEntity());
          if (depict.getEntity() instanceof ChildEntity)
            xMLWriter.e(PxKey.column, ((ChildEntity)depict.getEntity()).ownerColumn.getNameWithPath()); 
          xMLWriter.b(PxKey.entity);
        } 
        for (Callout callout : layout.diagram.callouts) {
          if (callout.b == group)
            a(xMLWriter, callout, (callout.c != group)); 
        } 
        xMLWriter.b(PxKey.group);
      } 
      for (Script script : layout.scripts) {
        if (StringUtil.isFilled(script.getText())) {
          xMLWriter.a(PxKey.script)
            .e(PxKey.name, script)
            .e(PxKey.id, script.getKey());
          xMLWriter.e(PxKey.language, script.getLanguage());
          if (script.isConfirmed())
            xMLWriter.c(PxKey.confirmed); 
          if (script.getFile() == null) {
            xMLWriter.b(PxKey.string, script.getText());
          } else {
            xMLWriter.e(PxKey.file, script.getFile().toURI());
          } 
          saveComment(script, xMLWriter);
          xMLWriter.b(PxKey.script);
        } 
      } 
      for (Browse browse : layout.browses) {
        if (!browse.a().isEmpty()) {
          xMLWriter.a(PxKey.browser)
            .e(PxKey.id, browse.getKey())
            .e(PxKey.name, browse)
            .a(PxKey.confirm_updates, browse.e());
          if (browse.isConfirmed())
            xMLWriter.c(PxKey.confirmed); 
          for (BrowseTable browseTable : browse.a())
            a(xMLWriter, browseTable); 
          xMLWriter.b(PxKey.browser);
        } 
      } 
      for (Query query : layout.queries) {
        Depict depict = query.a();
        if (depict != null) {
          xMLWriter.a(PxKey.query)
            .e(PxKey.id, query.getKey())
            .e(PxKey.name, query.getName());
          if (query.d())
            xMLWriter.c(PxKey.groupby); 
          if (query.isConfirmed())
            xMLWriter.c(PxKey.confirmed); 
          a(xMLWriter, depict, (QueryRelation)null);
          xMLWriter.b(PxKey.query);
        } 
      } 
      xMLWriter.b(PxKey.layout);
    } 
    xMLWriter.b(PxKey.project);
    xMLWriter.b();
  }
  
  public static void saveComment(Unit paramUnit, XMLWriter paramXMLWriter) {
    paramXMLWriter.b(PxKey.comment, paramUnit.getComment());
    Map map = paramUnit.getCommentTags();
    if (map != null)
      for (String str : map.keySet())
        paramXMLWriter.a(PxKey.comment_tag).e(PxKey.name, str).e(PxKey.value, map.get(str)).b(PxKey.comment_tag);  
  }
  
  private void a(XMLWriter paramXMLWriter, AbstractFunction paramAbstractFunction) {
    for (FunctionParameter functionParameter : paramAbstractFunction.inputParameters)
      paramXMLWriter.a(PxKey.input_param)
        .e(PxKey.name, functionParameter.a)
        .e(PxKey.jt, Integer.valueOf(functionParameter.b))
        .e(PxKey.type, functionParameter.d)
        .e(PxKey.inOut, Integer.valueOf(functionParameter.e))
        .b(PxKey.input_param); 
    for (FunctionParameter functionParameter : paramAbstractFunction.resultParameters)
      paramXMLWriter.a(PxKey.result_param)
        .e(PxKey.name, functionParameter.a)
        .e(PxKey.jt, Integer.valueOf(functionParameter.b))
        .e(PxKey.type, functionParameter.d)
        .b(PxKey.result_param); 
  }
  
  private static void a(XMLWriter paramXMLWriter, Folder paramFolder) {
    for (Column column : paramFolder) {
      paramXMLWriter.a(PxKey.column).e(PxKey.name, column.getName())
        .e(PxKey.prior, column.getUnitProperty(UnitProperty.a))
        .e(PxKey.type, column.getDataType());
      if (column.hasLength())
        paramXMLWriter.e(PxKey.length, Integer.valueOf(column.getLength())); 
      if (column.hasDecimal())
        paramXMLWriter.e(PxKey.decimal, Integer.valueOf(column.getDecimal())); 
      paramXMLWriter.e(PxKey.jt, Integer.valueOf(column.getDataType().getJavaType()));
      paramXMLWriter.e(PxKey.definition, column.getDefinition());
      if (column.getSpec() != AttributeSpec.normal)
        paramXMLWriter.e(PxKey.spec, column.getSpec()); 
      if (column.isMandatory())
        paramXMLWriter.c(PxKey.mandatory); 
      if (column.isUnsigned())
        paramXMLWriter.c(PxKey.unsigned); 
      if (column.getToDoFlag() > -1)
        paramXMLWriter.e(PxKey.todo, Short.valueOf(column.getToDoFlag())); 
      paramXMLWriter.e(PxKey.regexp, column.getGeneratorPattern());
      if (column.getGeneratorSeed() > -1)
        paramXMLWriter.e(PxKey.seed, Integer.valueOf(column.getGeneratorSeed())); 
      if (column.hasGeneratorNullsPercentage())
        paramXMLWriter.e(PxKey.regexp_nulls, Short.valueOf(column.getGeneratorNullsPercentage())); 
      if (column.getAssociatedSequence() != null)
        paramXMLWriter.e(PxKey.sequence, column.getAssociatedSequence().getName()); 
      if (column.isVirtual())
        paramXMLWriter.c(PxKey.virtual); 
      paramXMLWriter.b(PxKey.defo, column.getDefaultValue())
        .b(PxKey.enumeration, column.getEnumeration())
        .b(PxKey.identity, column.getIdentity())
        .b(PxKey.column_options, column.getOptions())
        .b(PxKey.type_options, column.getTypeOptions());
      saveComment(column, paramXMLWriter);
      if (column.hasChildEntity())
        a(paramXMLWriter, (column.getChildEntity()).columns); 
      paramXMLWriter.b(PxKey.column);
    } 
  }
  
  private void a(XMLWriter paramXMLWriter, AbstractTable paramAbstractTable) {
    for (ForeignKey foreignKey : paramAbstractTable.getRelations()) {
      if (!foreignKey.isDeduced()) {
        paramXMLWriter.a(PxKey.fk).e(PxKey.name, foreignKey);
        if (foreignKey.isVirtual())
          paramXMLWriter.c(PxKey.virtual); 
        AbstractTable abstractTable = foreignKey.getTargetEntity();
        if (abstractTable != null)
          paramXMLWriter.e(PxKey.to_schema, abstractTable.getSchema().getNameWithCatalog()).e(PxKey.to_table, abstractTable); 
        if (foreignKey.getEntity().is(UnitProperty.g).booleanValue()) {
          paramXMLWriter.e(PxKey.type, foreignKey.getRelationType());
          paramXMLWriter.a(PxKey.mandatory, foreignKey.isLogicalMandatory());
          paramXMLWriter.e(PxKey.cardinality, foreignKey.getRelationCardinality());
          paramXMLWriter.e(PxKey.range_from, Integer.valueOf(foreignKey.getLogicalRangeFrom()));
          paramXMLWriter.e(PxKey.range_to, Integer.valueOf(foreignKey.getLogicalRangeTo()));
        } 
        if (foreignKey.getDeleteAction() != ForeignKeyAction.noAction)
          paramXMLWriter.e(PxKey.delete_action, foreignKey.getDeleteAction()); 
        if (foreignKey.getUpdateAction() != ForeignKeyAction.noAction)
          paramXMLWriter.e(PxKey.update_action, foreignKey.getUpdateAction()); 
        paramXMLWriter.e(PxKey.options, foreignKey.getOptions());
        for (Iterator<Column> iterator1 = foreignKey.getSourceAttributes().iterator(), iterator2 = foreignKey.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
          Column column1 = iterator1.next();
          Column column2 = iterator2.next();
          paramXMLWriter.a(PxKey.fk_column);
          if (column1 != null)
            paramXMLWriter.e(PxKey.name, column1.getNameWithPath()); 
          if (column2 != null)
            paramXMLWriter.e(PxKey.pk, column2.getNameWithPath()); 
          paramXMLWriter.a();
        } 
        saveComment(foreignKey, paramXMLWriter);
        paramXMLWriter.b(PxKey.fk);
      } 
    } 
  }
  
  private static void a(XMLWriter paramXMLWriter, BrowseTable paramBrowseTable) {
    paramXMLWriter.a(PxKey.browse_table)
      .e(PxKey.schema, paramBrowseTable.c.getSchema().getNameWithCatalog());
    if (paramBrowseTable.c instanceof ChildEntity) {
      Column column = ((ChildEntity)paramBrowseTable.c).ownerColumn;
      paramXMLWriter.e(PxKey.entity, column.table).e(PxKey.column, column.getNameWithPath());
    } else {
      paramXMLWriter.e(PxKey.entity, paramBrowseTable.c);
    } 
    paramXMLWriter.e(PxKey.alias, paramBrowseTable.j())
      .e(PxKey.fk, paramBrowseTable.d)
      .e(PxKey.x, Integer.valueOf(paramBrowseTable.k()))
      .e(PxKey.y, Integer.valueOf(paramBrowseTable.l()))
      .e(PxKey.width, Integer.valueOf(paramBrowseTable.m()))
      .e(PxKey.height, Integer.valueOf(paramBrowseTable.n()));
    if (paramBrowseTable.e.k())
      paramXMLWriter.c(PxKey.record_view); 
    paramXMLWriter.e(PxKey.filter, paramBrowseTable.e());
    for (Attribute attribute : paramBrowseTable.f())
      paramXMLWriter.a(PxKey.filter).e(PxKey.on, attribute).e(PxKey.string, paramBrowseTable.b(attribute)).b(PxKey.filter); 
    for (Attribute attribute : paramBrowseTable.g()) {
      paramXMLWriter.a(PxKey.orderby).e(PxKey.on, attribute);
      if (paramBrowseTable.e(attribute) == 1)
        paramXMLWriter.c(PxKey.asc); 
      paramXMLWriter.b(PxKey.orderby);
    } 
    for (BrowseTable browseTable : paramBrowseTable.d())
      a(paramXMLWriter, browseTable); 
    saveComment(paramBrowseTable, paramXMLWriter);
    paramXMLWriter.b(PxKey.browse_table);
  }
  
  private static void a(XMLWriter paramXMLWriter, Callout paramCallout, boolean paramBoolean) {
    paramXMLWriter.a(PxKey.callout)
      .e(PxKey.x, Integer.valueOf(paramCallout.getPosition().e()))
      .e(PxKey.y, Integer.valueOf(paramCallout.getPosition().f()))
      .e(PxKey.pointer, paramCallout.a());
    if (paramCallout.c == null) {
      saveComment(paramCallout, paramXMLWriter);
    } else if (paramBoolean) {
      paramXMLWriter.e(PxKey.on, paramCallout.c.getName());
    } 
    paramXMLWriter.b(PxKey.callout);
  }
  
  private static void a(XMLWriter paramXMLWriter, Diagram paramDiagram, Shape paramShape) {
    paramXMLWriter.a(PxKey.shape)
      .e(PxKey.x, Integer.valueOf(paramShape.a.getPosition().e()))
      .e(PxKey.y, Integer.valueOf(paramShape.a.getPosition().f()))
      .e(PxKey.style, paramShape.b())
      .e(PxKey.color, ColorUtil.b(paramShape.c()));
    saveComment(paramShape, paramXMLWriter);
    for (Line line : paramShape.c) {
      paramXMLWriter.a(PxKey.line)
        .e(PxKey.name, line.getName())
        .e(PxKey.shape, Integer.valueOf(paramDiagram.shapes.indexOf(line.b())));
      saveComment(line, paramXMLWriter);
      paramXMLWriter.b(PxKey.line);
    } 
    paramXMLWriter.b(PxKey.shape);
  }
  
  private static void a(XMLWriter paramXMLWriter, Depict paramDepict, QueryRelation paramQueryRelation) {
    paramXMLWriter.a(PxKey.query_table)
      .e(PxKey.schema, ((QueryTable)paramDepict.entity).f().getSchema().getNameWithCatalog());
    if (((QueryTable)paramDepict.entity).f() instanceof ChildEntity) {
      ChildEntity childEntity = (ChildEntity)((QueryTable)paramDepict.getEntity()).f();
      paramXMLWriter.e(PxKey.name, childEntity.getEntity()).e(PxKey.column, childEntity.ownerColumn.getNameWithPath());
    } else {
      paramXMLWriter.e(PxKey.name, ((QueryTable)paramDepict.getEntity()).f());
    } 
    paramXMLWriter.e(PxKey.alias, ((QueryTable)paramDepict.entity).d())
      .e(PxKey.x, Integer.valueOf(paramDepict.getPosition().e()))
      .e(PxKey.y, Integer.valueOf(paramDepict.getPosition().f()));
    if (paramQueryRelation != null)
      paramXMLWriter.e(PxKey.fk, paramQueryRelation.a)
        .e(PxKey.type, paramQueryRelation); 
    for (AbstractQueryColumn abstractQueryColumn : ((QueryTable)paramDepict.entity).c) {
      if (abstractQueryColumn.isTicked()) {
        paramXMLWriter.a(PxKey.column).e(PxKey.name, abstractQueryColumn.x.getName()).e(PxKey.alias, abstractQueryColumn.b());
        if (abstractQueryColumn instanceof QueryOperationColumn) {
          QueryOperationColumn queryOperationColumn = (QueryOperationColumn)abstractQueryColumn;
          if (queryOperationColumn instanceof QueryFilter)
            paramXMLWriter.e(PxKey.filter, ((QueryFilter)queryOperationColumn).d()); 
          if (queryOperationColumn instanceof QueryAggregate)
            paramXMLWriter.e(PxKey.aggregate, ((QueryAggregate)queryOperationColumn).y); 
          if (queryOperationColumn instanceof QueryOrderBy)
            paramXMLWriter.e(PxKey.orderby, ((QueryOrderBy)queryOperationColumn).d() ? "asc" : "desc"); 
        } 
        paramXMLWriter.b(PxKey.column);
      } 
    } 
    for (QueryRelation queryRelation : ((QueryTable)paramDepict.entity).getImportedRelations()) {
      Depict depict = paramDepict.diagram.getDepictFor(queryRelation.b());
      a(paramXMLWriter, depict, queryRelation);
    } 
    paramXMLWriter.b(PxKey.query_table);
  }
}
