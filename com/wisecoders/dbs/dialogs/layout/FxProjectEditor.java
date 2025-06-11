package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.convert.fx.FxConversionDictionaryDialog;
import com.wisecoders.dbs.project.convert.fx.FxNamingDictionaryDialog;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

public class FxProjectEditor extends ButtonDialog$ implements FxUnitEditor, WorkspaceWindow {
  private final Workspace a;
  
  private final boolean b;
  
  private Project c;
  
  private final TextField d = new TextField();
  
  private final ComboBox e = new ComboBox();
  
  private final FxCommentPanel f;
  
  private final Label i = new Label();
  
  public FxProjectEditor(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean, String paramString) {
    this(paramWorkspaceWindow, (Project)null, paramBoolean, paramString);
  }
  
  FxProjectEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject, boolean paramBoolean, String paramString) {
    super(paramWorkspaceWindow.getWorkspace());
    this.b = paramBoolean;
    this.a = paramWorkspaceWindow.getWorkspace();
    this.c = paramProject;
    this.f = new FxCommentPanel(this.a, paramProject);
    this.rx.G(paramString);
    setDialogTitleAndHeader();
    this.rx.a("flagCanConvert", () -> (paramProject != null));
    if (paramProject != null)
      this.rx.G(paramProject.getDbId()); 
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).e().g();
    gridPane$.a(new int[] { -2, -1 });
    gridPane$.b(new int[] { -2, -2, -1 });
    gridPane$.a((Node)this.rx.e((this.c == null) ? "nameLabel" : "nameLabelNew"), "0, 0, r, c");
    gridPane$.a((Node)this.d, "1, 0, f, c");
    gridPane$.a((Node)this.rx.e((this.c == null) ? "dbmsLabel" : "dbmsLabelNew"), "0, 1, r, c");
    if (this.c == null) {
      new AutoCompleteComboBox(this.e);
      this.e.getItems().addAll(Dbms.getKnownDbmsExclude(new String[0]));
      this.e.setValue(this.b ? "LogicalDesign" : "MySql");
      this.e.setOnAction(paramActionEvent -> {
            if (this.c != null && !this.c.getDbId().equals(this.e.getValue())) {
              FxConversionDictionaryDialog fxConversionDictionaryDialog = new FxConversionDictionaryDialog(this, this.c, (String)this.e.getValue());
              fxConversionDictionaryDialog.showDialog();
              Project project = fxConversionDictionaryDialog.a();
              if (project != null) {
                FxFrame.a(project);
                close();
              } 
            } 
          });
      gridPane$.a((Node)this.e, "1, 1, l, c");
    } else {
      SplitMenuButton splitMenuButton = this.rx.g("convertDbms", false);
      splitMenuButton.getItems().addAll(this.rx.e(new String[] { "namingDictionary", "conversionDictionary", "separator", "convertDbms" }));
      splitMenuButton.setVisible((this.c != null));
      Label label = new Label(this.c.getDbId());
      label.getStyleClass().add("font-xl");
      gridPane$.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)label, (Node)splitMenuButton }, ), "1, 1, l, c");
      if (this.c.getFile() != null)
        this.i.setText(this.c.getFile().toString()); 
      this.d.setText(this.c.getName());
    } 
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,2,r,c");
    gridPane$.a((Node)this.f, "1,2,f,f");
    if (this.c != null && this.c.hasFile())
      try {
        BasicFileAttributes basicFileAttributes = (BasicFileAttributes)Files.readAttributes(this.c.getFile().toPath(), (Class)BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
        gridPane$.a((Node)this.rx.e("filePathLabel"), "0,3,r,c");
        gridPane$.a((Node)new Label(this.c.getFile().getPath()), "1,3,l,c");
        gridPane$.a((Node)this.rx.e("fileCreationTimeLabel"), "0,4,r,c");
        gridPane$.a((Node)new Label(String.valueOf(basicFileAttributes.creationTime())), "1,4,l,c");
        gridPane$.a((Node)this.rx.e("fileModificationTimeLabel"), "0,5,r,c");
        gridPane$.a((Node)new Label(String.valueOf(basicFileAttributes.lastModifiedTime())), "1,5,l,c");
      } catch (IOException iOException) {
        Log.b(iOException);
      }  
    setInitFocusedNode((Node)this.d);
    return (Node)gridPane$;
  }
  
  @Action(b = "flagCanConvert")
  public void convertDbms() {
    Project project = FxConversionDictionaryDialog.a(this, this.c);
    if (project != null) {
      FxFrame.a(project);
      close();
    } 
  }
  
  @Action
  public void namingDictionary() {
    (new FxNamingDictionaryDialog(this, NamingDictionary.c)).showDialog();
  }
  
  @Action
  public void conversionDictionary() {
    (new FxConversionDictionaryDialog(this, this.c)).showDialog();
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean a() {
    String str = this.d.getText();
    if (str == null || str.length() < 1) {
      showError("Please set the project name.");
      this.d.requestFocus();
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    if (!a())
      return false; 
    String str = this.d.getText();
    if (this.c == null) {
      String str1 = (String)this.e.getValue();
      this.c = new Project(str, str1);
      Layout layout = this.c.createLayout("Default Layout");
      layout.diagram.createCallout(this.rx.H(this.b ? "logicalDesignCallout" : "designOfflineCallout"), new Point(50.0D, 50.0D));
      String str2 = (Dbms.get(str1)).useDefaultSchema.b() ? "Default" : ((Dbms.get(str1)).autoLetterCases.b() ? Dbms.get(str1).toDefaultCases(str) : str);
      str2 = str2.replaceAll("\\.", "_");
      if ((Dbms.get(str1)).queriesUsesCatalogAndSchema.b()) {
        this.c.createSchema((Dbms.get(str1)).defaultSchema.c_(), str2);
      } else {
        this.c.createSchema(str2);
      } 
      this.a.b(this.c);
    } else {
      this.c.rename(str);
    } 
    this.c.setComment(this.f.b());
    this.c.setCommentTags(this.f.a());
    return true;
  }
  
  public AbstractUnit b() {
    return this.c;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
