package com.wisecoders.dbs.dialogs.web.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import javafx.scene.Node;

public class FxWebPageSource extends ButtonDialog$ {
  private final String a;
  
  public FxWebPageSource(WorkspaceWindow paramWorkspaceWindow, String paramString) {
    super(paramWorkspaceWindow);
    setTitle("Page Source");
    this.a = paramString;
  }
  
  public Node createContentPane() {
    StyledEditor styledEditor = new StyledEditor(Language.e, true);
    styledEditor.b(this.a);
    styledEditor.setPrefSize(750.0D, 550.0D);
    return (Node)styledEditor;
  }
  
  public void createButtons() {
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
}
