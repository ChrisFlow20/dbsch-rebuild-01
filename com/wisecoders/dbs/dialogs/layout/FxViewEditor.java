package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ViewImporter;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.completion.SqlCompletion;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

public class FxViewEditor extends FxDbDialog implements FxUnitEditor {
  private Schema a;
  
  private final String c;
  
  private View d;
  
  private View e;
  
  private final boolean f;
  
  private final Class i;
  
  private final FxCommentPanel j;
  
  private final TextField k = new TextField();
  
  private final StyledEditor l = new StyledEditor(true);
  
  private Button m;
  
  private final CheckBox n;
  
  private z o;
  
  public FxViewEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject, Class paramClass) {
    super(paramWorkspaceWindow, true);
    initModality(Modality.NONE);
    setDialogTitle("New View");
    this.c = paramProject.getDbId();
    this.i = paramClass;
    this.f = true;
    this.rx.G(this.c);
    this.j = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), null);
    this.n = this.rx.h("virtualCheckBox", false);
    if (paramProject.schemas.isEmpty()) {
      this.a = null;
      showError("Please create at least one schema.\nRight-click the schemas node in the Model Structure and choose 'Create Schema'.");
      skipShowingDialog();
    } else if (paramProject.schemas.size() == 1) {
      this.a = (Schema)paramProject.schemas.get(0);
      this.n.setSelected(SyncUtil.c(this.a));
      this.n.setDisable(SyncUtil.c(this.a));
    } else {
      ChoiceDialog choiceDialog = new ChoiceDialog((Schema)paramProject.schemas.get(0), paramProject.schemas);
      choiceDialog.initOwner(paramWorkspaceWindow.getDialogScene().getWindow());
      this.rx.a((Dialog)choiceDialog, "addTableDialog");
      choiceDialog.showAndWait().ifPresentOrElse(paramSchema -> {
            this.a = paramSchema;
            this.n.setSelected(SyncUtil.c(this.a));
            this.n.setDisable(SyncUtil.c(this.a));
          }this::skipShowingDialog);
    } 
  }
  
  FxViewEditor(WorkspaceWindow paramWorkspaceWindow, Folder paramFolder, Schema paramSchema) {
    this(paramWorkspaceWindow, paramFolder, paramSchema, (View)null);
  }
  
  FxViewEditor(WorkspaceWindow paramWorkspaceWindow, Folder paramFolder, View paramView) {
    this(paramWorkspaceWindow, paramFolder, paramView.getSchema(), paramView);
  }
  
  private FxViewEditor(WorkspaceWindow paramWorkspaceWindow, Folder paramFolder, Schema paramSchema, View paramView) {
    super(paramWorkspaceWindow, true);
    initModality(Modality.NONE);
    setDialogTitle(((paramView == null) ? "New " : "Edit ") + ((paramView == null) ? "New " : "Edit "));
    this.a = paramSchema;
    this.d = paramView;
    this.c = paramSchema.project.getDbId();
    this.i = paramFolder.childClass;
    this.f = (paramView == null);
    this.rx.G(this.c);
    this.j = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramView);
    this.n = this.rx.h("virtualCheckBox", (paramView != null && paramView.isVirtual()));
    if (paramView == null) {
      this.n.setSelected(SyncUtil.c(paramSchema));
      this.n.setDisable(SyncUtil.c(paramSchema));
    } else {
      this.n.setSelected(SyncUtil.c(paramView));
      this.n.setDisable((SyncUtil.c(paramSchema) || (!paramView.isVirtual() && this.b.getWorkspace().t())));
    } 
  }
  
  public Node createContentPane() {
    Dbms dbms1 = Dbms.get(this.c);
    Dbms dbms2 = Dbms.get(this.a.getDbId());
    if (dbms1.autoLetterCases.b())
      Rx.a(this.k, dbms1.getLetterCases()); 
    this.l.J.a(new SqlCompletion(this.l, this.b.m()));
    if (this.d == null) {
      setInitFocusedNode((Node)this.k);
      this.l.b((this.i == MaterializedView.class) ? dbms2.defaultMaterializedViewText.c_() : dbms2.defaultViewText.c_());
    } else {
      this.k.setText(this.d.getName());
      this.l.b(this.d.getScript());
    } 
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)this.l);
    setRegionPrefSize((Region)borderPane, 650.0D, 350.0D);
    GridPane$ gridPane$ = (new GridPane$()).e().g().a(new int[] { -2, -1, -2 }).b(new int[] { -2, -2, -2, -1 });
    gridPane$.a((Node)this.rx.e("nameLabel"), "0,0,r,c");
    gridPane$.a((Node)this.k, "1,0,f,c");
    gridPane$.a((Node)this.n, "2,0,l,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$.a((Node)this.j, "1,1,2,1,f,f");
    gridPane$.a((Node)this.rx.e("definitionLabel"), "0,2,l,c");
    gridPane$.a((Node)borderPane, "0,3,2,3,f,f");
    setInitFocusedNode((Node)this.k);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    this.m = createActionButton("validateView", ButtonBar.ButtonData.OTHER);
    createHelpButton("schema.html#view");
  }
  
  public boolean validate() {
    String str1 = this.k.getText();
    if (StringUtil.isEmptyTrim(str1)) {
      showError("Please set the view name.");
      this.k.requestFocus();
      return false;
    } 
    if (this.a.views.isWrongName(str1, this.d)) {
      showError("An view with this name already exists.\nPlease choose a different name.");
      this.k.requestFocus();
      return false;
    } 
    String str2 = this.l.t();
    if (StringUtil.isEmptyTrim(str2)) {
      showError("Please type in the view script.");
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    View view;
    String str1 = this.k.getText().trim();
    String str2 = this.l.t();
    if (this.i == MaterializedView.class) {
      view = this.doImplement ? new MaterializedView(this.a, str1) : ((this.d != null) ? this.d : this.a.createMaterializedView(str1));
    } else {
      view = this.doImplement ? new View(this.a, str1) : ((this.d != null) ? this.d : this.a.createView(str1));
    } 
    view.setScript(str2);
    if (this.d != null)
      for (Column column : this.d.columns)
        view.createColumn(column.getName(), column.getDataType());  
    view.setComment(this.j.b());
    view.setCommentTags(this.j.a());
    view.setVirtual((this.n.isSelected() && !this.n.isDisable()));
    addSyncOperation(this.a, this.d, view);
  }
  
  public AbstractUnit a() {
    return this.d;
  }
  
  public void saveSucceeded() {
    View view = (View)this.a.views.getByName(this.k.getText());
    if (view != null) {
      FxLayoutPane fxLayoutPane = this.b.o();
      if (fxLayoutPane != null && this.f)
        fxLayoutPane.c.a(view); 
      this.j.a(view);
      view.setVirtual((this.n.isSelected() && !this.n.isDisable()));
      if (this.b.t())
        try {
          Envoy envoy = this.b.s().startEnvoy("View editor test view script");
          try {
            ViewImporter.a(envoy, view);
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
        }  
    } 
    this.b.y();
  }
  
  @Action
  public Task validateView() {
    if (this.o == null || this.o.c == null) {
      String str = this.l.t();
      if (StringUtil.isEmptyTrim(str)) {
        showError("Please type in the view definition.");
      } else if (this.b.t()) {
        if (this.e == null)
          this.e = this.a.createView("dbs_validate_view"); 
        this.e.setScript(str);
        if (this.b.t())
          return this.o = new z(this, this.e, this.b.s().startEnvoy("Validate View '" + String.valueOf(this.d) + "'")); 
      } else {
        showError("Operation requires to be connected to the database");
      } 
    } else {
      this.o.c.close();
    } 
    return null;
  }
}
