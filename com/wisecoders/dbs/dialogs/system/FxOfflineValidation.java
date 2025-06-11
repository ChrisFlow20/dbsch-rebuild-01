package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.Hyperlink$;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class FxOfflineValidation extends Dialog$ implements WorkspaceWindow {
  private final WorkspaceWindow a;
  
  private final Hyperlink$ b;
  
  private final TextField c = new TextField();
  
  private final boolean d;
  
  public FxOfflineValidation(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean) {
    super(paramWorkspaceWindow);
    this.d = paramBoolean;
    this.a = paramWorkspaceWindow;
    this.b = new Hyperlink$(StringUtil.cutOfWithDots(License.a().c(paramBoolean), 60), paramActionEvent -> WebBrowserExternal.a(getWorkspace(), License.a().c(paramBoolean)));
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (String)getResult() : null);
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).a((Node)(new FlowPane$()).a(new Node[] { (Node)this.b, (Node)this.rx.j("copyToClipboard") }, ), "1,0,l,c")
      .a((Node)this.rx.e("responseLabel"), "0,1,r,c")
      .a((Node)this.c, "1,1,f,c");
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  @Action
  public void copyToClipboard() {
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(License.a().c(this.d));
    Clipboard.getSystemClipboard().setContent((Map)clipboardContent);
  }
  
  public boolean apply() {
    setResult(this.c.getText());
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a.getWorkspace();
  }
}
