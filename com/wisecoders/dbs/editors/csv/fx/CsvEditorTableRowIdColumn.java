package com.wisecoders.dbs.editors.csv.fx;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.DataFormat;

public class CsvEditorTableRowIdColumn extends TableColumn {
  private final CsvEditor a;
  
  public CsvEditorTableRowIdColumn(CsvEditor paramCsvEditor) {
    this.a = paramCsvEditor;
    setSortable(false);
    setCellFactory(paramTableColumn -> new c(this));
    getStyleClass().addAll((Object[])new String[] { "column-header", "text-gray" });
    setStyle("-fx-alignment: center-right;");
  }
  
  private static final DataFormat b = new DataFormat(new String[] { "application/javafx-tableview-row" });
}
