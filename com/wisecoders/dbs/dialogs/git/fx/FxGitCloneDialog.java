package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.dialogs.git.credentials.GitCredentials;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.io.File;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class FxGitCloneDialog extends Dialog$ {
  private final TextField a;
  
  private final TextField b = new TextField();
  
  private final GitCredentials c;
  
  public FxGitCloneDialog(Window paramWindow, GitCredentials paramGitCredentials) {
    super(paramWindow);
    this.c = paramGitCredentials;
    this.a = this.rx.t("urlField");
  }
  
  public Node createContentPane() {
    HBox$.setHgrow((Node)this.b, Priority.ALWAYS);
    this.rx.t("urlField");
    this.a.setPrefColumnCount(40);
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("urlLabel"), "0,0,r,c")
      .a((Node)this.a, "1,0,f,c")
      .a((Node)this.rx.e("directoryLabel"), "0,1,r,c")
      .a((Node)(new HBox$()).f().a(new Node[] { (Node)this.b, (Node)this.rx.j("chooseDirectory") }, ), "1,1,f,c")
      .a((Node)this.rx.j("configureCredentials"), "1,2,f,c");
  }
  
  public void createButtons() {
    createActionButton("gitClone");
    createCancelButton();
  }
  
  public boolean apply() {
    return (!StringUtil.isEmpty(this.a.getText()) && !StringUtil.isEmpty(this.b.getText()));
  }
  
  @Action
  public void chooseDirectory() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File file = directoryChooser.showDialog(getDialogScene().getWindow());
    if (file != null)
      this.b.setText(file.getAbsolutePath()); 
  }
  
  @Action
  public void configureCredentials() {
    this.c.a(this.a.getText());
    (new FxGitCredentialsDialog(getDialogScene(), this.c)).showDialog();
  }
  
  @Action
  public Task gitClone() {
    this.c.a(this.a.getText());
    return new FxGitCloneDialog$1(this);
  }
}
