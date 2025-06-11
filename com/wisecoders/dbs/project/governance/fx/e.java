package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.project.governance.model.InspectorWarning;
import javafx.scene.Node;
import javafx.scene.control.TableCell;

class e extends TableCell {
  protected void a(InspectorWarning paramInspectorWarning, boolean paramBoolean) {
    super.updateItem(paramInspectorWarning, paramBoolean);
    setGraphic(null);
    setText(null);
    getStyleClass().remove("font-bold");
    if (paramInspectorWarning != null && !isEmpty()) {
      setText(paramInspectorWarning.b.getName());
      setGraphic((Node)paramInspectorWarning.b.getSymbolicGlyph().glyph(new String[0]));
    } 
  }
}
