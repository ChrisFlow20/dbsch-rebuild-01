package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.Sys;
import java.io.File;
import javafx.scene.control.ListCell;

class h extends ListCell {
  protected void a(File paramFile, boolean paramBoolean) {
    super.updateItem(paramFile, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramFile != null && !paramBoolean) {
      setText(paramFile.getAbsolutePath());
      if (paramFile == Sys.i)
        setText("DbSchema Output Logs (" + String.valueOf(paramFile) + ")"); 
    } 
  }
}
