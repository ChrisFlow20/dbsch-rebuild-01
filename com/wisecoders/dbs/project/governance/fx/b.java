package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.project.governance.model.InspectorFolder;
import com.wisecoders.dbs.project.governance.model.InspectorFolder$Operation;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

class b extends ListCell {
  private b(FxChooseOperationDialog paramFxChooseOperationDialog) {}
  
  protected void a(InspectorFolder$Operation paramInspectorFolder$Operation, boolean paramBoolean) {
    super.updateItem(paramInspectorFolder$Operation, paramBoolean);
    setText(null);
    getStyleClass().removeAll((Object[])new String[] { "font-bold" });
    if (!paramBoolean && paramInspectorFolder$Operation != null) {
      setText(InspectorFolder.a(this.a.b.getWorkspace().m().getDbId(), paramInspectorFolder$Operation));
      setGraphic((Node)BootstrapIcons.folder_fill.glyph(new String[] { "glyph-orange" }));
      getStyleClass().add("font-bold");
    } 
  }
}
