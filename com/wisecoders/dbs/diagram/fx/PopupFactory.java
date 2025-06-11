package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class PopupFactory {
  public static void a(Workspace paramWorkspace, FxLayoutPane paramFxLayoutPane, ContextMenu$ paramContextMenu$, List<Unit> paramList) {
    paramContextMenu$.getItems().clear();
    paramContextMenu$.setAutoHide(true);
    paramContextMenu$.setHideOnEscape(true);
    if (paramList == null)
      return; 
    byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0;
    for (Unit unit : paramList) {
      if (unit instanceof Entity) {
        b1++;
        continue;
      } 
      if (unit instanceof Column) {
        b3++;
        continue;
      } 
      if (unit instanceof com.wisecoders.dbs.schema.Index) {
        b5++;
        continue;
      } 
      if (unit instanceof com.wisecoders.dbs.diagram.model.Relation) {
        b4++;
        continue;
      } 
      if (unit instanceof com.wisecoders.dbs.schema.LayoutDepict)
        b2++; 
    } 
    boolean bool1 = (b1 > 0 && b3 == 0 && b4 == 0) ? true : false;
    boolean bool2 = (b2 > 0 && b3 == 0 && b4 == 0) ? true : false;
    if (paramList.size() > 1) {
      paramContextMenu$.setAutoHide(true);
      paramContextMenu$.setHideOnEscape(true);
      if (!paramWorkspace.t() && b4 == 0 && ((b1 > 0 && b3 == 0) || (b1 == 0 && b3 > 0) || (b1 == 0 && b5 > 0)))
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "gridEditor" })); 
      if (bool1 && paramFxLayoutPane != null) {
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("newLayout"));
        Menu menu1 = new Menu(paramWorkspace.getRx().H("groupMenu"));
        paramContextMenu$.getItems().add(menu1);
        menu1.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "addTableToGroup", "ungroup" }));
        Menu menu2 = new Menu("Align");
        paramContextMenu$.getItems().add(menu2);
        menu2.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "alignLeft", "alignRight", "alignTop", "alignBottom" }));
        Menu menu3 = new Menu(paramWorkspace.getRx().H("showHideMenu"));
        menu3.getItems().addAll(paramWorkspace.getRx().e(new String[] { "hideAllColumns", "showAllColumns" }));
        menu3.getItems().addAll((Object[])new MenuItem[] { paramFxLayoutPane.a.A("showAllEntityForeignKeys") });
        paramContextMenu$.getItems().add(menu3);
        paramContextMenu$.getItems().add(paramFxLayoutPane.a.A("changeTableColor"));
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("dataGenerator"));
      } 
      if (!paramWorkspace.k()) {
        if (b1 > 0 || b3 > 0 || b4 > 0 || b5 > 0)
          paramContextMenu$.getItems().add(paramWorkspace.getRx().A("synchronizeRefreshItem")); 
        if (b4 == 0 && b1 == 0 && b3 > 0) {
          Menu menu = new Menu(paramWorkspace.getRx().H("buildSql"));
          menu.getItems().addAll(paramWorkspace.getRx().e(new String[] { "generateSelectSql", "generateInsertSql", "generateUpdateSql", "generateMergeSql", "generateDeleteSql", "separator", "generateCreateSql" }));
          paramContextMenu$.getItems().addAll((Object[])new MenuItem[] { (MenuItem)menu, paramFxLayoutPane.a.A("hideColumns"), paramWorkspace.getRx().A("addColumnToIndex") });
        } 
        if (b1 > 0 || b3 > 0 || b4 > 0)
          paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "separator", "browseSelected", "querySelected" })); 
      } 
      if (b5 > 0 && b5 == paramList.size())
        for (Unit unit : paramList) {
          MenuItem menuItem = new MenuItem("Edit " + unit.getSymbolicName() + " " + unit.getName(), (Node)BootstrapIcons.pencil.glyph(new String[0]));
          menuItem.setOnAction(paramActionEvent -> FxEditorFactory.a(paramWorkspace, paramUnit));
          paramContextMenu$.getItems().add(menuItem);
        }  
      if (bool1 || bool2)
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("excludeFromLayout")); 
      if (paramList.size() == 1) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("deleteUnit"));
      } 
    } else {
      Unit unit = (paramList.size() == 1) ? paramList.get(0) : null;
      a(paramWorkspace, paramFxLayoutPane, paramContextMenu$, unit);
    } 
    paramContextMenu$.a();
  }
  
  private static void a(Workspace paramWorkspace, FxLayoutPane paramFxLayoutPane, ContextMenu$ paramContextMenu$, Unit paramUnit) {
    if (paramUnit instanceof Folder) {
      Folder folder = (Folder)paramUnit;
      boolean bool = (paramWorkspace.l() && (!paramWorkspace.t() || (Dbms.get(paramWorkspace.m().getDbId())).alterSchema.b())) ? true : false;
      if (folder instanceof com.wisecoders.dbs.diagram.model.PropertyAddOnFolder || folder instanceof com.wisecoders.dbs.diagram.model.ScriptAddOnFolder) {
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "refreshAddOnFolder" }));
      } else if (!(folder.parent instanceof com.wisecoders.dbs.schema.View) && (folder.childClass != Schema.class || bool)) {
        if (paramWorkspace.j() && folder.childClass == Table.class) {
          paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "createCollectionAndInsertDocument", "newTable" }));
        } else {
          MenuItem menuItem = paramWorkspace.getRx().A("newUnitInFolder");
          paramContextMenu$.getItems().add(menuItem);
          menuItem.setText("Create " + folder.getChildrenName());
        } 
      } 
      if (!paramWorkspace.k() && (folder.childClass == Table.class || folder.childClass == Schema.class))
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("synchronizeRefreshItem")); 
      if (!paramContextMenu$.getItems().isEmpty())
        paramContextMenu$.getItems().add(new SeparatorMenuItem()); 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "findInPath", "expandChildren" }));
    } else if (paramUnit instanceof Table) {
      if (paramWorkspace.j())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("insertDocument")); 
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("editTable"));
      if (!paramWorkspace.t())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("gridEditor")); 
      if (!paramWorkspace.k())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("synchronizeRefreshItem")); 
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      if (!paramWorkspace.k()) {
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("sqlSelected"));
        Menu menu1 = new Menu(paramWorkspace.getRx().H("buildSql"));
        menu1.getItems().addAll(paramWorkspace.getRx().e(new String[] { "generateSelectSql", "generateInsertSql", "generateUpdateSql", "generateMergeSql", "generateDeleteSql", "generateCreateSql", "separator" }));
        menu1.getItems().add(paramWorkspace.getRx().y("generateSqlToClipboard"));
        paramContextMenu$.getItems().add(menu1);
      } 
      if (!paramWorkspace.k()) {
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "browseSelected", "querySelected" }));
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
      } 
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("newLayout"));
      Menu menu = new Menu(paramWorkspace.getRx().H("groupMenu"));
      menu.getItems().addAll(paramWorkspace.getRx().e(new String[] { "addTableToGroup", "unGroup" }));
      paramContextMenu$.getItems().add(menu);
      if (paramFxLayoutPane != null) {
        menu.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "separator", "selectRelatedEntities" }));
        if (!paramWorkspace.k())
          paramContextMenu$.getItems().addAll((Object[])new MenuItem[] { paramFxLayoutPane.c.ao.A("showPreviewDataPopup") }); 
        Menu menu1 = new Menu(paramWorkspace.getRx().H("showHideMenu"));
        menu1.getItems().addAll(paramWorkspace.getRx().e(new String[] { "hideAllColumns", "showAllColumns" }));
        Menu menu2 = new Menu("Show Table via Foreign Key");
        menu2.getItems().addAll(paramFxLayoutPane.c.a((Table)paramUnit, null, false, false));
        if (!menu2.getItems().isEmpty())
          menu1.getItems().add(menu2); 
        menu1.getItems().addAll((Object[])new MenuItem[] { paramFxLayoutPane.a.A("showAllEntityForeignKeys") });
        paramContextMenu$.getItems().add(menu1);
      } 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "orderColumnsAlphabetically" }));
      if (paramFxLayoutPane != null)
        paramContextMenu$.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "newCalloutOn", "changeTableColor" })); 
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      if (paramWorkspace.k())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("addSubEntity")); 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "addColumn", "newIndex", "newRelationFromEntity", "dependencies", "cloneUnit", "separator", "deleteUnit" }));
      if (paramFxLayoutPane != null)
        paramContextMenu$.getItems().add(paramFxLayoutPane.c.ao.A("excludeFromLayout")); 
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.ChildEntity) {
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("editUnit"));
      if (paramFxLayoutPane != null) {
        paramContextMenu$.getItems().add(paramFxLayoutPane.a.A("newCalloutOn"));
        paramContextMenu$.getItems().add(paramFxLayoutPane.a.A("changeTableColor"));
      } 
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      if (paramFxLayoutPane != null) {
        Menu menu1 = new Menu("Show Table via Foreign Key");
        menu1.getItems().addAll(paramFxLayoutPane.c.a((Entity)paramUnit, null, false, false));
        if (!menu1.getItems().isEmpty())
          paramContextMenu$.getItems().add(menu1); 
        paramContextMenu$.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "showAllEntityForeignKeys", "separator" }));
      } 
      if (!paramWorkspace.k())
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "browseSelected", "querySelected", "editGeneratorPattern", "separator" })); 
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("newLayout"));
      Menu menu = new Menu(paramWorkspace.getRx().H("groupMenu"));
      menu.getItems().addAll(paramWorkspace.getRx().e(new String[] { "addTableToGroup", "unGroup" }));
      paramContextMenu$.getItems().add(menu);
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "addColumn", "newIndex", "newRelationFromEntity", "dependencies", "orderColumnsAlphabetically" }));
      if (paramFxLayoutPane != null) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        paramContextMenu$.getItems().add(paramFxLayoutPane.c.ao.A("excludeFromLayout"));
      } 
    } else if (paramUnit instanceof Column) {
      Column column = (Column)paramUnit;
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("editUnit"));
      if (!paramWorkspace.t())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("gridEditor")); 
      if (paramFxLayoutPane != null)
        paramContextMenu$.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "hideColumns", "newCalloutOn" })); 
      if (column.getEntity() instanceof Table)
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("addColumnToIndex")); 
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("newRelationFromEntity"));
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      if (paramFxLayoutPane != null) {
        Menu menu = new Menu("Show Table via Foreign Key");
        menu.getItems().addAll(paramFxLayoutPane.c.a(column.getEntity(), column, false, false));
        if (!menu.getItems().isEmpty()) {
          paramContextMenu$.getItems().add(menu);
          paramContextMenu$.getItems().add(new SeparatorMenuItem());
        } 
      } 
      if (!paramWorkspace.k()) {
        Menu menu = new Menu(paramWorkspace.getRx().H("buildSql"));
        menu.getItems().addAll(paramWorkspace.getRx().e(new String[] { "generateSelectSql", "generateInsertSql", "generateUpdateSql", "generateMergeSql", "generateDeleteSql", "generateCreateSql", "separator" }));
        menu.getItems().add(paramWorkspace.getRx().y("generateSqlToClipboard"));
        paramContextMenu$.getItems().add(menu);
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("browseSelected"));
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "querySelected", "separator" }));
      } 
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("deleteUnit"));
    } else if (paramUnit instanceof com.wisecoders.dbs.diagram.model.Group) {
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("unitProperties"));
      if (paramFxLayoutPane != null)
        paramContextMenu$.getItems().add(paramFxLayoutPane.a.A("newCalloutOn")); 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "separator", "newTable", "newLayoutFromGroup", "separator" }));
      if (paramFxLayoutPane != null) {
        paramContextMenu$.getItems().add(paramFxLayoutPane.c.ao.A("autoplace"));
        paramContextMenu$.getItems().add(paramFxLayoutPane.a.A("groupsManager"));
      } 
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("deleteUnit"));
    } else if (paramUnit instanceof ForeignKey) {
      ForeignKey foreignKey = (ForeignKey)paramUnit;
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("editUnit"));
      if (paramFxLayoutPane != null)
        paramContextMenu$.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "newCalloutOn", "hideSelectedForeignKey", "showAllForeignKeys" })); 
      if (!paramWorkspace.k()) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "generateSelectSql", "generateCreateSql", "browseSelected", "querySelected" }));
      } else if (foreignKey.getRelationType() == RelationType.c) {
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "resolveManyToManyRelation" }));
      } 
      if (paramFxLayoutPane != null) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        MenuItem menuItem1 = paramFxLayoutPane.c.ao.A("showDestTable");
        menuItem1.setText("Show " + foreignKey.getTargetEntity().getName() + " ( Parent )");
        paramContextMenu$.getItems().add(menuItem1);
        MenuItem menuItem2 = paramFxLayoutPane.c.ao.A("showSourceTable");
        menuItem2.setText("Show " + foreignKey.getEntity().getName() + " ( Child )");
        paramContextMenu$.getItems().add(menuItem2);
        if (foreignKey.isVirtual())
          paramContextMenu$.getItems().add(paramWorkspace.getRx().A("virtualFkToNormal")); 
      } 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "separator", "deleteUnit" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.View) {
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("editUnit"));
      if (!paramWorkspace.k())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("synchronizeRefreshItem")); 
      if (paramFxLayoutPane != null) {
        if (!paramWorkspace.k())
          paramContextMenu$.getItems().add(paramFxLayoutPane.c.ao.A("showPreviewDataPopup")); 
        paramContextMenu$.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "newCalloutOn", "changeTableColor", "showColumns" }));
      } 
      Menu menu = new Menu(paramWorkspace.getRx().H("groupMenu"));
      menu.getItems().addAll(paramWorkspace.getRx().e(new String[] { "addTableToGroup", "unGroup" }));
      paramContextMenu$.getItems().add(menu);
      paramContextMenu$.getItems().add(new SeparatorMenuItem());
      if (!paramWorkspace.k()) {
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("browseSelected"));
        Menu menu1 = new Menu(paramWorkspace.getRx().H("buildSql"));
        menu1.getItems().addAll(paramWorkspace.getRx().e(new String[] { "generateSelectSql", "generateInsertSql", "generateUpdateSql", "generateMergeSql", "generateDeleteSql", "generateCreateSql" }));
        paramContextMenu$.getItems().add(menu1);
      } 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "newRelationFromEntity", "separator", "deleteUnit" }));
      if (paramFxLayoutPane != null)
        paramContextMenu$.getItems().add(paramFxLayoutPane.c.ao.A("excludeFromLayout")); 
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.Project) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "unitProperties", "synchronizeRefreshItem", "separator", "findInPath" }));
    } else if (paramUnit instanceof Schema) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "editUnit", "synchronizeRefreshItem", "synchronizeSchemas", "separator" }));
      if (paramWorkspace.j())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("createCollectionAndInsertDocument")); 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "newTable", "newView", "newSequence", "newProcedure", "newFunction", "separator", "deleteUnit" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.diagram.model.Callout) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "editUnit", "separator", "deleteUnit" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.Layout) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "activateLayout", "unitProperties", "synchronizeRefreshItem", "separator", "duplicateLayout", "deleteUnit" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.diagram.model.ToolUnit) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "editUnit", "editDescription", "renameUnit" }));
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "separator", "deleteUnit" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.DbUnit) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "editUnit", "renameUnit", "editDescription", "generateCreateSql" }));
      if (paramUnit instanceof com.wisecoders.dbs.schema.Function || paramUnit instanceof com.wisecoders.dbs.schema.Procedure || paramUnit instanceof com.wisecoders.dbs.schema.Trigger) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        paramContextMenu$.getItems().add(paramWorkspace.getRx().y("markSystemFunction"));
      } 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "separator", "deleteUnit" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.LayoutDepict) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "editUnit", "separator", "excludeFromLayout" }));
    } else if (paramUnit instanceof com.wisecoders.dbs.diagram.model.AbstractUnit) {
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "unitProperties", "separator", "deleteUnit" }));
    } else if (paramUnit == null) {
      paramContextMenu$.getItems().add(paramWorkspace.getRx().A("newTable"));
      if (paramWorkspace.j())
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("insertDocument")); 
      paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "newView", "newCallout", "newShape", "newGroup" }));
      if (!paramWorkspace.k()) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        if (paramFxLayoutPane == null) {
          paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "newSqlEditor", "newBrowseEditor", "newQueryEditor" }));
        } else {
          paramContextMenu$.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "newSqlEditor", "newBrowseEditor", "newQueryEditor" }));
        } 
        paramContextMenu$.getItems().add(paramWorkspace.getRx().A("dataGenerator"));
      } 
      if (paramFxLayoutPane != null) {
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        Menu menu = new Menu(paramWorkspace.getRx().H("showHideMenu"));
        menu.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "showAllColumns", "hideAllColumns" }));
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().addAll(paramFxLayoutPane.a.e(new String[] { "showAllForeignKeys", "highlightVirtualForeignKeys" }));
        paramContextMenu$.getItems().add(menu);
        paramContextMenu$.getItems().add(paramFxLayoutPane.c.ao.A("autoplace"));
        paramContextMenu$.getItems().add(new SeparatorMenuItem());
        paramContextMenu$.getItems().add(paramFxLayoutPane.a.A("documentation"));
        paramContextMenu$.getItems().addAll(paramWorkspace.getRx().e(new String[] { "duplicateLayout", "layoutProperties" }));
      } 
    } 
    paramContextMenu$.a();
  }
}
