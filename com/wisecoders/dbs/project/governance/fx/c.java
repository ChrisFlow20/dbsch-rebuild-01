package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.project.governance.model.Inspector;
import com.wisecoders.dbs.project.governance.model.InspectorNode;
import com.wisecoders.dbs.project.governance.model.InspectorParameter;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;

class c extends TreeTableCell {
  protected void a(InspectorNode paramInspectorNode, boolean paramBoolean) {
    super.updateItem(paramInspectorNode, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().removeAll((Object[])new String[] { "font-bold", "edited-cell" });
    if (paramInspectorNode != null) {
      setText(paramInspectorNode.toString());
      if (paramInspectorNode instanceof com.wisecoders.dbs.project.governance.model.InspectorFolder) {
        getStyleClass().add("font-bold");
        setGraphic((Node)Rx.q("folder.png"));
      } else if (paramInspectorNode instanceof Inspector) {
        Inspector inspector = (Inspector)paramInspectorNode;
        setGraphic(inspector.h() ? (Node)BootstrapIcons.funnel.glyph(new String[0]) : (Node)BootstrapIcons.clipboard_check.glyph(new String[0]));
      } else if (paramInspectorNode instanceof InspectorParameter) {
        InspectorParameter inspectorParameter = (InspectorParameter)paramInspectorNode;
        getStyleClass().add("font-italic");
        if (inspectorParameter.h()) {
          setGraphic((Node)BootstrapIcons.a123.glyph(new String[] { "glyph-blue" }));
        } else if (inspectorParameter.k()) {
          setGraphic((Node)BootstrapIcons.check.glyph(new String[] { "glyph-blue" }));
        } else {
          setGraphic((Node)BootstrapIcons.text_left.glyph(new String[] { "glyph-blue" }));
        } 
      } 
    } 
  }
}
