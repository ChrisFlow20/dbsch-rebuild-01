package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.editors.csv.model.CsvColumn;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class CsvEditorTableColumn extends TableColumn {
  private final CsvEditor a;
  
  private final Label b = new Label(null);
  
  public CsvEditorTableColumn(CsvEditor paramCsvEditor, CsvColumn paramCsvColumn) {
    this.a = paramCsvEditor;
    setEditable(true);
    setSortable(false);
    setUserData(paramCsvColumn);
    BootstrapIcons.prepareAsGlyph(this.b);
    this.b.setStyle("-fx-text-fill: #6c7683");
    setGraphic((Node)this.b);
    setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(paramCsvEditor.b.b(paramCsvColumn, ((Integer)paramCellDataFeatures.getValue()).intValue())));
    setOnEditCommit(paramCellEditEvent -> paramCsvEditor.a(((Integer)paramCellEditEvent.getRowValue()).intValue(), paramCsvColumn, (String)paramCellEditEvent.getNewValue()));
    setText(paramCsvColumn.getName());
    setCellFactory(paramTableColumn -> new b(this));
    a();
  }
  
  public void a() {
    CsvColumn csvColumn = (CsvColumn)getUserData();
    StringBuilder stringBuilder = new StringBuilder();
    switch (this.a.b.a(csvColumn)) {
      case 1:
        stringBuilder.append(BootstrapIcons.sort_alpha_down.character);
        break;
      case -1:
        stringBuilder.append(BootstrapIcons.sort_alpha_up.character);
        break;
    } 
    if (this.a.b.c(csvColumn) != null)
      stringBuilder.append(BootstrapIcons.funnel_fill.character); 
    if (csvColumn.getDataType().isNumeric()) {
      stringBuilder.append(BootstrapIcons.a123.character);
    } else if (csvColumn.getDataType().isDate()) {
      stringBuilder.append(BootstrapIcons.clock.character);
    } else if (csvColumn.getDataType().isBoolean()) {
      stringBuilder.append(BootstrapIcons.toggle_off.character);
    } 
    this.b.setText(stringBuilder.toString());
  }
  
  public CsvColumn b() {
    return (CsvColumn)getUserData();
  }
}
