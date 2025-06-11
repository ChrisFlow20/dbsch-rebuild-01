package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.dialogs.git.credentials.GitCredentials;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.PasswordFieldWithEye;
import java.io.File;
import java.nio.file.Paths;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;

public class FxGitCredentialsDialog extends ButtonDialog$ {
  private final TextField a = new TextField();
  
  private final PasswordFieldWithEye b = new PasswordFieldWithEye();
  
  private final GitCredentials c;
  
  private final Label d = this.rx.e("userNameLabel");
  
  private final Label e = this.rx.e("passwordLabel");
  
  private final Label f = this.rx.e("privateKeyLabel");
  
  private final Label i = this.rx.e("paraphraseLabel");
  
  private final CheckBox j;
  
  private final CheckBox k;
  
  private final TextField l;
  
  private final PasswordFieldWithEye m = new PasswordFieldWithEye();
  
  private final Button n = this.rx.j("choosePrivateKey");
  
  public FxGitCredentialsDialog(Scene paramScene, GitCredentials paramGitCredentials) {
    super(paramScene.getWindow());
    this.c = paramGitCredentials;
    this.a.setText(paramGitCredentials.b());
    this.l = this.rx.t("privateKey");
    String str = System.getProperty("user.home") + System.getProperty("user.home") + ".ssh" + File.separator + "id_rsa";
    this.j = this.rx.h("sshCheckBox", (paramGitCredentials.b() == null && paramGitCredentials.g() && (paramGitCredentials.d() != null || Paths.get(str, new String[0]).toFile().exists())));
    this.j.setDisable(!paramGitCredentials.g());
    if (this.j.isSelected())
      this.l.setText((paramGitCredentials.d() != null) ? paramGitCredentials.d() : str); 
    this.k = this.rx.h("rememberCheckBox", paramGitCredentials.f());
    this.j.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> a(paramBoolean2.booleanValue()));
    a(this.j.isSelected());
  }
  
  private void a(boolean paramBoolean) {
    this.d.setDisable(paramBoolean);
    this.a.setDisable(paramBoolean);
    this.e.setDisable(paramBoolean);
    this.b.setDisable(paramBoolean);
    this.k.setDisable(paramBoolean);
    this.f.setDisable(!paramBoolean);
    this.i.setDisable(!paramBoolean);
    this.l.setDisable(!paramBoolean);
    this.m.setDisable(!paramBoolean);
    this.n.setDisable(!paramBoolean);
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1, -2 });
    gridPane$.a((Node)this.d, "0,0,r,c");
    this.a.setPrefColumnCount(40);
    this.a.setText(this.c.b());
    gridPane$.a((Node)this.a, "1,0,f,c");
    gridPane$.a((Node)this.e, "0,1,r,c");
    this.b.a(this.c.c());
    gridPane$.a((Node)this.b, "1,1,f,c");
    gridPane$.a((Node)this.k, "1,2,l,c");
    HBox$.setHgrow((Node)this.l, Priority.ALWAYS);
    gridPane$.a((Node)this.j, "1,3,l,c")
      .a((Node)this.f, "0,4,r,c")
      .a((Node)(new HBox$()).a(new Node[] { (Node)this.l, (Node)this.n }, ), "1,4,f,c")
      .a((Node)this.i, "0,5,r,c")
      .a((Node)this.m, "1,5,f,c");
    return (Node)gridPane$;
  }
  
  @Action
  public void choosePrivateKey() {
    File file = FxFileChooser.a(getDialogScene(), "Choose Private Key", FileOperation.a, new FileType[] { FileType.D });
    if (file != null)
      this.l.setText(file.getAbsolutePath()); 
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    this.c.a(this.k.isSelected());
    this.c.b(this.j.isSelected() ? null : this.a.getText());
    this.c.c(this.j.isSelected() ? null : this.b.a());
    this.c.d(this.j.isSelected() ? this.l.getText() : null);
    this.c.e(this.j.isSelected() ? this.m.a() : null);
    return true;
  }
}
