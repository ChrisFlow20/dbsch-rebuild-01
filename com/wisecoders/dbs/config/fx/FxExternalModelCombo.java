package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.schema.Project;
import javafx.scene.control.ComboBox;

public class FxExternalModelCombo extends ComboBox {
  private final Project a;
  
  public FxExternalModelCombo(Project paramProject) {
    this.a = paramProject;
    setEditable(true);
    setMaxWidth(2.147483647E9D);
  }
}
