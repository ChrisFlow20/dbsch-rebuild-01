package com.wisecoders.dbs.editors.csv.fx;

import java.util.Map;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

class c extends TableCell {
  c(CsvEditorTableRowIdColumn paramCsvEditorTableRowIdColumn) {
    setOnDragDetected(paramMouseEvent -> {
          TableRow tableRow = getTableRow();
          if (tableRow != null && !tableRow.isEmpty()) {
            Integer integer = Integer.valueOf(tableRow.getIndex());
            Dragboard dragboard = tableRow.startDragAndDrop(new TransferMode[] { TransferMode.MOVE });
            dragboard.setDragView((Image)tableRow.snapshot(null, null));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(CsvEditorTableRowIdColumn.b, integer);
            dragboard.setContent((Map)clipboardContent);
            paramMouseEvent.consume();
          } 
        });
    setOnDragOver(paramDragEvent -> {
          Dragboard dragboard = paramDragEvent.getDragboard();
          if (dragboard.hasContent(CsvEditorTableRowIdColumn.b) && getTableRow().getIndex() != ((Integer)dragboard.getContent(CsvEditorTableRowIdColumn.b)).intValue()) {
            paramDragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            paramDragEvent.consume();
          } 
        });
    setOnDragDropped(paramDragEvent -> {
          Dragboard dragboard = paramDragEvent.getDragboard();
          if (dragboard.hasContent(CsvEditorTableRowIdColumn.b)) {
            int i = ((Integer)dragboard.getContent(CsvEditorTableRowIdColumn.b)).intValue();
            int j = getTableRow().isEmpty() ? getTableView().getItems().size() : getTableRow().getIndex();
            this.a.a.b.a(i, j);
            getTableView().refresh();
            paramDragEvent.setDropCompleted(true);
            getTableView().getSelectionModel().clearAndSelect(j);
            paramDragEvent.consume();
          } 
        });
  }
  
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setText(null);
    if (!paramBoolean && getTableRow() != null)
      setText(String.valueOf(this.a.a.b.d.get(getTableRow().getIndex()))); 
  }
}
