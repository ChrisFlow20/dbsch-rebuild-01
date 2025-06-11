package com.wisecoders.dbs.dialogs.git.fx;

import javafx.scene.control.TreeTableCell;

class a extends TreeTableCell {
  protected void a(FxGitAuditDialog$Change paramFxGitAuditDialog$Change, boolean paramBoolean) {
    super.updateItem(paramFxGitAuditDialog$Change, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramFxGitAuditDialog$Change != null)
      setText(paramFxGitAuditDialog$Change.b); 
  }
}
