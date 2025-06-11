package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.dbms.connect.model.SqlEvent;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

class a extends ListCell {
  a() {
    setStyle("-fx-padding: 2px 0 2px 0;");
    setOnDragDetected(paramMouseEvent -> {
          if (!isEmpty()) {
            Dragboard dragboard = startDragAndDrop(new TransferMode[] { TransferMode.COPY });
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(((SqlEvent)getItem()).b);
            dragboard.setContent((Map)clipboardContent);
          } 
        });
  }
  
  protected void a(SqlEvent paramSqlEvent, boolean paramBoolean) {
    super.updateItem(paramSqlEvent, paramBoolean);
    setText(null);
    setTooltip(null);
    setGraphic(null);
    if (!paramBoolean && paramSqlEvent != null) {
      setText(paramSqlEvent.toString());
      setTooltip(new Tooltip(paramSqlEvent.c()));
      switch (FxHistoryPane$1.a[paramSqlEvent.a().ordinal()]) {
        case 1:
          setGraphic((Node)BootstrapIcons.play_fill.glyph(new String[] { "glyph-green" }));
          break;
        case 2:
          setGraphic((Node)BootstrapIcons.exclamation_triangle.glyph(new String[] { "glyph-red" }));
          break;
        case 3:
          setGraphic((Node)BootstrapIcons.check.glyph(new String[] { "glyph-green" }));
          break;
      } 
    } 
  }
}
