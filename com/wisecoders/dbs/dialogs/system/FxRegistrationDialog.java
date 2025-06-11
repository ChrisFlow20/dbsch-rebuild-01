package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FxRegistrationDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final FxFrame a;
  
  private final TextArea b;
  
  private final TextField c;
  
  private final TextField d;
  
  private final TextField e;
  
  private final TextField f;
  
  private final TabPane i = new TabPane();
  
  private final CheckBox j;
  
  private final Label k;
  
  private final Label l;
  
  private final Label m;
  
  private final Tab n;
  
  public FxRegistrationDialog(FxFrame paramFxFrame) {
    super(paramFxFrame);
    this.a = paramFxFrame;
    this.b = this.rx.s("licenseField");
    this.e = this.rx.t("userNameField");
    this.f = this.rx.t("userNameField");
    this.c = this.rx.t("hostField");
    this.d = this.rx.t("portField");
    this.j = this.rx.v("flsEnable");
    this.k = this.rx.e("serverHostLabel");
    this.l = this.rx.e("serverPortLabel");
    this.m = this.rx.e("userNameLabel");
    this.b.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.rx.b());
    this.e.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.rx.b());
    this.c.setText(Keys.h.a());
    this.d.setText(Keys.i.a());
    this.f.setText(Keys.g.a());
    this.n = new Tab(this.rx.H("serverTab"));
    this.n.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> this.rx.b());
    this.rx.a("flagHasText", () -> (StringUtil.isFilledTrim(this.b.getText()) || this.n.isSelected()));
    this.j.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          this.c.setDisable(!paramBoolean2.booleanValue());
          this.d.setDisable(!paramBoolean2.booleanValue());
          this.f.setDisable(!paramBoolean2.booleanValue());
          this.k.setDisable(!paramBoolean2.booleanValue());
          this.l.setDisable(!paramBoolean2.booleanValue());
          this.m.setDisable(!paramBoolean2.booleanValue());
        });
    this.j.setSelected(Keys.h.e());
    showDialog();
  }
  
  public Node createContentPane() {
    this.b.setPrefSize(750.0D, 350.0D);
    GridPane$ gridPane$1 = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -2, -1, -2 }).a((Node)this.rx.e("userNameLabel"), "0,0,r,c").a((Node)this.e, "1,0,l,c").a((Node)this.rx.e("licenseLabel"), "0,1,r,c").a((Node)this.b, "1,1,f,f").a((Node)this.rx.j("loadFromFile"), "1,2,l,c");
    GridPane$ gridPane$2 = (new GridPane$()).l().a(new int[] { -2, -1 }).a((Node)this.rx.e("serverTabHeader"), "0,0,1,0,l,c").a((Node)this.j, "1,1,l,c").a((Node)this.k, "0,2,r,c").a((Node)this.c, "1,2,f,c").a((Node)this.l, "0,3,r,c").a((Node)this.d, "1,3,f,c").a((Node)this.m, "0,4,r,c").a((Node)this.f, "1,4,l,c");
    Rx.a(this.i);
    this.i.getStyleClass().add("floating");
    this.i.getTabs().add(new Tab(this.rx.H("licenseKey"), (Node)gridPane$1));
    this.n.setContent((Node)gridPane$2);
    this.i.getTabs().add(this.n);
    return (Node)this.i;
  }
  
  public void createButtons() {
    createActionButton("activate");
    createActionButton("purchase");
    createActionButton("contactSupport", ButtonBar.ButtonData.LEFT);
    createActionButton("manageLicenses", ButtonBar.ButtonData.LEFT);
    createCancelButton();
  }
  
  @Action
  public boolean apply() {
    return true;
  }
  
  @Action(b = "flagHasText")
  public Task activate() {
    if (this.j.isSelected())
      return a(); 
    this.i.getSelectionModel().select(0);
    Keys.g.c();
    Keys.h.c();
    Keys.i.c();
    return b();
  }
  
  private Task a() {
    if (StringUtil.isEmptyTrim(this.c.getText()) || StringUtil.isEmptyTrim(this.d.getText()) || StringUtil.isEmptyTrim(this.f.getText())) {
      showError("fillFlsFields");
      return null;
    } 
    Keys.g.a(this.f.getText());
    Keys.h.a(this.c.getText());
    Keys.i.a(this.d.getText());
    return new FxRegistrationDialog$1(this, this.a);
  }
  
  private Task b() {
    if (StringUtil.isEmptyTrim(this.b.getText()) || StringUtil.isEmptyTrim(this.e.getText())) {
      showError("fillFields");
      return null;
    } 
    License.b();
    String str = this.b.getText().trim();
    Keys.d.a(str);
    Keys.g.a(this.e.getText());
    if (!str.equals(Keys.d.a())) {
      showError(this.rx.H("saveFailed"));
      return null;
    } 
    License license = License.a();
    switch (FxRegistrationDialog$3.a[license.g().ordinal()]) {
      case 1:
        if (license.m())
          return new FxRegistrationDialog$2(this, ActivationTask$Mode.a, license); 
        c();
        break;
      case 2:
      case 3:
        showError(this.rx.H("renew"));
        break;
      case 4:
      case 5:
      case 6:
      case 7:
        showError(this.rx.H("failed"));
        break;
    } 
    return null;
  }
  
  private void c() {
    this.a.c();
    this.a.b.b();
    showInformation(this.rx.H("regSucceed"), new String[0]);
    close();
  }
  
  @Action
  public void purchase() {
    WebBrowserExternal.a(this.a, this.rx.a("buy.url", new String[0]));
  }
  
  @Action
  public void manageLicenses() {
    WebBrowserExternal.a(this.a, this.rx.H("customerAreaURL"));
  }
  
  @Action
  public void contactSupport() {
    (new FxTechnicalSupportDialog(getDialogScene().getWindow())).showDialog();
  }
  
  @Action
  public void loadFromFile() {
    File file = FxFileChooser.a(getDialogScene(), "Choose File", FileOperation.a, new FileType[] { FileType.i });
    if (file != null)
      try {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        try {
          ArrayList<String> arrayList = new ArrayList();
          ArrayList<Integer> arrayList1 = new ArrayList();
          StringBuilder stringBuilder = new StringBuilder();
          String str;
          while ((str = bufferedReader.readLine()) != null) {
            str = str.trim();
            if (!str.isEmpty())
              stringBuilder.append(str).append("\n"); 
            if (str.startsWith("#### END ####")) {
              arrayList.add(stringBuilder.toString());
              stringBuilder.delete(0, stringBuilder.length());
              arrayList1.add(Integer.valueOf(arrayList.size()));
            } 
          } 
          if (!stringBuilder.isEmpty())
            arrayList.add(stringBuilder.toString()); 
          if (arrayList.size() > 1) {
            ChoiceDialog choiceDialog = new ChoiceDialog(Integer.valueOf(1), arrayList1);
            this.rx.a((Dialog)choiceDialog, "chooseSeatDialog");
            choiceDialog.showAndWait().ifPresent(paramInteger -> this.b.setText(paramList.get(paramInteger.intValue() - 1)));
          } else if (arrayList.size() == 1) {
            this.b.setText(arrayList.get(0));
          } 
          bufferedReader.close();
        } catch (Throwable throwable) {
          try {
            bufferedReader.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      }  
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
