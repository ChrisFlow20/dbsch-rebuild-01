package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;

class b extends TreeTableCell {
  protected void a(FxGitAuditDialog$Change paramFxGitAuditDialog$Change, boolean paramBoolean) {
    super.updateItem(paramFxGitAuditDialog$Change, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramFxGitAuditDialog$Change != null) {
      setText(paramFxGitAuditDialog$Change.a);
      if (paramFxGitAuditDialog$Change.c != null)
        setGraphic((Node)Rx.q(paramFxGitAuditDialog$Change.c)); 
    } 
  }
}
