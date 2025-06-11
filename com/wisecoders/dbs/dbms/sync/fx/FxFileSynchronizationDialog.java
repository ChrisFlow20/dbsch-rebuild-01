package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.nio.file.Paths;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class FxFileSynchronizationDialog extends ButtonDialog$ implements WorkspaceWindow {
  private static final String a = "FILE-SYNC-FOLDER";
  
  private final Workspace b;
  
  private final ComboBox c = new ComboBox();
  
  private final CheckBox d = this.rx.v("virtualCheckBox");
  
  private final History e = new History("SyncFile");
  
  public FxFileSynchronizationDialog(Workspace paramWorkspace) {
    super(paramWorkspace);
    this.b = paramWorkspace;
    this.c.setMaxWidth(Double.MAX_VALUE);
    a();
  }
  
  private void a() {
    this.c.getItems().clear();
    for (HistoryFile historyFile : this.e.c())
      this.c.getItems().add(historyFile.b.getAbsolutePath()); 
    this.c.setValue("");
  }
  
  private File b() {
    String str = (String)this.c.getValue();
    if (StringUtil.isFilledTrim(str))
      return Paths.get(str, new String[0]).toFile(); 
    return null;
  }
  
  public Workspace getWorkspace() {
    return this.b;
  }
  
  public Node createContentPane() {
    this.c.setMinWidth(220.0D);
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("fileLabel"), "0,0,r,c")
      .a((Node)(new FlowPane$()).g().a(new Node[] { (Node)this.c, (Node)this.rx.j("chooseFile") }, ), "1,0,f,c")
      .a((Node)this.d, "1,1,l,c");
  }
  
  public void createButtons() {
    createActionButton("sync");
    createCancelButton();
  }
  
  public boolean apply() {
    if (b() == null) {
      this.rx.c(getDialogScene(), "chooseFileError");
      return false;
    } 
    return true;
  }
  
  @Action
  public Task sync() {
    if (apply()) {
      File file = b();
      Project project = this.b.m();
      if (file != null) {
        this.e.a(null, b());
        this.e.a(b());
        Pref.a("FILE-SYNC-FOLDER", file.getParent());
        return new FxFileSynchronizationDialog$1(this, this.b, file, project);
      } 
    } 
    return null;
  }
  
  @Action
  public void chooseFile() {
    String str = Pref.c("FILE-SYNC-FOLDER", (this.b.m().getFile() != null) ? this.b.m().getFile().getParent() : null);
    File file = FxFileChooser.a(getDialogScene(), this.rx.H("chooseFile"), str, FileOperation.a, new FileType[] { FileType.a });
    if (file != null) {
      Pref.a("FILE-SYNC-FOLDER", file.getParent());
      this.c.getItems().add(0, file.getAbsolutePath());
      this.c.setValue(file.getAbsolutePath());
    } 
  }
}
