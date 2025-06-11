package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.VirtualForeignKeySuggestion;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;

class ab extends TableCell {
  protected void a(VirtualForeignKeySuggestion paramVirtualForeignKeySuggestion, boolean paramBoolean) {
    super.updateItem(paramVirtualForeignKeySuggestion, paramBoolean);
    setTooltip(null);
    setGraphic(null);
    setText(null);
    if (paramVirtualForeignKeySuggestion != null && !paramBoolean)
      switch (FxVirtualForeignKeysDialog$4.a[paramVirtualForeignKeySuggestion.getStatus().ordinal()]) {
        case 1:
          setGraphic((Node)Rx.q("ok.png"));
          setText("Succeed");
          break;
        case 2:
          setGraphic((Node)Rx.q("help.png"));
          setText("Parsed, no tables found");
          break;
        case 3:
          setGraphic((Node)Rx.q("cancel.png"));
          setText(StringUtil.removeNewLine(paramVirtualForeignKeySuggestion.getErrorString(), true));
          setTooltip(new Tooltip(paramVirtualForeignKeySuggestion.getErrorString()));
          break;
      }  
  }
}
