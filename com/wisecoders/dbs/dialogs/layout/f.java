package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;

class f extends TreeTableCell {
  f(FxDependencyDialog paramFxDependencyDialog) {}
  
  protected void a(DependencyNode paramDependencyNode, boolean paramBoolean) {
    super.updateItem(paramDependencyNode, paramBoolean);
    setGraphic(null);
    setText(null);
    if (paramDependencyNode != null && !isEmpty()) {
      boolean bool = (getTableRow() != null && getTableRow().getTreeItem() != null && this.a.f.a((DependencyNode)getTableRow().getTreeItem().getValue())) ? true : false;
      setText(paramDependencyNode.b.getName());
      if (bool) {
        setGraphic((Node)BootstrapIcons.recycle.glyph(new String[] { "glyph-orange" }));
      } else if (paramDependencyNode.b instanceof AbstractUnit) {
        setGraphic((Node)((AbstractUnit)paramDependencyNode.b).getSymbolicGlyph().glyph(new String[0]));
      } 
    } 
  }
}
