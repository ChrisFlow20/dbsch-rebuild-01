package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.fx.SyncTreeItem;
import com.wisecoders.dbs.dbms.sync.fx.SyncUnitColumn;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.SyncGitTextColumn;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import javafx.scene.Node;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class FxGitDiffDialog extends Dialog$ {
  private final TreeTableView a = new TreeTableView();
  
  public FxGitDiffDialog(WorkspaceWindow paramWorkspaceWindow, Project paramProject1, Project paramProject2) {
    super(paramWorkspaceWindow);
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { new SyncUnitColumn(), new SyncGitTextColumn() });
    Rx.c(this.a, new double[] { 0.3D, 0.6D });
    try {
      SyncPair syncPair = new SyncPair(paramProject1, paramProject2);
      this.a.setRoot(new SyncTreeItem(syncPair));
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  public Node createContentPane() {
    return (Node)this.a;
  }
  
  public void createButtons() {
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
}
