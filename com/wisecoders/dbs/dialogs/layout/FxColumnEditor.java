package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.DelayedTimeline;
import com.wisecoders.dbs.sys.DelayedTimelineHandler;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FxColumnEditor extends FxDbDialog implements FxUnitEditor {
  private static final String a = "lastDataType";
  
  private final String c;
  
  private final Entity d;
  
  private final Column e;
  
  private Column f;
  
  private final boolean i;
  
  private final AttributeSpec j;
  
  private boolean k;
  
  private boolean l = false;
  
  private final String m = this.rx.H("manageDataTypes");
  
  private final HBox$ n = (new HBox$()).d().a(Pos.CENTER);
  
  private final TextField o = new TextField();
  
  private final TextField p = new TextField();
  
  private final TextField q = new TextField();
  
  private final TextField r = new TextField();
  
  private final TextField s = new TextField();
  
  private final FxSyntaxOptionComboBox t;
  
  private final FxSyntaxOptionComboBox u;
  
  private final FxSyntaxOptionComboBox v;
  
  private final ComboBox w = new ComboBox();
  
  private final CheckBox x = this.rx.v("checkSequence");
  
  private final ComboBox y = new ComboBox();
  
  private final CheckBox z;
  
  private final CheckBox A = this.rx.h("checkMandatory", false);
  
  private final CheckBox B = this.rx.h("checkUnsigned", false);
  
  private final TextField C = new TextField();
  
  private final Label D;
  
  private final Label E;
  
  private final Label F = this.rx.a("precisionLabel", (Node)this.p);
  
  private final Label G = this.rx.a("decimalLabel", (Node)this.q);
  
  private final Label H = this.rx.a("defaultLabel", (Node)this.C);
  
  private final Label I = this.rx.a("enumerationLabel", (Node)this.r);
  
  private final Label J;
  
  private final Label K = this.rx.a("definitionLabel", (Node)this.s);
  
  private final CheckBox L;
  
  private final Button M = this.rx.j("currentDate");
  
  private final boolean N;
  
  private final FxCommentPanel O;
  
  private final SplitMenuButton P = this.rx.g("toDo", false);
  
  private short Q = -1;
  
  public FxColumnEditor(WorkspaceWindow paramWorkspaceWindow, Entity paramEntity, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramEntity, (Column)null, AttributeSpec.normal, paramBoolean);
  }
  
  public FxColumnEditor(WorkspaceWindow paramWorkspaceWindow, Entity paramEntity, AttributeSpec paramAttributeSpec, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramEntity, (Column)null, paramAttributeSpec, paramBoolean);
  }
  
  public FxColumnEditor(WorkspaceWindow paramWorkspaceWindow, Column paramColumn, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramColumn.getEntity(), paramColumn, AttributeSpec.normal, paramBoolean);
  }
  
  FxColumnEditor(WorkspaceWindow paramWorkspaceWindow, Entity paramEntity, Column paramColumn, AttributeSpec paramAttributeSpec, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    this.rx.G((paramEntity.getSchema()).project.getDbId());
    this.d = paramEntity;
    this.e = paramColumn;
    this.j = paramAttributeSpec;
    this.i = (paramColumn == null);
    this.c = (paramEntity.getSchema()).project.getDbId();
    this.D = this.rx.a("nameLabel", (Node)this.o);
    this.O = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramColumn);
    this.E = this.rx.a("descriptionLabel", (Node)this.O);
    setDialogTitle(this.rx.H((paramColumn == null) ? "newColumnTitle" : "existingColumnTitle") + " " + this.rx.H((paramColumn == null) ? "newColumnTitle" : "existingColumnTitle"));
    this.t = new FxSyntaxOptionComboBox(this, this.c);
    this.t.b("Type Options");
    this.u = new FxSyntaxOptionComboBox(this, (paramAttributeSpec == AttributeSpec.computed) ? (Dbms.get(this.c)).columnComputedOptions : (Dbms.get(this.c)).columnOptions);
    this.u.b(this.rx.H("optionsPromptText.text"));
    this.J = this.rx.a("optionsLabel", (Node)this.u);
    this.v = new FxSyntaxOptionComboBox(this, (Dbms.get(this.c)).columnIdentityOptions);
    this.v.b(this.rx.H("identity.promptText"));
    this.z = this.rx.h("checkPk", false);
    this.L = this.rx.h("virtualCheckBox", false);
    this.C.setOnKeyTyped(paramKeyEvent -> {
          DataType dataType = (DataType)this.w.getSelectionModel().getSelectedItem();
          String str = this.C.getText();
          if (this.l && dataType != null && (dataType.isText() || dataType.isChar() || dataType.isDate()) && str != null && str.length() == 1 && Character.isLetter(str.charAt(0))) {
            this.C.setText("'" + str + "'");
            this.l = false;
            this.C.positionCaret(2);
          } 
        });
    this.o.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (paramString2 != null) {
            DbmsTypes dbmsTypes = DbmsTypes.get(this.c);
            if (this.i && !this.k) {
              paramString2 = paramString2.toLowerCase();
              boolean bool = false;
              for (DataType dataType : dbmsTypes.dataTypes) {
                for (Pattern pattern : dataType.matcherPatterns) {
                  if (pattern.matcher(paramString2).find()) {
                    a(dataType, 0);
                    bool = true;
                  } 
                } 
              } 
              if (!bool)
                if (paramString2.endsWith("name")) {
                  a(dbmsTypes.getDataType(12), 100);
                } else if (paramString2.endsWith("text")) {
                  a(dbmsTypes.getDataType(12), 250);
                } else if (paramString2.endsWith("note")) {
                  a(dbmsTypes.getDataType(12), 600);
                } else if (paramString2.endsWith("address")) {
                  a(dbmsTypes.getDataType(12), 100);
                } else if (paramString2.endsWith("id")) {
                  a(dbmsTypes.getDataType(4), 11);
                } else if (paramString2.endsWith("age")) {
                  a(dbmsTypes.getDataType(4), 2);
                } else if (paramString2.endsWith("amount")) {
                  a(dbmsTypes.getDataType(4), 8);
                } else if (paramString2.endsWith("timestamp")) {
                  a(dbmsTypes.getDataType(93), 12);
                } else if (paramString2.contains("date") || paramString2.contains("day")) {
                  a(dbmsTypes.getDataType(91), 12);
                } else if (paramString2.endsWith("status") || paramString2.endsWith("day")) {
                  a(dbmsTypes.getDataType(1), 1);
                } else if (paramString2.endsWith("language") || paramString2.endsWith("country") || paramString2.endsWith("currency")) {
                  a(dbmsTypes.getDataType(1), 2);
                } else {
                  for (Entity entity : paramEntity.getSchema().getEntities()) {
                    for (Attribute attribute : entity.getAttributes()) {
                      if (attribute != paramColumn && paramString2.equals(attribute.getName()) && attribute instanceof Column)
                        a(attribute.getDataType(), ((Column)attribute).getLength()); 
                    } 
                  } 
                }  
            } 
          } 
        });
    if ("LogicalDesign".equals(this.c))
      NamingDictionary.c.a(this.o); 
    boolean bool = paramEntity.getSchema().is(UnitProperty.f).booleanValue();
    if (paramEntity instanceof Table)
      for (Index index : ((Table)paramEntity).getIndexes()) {
        if (index.getType() == IndexType.PRIMARY_KEY && (
          paramColumn == null || !index.getColumns().contains(paramColumn)))
          bool = true; 
      }  
    this.N = bool;
    if (paramColumn == null) {
      this.L.setSelected(SyncUtil.c(paramEntity));
      this.L.setDisable(SyncUtil.c(paramEntity));
    } else {
      this.o.setDisable((!paramEntity.getSchema().is(UnitProperty.f).booleanValue() && paramWorkspaceWindow.getWorkspace().t() && !(Dbms.get(paramEntity.getSchema())).alterColumn.c()));
      this.L.setSelected(paramColumn.isVirtual());
      this.L.setDisable((SyncUtil.c(paramEntity) || (!paramColumn.isVirtual() && this.b.getWorkspace().t())));
      this.Q = paramColumn.getToDoFlag();
    } 
    e();
    DelayedTimeline delayedTimeline = new DelayedTimeline(this);
    DelayedTimelineHandler delayedTimelineHandler = delayedTimeline.a(1000L, this::b);
    this.o.setOnKeyTyped(paramKeyEvent -> paramDelayedTimelineHandler.a());
    this.C.setOnAction(paramActionEvent -> paramDelayedTimelineHandler.a());
    this.v.setOnAction(paramActionEvent -> paramDelayedTimelineHandler.a());
    d();
    this.w.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramObject1, paramObject2) -> {
          if (paramObject2 == this.m) {
            if (paramObject1 != null && !this.m.equals(paramObject1))
              Pref.a("lastDataType", paramObject1.toString()); 
            this.w.getSelectionModel().select(paramObject1);
            dbmsSettings();
            d();
          } else if (paramObject2 instanceof DataType) {
            this.t.a(((DataType)paramObject2).options);
          } 
        });
    this.w.setCellFactory(paramListView -> new a(this));
    this.w.setButtonCell(new a(this));
    this.w.setMaxWidth(400.0D);
  }
  
  public void a(DataType paramDataType, int paramInt) {
    if (this.i && !this.k) {
      this.w.setValue(paramDataType);
      a(true);
      if (!this.p.isDisable())
        this.p.setText("" + paramInt); 
      this.k = false;
    } 
  }
  
  private void b() {
    String str = this.o.getText();
    Column column = (Column)AbstractUnit.getByName(this.d.getAttributes(), str);
    if (this.i)
      if (column != null && column != this.e) {
        showNotificationPane("duplicateNameWarning");
      } else if (Dbms.get(this.d.getSchema()).isReservedKeyword(str)) {
        showNotificationPane("reservedKeywordWarning");
      } else if (this.v.b() && StringUtil.isFilledTrim(this.C.getText())) {
        showNotificationPane("defaultWithAutoIncrementWarning");
      }  
  }
  
  private Node c() {
    VBox$ vBox$ = (new VBox$()).k();
    this.L.setDisable((SyncUtil.c(this.d) || (this.e != null && !this.e.isVirtual() && this.b.getWorkspace().t())));
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)this.L, (Node)(new FlowPane$()).a().a(new Node[] { (Node)this.x, (Node)this.y }) });
    return (Node)vBox$;
  }
  
  public Node createContentPane() {
    this.p.setPrefColumnCount(5);
    this.q.setPrefColumnCount(3);
    Rx.a(this.p);
    Rx.a(this.q);
    VBox$ vBox$ = (new VBox$()).l().i();
    GridPane$ gridPane$1 = (new GridPane$()).e().a(new int[] { -2, -2, -2, -2, -2, -1 }).a(new Node[] { (Node)this.w, (Node)this.F, (Node)this.p, (Node)this.G, (Node)this.q, (Node)this.t });
    GridPane$ gridPane$2 = (new GridPane$()).e().a(new int[] { -2, -2, -2, -1 }).a(new Node[] { (Node)this.z, (Node)this.A, (Node)this.B, (Node)this.v });
    this.n.setVisible(false);
    HBox.setHgrow((Node)this.r, Priority.ALWAYS);
    this.n.getChildren().addAll((Object[])new Node[] { (Node)this.I, (Node)this.r });
    switch (FxColumnEditor$1.a[((this.e != null) ? this.e.getSpec() : this.j).ordinal()]) {
      case 1:
        gridPane$3 = (new GridPane$()).e().a(new int[] { -2, -2, -2, -2, -1 }).a(new Node[] { (Node)this.H, (Node)this.C, (Node)this.M, (Node)this.J, (Node)this.u });
        this.J.visibleProperty().bind((ObservableValue)this.u.visibleProperty());
        vBox$.getChildren().addAll((Object[])new Node[] { (Node)gridPane$1, (Node)gridPane$2, (Node)gridPane$3, (Node)this.n });
        break;
      case 2:
        gridPane$3 = (new GridPane$()).e().a(new int[] { -2, -1, -2, -1 }).a(new Node[] { (Node)this.K, (Node)this.s, (Node)this.A, (Node)this.u });
        if (Dbms.get(this.c).computedColumnUsesDataType())
          vBox$.getChildren().addAll((Object[])new Node[] { (Node)gridPane$1 }); 
        vBox$.getChildren().add(gridPane$3);
        break;
      case 3:
        vBox$.getChildren().addAll((Object[])new Node[] { (Node)gridPane$1 });
        break;
    } 
    GridPane$ gridPane$3 = (new GridPane$()).e().g();
    gridPane$3.a(new int[] { -2, -1 });
    gridPane$3.b(new int[] { -2, -1 });
    gridPane$3.a((Node)this.D, "0,0,r,c");
    gridPane$3.a((Node)this.o, "1,0,f,c");
    gridPane$3.a((Node)this.E, "0,1,r,c");
    gridPane$3.a((Node)this.O, "1,1,f,f");
    this.P.getItems().addAll(this.rx.e(new String[] { "toDoNo", "toDo0", "toDo1", "toDo2" }));
    this.P.setFocusTraversable(false);
    this.O.a((Node)this.P);
    if ((Dbms.get(this.c)).autoLetterCases.b()) {
      LetterCase letterCase = Dbms.get(this.c).getLetterCases();
      Rx.a(this.o, letterCase);
      this.D.setText("_Name (" + String.valueOf(letterCase) + ")");
    } 
    this.y.getItems().addAll((this.d.getSchema()).sequences);
    if (this.e == null) {
      this.z.setSelected(false);
      if (this.d instanceof UserDataType || (!"LogicalDesign".equals(this.c) && !(Dbms.get(this.c)).pkCreate.j() && !(Dbms.get(this.c)).pkCreateInline.j() && !(Dbms.get(this.c)).pkCreateEnding.j()))
        this.z.setVisible(false); 
      this.y.setDisable(true);
    } else {
      this.o.setText(this.e.getName());
      DataType dataType = this.e.getDataType();
      this.w.setValue(dataType);
      this.C.setText(this.e.getDefaultValue());
      if (StringUtil.isFilledTrim(this.e.getDefaultValue()))
        this.l = false; 
      this.v.a(this.e.getIdentity());
      this.B.setSelected(this.e.isUnsigned());
      if (this.e.getEnumeration() != null && !this.e.getEnumeration().isEmpty())
        this.r.setText(this.e.getEnumeration()); 
      if (StringUtil.isFilledTrim(this.e.getTypeOptions()))
        this.t.a(this.e.getTypeOptions()); 
      if (StringUtil.isFilledTrim(this.e.getOptions()))
        this.u.a(this.e.getOptions()); 
      boolean bool = (this.d instanceof Table && ((Table)this.d).columnIsPk(this.e)) ? true : false;
      this.z.setSelected(bool);
      if (this.d instanceof UserDataType || (
        !"LogicalDesign".equals(this.c) && !bool && 
        
        !(Dbms.get(this.c)).pkCreate.j() && 
        !(Dbms.get(this.c)).pkCreateInline.j() && 
        !(Dbms.get(this.c)).pkCreateEnding.j()))
        this.z.setVisible(false); 
      this.s.setText(this.e.getDefinition());
      this.A.setSelected(this.e.isMandatory());
      this.x.setSelected(this.e.hasAssociatedSequence());
      this.y.setValue(this.e.getAssociatedSequence());
    } 
    setInitFocusedNode((Node)this.o);
    this.x.setOnAction(paramActionEvent -> this.y.setDisable(!this.x.isSelected()));
    a(true);
    this.l = true;
    this.z.setOnAction(paramActionEvent -> a(false));
    if (this.w.getValue() instanceof DataType)
      this.t.a(((DataType)this.w.getValue()).options); 
    this.w.setOnAction(paramActionEvent -> {
          this.k = true;
          a(false);
        });
    new AutoCompleteComboBox(this.w);
    this.v.setOnAction(paramActionEvent -> {
          if (this.v.b())
            this.z.setSelected(true); 
          a(false);
        });
    TabPane tabPane = new TabPane();
    Rx.a(tabPane);
    tabPane.getStyleClass().add("floating");
    tabPane.getTabs().add(new Tab(this.rx.H("dataTypeTab"), (Node)vBox$));
    tabPane.getTabs().add(new Tab(this.rx.H("otherTab"), c()));
    tabPane.getSelectionModel().select(0);
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)gridPane$3);
    borderPane.setBottom((Node)tabPane);
    return (Node)borderPane;
  }
  
  private void d() {
    this.w.getItems().clear();
    String str = Pref.c("lastDataType", "INT");
    for (DataType dataType : DbmsTypes.get((this.d.getSchema()).project.getDbId()).getDataTypes()) {
      this.w.getItems().add(dataType);
      if (dataType.toString().equalsIgnoreCase(str))
        this.w.setValue(dataType); 
    } 
    for (Schema schema : (this.d.getSchema()).project.schemas) {
      for (UserDataType userDataType : schema.userDataTypes) {
        this.w.getItems().add(userDataType);
        if (userDataType.toString().equalsIgnoreCase(str))
          this.w.setValue(userDataType); 
      } 
    } 
    this.w.getItems().add(this.m);
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#column");
  }
  
  public AbstractUnit a() {
    return (this.f != null) ? this.f : this.e;
  }
  
  private void a(Precision paramPrecision) {
    this.F.setText((paramPrecision == Precision.LENGTH) ? "_Length" : "_Precision");
  }
  
  private void a(boolean paramBoolean) {
    Object object = this.w.getSelectionModel().getSelectedItem();
    if (object instanceof DataType) {
      DataType dataType = (DataType)object;
      boolean bool1 = (dataType.getPrecision() == Precision.DECIMAL || dataType.getPrecision() == Precision.LENGTH || dataType.getPrecision() == Precision.PRECISION) ? true : false;
      Dbms dbms = Dbms.get(this.c);
      if (this.z.isSelected() && dbms.columnIdentitySetMandatory.b() && !this.t.b())
        this.A.setSelected(true); 
      boolean bool2 = (dbms.columnGeneratedUnsetMandatory.b() && this.t.b() && this.t.a().toLowerCase().contains("generated")) ? true : false;
      if (bool2)
        this.A.setSelected(false); 
      this.A.setDisable((this.z.isSelected() || bool2));
      boolean bool3 = (dataType.getPrecision() == Precision.DECIMAL) ? true : false;
      this.p.setDisable(!bool1);
      this.F.setDisable(!bool1);
      if (!bool1)
        this.p.setText(""); 
      a(dataType.getPrecision());
      this.q.setDisable(!bool3);
      this.G.setDisable(!bool3);
      if (!bool3)
        this.q.setText(""); 
      this.M.setDisable((bool2 || ((dataType
          .getJavaType() != 92 || !dbms.systemTime.p()) && (dataType
          .getJavaType() != 93 || !dbms.systemTimestamp.p()) && (dataType
          .getJavaType() != 91 || dbms.systemDate.c_() == null))));
      this.n.setVisible((dataType.getPrecision() == Precision.ENUMERATION));
      if (paramBoolean && this.e != null) {
        if (bool1 && this.e.hasLength())
          this.p.setText("" + this.e.getLength()); 
        if (bool3 && this.e.hasDecimal())
          this.q.setText("" + this.e.getDecimal()); 
      } 
      if (!dataType.isNumeric())
        this.B.setSelected(false); 
      this.B.setDisable((!dbms.alterColumn.g() || !dataType.isNumeric()));
      this.z.setDisable(this.N);
      if (dbms.columnIdentityOptions.j() || (this.e != null && StringUtil.isFilled(this.e.getIdentity()))) {
        this.v.setDisable(((!dataType.isNumeric() || this.N) && !dbms.identityOnAllColumns.b()));
        this.v.a(dbms.columnIdentityOptions);
      } else {
        this.v.setVisible(false);
      } 
      if (this.v.b())
        this.C.setText(null); 
      this.C.setDisable(this.v.b());
      this.H.setDisable(this.v.b());
      this.x.setDisable((dataType.isSerial() || !StringUtil.isEmptyTrim(this.C.getText())));
    } 
  }
  
  public boolean validate() {
    String str1 = this.o.getText();
    if (str1 != null)
      str1 = str1.trim(); 
    if (StringUtil.isEmptyTrim(str1)) {
      showError("setColumnNameError");
      this.o.requestFocus();
      return false;
    } 
    Unit unit = AbstractUnit.getByName((this.e != null) ? this.e.getParentEntity().getAttributes() : this.d.getAttributes(), str1);
    if (unit != null && unit != this.e) {
      showError("duplicateColumnNameError");
      this.o.requestFocus();
      return false;
    } 
    DataType dataType = (DataType)this.w.getSelectionModel().getSelectedItem();
    int i = DataType.UNSET;
    try {
      String str = this.p.getText().trim();
      i = str.isEmpty() ? DataType.UNSET : Integer.parseInt(str);
    } catch (NumberFormatException numberFormatException) {
      Log.h();
    } 
    if (dataType == null) {
      showError("missingDataTypeError");
      this.w.requestFocus();
      return false;
    } 
    if (dataType.getPrecision() == Precision.LENGTH && !dataType.hasDefoLegth() && i == DataType.UNSET) {
      showError("missingLengthError");
      this.p.requestFocus();
      return false;
    } 
    String str2 = this.C.getText();
    if (StringUtil.isFilledTrim(str2)) {
      if (str2.startsWith("'") && !str2.endsWith("'") && !str2.toUpperCase().contains("ON UPDATE")) {
        showNotificationPane("quotesInDefaultValueNotification");
        this.C.requestFocus();
      } 
      if (this.v.b()) {
        showError("autoIncrementDefaultValueError");
        this.C.requestFocus();
        return false;
      } 
    } 
    if (this.j == AttributeSpec.computed && StringUtil.isEmpty(this.s.getText())) {
      showError("computedColumnDefinition");
      this.s.requestFocus();
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    String str = this.o.getText().trim();
    DataType dataType = (DataType)this.w.getSelectionModel().getSelectedItem();
    int i = DataType.UNSET;
    try {
      String str1 = this.p.getText().trim();
      i = (str1.isEmpty() || this.p.isDisable()) ? DataType.UNSET : Integer.parseInt(str1);
    } catch (NumberFormatException numberFormatException) {
      Log.h();
    } 
    int j = DataType.UNSET;
    try {
      String str1 = this.q.getText().trim();
      j = str1.isEmpty() ? DataType.UNSET : Integer.parseInt(str1);
    } catch (NumberFormatException numberFormatException) {
      Log.h();
    } 
    Entity entity = this.d;
    if (entity instanceof Table) {
      Table table = (Table)entity;
      if (!this.b.t() && this.e != null && (
        !this.e.getName().equals(str) || this.e.getDataType() != dataType || this.e.getLength() != i || this.e.getDecimal() != j) && this.e
        .updateCascade(true, this.e.getName(), str, dataType, i, j) && this.rx
        .a(getDialogScene(), "confirmUpdateCascade"))
        this.e.updateCascade(false, this.e.getName(), str, dataType, i, j); 
      Column column = this.doImplement ? new Column(table, str, dataType, this.j) : ((this.e != null) ? this.e : table.createColumn(str, dataType, this.j));
      column.rename(str);
      a(column, dataType, i, j);
      addSyncOperation(table, this.e, column);
      if (!this.N) {
        Index index = table.getPrimaryKey();
        if (this.z.isSelected() && index == null) {
          String str1 = table.schema.nameFinder.a(table, column, IndexType.PRIMARY_KEY);
          index = this.doImplement ? new Index(table, str1) : table.createIndex(str1);
          index.addColumn(column);
          index.setType(IndexType.PRIMARY_KEY);
          addSyncOperation(table, (AbstractUnit)null, index);
        } else if (!this.z.isSelected() && index != null) {
          index.markForDeletion();
          if (this.doImplement) {
            addSyncOperation(table, index, (AbstractUnit)null);
          } else {
            table.refresh();
          } 
        } 
      } 
    } else {
      entity = this.d;
      if (entity instanceof ChildEntity) {
        ChildEntity childEntity = (ChildEntity)entity;
        Column column = this.doImplement ? new Column(childEntity, str, dataType) : childEntity.createColumn(str, dataType);
        a(column, dataType, i, j);
        addSyncOperation(childEntity, this.e, column);
      } else {
        entity = this.d;
        if (entity instanceof UserDataType) {
          UserDataType userDataType = (UserDataType)entity;
          Column column = (this.e != null) ? this.e : userDataType.createColumn(str, dataType);
          column.rename(str);
          a(column, dataType, i, j);
          addSyncOperation(userDataType, this.e, column);
        } 
      } 
    } 
  }
  
  private void a(Column paramColumn, DataType paramDataType, int paramInt1, int paramInt2) {
    paramColumn.setMandatory(this.A.isSelected());
    paramColumn.setUnsigned(this.B.isSelected());
    paramColumn.setIdentity(this.v.a());
    Pref.a("lastDataType", paramDataType.toString());
    paramColumn.setDataType(paramDataType);
    paramColumn.setLength(paramInt1);
    paramColumn.setDecimal(paramInt2);
    if (paramColumn.hasDecimal() && !paramColumn.hasLength())
      paramColumn.setLength(0); 
    paramColumn.setDefaultValue(this.C.getText());
    paramColumn.setEnumeration(this.r.getText());
    paramColumn.setTypeOptions(this.t.a());
    paramColumn.setOptions(this.u.a());
    paramColumn.setComment(this.O.b());
    paramColumn.setCommentTags(this.O.a());
    paramColumn.setDefinition(this.s.getText());
    paramColumn.setVirtual((this.L.isSelected() && !this.L.isDisable()));
  }
  
  public void saveSucceeded() {
    this.f = (Column)AbstractUnit.getByName(this.d.getAttributes(), this.o.getText());
    FxLayoutPane fxLayoutPane = getWorkspace().o();
    if (this.f != null) {
      this.O.a(this.f);
      if (this.f.setOptions(this.u.a()))
        this.b.u(); 
      this.f.setToDoFlag(this.Q);
      this.f.setVirtual((this.L.isSelected() && !this.L.isDisable()));
      this.f.setAssociatedSequence(this.x.isSelected() ? (Sequence)this.y.getSelectionModel().getSelectedItem() : null);
      if (this.f instanceof Entity && this.f.getDataType().isJsonMapOrArray() && fxLayoutPane != null) {
        fxLayoutPane.c.a((Entity)this.f);
        fxLayoutPane.c.k();
      } 
      if (this.i && (this.f.getDataType().isJsonList() || this.f.getDataType().isJsonMap())) {
        ChildEntity childEntity = this.f.getCreateChildEntity();
        if (fxLayoutPane != null) {
          Depict depict1 = fxLayoutPane.c.a(childEntity);
          Depict depict2 = fxLayoutPane.c.a.getDepictFor(this.f.getEntity());
          if (depict2 != null && depict1 != null)
            fxLayoutPane.c.a.addInSameGroupAs(depict2, depict1); 
        } 
      } 
    } 
  }
  
  @Action
  public void toDo0() {
    this.Q = 0;
    e();
  }
  
  @Action
  public void toDo1() {
    this.Q = 1;
    e();
  }
  
  @Action
  public void toDo2() {
    this.Q = 2;
    e();
  }
  
  @Action
  public void toDoNo() {
    this.Q = -1;
    e();
  }
  
  @Action
  public void dbmsSettings() {
    (new FxSettingsDialog(getWorkspace(), FxSettingsDialog$SelectTab.c)).showDialog();
  }
  
  @Action
  public void currentDate() {
    Dbms dbms = Dbms.get(this.c);
    DataType dataType = (DataType)this.w.getSelectionModel().getSelectedItem();
    if (dataType != null)
      switch (dataType.getJavaType()) {
        case 92:
          this.C.setText(dbms.systemTime.c_());
          break;
        case 93:
          this.C.setText(dbms.systemTimestamp.c_());
          break;
        case 91:
          this.C.setText(dbms.systemDate.c_());
          break;
      }  
  }
  
  private void e() {
    switch (this.Q) {
      case -1:
        this.P.setGraphic((Node)new ImageView(Rx.c("todo_no.png")));
        break;
      case 0:
        this.P.setGraphic((Node)new ImageView(Rx.c("todo0.png")));
        break;
      case 1:
        this.P.setGraphic((Node)new ImageView(Rx.c("todo1.png")));
        break;
      case 2:
        this.P.setGraphic((Node)new ImageView(Rx.c("todo2.png")));
        break;
    } 
  }
}
