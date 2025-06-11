package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.VBox$;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.eclipse.jgit.revwalk.RevCommit;

public class FxGitAuditDialog extends Dialog$ {
  private final FxGitAuditDialog$ChangeTreeItem a = new FxGitAuditDialog$ChangeTreeItem(new FxGitAuditDialog$Change("Root"));
  
  private final TreeTableView b = new TreeTableView();
  
  private final TextField c = new TextField();
  
  public FxGitAuditDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Name");
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new b());
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Details");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn2.setCellFactory(paramTreeTableColumn -> new a());
    this.b.setRoot(this.a);
    this.b.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2 });
    this.b.setShowRoot(false);
    this.b.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    Rx.a(this.b, new double[] { 0.3D, 0.2D, 0.5D });
    setRegionPrefSize((Region)this.b, 850.0D, 600.0D);
    this.c.getStyleClass().add("search-field");
    this.c.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.a.a((this.c.getText() != null) ? this.c.getText().toLowerCase().trim() : null));
  }
  
  public Node createContentPane() {
    VBox$.setVgrow((Node)this.b, Priority.ALWAYS);
    return (Node)(new VBox$()).l().c(new Node[] { (Node)this.c, (Node)this.b });
  }
  
  public void createButtons() {
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  protected void a(RevCommit paramRevCommit, SyncPair paramSyncPair) {
    FxGitAuditDialog$ChangeTreeItem fxGitAuditDialog$ChangeTreeItem = this.a.a(new FxGitAuditDialog$Change(paramRevCommit.getAuthorIdent().getName() + " @ " + paramRevCommit.getAuthorIdent().getName()));
    a(fxGitAuditDialog$ChangeTreeItem, paramSyncPair);
  }
  
  protected void a(FxGitAuditDialog$ChangeTreeItem paramFxGitAuditDialog$ChangeTreeItem, SyncPair paramSyncPair) {
    for (AbstractDiff abstractDiff : paramSyncPair.getDifferences())
      paramFxGitAuditDialog$ChangeTreeItem.a(new FxGitAuditDialog$Change(abstractDiff)); 
    for (SyncPair syncPair : paramSyncPair.getChildrenPairs()) {
      FxGitAuditDialog$Change fxGitAuditDialog$Change = new FxGitAuditDialog$Change(syncPair);
      FxGitAuditDialog$ChangeTreeItem fxGitAuditDialog$ChangeTreeItem = paramFxGitAuditDialog$ChangeTreeItem.a(fxGitAuditDialog$Change);
      a(fxGitAuditDialog$ChangeTreeItem, syncPair);
    } 
  }
}
