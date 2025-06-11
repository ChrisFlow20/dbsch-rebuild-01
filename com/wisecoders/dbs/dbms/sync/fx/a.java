package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncAction;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.MouseEvent;

class a extends TreeTableCell {
  private final SyncSide b;
  
  private final SyncAction c;
  
  private final ToggleButton d = new ToggleButton();
  
  a(FxSynchronizationDialog paramFxSynchronizationDialog, SyncSide paramSyncSide, SyncAction paramSyncAction) {
    this.b = paramSyncSide;
    this.c = paramSyncAction;
    setAlignment(Pos.CENTER);
    this.d.setPrefWidth(2.147483647E9D);
    this.d.setOnMouseClicked(paramMouseEvent -> {
          if ((paramMouseEvent.isShiftDown() || paramMouseEvent.isControlDown()) && this.a.n > -1) {
            for (int i = Math.min(getTableRow().getIndex(), this.a.n); i <= Math.max(getTableRow().getIndex(), this.a.n); i++) {
              TreeItem treeItem = this.a.c.getTreeItem(i);
              if (treeItem instanceof SyncTreeItem) {
                SyncTreeItem syncTreeItem = (SyncTreeItem)treeItem;
                if (this.a.c.getTreeItem(i).getValue() instanceof AbstractDiff) {
                  if (paramSyncSide == SyncSide.left) {
                    syncTreeItem.a((((SyncDiff)getItem()).getAction() == SyncAction.toLeft) ? SyncAction.noAction : SyncAction.toLeft);
                  } else {
                    syncTreeItem.a((((SyncDiff)getItem()).getAction() == SyncAction.toRight) ? SyncAction.noAction : SyncAction.toRight);
                  } 
                  this.a.c.getSelectionModel().select(i);
                } 
              } 
            } 
          } else if (getTableRow() != null) {
            TreeItem treeItem = getTableRow().getTreeItem();
            if (treeItem instanceof SyncTreeItem) {
              SyncTreeItem syncTreeItem = (SyncTreeItem)treeItem;
              if (paramSyncSide == SyncSide.left) {
                syncTreeItem.a((((SyncDiff)getItem()).getAction() == SyncAction.toLeft) ? SyncAction.noAction : SyncAction.toLeft);
              } else {
                syncTreeItem.a((((SyncDiff)getItem()).getAction() == SyncAction.toRight) ? SyncAction.noAction : SyncAction.toRight);
              } 
              this.a.a((SyncDiff)getItem());
            } 
          } 
          this.a.n = getTableRow().getIndex();
          this.a.c.refresh();
          this.a.c.getSelectionModel().select(getTableRow().getIndex());
          this.a.rx.b();
        });
  }
  
  protected void a(SyncDiff paramSyncDiff, boolean paramBoolean) {
    super.updateItem(paramSyncDiff, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().clear();
    if (!paramBoolean && paramSyncDiff != null) {
      this.d.setSelected((paramSyncDiff.getAction() == this.c));
      if (paramSyncDiff instanceof AbstractDiff) {
        AbstractDiff abstractDiff = (AbstractDiff)paramSyncDiff;
        this.d.setText(abstractDiff.getOperationString(this.b));
      } else {
        this.d.setText(null);
      } 
      setGraphic((Node)this.d);
    } 
  }
}
