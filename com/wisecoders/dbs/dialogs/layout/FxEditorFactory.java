package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.browse.fx.FxBrowseEditor;
import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.data.filter.fx.FxFilterEditor;
import com.wisecoders.dbs.data.filter.fx.FxQueryColumnEditor;
import com.wisecoders.dbs.data.filter.fx.FxQueryEntityEditor;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Line;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.ToolUnit;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.query.fx.FxQueryEditor;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryAggregate;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.query.model.items.QueryFilter;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.PropertyAddOn;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.schema.ScriptAddOn;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.fx.Dialog$;

public class FxEditorFactory {
  public static FxUnitEditor a(WorkspaceWindow paramWorkspaceWindow, Unit paramUnit) {
    return a(paramWorkspaceWindow, paramUnit, true);
  }
  
  public static FxUnitEditor a(WorkspaceWindow paramWorkspaceWindow, Unit paramUnit, boolean paramBoolean) {
    FxToolEditor fxToolEditor;
    FxProjectEditor fxProjectEditor = null;
    if (paramUnit != null) {
      if (paramUnit instanceof ToolUnit) {
        Layout layout = ((ToolUnit)paramUnit).getLayout();
        if (layout != null)
          paramWorkspaceWindow.getWorkspace().a(layout); 
      } else if (paramUnit instanceof com.wisecoders.dbs.schema.LayoutDepict) {
        paramUnit = paramUnit.getEntity();
      } 
      if (paramUnit instanceof Project) {
        Project project = (Project)paramUnit;
        fxProjectEditor = new FxProjectEditor(paramWorkspaceWindow, project, false, null);
      } else if (paramUnit instanceof Layout) {
        Layout layout = (Layout)paramUnit;
        FxLayoutEditor fxLayoutEditor = new FxLayoutEditor(paramWorkspaceWindow, layout.project, layout);
      } else if (paramUnit instanceof Schema) {
        Schema schema = (Schema)paramUnit;
        FxSchemaEditor fxSchemaEditor = new FxSchemaEditor(paramWorkspaceWindow, schema.project, schema);
      } else if (paramUnit instanceof Group) {
        Group group = (Group)paramUnit;
        FxGroupEditor fxGroupEditor = new FxGroupEditor(paramWorkspaceWindow, group);
      } else if (paramUnit instanceof Column && ((Column)paramUnit).hasChildEntity()) {
        FxColumnEditor fxColumnEditor = new FxColumnEditor(paramWorkspaceWindow, ((Column)paramUnit).getChildEntity(), (Column)paramUnit, AttributeSpec.normal, paramBoolean);
      } else if (paramUnit instanceof ChildEntity) {
        ChildEntity childEntity = (ChildEntity)paramUnit;
        FxTableEditor fxTableEditor = new FxTableEditor(paramWorkspaceWindow, childEntity, paramBoolean);
      } else if (paramUnit instanceof Table) {
        Table table = (Table)paramUnit;
        FxTableEditor fxTableEditor = new FxTableEditor(paramWorkspaceWindow, table, paramBoolean);
      } else if (paramUnit instanceof View) {
        View view = (View)paramUnit;
        FxViewEditor fxViewEditor = new FxViewEditor(paramWorkspaceWindow, (Folder)view.getParent(), view);
      } else if (paramUnit instanceof QueryTable) {
        QueryTable queryTable = (QueryTable)paramUnit;
        (new FxQueryEntityEditor(paramWorkspaceWindow, queryTable)).showDialog();
      } else if (paramUnit instanceof QueryColumn) {
        QueryColumn queryColumn = (QueryColumn)paramUnit;
        (new FxQueryColumnEditor(paramWorkspaceWindow, queryColumn)).showDialog();
      } else if (paramUnit instanceof QueryAggregate) {
        QueryAggregate queryAggregate = (QueryAggregate)paramUnit;
        (new FxQueryColumnEditor(paramWorkspaceWindow, queryAggregate)).showDialog();
      } else if (paramUnit instanceof QueryFilter) {
        QueryFilter queryFilter = (QueryFilter)paramUnit;
        (new FxFilterEditor(paramWorkspaceWindow, queryFilter.a(), queryFilter.x, queryFilter.d())).showDialog();
      } else if (paramUnit instanceof Column) {
        Column column = (Column)paramUnit;
        AbstractTable abstractTable = column.getEntity();
        if (abstractTable instanceof View) {
          View view = (View)abstractTable;
          FxViewColumnEditor fxViewColumnEditor = new FxViewColumnEditor(paramWorkspaceWindow, view, column);
        } else {
          FxColumnEditor fxColumnEditor = new FxColumnEditor(paramWorkspaceWindow, column, paramBoolean);
        } 
      } else if (paramUnit instanceof Index) {
        Index index = (Index)paramUnit;
        FxIndexEditor fxIndexEditor = new FxIndexEditor(paramWorkspaceWindow, index, paramBoolean);
      } else if (paramUnit instanceof ForeignKey) {
        ForeignKey foreignKey = (ForeignKey)paramUnit;
        FxForeignKeyEditor fxForeignKeyEditor = new FxForeignKeyEditor(paramWorkspaceWindow, foreignKey, paramBoolean);
      } else if (paramUnit instanceof Constraint) {
        Constraint constraint = (Constraint)paramUnit;
        FxConstraintEditor fxConstraintEditor = new FxConstraintEditor(paramWorkspaceWindow, constraint, paramBoolean);
      } else if (paramUnit instanceof Sequence) {
        Sequence sequence = (Sequence)paramUnit;
        FxSequenceEditor fxSequenceEditor = new FxSequenceEditor(paramWorkspaceWindow, sequence);
      } else if (paramUnit instanceof UserDataType) {
        UserDataType userDataType = (UserDataType)paramUnit;
        if ("LogicalDesign".equals(userDataType.getDbId()) && !userDataType.columns.isEmpty()) {
          FxTableEditor fxTableEditor = new FxTableEditor(paramWorkspaceWindow, userDataType, paramBoolean);
        } else {
          FxUserDataTypeEditor fxUserDataTypeEditor = new FxUserDataTypeEditor(paramWorkspaceWindow, userDataType);
        } 
      } else if (paramUnit instanceof com.wisecoders.dbs.diagram.model.Callout) {
        FxCalloutEditor fxCalloutEditor = new FxCalloutEditor(paramWorkspaceWindow, paramUnit);
      } else if (paramUnit instanceof Shape) {
        Shape shape = (Shape)paramUnit;
        FxShapeEditor fxShapeEditor = new FxShapeEditor(paramWorkspaceWindow, shape);
      } else if (paramUnit instanceof Line) {
        Line line = (Line)paramUnit;
        FxLineEditor fxLineEditor = new FxLineEditor(paramWorkspaceWindow, line);
      } else if (paramUnit instanceof ToolUnit) {
        ToolUnit toolUnit = (ToolUnit)paramUnit;
        FxLayoutPane fxLayoutPane = paramWorkspaceWindow.getWorkspace().a(toolUnit.getLayout());
        fxToolEditor = fxLayoutPane.b(toolUnit);
      } else if (paramUnit instanceof PropertyAddOn) {
        PropertyAddOn propertyAddOn = (PropertyAddOn)paramUnit;
        FxAddOnEditor fxAddOnEditor = new FxAddOnEditor(paramWorkspaceWindow, propertyAddOn.b, propertyAddOn);
      } else if (paramUnit instanceof ScriptAddOn) {
        ScriptAddOn scriptAddOn = (ScriptAddOn)paramUnit;
        paramWorkspaceWindow.getRx().a(paramWorkspaceWindow.getWorkspace().a(scriptAddOn));
      } else if (paramUnit instanceof Sql) {
        FxLayoutPane fxLayoutPane = paramWorkspaceWindow.getWorkspace().q();
        fxToolEditor = fxLayoutPane.b(paramUnit);
      } 
      if (fxToolEditor instanceof Dialog$) {
        Dialog$ dialog$ = (Dialog$)fxToolEditor;
        dialog$.showDialog();
      } 
    } 
    return fxToolEditor;
  }
  
  public static void a(WorkspaceWindow paramWorkspaceWindow, Folder paramFolder) {
    a(paramWorkspaceWindow, paramFolder, true);
  }
  
  public static FxUnitEditor a(WorkspaceWindow paramWorkspaceWindow, Folder paramFolder, boolean paramBoolean) {
    FxUnitEditor fxUnitEditor;
    FxColumnEditor fxColumnEditor = null;
    Class<Column> clazz = paramFolder.getChildClass();
    AbstractUnit abstractUnit = paramFolder.getParent();
    Folder folder = paramFolder;
    FxLayoutPane fxLayoutPane = paramWorkspaceWindow.getWorkspace().o();
    while (folder != null && folder.getParent() != null) {
      TreeUnit treeUnit = folder.getParent();
      if (treeUnit instanceof Layout)
        fxLayoutPane = paramWorkspaceWindow.getWorkspace().a((Layout)treeUnit); 
    } 
    if (clazz == Column.class && abstractUnit instanceof Column) {
      Column column = (Column)abstractUnit;
      fxColumnEditor = new FxColumnEditor(paramWorkspaceWindow, column.getChildEntity(), paramBoolean);
    } else if (clazz == Column.class && abstractUnit instanceof Entity) {
      Entity entity = (Entity)abstractUnit;
      fxColumnEditor = new FxColumnEditor(paramWorkspaceWindow, entity, paramBoolean);
    } else if (clazz == Constraint.class) {
      FxConstraintEditor fxConstraintEditor = new FxConstraintEditor(paramWorkspaceWindow, (Table)abstractUnit, paramBoolean);
    } else if (clazz == Schema.class) {
      FxSchemaEditor fxSchemaEditor = new FxSchemaEditor(paramWorkspaceWindow, (Project)abstractUnit, null);
    } else if (clazz == View.class) {
      FxViewEditor fxViewEditor = new FxViewEditor(paramWorkspaceWindow, paramFolder, (Schema)abstractUnit);
    } else if (clazz == MaterializedView.class) {
      FxViewEditor fxViewEditor = new FxViewEditor(paramWorkspaceWindow, paramFolder, (Schema)abstractUnit);
    } else if (clazz == Table.class) {
      FxTableEditor fxTableEditor = new FxTableEditor(paramWorkspaceWindow, (Schema)abstractUnit, Table.class, paramBoolean);
    } else if (clazz == AbstractTable.class && abstractUnit instanceof Layout) {
      Layout layout = (Layout)abstractUnit;
      FxTableEditor fxTableEditor = new FxTableEditor(paramWorkspaceWindow, layout.project, paramBoolean);
    } else if (clazz == Index.class) {
      FxIndexEditor fxIndexEditor = new FxIndexEditor(paramWorkspaceWindow, (Table)abstractUnit, paramBoolean);
    } else if (clazz == ForeignKey.class && abstractUnit instanceof ChildEntity) {
      ChildEntity childEntity = (ChildEntity)abstractUnit;
      FxForeignKeyEditor fxForeignKeyEditor = new FxForeignKeyEditor(paramWorkspaceWindow, childEntity.ownerColumn.table, null, paramBoolean);
    } else if (clazz == ForeignKey.class) {
      FxForeignKeyEditor fxForeignKeyEditor = new FxForeignKeyEditor(paramWorkspaceWindow, (AbstractTable)abstractUnit, null, paramBoolean);
    } else if (clazz == Sequence.class) {
      FxSequenceEditor fxSequenceEditor = new FxSequenceEditor(paramWorkspaceWindow, (Schema)abstractUnit);
    } else if (clazz == UserDataType.class) {
      fxUnitEditor = "LogicalDesign".equals(paramWorkspaceWindow.getWorkspace().m().getDbId()) ? (new FxGenericUserDataTypeEditor(paramWorkspaceWindow, (Schema)abstractUnit, paramBoolean)).showDialog().orElse(null) : new FxUserDataTypeEditor(paramWorkspaceWindow, (Schema)abstractUnit);
    } else if (clazz == Layout.class) {
      fxUnitEditor = new FxLayoutEditor(paramWorkspaceWindow, (Project)abstractUnit, null);
    } else if (clazz == Browse.class) {
      fxUnitEditor = new FxBrowseEditor(paramWorkspaceWindow.getWorkspace().o(), fxLayoutPane.d.createBrowse(null));
    } else if (clazz == Query.class) {
      fxUnitEditor = new FxQueryEditor(fxLayoutPane.b, fxLayoutPane.d.createQuery(null));
    } else if (clazz == PropertyAddOn.class) {
      fxUnitEditor = new FxAddOnEditor(paramWorkspaceWindow, (PropertyAddOnFolder)paramFolder);
    } else if (clazz == BrowseTable.class) {
      fxColumnEditor = null;
    } else if (Sql.class.isAssignableFrom(clazz)) {
      if (clazz == Script.class || clazz == ScriptAddOn.class) {
        String str = paramFolder.proposeName(null);
        fxUnitEditor = paramWorkspaceWindow.getWorkspace().q().a(((Layout)abstractUnit).createScript(str));
      } else {
        String str = paramWorkspaceWindow.getRx().b(paramWorkspaceWindow.getDialogScene(), paramFolder.getChildrenName() + " Name", paramFolder.proposeName(null));
        if (str != null)
          if (clazz == Procedure.class) {
            fxUnitEditor = paramWorkspaceWindow.getWorkspace().q().a(((Schema)abstractUnit).createProcedure(str).setDefaultText());
          } else if (clazz == Function.class) {
            fxUnitEditor = paramWorkspaceWindow.getWorkspace().q().a(((Schema)abstractUnit).createFunction(str).setDefaultText());
          } else if (clazz == Rule.class) {
            Schema schema2 = (Schema)abstractUnit, schema1 = (abstractUnit instanceof Schema) ? schema2 : ((Table)abstractUnit).getSchema();
            Table table = (Table)abstractUnit;
            fxUnitEditor = paramWorkspaceWindow.getWorkspace().q().a(schema1.createRule(str, (abstractUnit instanceof Table) ? table : null).setDefaultText());
          } else if (clazz == Trigger.class) {
            Schema schema2 = (Schema)abstractUnit, schema1 = (abstractUnit instanceof Schema) ? schema2 : ((Table)abstractUnit).getSchema();
            Table table = (Table)abstractUnit;
            fxUnitEditor = paramWorkspaceWindow.getWorkspace().q().a(schema1.createTrigger(str, (abstractUnit instanceof Table) ? table : null).setDefaultText());
          }  
      } 
    } 
    if (fxUnitEditor instanceof Dialog$) {
      Dialog$ dialog$ = (Dialog$)fxUnitEditor;
      dialog$.showDialog();
    } else if (fxUnitEditor instanceof FxToolEditor) {
      FxToolEditor fxToolEditor = (FxToolEditor)fxUnitEditor;
      fxLayoutPane.a(fxToolEditor);
    } 
    return fxUnitEditor;
  }
}
