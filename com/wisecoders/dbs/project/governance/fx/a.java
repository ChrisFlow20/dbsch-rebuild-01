package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.project.governance.model.InspectorDefinition;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

class a extends ListCell {
  protected void a(InspectorDefinition paramInspectorDefinition, boolean paramBoolean) {
    super.updateItem(paramInspectorDefinition, paramBoolean);
    setText(null);
    getStyleClass().removeAll((Object[])new String[] { "font-bold" });
    if (!paramBoolean && paramInspectorDefinition != null) {
      setText(paramInspectorDefinition.toString());
      setGraphic((Node)BootstrapIcons.clipboard_check.glyph(new String[] { "glyph-blue" }));
    } 
  }
}
