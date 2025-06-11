package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.VirtualForeignKeySuggestion;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;

class aa extends TableCell {
  protected void a(VirtualForeignKeySuggestion paramVirtualForeignKeySuggestion, boolean paramBoolean) {
    super.updateItem(paramVirtualForeignKeySuggestion, paramBoolean);
    if (paramVirtualForeignKeySuggestion != null && !paramBoolean) {
      setText(paramVirtualForeignKeySuggestion.getQuery());
      setTooltip(new Tooltip(paramVirtualForeignKeySuggestion.getQuery()));
    } 
  }
}
