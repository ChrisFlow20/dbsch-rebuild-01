package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.reverseEngineer.fx.FxDbSynchronizationTask;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.DelayedTimeline;
import com.wisecoders.dbs.sys.DelayedTimelineHandler;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxColorPicker;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

@DoNotObfuscate
public class FxTableEditor extends FxDbDialog implements FxUnitEditor {
  public static final String KEY_LAST_SCHEMA = "LastSchema";
  
  private final boolean c;
  
  private final Schema d;
  
  private final Entity e;
  
  protected final String a;
  
  private final TabPane f = new TabPane();
  
  private final FxCommentPanel i;
  
  private final TextField j = new TextField();
  
  private final FxSyntaxOptionComboBox k;
  
  private final v l;
  
  private final w m;
  
  private final FxColorPicker n = new FxColorPicker();
  
  private GenericColumnDetailPane o;
  
  private final Label p;
  
  private final Label q;
  
  private final CheckBox r;
  
  private boolean s = false;
  
  private Schema t;
  
  public FxTableEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    this.t = null;
    this.c = true;
    this.a = paramProject.getDbId();
    this.k = new FxSyntaxOptionComboBox(this, this.a);
    this.rx.G(this.a);
    setDialogTitle(this.rx.H("titleNewTable"));
    this.p = this.rx.a("nameLabel", (Node)this.j);
    this.r = this.rx.h("virtualCheckBox", false);
    this.l = new v(this);
    this.m = new w(this);
    if (paramProject.schemas.isEmpty()) {
      this.e = null;
      this.d = null;
      this.i = null;
      this.q = null;
      showError("chooseSchema");
      skipShowingDialog();
    } else if (paramProject.schemas.size() == 1) {
      this.d = (Schema)paramProject.schemas.get(0);
      String str = Dbms.get(this.a).toDefaultCases(this.d.nameFinder.b());
      this.e = (!paramBoolean || paramProject.is(UnitProperty.f).booleanValue()) ? this.d.createTable(str) : new Table(this.d, str);
      this.i = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), this.e);
      this.q = this.rx.a("descriptionLabel", (Node)this.i);
      this.r.setSelected(SyncUtil.c(this.d));
      this.r.setDisable(SyncUtil.c(this.d));
    } else {
      Schema schema = (Pref.a("LastSchema") != null) ? paramProject.getSchema(Pref.a("LastSchema")) : null;
      ChoiceDialog choiceDialog = new ChoiceDialog(schema, paramProject.schemas);
      choiceDialog.initOwner(this.b.getWorkspace().getWindow());
      this.rx.a((Dialog)choiceDialog, "chooseSchemaDialog");
      Optional optional = choiceDialog.showAndWait();
      optional.ifPresent(paramSchema -> this.t = paramSchema);
      if (this.t == null) {
        this.d = null;
        this.e = null;
        this.i = null;
        this.q = null;
        skipShowingDialog();
      } else {
        this.d = this.t;
        String str = Dbms.get(this.a).toDefaultCases(this.t.nameFinder.b());
        this.e = (!paramBoolean || paramProject.is(UnitProperty.f).booleanValue()) ? this.t.createTable(str) : new Table(this.t, str);
        this.i = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), this.e);
        this.q = this.rx.a("descriptionLabel", (Node)this.i);
        this.r.setSelected(SyncUtil.c(this.d));
        this.r.setDisable(SyncUtil.c(this.d));
        Pref.a("LastSchema", this.d.getNameWithCatalog());
      } 
    } 
  }
  
  FxTableEditor(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema, Class<UserDataType> paramClass, boolean paramBoolean) {
    super(paramWorkspaceWindow.getWorkspace(), paramBoolean);
    this.t = null;
    this.d = paramSchema;
    this.a = paramSchema.project.getDbId();
    this.k = new FxSyntaxOptionComboBox(this, this.a);
    this.rx.G(this.a);
    this.p = this.rx.a((paramClass == UserDataType.class) ? "udtLabel" : "nameLabel", (Node)this.j);
    String str = Dbms.get(this.a).toDefaultCases(paramSchema.nameFinder.b());
    this.e = (paramClass == UserDataType.class) ? paramSchema.createUserDataType(str) : ((!paramBoolean || paramSchema.is(UnitProperty.f).booleanValue()) ? paramSchema.createTable(str) : new Table(paramSchema, str));
    this.c = true;
    this.r = this.rx.h("virtualCheckBox", false);
    this.i = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), this.e);
    this.q = this.rx.a("descriptionLabel", (Node)this.i);
    this.l = new v(this);
    this.m = new w(this);
    this.r.setSelected(SyncUtil.c(paramSchema));
    this.r.setDisable(SyncUtil.c(paramSchema));
    setDialogTitle((paramSchema.is(UnitProperty.f).booleanValue() ? "New Collection " : ("New " + ((paramClass == UserDataType.class) ? "User Data Type " : "Table") + " in " + (Dbms.get(this.a)).schemaAlias.c_() + " ")) + (paramSchema.is(UnitProperty.f).booleanValue() ? "New Collection " : ("New " + ((paramClass == UserDataType.class) ? "User Data Type " : "Table") + " in " + (Dbms.get(this.a)).schemaAlias.c_() + " ")));
  }
  
  FxTableEditor(WorkspaceWindow paramWorkspaceWindow, Entity paramEntity, boolean paramBoolean) {
    super(paramWorkspaceWindow.getWorkspace(), paramBoolean);
    this.t = null;
    this.a = (paramEntity.getSchema()).project.getDbId();
    this.d = paramEntity.getSchema();
    this.e = paramEntity;
    this.c = false;
    this.rx.G(this.a);
    this.r = this.rx.h("virtualCheckBox", false);
    this.k = new FxSyntaxOptionComboBox(this, this.a);
    this.p = this.rx.a("nameLabel", (Node)this.j);
    this.i = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramEntity);
    this.q = this.rx.a("descriptionLabel", (Node)this.i);
    this.rx.G(this.a);
    this.j.setDisable((paramWorkspaceWindow.getWorkspace().t() && !(Dbms.get(this.a)).alterTable.c()));
    this.r.setSelected(SyncUtil.c(paramEntity));
    this.r.setDisable((SyncUtil.c(this.d) || (!paramEntity.isVirtual() && this.b.getWorkspace().t())));
    this.l = new v(this);
    this.m = new w(this);
    setDialogTitle((paramEntity.getSchema().is(UnitProperty.f).booleanValue() ? "Edit Collection in Database " : ("Edit " + paramEntity.getSymbolicName() + " in " + (Dbms.get(this.a)).schemaAlias.c_() + " ")) + (paramEntity.getSchema().is(UnitProperty.f).booleanValue() ? "Edit Collection in Database " : ("Edit " + paramEntity.getSymbolicName() + " in " + (Dbms.get(this.a)).schemaAlias.c_() + " ")));
  }
  
  public Node createContentPane() {
    this.j.setPrefColumnCount(30);
    this.k.a((Dbms.get(this.a)).tableSpecificationOptions);
    this.k.b(this.rx.H("specificationOptions.promptText"));
    boolean bool = this.d.project.is(UnitProperty.g).booleanValue();
    if (bool)
      NamingDictionary.c.a(this.j); 
    GridPane$ gridPane$1 = (new GridPane$()).e().g().a(new int[] { -2, -2, -2, -1, -2, -2 }).b(new int[] { -2, -1 });
    gridPane$1.a((Node)this.p, "0,0,r,c");
    gridPane$1.a((Node)this.j, "1,0,l,c");
    gridPane$1.a((Node)this.n, "2,0,l,c");
    gridPane$1.a((Node)this.k, "3,0,l,c");
    gridPane$1.a((Node)this.r, "4,0,l,c");
    this.n.a(this.b.m());
    this.n.setTooltip(new Tooltip("Table color, is a mixture of the configured color and predefined color in DbSchema/config/appearance/theme file."));
    gridPane$1.a((Node)this.q, "0,1,r,c");
    gridPane$1.a((Node)this.i, "1,1,4,1,f,f");
    this.j.setText(this.e.getName());
    Dbms dbms = Dbms.get(this.a);
    if (!this.d.is(UnitProperty.f).booleanValue() && dbms.autoLetterCases.b()) {
      LetterCase letterCase = dbms.getLetterCases();
      Rx.a(this.j, letterCase);
      this.p.setText(this.rx.H("tableName") + " (" + this.rx.H("tableName") + ")");
    } 
    if (this.e instanceof Table)
      if (this.c) {
        this.l.a.a.b((Dbms.get(this.a)).defaultTableStorage.c_());
      } else {
        this.l.a.a.b(((Table)this.e).getOptions());
        this.k.a(((Table)this.e).getSpecificationOptions());
        this.m.a.a.b(((Table)this.e).getPreScript());
        this.m.b.a.b(((Table)this.e).getPostScript());
      }  
    Depict depict = (this.b.p() != null) ? (this.b.p()).diagram.getDepictFor(this.e) : null;
    this.n.a((depict != null) ? depict.getColor() : Color.web("#c1d8ee"));
    if (this.b.m() != null && (Dbms.get(this.b.m().getDbId())).useTreeTableDetailsPane.b()) {
    
    } else {
    
    } 
    this
      
      .o = new FxTableEditor$1(this, this, (Folder)this.e.getAttributes(), depict, "Details", !(Dbms.get(this.a)).disableReorderColumns.b(), (this.doImplement && !this.c));
    ((Tab)this.o).setText(this.rx.H("columns"));
    ((Tab)this.o).setGraphic((Node)BootstrapIcons.list_ul.glyph(new String[0]));
    if (this.e instanceof Table && ((Dbms.get(this.a)).columnCreateComputed.p() || (Dbms.get(this.a)).columnCreateComputedInline.p()))
      this.o.a(this.rx.A("createComputedColumn")); 
    Rx.a(this.f);
    setRegionPrefSize((Region)this.f, 850.0D, 450.0D);
    this.f.getTabs().add(this.o);
    this.f.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTab1, paramTab2) -> {
          if (paramTab2 instanceof FxTableDetailPane) {
            FxTableDetailPane fxTableDetailPane = (FxTableDetailPane)paramTab2;
            fxTableDetailPane.a();
          } 
        });
    if (this.e instanceof Table) {
      FxTableDetailPane fxTableDetailPane1 = new FxTableDetailPane(this, ((Table)this.e).getIndexes(), "On", false, (this.doImplement && !this.c));
      fxTableDetailPane1.setText(this.rx.H("indexes"));
      fxTableDetailPane1.setGraphic((Node)BootstrapIcons.key_fill.glyph(new String[0]));
      this.f.getTabs().add(fxTableDetailPane1);
    } 
    FxTableDetailPane fxTableDetailPane = new FxTableDetailPane(this, (Folder)this.e.getRelations(), "To", false, (this.doImplement && !this.c));
    fxTableDetailPane.setText(this.rx.H("foreignKeys"));
    fxTableDetailPane.setGraphic((Node)BootstrapIcons.arrow_90deg_right.glyph(new String[0]));
    this.f.getTabs().add(fxTableDetailPane);
    Entity entity = this.e;
    if (entity instanceof Table) {
      Table table = (Table)entity;
      if (dbms.alterConstraint.c() || dbms.alterConstraint.b() || bool) {
        FxTableDetailPane fxTableDetailPane1 = new FxTableDetailPane(this, table.constraints, "Details", false, (this.doImplement && !this.c));
        fxTableDetailPane1.setText(this.rx.H("constraints"));
        fxTableDetailPane1.setGraphic((Node)BootstrapIcons.check2_circle.glyph(new String[0]));
        this.f.getTabs().add(fxTableDetailPane1);
      } 
    } 
    if (this.e instanceof Table && !bool) {
      if (this.l.getText() == null)
        this.l.setText(this.rx.H("options")); 
      if (this.m.getText() == null)
        this.m.setText(this.rx.H("scripts")); 
      this.f.getTabs().addAll((Object[])new Tab[] { this.l, this.m });
    } 
    this.f.getSelectionModel().select(0);
    this.f.selectionModelProperty().addListener((paramObservableValue, paramSingleSelectionModel1, paramSingleSelectionModel2) -> {
          this.e.refresh();
          if (this.f.getSelectionModel().getSelectedItem() instanceof FxTableDetailPane)
            ((FxTableDetailPane)this.f.getSelectionModel().getSelectedItem()).a(); 
        });
    if (this.c) {
      this.j.selectAll();
      setInitFocusedNode((Node)this.j);
    } else {
      setInitFocusedNode(this.o.b());
    } 
    DelayedTimeline delayedTimeline = new DelayedTimeline(this);
    DelayedTimelineHandler delayedTimelineHandler = delayedTimeline.a(1000L, this::b);
    this.j.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> paramDelayedTimelineHandler.a());
    GridPane$ gridPane$2 = (new GridPane$()).e().a(new int[] { -1 }).b(new int[] { -2, -1 });
    gridPane$2.a((Node)gridPane$1, "0,0,f,f");
    gridPane$2.a((Node)this.f, "0,1,f,f");
    return (Node)gridPane$2;
  }
  
  @Action
  public void createComputedColumn() {
    (new FxColumnEditor(this.b, this.e, AttributeSpec.computed, (this.doImplement && !this.c))).showDialog();
    this.o.a();
  }
  
  private void b() {
    String str = this.j.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showNotificationPane("setNameWarning");
    } else {
      AbstractTable abstractTable = (AbstractTable)this.d.tables.getByName(str);
      if (abstractTable == null)
        abstractTable = (AbstractTable)this.d.views.getByName(str); 
      if (abstractTable != this.e && abstractTable != null) {
        showNotificationPane("duplicateNameWarning");
      } else if (Dbms.get(this.d).isReservedKeyword(str)) {
        showNotificationPane("reservedKeywordWarning");
      } else if (this.c) {
        this.e.rename(str);
      } 
    } 
  }
  
  public boolean cancel() {
    if (this.c) {
      this.e.markForDeletion();
      this.d.refresh();
    } 
    return true;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#table");
    if (!this.d.is(UnitProperty.g).booleanValue())
      createActionButton("itemStatement", ButtonBar.ButtonData.LEFT); 
  }
  
  public boolean validate() {
    Attribute attribute;
    if (this.e.getAttributes().isEmpty() && !this.e.is(UnitProperty.g).booleanValue()) {
      showError("addColumnError");
      return false;
    } 
    String str = this.j.getText();
    if (str != null)
      str = str.trim(); 
    if (StringUtil.isEmptyTrim(str)) {
      showError("setTableNameError");
      this.j.requestFocus();
      return false;
    } 
    Table table = null;
    if (this.e instanceof Table) {
      Table table1 = this.e.getSchema().getTable(str);
      if (table1 != null && table1 != this.e)
        table = table1; 
    } else {
      Entity entity = this.e;
      if (entity instanceof ChildEntity) {
        ChildEntity childEntity = (ChildEntity)entity;
        for (Attribute attribute1 : this.e.getEntity().getAttributes()) {
          if (attribute1 != childEntity.ownerColumn && attribute1.getName().equals(str))
            attribute = attribute1; 
        } 
      } 
    } 
    if (attribute != null) {
      showError(String.format(this.rx.H("chooseDifferentName"), new Object[] { attribute.getSymbolicName() }));
      this.j.requestFocus();
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    String str = this.j.getText().trim();
    Dbms.get(this.e.getSchema().getDbId()).validateCreatedUnit(this.e);
    if (this.doImplement) {
      if (this.c) {
        this.e.rename(str);
        this.e.setComment(this.i.b());
        if (this.e instanceof Table) {
          Table table = (Table)this.e;
          table.setOptions(this.l.a.a.t());
          table.setSpecificationOptions(this.k.a());
          table.setPreScript(this.m.a.a.t());
          table.setPostScript(this.m.b.a.t());
          table.setCommentTags(this.i.a());
          table.setVirtual((this.r.isSelected() && !this.r.isDisable()));
          addSyncOperation(this.d, (AbstractUnit)null, (Table)this.e);
        } 
      } else if (this.e instanceof Table) {
        Table table = new Table(this.d, str);
        table.setOptions(this.l.a.a.t());
        table.setPreScript(this.m.a.a.t());
        table.setPostScript(this.m.b.a.t());
        table.setSpecificationOptions(this.k.a());
        table.setComment(this.i.b());
        table.setCommentTags(this.i.a());
        table.setVirtual((this.r.isSelected() && !this.r.isDisable()));
        addSyncOperation(this.d, (Table)this.e, table);
      } else if (this.e instanceof UserDataType) {
        UserDataType userDataType = new UserDataType(this.d, str);
        addSyncOperation(this.d, (UserDataType)this.e, userDataType);
      } else if (this.e instanceof ChildEntity) {
        ChildEntity childEntity = new ChildEntity(((ChildEntity)this.e).ownerColumn);
        childEntity.rename(str);
        childEntity.setComment(this.i.b());
        childEntity.setCommentTags(this.i.a());
        addSyncOperation(this.d, (ChildEntity)this.e, childEntity);
      } 
    } else {
      this.e.rename(str);
      this.e.setComment(this.i.b());
      if (this.e instanceof Table) {
        Table table = (Table)this.e;
        table.setOptions(this.l.a.a.t());
        table.setPreScript(this.m.a.a.t());
        table.setPostScript(this.m.b.a.t());
        table.setSpecificationOptions(this.k.a());
        table.setVirtual((this.r.isSelected() && !this.r.isDisable()));
        table.setCommentTags(this.i.a());
      } 
      if (this.b.o() != null) {
        (this.b.o()).c.a(this.e);
        (this.b.o()).c.k();
      } 
    } 
    if (this.s && this.b.o() != null)
      (this.b.o()).c.k(); 
  }
  
  public AbstractUnit getUnit() {
    return (AbstractUnit)this.e;
  }
  
  public void saveSucceeded() {
    Table table = (Table)this.d.tables.getByName(this.j.getText());
    if (table != null) {
      this.i.a(table);
      if (table.setOptions(this.l.a.a.t()))
        this.b.u(); 
      if (table.setPreScript(this.m.a.a.t()))
        this.b.u(); 
      if (table.setPostScript(this.m.b.a.t()))
        this.b.u(); 
      if (table.setSpecificationOptions(this.k.a()))
        this.b.u(); 
      table.setVirtual((this.r.isSelected() && !this.r.isDisable()));
      FxLayoutPane fxLayoutPane = this.b.o();
      if (fxLayoutPane != null) {
        if (this.c)
          fxLayoutPane.c.a(table, true); 
        Depict depict = fxLayoutPane.c.d().getDepictFor(table);
        if (depict != null && 
          depict.setColor(this.n.b()))
          this.b.u(); 
        Unit unit = this.b.C();
        if (unit instanceof Group) {
          Group group = (Group)unit;
          if (fxLayoutPane.d.diagram.groups.contains(group))
            group.attachDepict(depict); 
        } 
        this.d.refresh();
        fxLayoutPane.c.k();
      } 
    } 
  }
  
  boolean a() {
    return this.c;
  }
  
  public void setRefreshDiagram() {
    this.s = true;
  }
  
  @Action
  public void itemStatement() {
    Table table = (Table)this.e.getEntity();
    FxDbSynchronizationTask.a(this.d.project, System.out);
    SyncPair syncPair = new SyncPair(null, table);
    String str = syncPair.generateCommitScript(table.getDbId(), null, SyncSide.right).toString();
    (new FxStatementDialog(this, str)).showDialog();
  }
}
