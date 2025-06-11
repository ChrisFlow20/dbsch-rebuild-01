package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.browse.fx.FxBrowseEditor;
import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.nodes.EditorSyncRoot;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.layout.FxCalloutEditor;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.FxForeignKeyEditor;
import com.wisecoders.dbs.dialogs.layout.FxGroupEditor;
import com.wisecoders.dbs.dialogs.layout.FxGroupsEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.query.fx.FxQueryEditor;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.fx.FxJsonInsertEditor;
import com.wisecoders.dbs.sql.fx.FxSqlEditor;
import com.wisecoders.dbs.sql.generator.StatementType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Pro;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.ChoiceDialogWithFilterableCombo;
import com.wisecoders.dbs.sys.fx.FxColorPicker;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FxLayoutPane extends FxSplitWithTabPane implements GenericLayoutPane, FxToolEditor {
  public final Rx a = new Rx(FxLayoutPane.class, this);
  
  public final Workspace b;
  
  public final FxDiagramPane c;
  
  public final Layout d;
  
  public FxLayoutPane(Workspace paramWorkspace, Layout paramLayout, boolean paramBoolean) {
    this.b = paramWorkspace;
    this.d = paramLayout;
    this.c = new FxDiagramPane(this, paramLayout);
    this.a.G(paramLayout.project.getDbId());
    addEventHandler(KeyEvent.ANY, paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.LEFT || paramKeyEvent.getCode() == KeyCode.RIGHT || paramKeyEvent.getCode() == KeyCode.UP || paramKeyEvent.getCode() == KeyCode.DOWN)
            paramKeyEvent.consume(); 
        });
    AnchorPane anchorPane = new AnchorPane(new Node[] { (Node)this.c, (Node)this.c.b });
    anchorPane.setMinHeight(0.0D);
    AnchorPane.setTopAnchor((Node)this.c.b, Double.valueOf(10.0D));
    AnchorPane.setRightAnchor((Node)this.c.b, Double.valueOf(15.0D));
    AnchorPane.setTopAnchor((Node)this.c, Double.valueOf(0.0D));
    AnchorPane.setLeftAnchor((Node)this.c, Double.valueOf(0.0D));
    AnchorPane.setRightAnchor((Node)this.c, Double.valueOf(0.0D));
    AnchorPane.setBottomAnchor((Node)this.c, Double.valueOf(0.0D));
    getItems().addAll((Object[])new Node[] { (Node)anchorPane });
    b b = new b(this, paramBoolean);
    if (paramLayout.is(UnitProperty.f).booleanValue()) {
      b.a(paramLayout.scripts).a(paramLayout.browses).a(paramLayout.queries);
    } else {
      b.a(paramLayout.browses).a(paramLayout.queries).a(paramLayout.scripts);
    } 
    b.a();
    if (this.c.a.callouts.size() == 1) {
      Platform.runLater(() -> this.c.c(this.c.a.callouts.get(0)));
    } else {
      Objects.requireNonNull(this.c);
      Platform.runLater(this.c::i);
    } 
  }
  
  public void a() {
    this.b.x();
  }
  
  public void q() {
    this.c.k();
    for (Tab tab : this.f.getTabs()) {
      if (tab instanceof FxToolEditorTab)
        ((FxToolEditorTab)tab).a().q(); 
    } 
  }
  
  public void a(Entity paramEntity) {
    this.c.a(paramEntity);
  }
  
  public void b(Entity paramEntity) {
    this.d.diagram.attachRecursiveAndCreateGroupForHierarchicalEntities(paramEntity, this.c.v());
    this.c.a(200.0D, 0.0D);
  }
  
  public FxSqlEditor a(Sql paramSql) {
    return new FxSqlEditor(this.b, this, paramSql);
  }
  
  public void a(FxToolEditor paramFxToolEditor) {
    a(2147483647, new FxToolEditorTab(paramFxToolEditor));
  }
  
  public boolean i() {
    FxUtil.a(this.d.scripts);
    FxUtil.a(this.d.browses);
    FxUtil.a(this.d.queries);
    for (Tab tab : this.f.getTabs()) {
      Node node = tab.getContent();
      if (node instanceof FxToolEditor) {
        FxToolEditor fxToolEditor = (FxToolEditor)node;
        fxToolEditor.i();
        if (fxToolEditor.j() != null)
          Pref.a("open:" + fxToolEditor.j().getKey(), this.f.getTabs().indexOf(tab)); 
      } 
    } 
    return true;
  }
  
  public void f() {
    for (Tab tab : this.f.getTabs()) {
      if (tab instanceof FxToolEditorTab)
        ((FxToolEditorTab)tab).a().f(); 
    } 
  }
  
  public FxToolEditor a(Unit paramUnit) {
    for (Tab tab : this.f.getTabs()) {
      if (tab instanceof FxToolEditorTab) {
        FxToolEditorTab fxToolEditorTab = (FxToolEditorTab)tab;
        if (fxToolEditorTab.a().j() == paramUnit) {
          this.f.getSelectionModel().select(tab);
          return fxToolEditorTab.a();
        } 
      } 
    } 
    return null;
  }
  
  public FxToolEditor b(Unit paramUnit) {
    FxToolEditor fxToolEditor = a(paramUnit);
    if (fxToolEditor == null)
      if (paramUnit instanceof Browse) {
        a(fxToolEditor = new FxBrowseEditor(this, (Browse)paramUnit));
      } else if (paramUnit instanceof Query) {
        a(fxToolEditor = new FxQueryEditor(this.b, (Query)paramUnit));
      } else if (paramUnit instanceof Sql) {
        a(fxToolEditor = a((Sql)paramUnit));
      }  
    return fxToolEditor;
  }
  
  public void a(List<Unit> paramList, boolean paramBoolean) {
    Browse browse = null;
    if (paramList.size() == 1)
      for (Browse browse1 : this.d.browses) {
        if (browse1.b.contains(paramList.get(0)))
          browse = browse1; 
      }  
    if (browse == null)
      browse = this.d.createBrowse(!paramList.isEmpty() ? a(((Unit)paramList.get(0)).getName()) : null); 
    FxBrowseEditor fxBrowseEditor = (FxBrowseEditor)FxEditorFactory.a(this.b, browse);
    FxUtil.a(0.3D, paramActionEvent -> paramFxBrowseEditor.a(paramList, paramBoolean));
  }
  
  public void b(List<Unit> paramList, boolean paramBoolean) {
    Query query = this.d.createQuery(!paramList.isEmpty() ? a(((Unit)paramList.get(0)).getName()) : null);
    FxQueryEditor fxQueryEditor = (FxQueryEditor)FxEditorFactory.a(this.b, query);
    fxQueryEditor.h.a(paramList, paramBoolean);
  }
  
  public void a(List<Unit> paramList, StatementType paramStatementType, boolean paramBoolean) {
    if (this.d.is(UnitProperty.f).booleanValue() && paramStatementType == StatementType.a) {
      new FxJsonInsertEditor(this.b, this.b.s(), paramList);
    } else {
      String str = Dbms.get(this.d.project.getDbId()).getScriptGenerator(paramList).a(paramStatementType);
      if (paramBoolean && Sys.B.generateSqlToClipboard.b()) {
        FxUtil.c(str);
        this.a.d(getWorkspace(), "SQL saved in clipboard.");
      } else {
        Script script = this.d.createScript(!paramList.isEmpty() ? a(((Unit)paramList.get(0)).getName()) : null);
        FxSqlEditor fxSqlEditor = (FxSqlEditor)FxEditorFactory.a(this.b, script);
        fxSqlEditor.i.b(str);
        fxSqlEditor.i.c(true);
      } 
    } 
  }
  
  public void c(Unit paramUnit) {
    if (paramUnit instanceof AbstractUnit) {
      SyncPair syncPair = new SyncPair(null, (AbstractUnit)paramUnit);
      syncPair.setAlwaysIncludeFks(true);
      syncPair.synchronize();
      AlterScript alterScript = syncPair.generateCommitScript(this.d.project.getDbId(), null, SyncSide.right);
      if (Sys.B.generateSqlToClipboard.b()) {
        FxUtil.c(alterScript.toString());
        this.a.d(getWorkspace(), "SQL saved in clipboard.");
      } else {
        Script script = this.d.createScript(a(paramUnit.getName()));
        FxSqlEditor fxSqlEditor = (FxSqlEditor)FxEditorFactory.a(this.b, script);
        fxSqlEditor.i.b(alterScript.toString());
        this.b.u();
      } 
    } 
  }
  
  private String a(String paramString) {
    while (true) {
      String str = paramString;
      paramString = this.d.scripts.proposeName(paramString);
      paramString = this.d.browses.proposeName(paramString);
      paramString = this.d.queries.proposeName(paramString);
      if (paramString.equalsIgnoreCase(str))
        return paramString; 
    } 
  }
  
  public void a(List paramList) {
    if (paramList != null && !paramList.isEmpty())
      if (this.d.diagram.groups.isEmpty()) {
        (new FxGroupEditor(this.b, this.d.diagram, paramList)).showDialog();
      } else {
        ArrayList<String> arrayList = new ArrayList(this.d.diagram.groups);
        arrayList.sort(Comparator.comparing(paramObject -> ((Group)paramObject).getName()));
        arrayList.add(0, "-- Create New Group --");
        ChoiceDialogWithFilterableCombo choiceDialogWithFilterableCombo = new ChoiceDialogWithFilterableCombo(getScene(), null, arrayList);
        this.a.a(choiceDialogWithFilterableCombo, "chooseGroupDialog");
        choiceDialogWithFilterableCombo.a.setCellFactory(paramListView -> new FxLayoutPane$1(this));
        choiceDialogWithFilterableCombo.showAndWait().ifPresent(paramObject -> {
              if (paramObject instanceof Group) {
                Group group = (Group)paramObject;
                group.attachAllTables(paramList);
                this.c.k();
                this.b.u();
              } 
              if (paramObject instanceof String)
                (new FxGroupEditor(this.b, this.d.diagram, paramList)).showDialog(); 
            });
      }  
  }
  
  public void a(Collection paramCollection) {
    if (paramCollection != null && !paramCollection.isEmpty())
      for (Unit unit : paramCollection) {
        Depict depict = this.d.diagram.getDepictFor(unit.getEntity());
        if (depict != null)
          for (Group group : this.d.diagram.groups)
            group.dettach(depict);  
      }  
    this.d.refresh();
  }
  
  @Action
  public void newGroup() {
    (new FxGroupEditor(this.b, this.d.diagram, this.c.c.b)).showDialog();
  }
  
  @Action
  public void groupsManager() {
    (new FxGroupsEditor(this.b, this.d.project, this.d)).showDialog();
  }
  
  @Action
  public void addTableToGroup() {
    a(this.b.E());
  }
  
  @Action
  public void changeTableColor() {
    FxColorPicker fxColorPicker = new FxColorPicker(this.c.P());
    fxColorPicker.a(this.d.project);
    CheckBox checkBox = this.a.h("overwriteTableColorInAllLayout", false);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    this.a.a((Dialog)alert, "chooseTableColorDialog");
    alert.getDialogPane().setContent((Node)(new VBox$()).l().c(new Node[] { (Node)fxColorPicker, (Node)checkBox }));
    alert.initOwner(getWorkspace().getWindow());
    alert.showAndWait().ifPresent(paramButtonType -> {
          if (paramButtonType == ButtonType.OK && paramFxColorPicker.b() != null) {
            if (paramCheckBox.isSelected()) {
              for (Layout layout : this.d.project.layouts) {
                for (Depict depict : layout.getDepicts()) {
                  if (this.b.E().contains(depict.getEntity()))
                    depict.setColor(paramFxColorPicker.b()); 
                } 
              } 
            } else {
              for (Depict depict : this.d.getDepicts()) {
                if (this.b.E().contains(depict.getEntity()))
                  depict.setColor(paramFxColorPicker.b()); 
              } 
            } 
            this.b.u();
          } 
        });
  }
  
  @Action
  public void ungroup() {
    a(this.b.E());
  }
  
  @Pro
  @Action
  public void documentation() {
    this.c.documentation();
  }
  
  @Action
  public void newCalloutOn() {
    (new FxCalloutEditor(this.b, this.c.c.c())).showDialog();
  }
  
  @Pro
  @Action
  public void newBrowse() {
    b(this.d.createBrowse((String)null));
  }
  
  @Pro
  @Action
  public void newQuery() {
    b(this.d.createQuery((String)null));
  }
  
  @Action
  public void newScript() {
    b(this.d.createScript((String)null));
  }
  
  @Action
  public void highlightVirtualForeignKeys() {
    this.c.O();
  }
  
  @Action
  public void hideColumns() {
    for (Attribute attribute : this.c.c.e) {
      Depict depict = this.c.a.getDepictFor(attribute.getEntity());
      if (depict != null)
        depict.setAttributeVisible(attribute, false); 
    } 
    this.c.k();
  }
  
  @Action
  public void showColumns() {
    for (Entity entity : this.c.c.b) {
      Depict depict = this.c.a.getDepictFor(entity);
      if (depict != null)
        depict.showAllAttributes(); 
    } 
    this.c.k();
  }
  
  void a(Table paramTable, List paramList) {
    for (Attribute attribute : paramList) {
      Column column = (Column)paramTable.columns.getByName(attribute.getName());
      if (column == null && attribute instanceof Column) {
        Column column1 = new Column(paramTable, attribute.getName());
        column1.cloneFrom((Column)attribute);
        column1.setIdentity((String)null);
        EditorSyncRoot editorSyncRoot = new EditorSyncRoot(paramTable, null, column1, false, null);
        if (this.b.t()) {
          try {
            Envoy envoy = this.b.s().startEnvoy("Clone Column");
            try {
              if (!Sys.B.readOnly.b())
                editorSyncRoot.a(envoy); 
              editorSyncRoot.a();
              if (envoy != null)
                envoy.close(); 
            } catch (Throwable throwable) {
              if (envoy != null)
                try {
                  envoy.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
          } catch (Exception exception) {
            Log.b(exception);
            this.a.b(this.b, exception.toString(), exception);
          } 
        } else {
          editorSyncRoot.a(this);
        } 
        this.c.k();
      } 
    } 
  }
  
  public void a(Table paramTable, Column paramColumn) {
    Column column = (Column)paramTable.columns.getByName(paramColumn.getName());
    if (column == null) {
      TextInputDialog textInputDialog = new TextInputDialog(paramColumn.getName());
      this.a.a((Dialog)textInputDialog, "cloneDialog");
      textInputDialog.initOwner(this.b.getWindow());
      textInputDialog.setContentText(String.format("Clone column '%s' in '%s' as ...?", new Object[] { paramColumn, paramTable }));
      String str = textInputDialog.showAndWait().orElse(null);
      if (str != null) {
        Column column1 = new Column(paramTable, str);
        column1.cloneFrom(paramColumn);
        column1.setIdentity((String)null);
        column1.setDefaultValue((String)null);
        if (this.b.t())
          column1.setMandatory(false); 
        EditorSyncRoot editorSyncRoot = new EditorSyncRoot(paramTable, null, column1, false, null);
        if (this.b.t()) {
          try {
            Envoy envoy = this.b.s().startEnvoy("Clone Column");
            try {
              editorSyncRoot.a(envoy);
              editorSyncRoot.a();
              if (envoy != null)
                envoy.close(); 
            } catch (Throwable throwable) {
              if (envoy != null)
                try {
                  envoy.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
          } catch (Exception exception) {
            Log.b(exception);
            this.a.b(this.b, exception.toString(), exception);
          } 
        } else {
          editorSyncRoot.a(this);
        } 
        this.c.k();
        column = (Column)paramTable.columns.getByName(str);
        if (column != null) {
          RadioButton radioButton1 = new RadioButton(String.format("%s ( %s ) references %s ( %s )", new Object[] { paramColumn.getEntity(), paramColumn, paramTable, str }));
          RadioButton radioButton2 = new RadioButton(String.format("%s ( %s ) references %s ( %s )", new Object[] { paramTable, str, paramColumn.getEntity(), paramColumn }));
          radioButton1.setMnemonicParsing(false);
          radioButton2.setMnemonicParsing(false);
          new ToggleGroup$(new ToggleButton[] { (ToggleButton)radioButton1, (ToggleButton)radioButton2 });
          radioButton1.setSelected(true);
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.initOwner(this.b.getWindow());
          this.a.a((Dialog)alert, "createFkDialog");
          VBox$ vBox$ = (new VBox$()).l();
          vBox$.getChildren().addAll((Object[])new Node[] { (Node)this.a.e("createFkOnClonedColumn"), (Node)radioButton1, (Node)radioButton2 });
          alert.getDialogPane().setContent((Node)vBox$);
          Column column2 = column;
          alert.showAndWait().ifPresent(paramButtonType -> {
                if (paramRadioButton.isSelected()) {
                  (new FxForeignKeyEditor(this.b, paramColumn1, paramColumn2, true)).showDialog();
                } else {
                  (new FxForeignKeyEditor(this.b, paramColumn2, paramColumn1, true)).showDialog();
                } 
              });
        } 
      } 
    } else {
      (new FxForeignKeyEditor(this.b, paramColumn, column, true)).showDialog();
    } 
    this.c.k();
  }
  
  public void g() {
    for (Tab tab : this.f.getTabs()) {
      if (tab instanceof FxToolEditorTab)
        ((FxToolEditorTab)tab).a().g(); 
    } 
    this.c.m();
    this.b.u();
  }
  
  public boolean e() {
    if (this.d.diagram.depicts.isEmpty() && this.d.browses.isEmpty() && this.d.scripts.isEmpty()) {
      this.d.markForDeletion();
      this.d.refresh();
      return true;
    } 
    if (!this.d.isConfirmed()) {
      switch (FxLayoutPane$2.a[this.a.b(this.b, "saveEditorMessage", new String[0]).c().ordinal()]) {
        case 1:
          this.d.setConfirmed(true);
        case 2:
          if (!this.d.isConfirmed()) {
            this.d.markForDeletion();
            getWorkspace().m().refresh();
          } 
      } 
      return false;
    } 
    return true;
  }
  
  @Action
  public void showAllForeignKeys() {
    this.d.diagram.showAllForeignKeys();
    this.c.k();
  }
  
  @Action
  public void showAllEntityForeignKeys() {
    Unit unit = this.c.c.c();
    if (unit != null) {
      this.d.diagram.showAllEntityForeignKeys(unit.getEntity());
      this.c.k();
    } 
  }
  
  @Action
  public void hideSelectedForeignKey() {
    if (this.b.C() instanceof ForeignKey) {
      this.d.diagram.hideRelation((ForeignKey)this.b.C());
      this.c.k();
    } 
  }
  
  @Action
  public void hideAllColumns() {
    this.d.diagram.hideAllColumns();
    this.c.k();
  }
  
  @Action
  public void showAllColumns() {
    this.d.diagram.showAllColumns();
    this.c.k();
  }
  
  @Action
  public void newBrowseEditor() {
    a(new FxBrowseEditor(this, this.d.createBrowse((String)null)));
    getWorkspace().u();
  }
  
  @Pro
  @Action
  public void newQueryEditor() {
    a(new FxQueryEditor(this.b, this.d.createQuery((String)null)));
    getWorkspace().u();
  }
  
  @Action
  public void newSqlEditor() {
    a(new FxSqlEditor(this.b, this, this.d.createScript((String)null)));
    getWorkspace().u();
  }
  
  public AbstractUnit j() {
    return this.d;
  }
  
  public Workspace getWorkspace() {
    return this.b;
  }
  
  public String h() {
    return this.d.getName();
  }
  
  public Glyph m() {
    return BootstrapIcons.diagram_2_fill;
  }
  
  public String n() {
    return "diagram.png";
  }
  
  @Action
  public void alignLeft() {
    this.c.a(Pos.CENTER_LEFT);
  }
  
  @Action
  public void alignRight() {
    this.c.a(Pos.CENTER_RIGHT);
  }
  
  @Action
  public void alignTop() {
    this.c.a(Pos.TOP_CENTER);
  }
  
  @Action
  public void alignBottom() {
    this.c.a(Pos.BOTTOM_CENTER);
  }
  
  @Action
  public void selectRelatedEntities() {
    this.c.N();
  }
  
  public void b() {
    this.f.getTabs().removeIf(paramTab -> (paramTab.getContent() instanceof FxToolEditor && ((FxToolEditor)paramTab.getContent()).j() != null && ((FxToolEditor)paramTab.getContent()).j().isMarkedForDeletion()));
    for (Tab tab : this.f.getTabs()) {
      if (tab.getContent() instanceof FxToolEditor)
        ((FxToolEditorTab)tab).a(((FxToolEditor)tab.getContent()).h()); 
    } 
  }
  
  public void a(boolean paramBoolean, int paramInt) {
    for (Tab tab : this.f.getTabs()) {
      if (tab.getContent() instanceof FxToolEditor)
        ((FxToolEditor)tab.getContent()).a(paramBoolean, paramInt); 
    } 
  }
  
  public boolean o() {
    return true;
  }
  
  public void p() {
    Layout layout = this.d.project.cloneLayout(this.d);
    getWorkspace().a(layout);
  }
  
  public void a(boolean paramBoolean) {
    this.c.setVisible(paramBoolean);
  }
  
  public void c() {
    this.b.u();
  }
}
