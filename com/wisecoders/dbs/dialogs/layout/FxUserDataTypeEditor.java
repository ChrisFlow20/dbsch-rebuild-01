package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionPane;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeUtil;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FxUserDataTypeEditor extends FxDbDialog implements FxUnitEditor {
  private final Schema a;
  
  private UserDataType c;
  
  private final boolean d;
  
  private final String e;
  
  private final TextField f = new TextField();
  
  private final FxSyntaxOptionPane i;
  
  private final ComboBox j = new ComboBox();
  
  private final ComboBox k = new ComboBox();
  
  private final CheckBox l;
  
  private final FxCommentPanel m;
  
  private final Label n = this.rx.e("nameLabel");
  
  private Schema o;
  
  public FxUserDataTypeEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject) {
    super(paramWorkspaceWindow, true);
    if (paramProject.schemas.isEmpty()) {
      this.a = null;
      showError("Please create at least one schema.\nRight-click the schemas node in the Model Structure and choose 'Create Schema'.");
      skipShowingDialog();
    } else if (paramProject.schemas.size() == 1) {
      this.a = (Schema)paramProject.schemas.get(0);
    } else {
      ChoiceDialog choiceDialog = new ChoiceDialog((Schema)paramProject.schemas.get(0), paramProject.schemas);
      choiceDialog.initOwner(getDialogPane().getScene().getWindow());
      this.rx.a((Dialog)choiceDialog, "addTableDialog");
      choiceDialog.showAndWait().ifPresentOrElse(paramSchema -> this.o = paramSchema, this::skipShowingDialog);
      this.a = this.o;
    } 
    this.d = true;
    this.e = paramProject.getDbId();
    this.i = new FxSyntaxOptionPane(paramWorkspaceWindow.getWorkspace(), (Dbms.get(this.e)).userDataTypeDeclaration);
    this.m = new FxCommentPanel(getWorkspace(), this.c);
    setDialogTitle("New " + (Dbms.get(paramProject.getDbId())).userDataTypeAlias.c_());
    this.j.getItems().addAll((Object[])DataTypeUtil.getJavaTypesArray());
    this.k.getItems().addAll((Object[])new Precision[] { Precision.NONE, Precision.LENGTH, Precision.DECIMAL, Precision.ENUMERATION });
    this.l = this.rx.h("checkVirtual", false);
    this.j.setCellFactory(paramListView -> new x());
    this.j.setButtonCell(new x());
    this.k.setCellFactory(paramListView -> new y());
    this.k.setButtonCell(new y());
  }
  
  public FxUserDataTypeEditor(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema) {
    this(paramWorkspaceWindow, paramSchema, (UserDataType)null);
  }
  
  public FxUserDataTypeEditor(WorkspaceWindow paramWorkspaceWindow, UserDataType paramUserDataType) {
    this(paramWorkspaceWindow, paramUserDataType.schema, paramUserDataType);
  }
  
  private FxUserDataTypeEditor(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema, UserDataType paramUserDataType) {
    super(paramWorkspaceWindow, true);
    this.a = paramSchema;
    this.c = paramUserDataType;
    this.d = (paramUserDataType == null);
    this.e = paramSchema.project.getDbId();
    this.i = new FxSyntaxOptionPane(paramWorkspaceWindow.getWorkspace(), (Dbms.get(this.e)).userDataTypeDeclaration);
    this.m = new FxCommentPanel(getWorkspace(), paramUserDataType);
    setDialogTitle(((paramUserDataType == null) ? "New " : "Edit ") + ((paramUserDataType == null) ? "New " : "Edit "));
    this.j.getItems().addAll((Object[])DataTypeUtil.getJavaTypesArray());
    this.k.getItems().addAll((Object[])new Precision[] { Precision.NONE, Precision.LENGTH, Precision.DECIMAL, Precision.ENUMERATION });
    this.l = this.rx.h("checkVirtual", (paramUserDataType != null && paramUserDataType.isVirtual()));
    this.j.setCellFactory(paramListView -> new x());
    this.j.setButtonCell(new x());
    this.k.setCellFactory(paramListView -> new y());
    this.k.setButtonCell(new y());
  }
  
  public Node createContentPane() {
    this.l.setDisable((SyncUtil.c(this.a) || (this.c != null && !this.c.isVirtual() && this.b.getWorkspace().t())));
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1 }).b(new int[] { -2, -1, -2, -2, -2, -1 }).l();
    gridPane$.a((Node)this.n, "0,0,r,c");
    gridPane$.a((Node)this.f, "1,0,f,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$.a((Node)this.m, "1,1,f,f");
    gridPane$.a((Node)this.rx.e("precisionLabel"), "0,2,r,c");
    gridPane$.a((Node)this.k, "1,2,l,c");
    gridPane$.a((Node)this.rx.e("javaTypeLabel"), "0,3,r,c");
    gridPane$.a((Node)this.j, "1,3,l,c");
    gridPane$.a((Node)this.l, "1,4,l,c");
    gridPane$.a((Node)this.rx.e("definitionLabel"), "0,5,r,c");
    gridPane$.a((Node)this.i, "1,5,f,c");
    Dbms dbms = Dbms.get(this.e);
    if (this.c == null) {
      if (dbms.autoLetterCases.b())
        Rx.a(this.f, dbms.getLetterCases()); 
      this.i.a.b((Dbms.get(this.a.getDbId())).defaultDomainText.c_());
      this.f.setText(this.a.userDataTypes.proposeName(dbms.toDefaultCases("Unknown_")));
      this.f.selectAll();
      this.k.setValue(Precision.NONE);
      this.j.setValue(Integer.valueOf(4));
      if (!(Dbms.get(this.a.getDbId())).defaultDomainText.j())
        this.l.setSelected(true); 
      Objects.requireNonNull(this.f);
      Platform.runLater(this.f::requestFocus);
    } else {
      this.f.setText(this.c.getName());
      if (dbms.autoLetterCases.b())
        Rx.a(this.f, dbms.getLetterCases()); 
      this.j.setValue(Integer.valueOf(this.c.getJavaType()));
      this.k.setValue(this.c.getPrecision());
      this.i.a.b(this.c.getScript());
      this.l.setSelected(this.c.isVirtual());
    } 
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#seq");
  }
  
  public boolean validate() {
    String str = this.f.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please set the data type name.");
      this.f.requestFocus();
      return false;
    } 
    if (this.l.isSelected() && StringUtil.isEmptyTrim(this.i.a.t())) {
      showError("Please set the Definition.");
      this.i.requestFocus();
      return false;
    } 
    if (this.a.userDataTypes.isWrongName(str, this.c)) {
      showError("Please choose a different name.\n A data type with this name already exists.");
      this.f.requestFocus();
      return false;
    } 
    if (this.k.getValue() == null) {
      showError("Please choose the Specification.");
      this.k.requestFocus();
      return false;
    } 
    if (this.j.getValue() == null) {
      showError("Please choose the Java Type.");
      this.j.requestFocus();
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    DataType dataType;
    String str = this.f.getText();
    boolean bool = this.l.isSelected();
    if (bool) {
      dataType = (this.c != null) ? this.c : this.a.createUserDataType(str);
    } else {
      dataType = this.doImplement ? new UserDataType(this.a, str) : ((this.c != null) ? this.c : DbmsTypes.get(this.e).getOrCreateDataType(((Integer)this.j.getValue()).intValue(), str));
    } 
    if (dataType instanceof UserDataType) {
      UserDataType userDataType = (UserDataType)dataType;
      userDataType.setScript(this.i.a.t());
      userDataType.setVirtual(this.l.isSelected());
    } 
    dataType.setPrecision((Precision)this.k.getValue());
    if (this.j.getValue() != null)
      dataType.setJavaType(((Integer)this.j.getValue()).intValue()); 
    dataType.setComment(this.m.b());
    dataType.setCommentTags(this.m.a());
    if (!bool)
      addSyncOperation(this.a, this.c, dataType); 
  }
  
  public void saveSucceeded() {
    UserDataType userDataType = (UserDataType)this.a.userDataTypes.getByName(this.f.getText());
    if (userDataType != null) {
      userDataType.setPrecision((Precision)this.k.getValue());
      if (this.j.getValue() != null)
        userDataType.setJavaType(((Integer)this.j.getValue()).intValue()); 
      userDataType.setComment(this.m.b());
      userDataType.setCommentTags(this.m.a());
    } 
  }
  
  public AbstractUnit a() {
    return this.c;
  }
}
