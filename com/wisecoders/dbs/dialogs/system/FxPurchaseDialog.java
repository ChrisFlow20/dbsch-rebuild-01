package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Features;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class FxPurchaseDialog extends ButtonDialog$ {
  private final Rx a = new Rx(FxPurchaseDialog.class, this);
  
  private final WorkspaceWindow b;
  
  public FxPurchaseDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.b = paramWorkspaceWindow;
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l();
    byte b = 0;
    while (this.a.H("advantage" + b) != null) {
      byte b1 = b;
      Hyperlink hyperlink = new Hyperlink(this.a.H("advantage" + b));
      hyperlink.getStyleClass().addAll((Object[])new String[] { "font-xl", "font-bold" });
      hyperlink.setOnAction(paramActionEvent -> WebBrowserExternal.a(getDialogScene(), this.a.a("advantage" + paramInt + ".href", new String[0])));
      gridPane$.a((Node)hyperlink, "0," + b + ",r,c");
      Label label = new Label("  " + this.a.a("advantage" + b + ".tooltip", new String[0]));
      label.getStyleClass().addAll((Object[])new String[] { "font-xl" });
      label.setWrapText(true);
      gridPane$.a((Node)label, "1," + b + ",l,c");
      b++;
    } 
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    if (License.a().g() == Features.c)
      createActionButton("activateTrial"); 
  }
  
  public boolean apply() {
    WebBrowserExternal.a(this.b.getWorkspace(), this.a.a("buy.url", new String[0]));
    return true;
  }
  
  @Action
  public void activateTrial() {
    a((FxFrame)this.b.getWorkspace());
    this.a.a(getDialogScene(), "activateTrialSucceed", new String[0]);
    hide();
  }
  
  public static void a(FxFrame paramFxFrame) {
    Keys.k.a(Long.valueOf(System.currentTimeMillis()));
    paramFxFrame.c();
    paramFxFrame.b.b();
  }
}
