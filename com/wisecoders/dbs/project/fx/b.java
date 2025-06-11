package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.FxDnd;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.generator.StatementType;
import com.wisecoders.dbs.sys.Rx;
import java.util.Map;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

class b extends FxTreeCell {
  b(FxProjectStructure paramFxProjectStructure) {
    setOnDragDetected(paramMouseEvent -> {
          Dragboard dragboard = startDragAndDrop(new TransferMode[] { paramMouseEvent.isControlDown() ? TransferMode.COPY : TransferMode.MOVE });
          dragboard.setDragView(Rx.c("table.png"));
          FxDnd.a(this.a.c());
          ClipboardContent clipboardContent = new ClipboardContent();
          clipboardContent.put(FxDnd.a, Integer.valueOf(1));
          clipboardContent.putString(Dbms.get(this.a.b.m().getDbId()).getScriptGenerator(this.a.c()).a(StatementType.d));
          dragboard.setContent((Map)clipboardContent);
          paramMouseEvent.consume();
        });
    setOnDragOver(paramDragEvent -> {
          if (paramDragEvent.getDragboard().hasContent(FxDnd.a) && FxDnd.b() && a() != null) {
            paramDragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            paramDragEvent.consume();
          } 
        });
    setOnDragDropped(paramDragEvent -> {
          Schema schema = a();
          if (schema != null)
            if (this.a.b.t()) {
              this.a.c.a(getScene(), "moveOnlyInOfflineMode", new String[0]);
            } else if (paramDragEvent.getDragboard().hasContent(FxDnd.a) && FxDnd.b() && this.a.c.a(getScene(), (paramDragEvent.getTransferMode() == TransferMode.COPY) ? "confirmCloneTables" : "confirmMoveTables")) {
              for (Unit unit : FxDnd.b) {
                if (unit instanceof Table) {
                  SyncUtil.a(this.a.b, schema, (Table)unit, (paramDragEvent.getTransferMode() == TransferMode.MOVE));
                  if (paramDragEvent.getTransferMode() == TransferMode.MOVE)
                    unit.markForDeletion(); 
                } 
              } 
              schema.project.refresh();
              this.a.b.y();
            }  
          paramDragEvent.consume();
        });
  }
  
  private Schema a() {
    TreeUnit treeUnit = (TreeUnit)getItem();
    if (treeUnit instanceof com.wisecoders.dbs.diagram.model.Folder)
      treeUnit = treeUnit.getParent(); 
    if (treeUnit instanceof Schema)
      return (Schema)treeUnit; 
    return null;
  }
}
