package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FxSchemaEditor extends FxDbDialog implements FxUnitEditor {
  private final Project a;
  
  private final String c;
  
  private final Schema d;
  
  private final TextField e = new TextField();
  
  private final ComboBox f = new ComboBox();
  
  private final FxCommentPanel i;
  
  private final Label j;
  
  private final FxSyntaxOptionComboBox k;
  
  private final FxSyntaxOptionComboBox l;
  
  private final Label m = this.rx.e("specificationOptionsLabel");
  
  private final Label n = this.rx.e("optionsLabel");
  
  private final Label o = this.rx.e("catalogLabel");
  
  private final TextArea p = new TextArea();
  
  private final TextArea q = new TextArea();
  
  private final Button r;
  
  private final CheckBox s;
  
  FxSchemaEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject, Schema paramSchema) {
    super(paramWorkspaceWindow, true);
    this.a = paramProject;
    this.d = paramSchema;
    this.c = paramProject.getDbId();
    this.rx.G(this.c);
    Objects.requireNonNull(this.b);
    this.rx.a("flagIsOnline", this.b::t);
    Dbms dbms = Dbms.get(this.c);
    this.k = new FxSyntaxOptionComboBox(this, dbms.schemaSpecificationOptions);
    this.k.b("Type");
    this.k.d();
    this.l = new FxSyntaxOptionComboBox(this, (Dbms.get(this.c)).schemaOptions);
    this.l.b("Options");
    this.i = new FxCommentPanel(getWorkspace(), this.d);
    this.j = this.rx.e("schemaName");
    this.r = this.rx.j("createDatabase");
    this.s = this.rx.v("virtualCheckBox");
    this.o.setText(dbms.catalogAlias.c_() + " Name");
    Rx.b(this.e);
    Rx.b(this.f.getEditor());
    if (paramSchema == null) {
      boolean bool = this.b.getWorkspace().t();
      if (!bool && !dbms.alterSchema.b() && !paramProject.is(UnitProperty.g).booleanValue())
        if (dbms.databaseCreate.p()) {
          this.rx.d(paramWorkspaceWindow.getWorkspace(), "For " + this.c + " you can create a new schema in the Connection Editor, when connecting first to the database");
        } else {
          this.rx.d(paramWorkspaceWindow.getWorkspace(), "For " + this.c + " a new schema can be created only using database specific tools.\nPlease check the " + this.c + " documentation.");
          skipShowingDialog();
        }  
      setDialogTitle(this.rx.H("titleNew"));
    } else {
      this.l.setValue(paramSchema.getOptions());
      this.k.setValue(paramSchema.getSpecificationOptions());
      this.p.setText(paramSchema.getPreScript());
      this.q.setText(paramSchema.getPostScript());
      this.s.setSelected(paramSchema.isVirtual());
      this.s.setDisable((!paramSchema.isVirtual() && this.b.getWorkspace().t()));
      boolean bool = (!(Dbms.get(this.c)).alterSchema.c() && this.b.getWorkspace().t()) ? true : false;
      this.e.setDisable(bool);
      this.f.setDisable(bool);
      setDialogTitle(this.rx.H("titleEdit"));
    } 
  }
  
  public Node createContentPane() {
    this.e.setPrefColumnCount(20);
    this.m.visibleProperty().bind((ObservableValue)this.k.visibleProperty());
    this.n.visibleProperty().bind((ObservableValue)this.l.visibleProperty());
    this.f.setOnShowing(paramEvent -> a((String)null));
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1 }).b(new int[] { -2, -2, -1, -2, -2, -1 }).e().g();
    gridPane$.a((Node)this.o, "0,0,r,c");
    gridPane$.a((Node)(new HBox$()).d().a(new Node[] { (Node)this.f, (Node)this.r }, ), "1,0,f,c");
    setManagedVisible(new Node[] { (Node)this.o, (Node)this.f, (Node)this.r });
    gridPane$.a((Node)this.j, "0,1,r,c");
    gridPane$.a((Node)this.e, "1,1,f,c");
    gridPane$.a((Node)this.s, "1,2,l,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,3,r,c");
    gridPane$.a((Node)this.i, "1,3,f,f");
    gridPane$.a((Node)this.m, "0,4,r,c");
    gridPane$.a((Node)this.k, "1,4,f,c");
    gridPane$.a((Node)this.n, "0,5,r,c");
    gridPane$.a((Node)this.l, "1,5,f,c");
    if (!this.a.is(UnitProperty.g).booleanValue()) {
      GridPane$ gridPane$1 = (new GridPane$()).e();
      gridPane$1.a((Node)this.rx.e("preScriptLabel"), "0,0,l,c");
      gridPane$1.a((Node)this.p, "0,1,f,c");
      gridPane$1.a((Node)this.rx.e("postScriptLabel"), "1,0,l,c");
      gridPane$1.a((Node)this.q, "1,1,f,c");
      gridPane$.a((Node)gridPane$1, "0,6,1,6,f,c");
      this.p.setPromptText(this.rx.H("preScript.promptText.text"));
      this.q.setPromptText(this.rx.H("postScript.promptText.text"));
      this.p.setPrefSize(150.0D, 170.0D);
      this.q.setPrefSize(150.0D, 170.0D);
    } 
    Dbms dbms = Dbms.get(this.c);
    this.o.setVisible(dbms.queriesUsesCatalogAndSchema.b());
    this.f.setEditable(true);
    this.f.setVisible(dbms.queriesUsesCatalogAndSchema.b());
    if (dbms.autoLetterCases.b()) {
      LetterCase letterCase = dbms.getLetterCases();
      Rx.a(this.e, letterCase);
      this.j.setText("Schema Name (" + String.valueOf(letterCase) + ")");
    } 
    if (this.d == null) {
      this.e.setText(this.a.schemas.proposeName(dbms.toDefaultCases("Schema_")));
      this.e.selectAll();
      setInitFocusedNode((Node)this.e);
    } else {
      this.e.setText(this.d.getName());
      this.f.setValue(this.d.getCatalogName());
    } 
    return (Node)gridPane$;
  }
  
  private void a(String paramString) {
    this.f.getItems().clear();
    if (this.b.t()) {
      try {
        Envoy envoy = this.b.s().startEnvoy("listCatalogs");
        try {
          for (Dbms$SchemaCatalogEntry dbms$SchemaCatalogEntry : Dbms.get(this.a.getDbId()).listSchemasAndCatalogs(envoy)) {
            if (dbms$SchemaCatalogEntry.b != null && !this.f.getItems().contains(dbms$SchemaCatalogEntry.b))
              this.f.getItems().add(dbms$SchemaCatalogEntry.b); 
          } 
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
      } catch (Throwable throwable) {
        Log.b(throwable);
        this.rx.a(getDialogScene(), throwable);
      } 
    } else {
      for (Schema schema : this.a.schemas) {
        if (schema.getCatalogName() != null && !this.f.getItems().contains(schema.getCatalogName()))
          this.f.getItems().add(schema.getCatalogName()); 
      } 
    } 
    if (paramString != null && this.f.getItems().contains(paramString))
      this.f.setValue(paramString); 
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html");
  }
  
  public boolean validate() {
    String str = this.e.getText();
    if (str != null)
      str = str.trim(); 
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please set the schema name.");
      this.e.requestFocus();
      return false;
    } 
    Schema schema = this.a.getSchema(this.f.isVisible() ? (String)this.f.getValue() : null, str);
    if (schema != null && schema != this.d) {
      showError("Please choose a different name.\n A schema with this name already exists.");
      this.e.requestFocus();
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    String str1 = this.e.getText().trim();
    String str2 = StringUtil.isFilledTrim(this.f.getValue()) ? (String)this.f.getValue() : null;
    Schema schema = this.doImplement ? new Schema(this.a, str1, str2) : ((this.d != null) ? this.d : this.a.createSchema(str1, str2));
    schema.setComment(this.i.b());
    schema.setCommentTags(this.i.a());
    schema.setCatalogName(str2);
    schema.setSpecificationOptions(this.k.a());
    schema.setOptions(this.l.a());
    schema.setVirtual(this.s.isSelected());
    addSyncOperation(this.a, this.d, schema);
  }
  
  public void saveSucceeded() {
    Schema schema = this.a.getSchema((String)this.f.getValue(), this.e.getText());
    if (schema == null)
      schema = this.a.getSchema((String)null, this.e.getText()); 
    if (schema != null) {
      schema.setComment(this.i.b());
      schema.setCommentTags(this.i.a());
      schema.setCatalogName(StringUtil.isFilledTrim(this.f.getValue()) ? (String)this.f.getValue() : null);
      schema.setSpecificationOptions(this.k.a());
      schema.setOptions(this.l.a());
      schema.setPreScript(this.p.getText());
      schema.setPostScript(this.q.getText());
      schema.setVirtual(this.s.isSelected());
    } 
  }
  
  public AbstractUnit a() {
    return this.d;
  }
  
  @Action(b = "flagIsOnline")
  public void createDatabase() {
    (new FxCreateDatabaseDialog(this, this.b.s(), this.a.getDbId())).showDialog().ifPresent(this::a);
  }
}
