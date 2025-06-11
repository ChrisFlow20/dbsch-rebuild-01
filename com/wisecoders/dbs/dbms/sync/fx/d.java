package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiff;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;
import javafx.scene.image.ImageView;

class d extends TreeTableCell {
  protected void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    super.updateItem(paramAbstractUnit, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramAbstractUnit != null) {
      setText(paramAbstractUnit.getName());
      SyncDiff syncDiff = (getTableRow() != null && getTableRow().getTreeItem() != null) ? (SyncDiff)getTableRow().getTreeItem().getValue() : null;
      if (paramAbstractUnit instanceof Schema) {
        setText(((Schema)paramAbstractUnit).getNameWithCatalog());
      } else if (paramAbstractUnit instanceof com.wisecoders.dbs.diagram.model.AbstractTable && syncDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.CommentDiff) {
        setGraphic((Node)BootstrapIcons.chat_left_text_fill.glyph(new String[] { "glyph-orange" }));
      } else if (paramAbstractUnit instanceof com.wisecoders.dbs.diagram.model.AbstractTable && syncDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff) {
        setGraphic((Node)BootstrapIcons.arrow_right_circle_fill.glyph(new String[] { "glyph-orange" }));
      } else if (paramAbstractUnit.getSymbolicIcon() != null) {
        setGraphic((Node)new ImageView(Rx.c(paramAbstractUnit.getSymbolicIcon())));
      } else if (paramAbstractUnit.getSymbolicGlyph() != null) {
        setGraphic((Node)paramAbstractUnit.getSymbolicGlyph().glyph(new String[0]));
      } 
    } 
  }
}
