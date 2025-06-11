package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class FxCreateDatabaseDialog extends Dialog$ implements WorkspaceWindow {
  private final WorkspaceWindow a;
  
  private final Connector b;
  
  private final Label c;
  
  private final TextField d = new TextField();
  
  private final Button e;
  
  private final FxSyntaxOptionComboBox f;
  
  private final Label i = this.rx.e("optionsLabel");
  
  private final boolean j;
  
  public FxCreateDatabaseDialog(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector, String paramString) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramWorkspaceWindow;
    this.b = paramConnector;
    initOwner(paramWorkspaceWindow);
    setDialogTitle("Create Database");
    Dbms dbms = Dbms.get(paramString);
    this.f = new FxSyntaxOptionComboBox(this, paramString, dbms.databaseOptions);
    String str = dbms.databaseCreate.c_();
    this.j = (str != null && str.contains("${file}"));
    boolean bool = (str != null && str.contains("${folder}")) ? true : false;
    this.c = this.rx.e(bool ? "folderLabel" : (this.j ? "fileLabel" : "nameLabel"));
    this.e = this.rx.j("choose");
    this.f.b("Options");
    this.f.d();
    this.e.setVisible((this.j || bool));
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (String)getResult() : null);
  }
  
  public Node createContentPane() {
    this.d.setPrefColumnCount(20);
    this.i.visibleProperty().bind((ObservableValue)this.f.visibleProperty());
    Label label = this.rx.e("optionsLabel");
    label.visibleProperty().bind((ObservableValue)this.f.visibleProperty());
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1, -2 }).b(new int[] { -2, -2 }).e().g();
    gridPane$.a((Node)this.c, "0,0,r,c");
    gridPane$.a((Node)this.d, "1,0,f,c");
    gridPane$.a((Node)this.e, "2,0,f,c");
    gridPane$.a((Node)label, "0,1,r,c");
    gridPane$.a((Node)this.f, "1,1,f,c");
    setInitFocusedNode((Node)this.d);
    return (Node)gridPane$;
  }
  
  @Action
  public void choose() {
    if (this.j) {
      File file = FxFileChooser.a(getDialogPane().getScene(), "Save Database File", FileOperation.b, new FileType[] { FileType.w });
      this.d.setText((file != null) ? file.getAbsolutePath().replaceAll("\\\\", "/") : "");
    } else {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      directoryChooser.setTitle("Choose Directory");
      File file = directoryChooser.showDialog(getOwner());
      this.d.setText((file != null) ? file.getAbsolutePath().replaceAll("\\\\", "/") : "");
    } 
  }
  
  public void createButtons() {
    createActionButton("createDatabase");
    createCancelButton();
    createHelpButton("schema.html");
  }
  
  @Action
  public Task createDatabase() {
    if (StringUtil.isEmpty(this.d.getText())) {
      this.rx.c(getDialogScene(), "Please set the name");
      return null;
    } 
    return new FxCreateDatabaseDialog$1(this);
  }
  
  public boolean apply() {
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a.getWorkspace();
  }
}
