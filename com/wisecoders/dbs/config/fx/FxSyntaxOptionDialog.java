package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.SyntaxOption;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxSyntaxOptionDialog extends Dialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final ListView b = new ListView();
  
  private final SyntaxOption c;
  
  private final String d;
  
  private final String e;
  
  FxSyntaxOptionDialog(WorkspaceWindow paramWorkspaceWindow, String paramString1, SyntaxOption paramSyntaxOption, String paramString2) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow.getWorkspace();
    this.c = paramSyntaxOption;
    this.d = paramString2;
    this.e = paramString1;
    initOwner(paramWorkspaceWindow);
    if (this.c != null && this.c.a())
      this.b.getItems().addAll(paramSyntaxOption.b()); 
    this.b.setCellFactory(paramListView -> new c(this));
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (SyntaxOption)getResult() : null);
  }
  
  public Node createContentPane() {
    VBox.setVgrow((Node)this.b, Priority.ALWAYS);
    RowPane$ rowPane$ = new RowPane$();
    rowPane$.setPrefSize(600.0D, 400.0D);
    rowPane$.a();
    return (Node)rowPane$.a().a((Node)this.b);
  }
  
  public void createButtons() {
    createOkButton();
    createCloseButton();
    if (this.d != null) {
      Button button = createHelpButton(this.d);
      button.setText(this.e + " Documentation");
    } 
  }
  
  public boolean apply() {
    if (this.c != null)
      setResult(this.c); 
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
