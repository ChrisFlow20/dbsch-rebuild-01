package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.util.Arrays;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class FxPublishArticleDialog extends Dialog$ {
  private static final String a = "PublishUser";
  
  private static final String b = "PublishEmail";
  
  private final TextField c = new TextField();
  
  private final TextField d = new TextField();
  
  private final File e;
  
  public FxPublishArticleDialog(WorkspaceWindow paramWorkspaceWindow, File paramFile) {
    super(paramWorkspaceWindow);
    this.e = paramFile;
    setInitFocusedNode((Node)this.c);
    this.c.setText(Pref.c("PublishUser", Keys.g.a()));
    this.d.setText(Pref.c("PublishEmail", (License.a()).c));
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("userNameLabel"), "0,0,r,c")
      .a((Node)this.c, "1,0,f,c")
      .a((Node)this.rx.e("emailLabel"), "0,1,r,c")
      .a((Node)this.d, "1,1,f,c");
  }
  
  public void createButtons() {
    createActionButton("publish");
    createCancelButton();
  }
  
  public boolean apply() {
    Pref.a("PublishUser", this.c.getText());
    Pref.a("PublishEmail", this.d.getText());
    return true;
  }
  
  @Action
  public Task publish() {
    File[] arrayOfFile = this.e.listFiles();
    if (arrayOfFile == null || arrayOfFile.length == 0) {
      this.rx.c(getDialogScene(), "cannotPublishEmptyArticle");
      return null;
    } 
    return new TechnicalSupportTask(this, "Article: " + this.e.getName(), this.d.getText(), "New Article by " + this.c.getText() + " " + this.d.getText(), Arrays.asList(arrayOfFile));
  }
}
