package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sys.fx.Dialog$;
import javafx.scene.Node;

public class FxStatementDialog extends Dialog$ {
  private final StyledEditor a;
  
  public FxStatementDialog(WorkspaceWindow paramWorkspaceWindow, String paramString) {
    super(paramWorkspaceWindow);
    this.a = new StyledEditor(paramWorkspaceWindow.getWorkspace().j() ? Language.c : Language.a, true);
    this.a.b(paramString);
    this.a.e(false);
    this.a.setPrefSize(850.0D, 450.0D);
  }
  
  public Node createContentPane() {
    return (Node)this.a;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    return true;
  }
}
