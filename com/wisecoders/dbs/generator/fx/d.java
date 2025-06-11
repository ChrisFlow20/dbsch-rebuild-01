package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.ContextMenuEvent;

class d extends TreeTableCell {
  private d(FxGeneratorRepositoryDialog paramFxGeneratorRepositoryDialog) {
    setStyle("-fx-font-weight: bold");
    setOnContextMenuRequested(paramContextMenuEvent -> {
          if (this.a.j != null)
            this.a.j.hide(); 
          this.a.j = (new ContextMenu$()).a(this.a.rx, new String[] { "editPattern", "separator", "addPattern", "addCategory", "separator", "deletePattern" });
          this.a.j.show((Node)this.a.c, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
  }
  
  protected void updateItem(Object paramObject, boolean paramBoolean) {
    super.updateItem(paramObject, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramObject != null) {
      setText(String.valueOf(paramObject));
      if (paramObject instanceof com.wisecoders.dbs.generator.engine.plan.Category)
        setGraphic((Node)BootstrapIcons.file_fill.glyph(new String[] { "glyph-orange" })); 
    } 
  }
}
