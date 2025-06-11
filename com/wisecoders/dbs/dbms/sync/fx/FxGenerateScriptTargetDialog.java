package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class FxGenerateScriptTargetDialog extends Dialog$ {
  private static final String a = "generateSQLFile";
  
  private final TextField b = new TextField();
  
  private final RadioButton c = this.rx.w("radioSQLEditor");
  
  private final RadioButton d = this.rx.w("radioFile");
  
  public FxGenerateScriptTargetDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow.getWorkspace());
    initOwner(paramWorkspaceWindow);
    Objects.requireNonNull(this.d);
    this.rx.a("useFile", this.d::isSelected);
    this.rx.a("canContinue", () -> (this.c.isSelected() || StringUtil.isFilled(this.b)));
    this.b.setText(Pref.c("generateSQLFile", (String)null));
    this.c.setSelected(true);
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.c, (ToggleButton)this.d });
    this.b.setDisable(true);
    this.d.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          this.b.setDisable(!paramBoolean2.booleanValue());
          this.rx.b();
        });
    this.rx.b();
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (String)getResult() : null);
  }
  
  public Node createContentPane() {
    this.b.setPrefColumnCount(20);
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).a((Node)this.c, "0,0,1,0,l,c")
      .a((Node)this.d, "0,1,1,1,l,c")
      .a((Node)(new FlowPane$()).a(new Node[] { (Node)this.b, (Node)this.rx.j("chooseFile") }, ), "1,2,l,c");
  }
  
  @Action(b = "useFile")
  public void chooseFile() {
    File file = FxFileChooser.a(getDialogScene(), "Choose Excel, JSon, Csv or text file", FileOperation.a, new FileType[] { FileType.D });
    if (file != null)
      this.b.setText(file.getAbsolutePath()); 
  }
  
  public void createButtons() {
    createActionButton("generate", ButtonBar.ButtonData.OK_DONE);
    createCancelButton();
  }
  
  @Action(b = "canContinue")
  public void generate() {
    Pref.a("generateSQLFile", this.b.getText());
    setResult(this.d.isSelected() ? this.b.getText() : "");
    hide();
  }
  
  public boolean apply() {
    return true;
  }
}
