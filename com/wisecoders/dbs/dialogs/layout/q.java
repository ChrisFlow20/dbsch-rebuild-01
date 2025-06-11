package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.Group;
import javafx.scene.control.ListCell;

class q extends ListCell {
  protected void a(Group paramGroup, boolean paramBoolean) {
    super.updateItem(paramGroup, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramGroup != null && !paramBoolean)
      setText(paramGroup.getName()); 
  }
}
