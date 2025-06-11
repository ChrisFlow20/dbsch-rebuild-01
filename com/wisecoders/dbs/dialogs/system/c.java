package com.wisecoders.dbs.dialogs.system;

import java.io.File;
import javafx.scene.control.ListCell;

class c extends ListCell {
  protected void a(File paramFile, boolean paramBoolean) {
    super.updateItem(paramFile, paramBoolean);
    setText(null);
    if (paramFile != null && !paramBoolean)
      setText(paramFile.getName()); 
  }
}
