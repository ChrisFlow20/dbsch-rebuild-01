package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.editors.csv.model.CsvColumn;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CsvEditorTableView extends TableView {
  private final CsvEditor a;
  
  private final ContextMenu$ b = new ContextMenu$();
  
  public CsvEditorTableView(CsvEditor paramCsvEditor) {
    this.a = paramCsvEditor;
    getSelectionModel().setCellSelectionEnabled(true);
    getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setEditable(true);
    setItems(paramCsvEditor.b.d);
    getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    getSelectionModel().setCellSelectionEnabled(true);
    VBox.setVgrow((Node)this, Priority.ALWAYS);
  }
  
  protected void a() {
    getColumns().add(new CsvEditorTableRowIdColumn(this.a));
    g();
    setOnContextMenuRequested(paramContextMenuEvent -> {
          this.b.setAutoHide(true);
          this.b.getItems().clear();
          this.b.getItems().addAll(this.a.a.e(new String[] { 
                  "clipboardCopy", "clipboardPaste", "separator", "renameColumn", "separator", "insertColumnLeft", "insertColumnRight", "separator", "insertRowBefore", "insertRowAfter", 
                  "separator" }));
          this.b.getItems().add(this.a.a.y("acceptCRLF"));
          this.b.getItems().addAll(this.a.a.e(new String[] { "separator", "filter", "dropFilter", "separator", "sortAsc", "sortDesc", "sortDrop", "separator", "deleteRow", "deleteColumn" }));
          this.b.show((Node)this, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
  }
  
  public TableColumn b() {
    Iterator<TablePosition> iterator = getSelectionModel().getSelectedCells().iterator();
    if (iterator.hasNext()) {
      TablePosition tablePosition = iterator.next();
      return tablePosition.getTableColumn();
    } 
    return null;
  }
  
  public CsvColumn c() {
    if (f() != null && f().getUserData() instanceof CsvColumn)
      return (CsvColumn)f().getUserData(); 
    return null;
  }
  
  public int d() {
    TableColumn tableColumn = f();
    if (tableColumn != null && tableColumn.getUserData() instanceof CsvColumn)
      for (byte b = 0; b < getColumns().size(); b++) {
        if (((TableColumn)getColumns().get(b)).getUserData() == tableColumn)
          return ((CsvColumn)tableColumn.getUserData()).a(); 
      }  
    return -1;
  }
  
  public int e() {
    if (b() != null && b().getUserData() instanceof CsvColumn)
      for (byte b = 0; b < getColumns().size(); b++) {
        if (((TableColumn)getColumns().get(b)).getUserData() == b().getUserData())
          return b; 
      }  
    return -1;
  }
  
  public TableColumn f() {
    if (b() != null && b().getUserData() instanceof CsvColumn)
      for (TableColumn tableColumn : getColumns()) {
        if (tableColumn.getUserData() == b().getUserData())
          return tableColumn; 
      }  
    return null;
  }
  
  public void g() {
    ArrayList arrayList = new ArrayList(this.a.b.b);
    ArrayList<TableColumn> arrayList1 = new ArrayList();
    for (TableColumn tableColumn : getColumns()) {
      if (tableColumn.getUserData() instanceof CsvColumn) {
        CsvColumn csvColumn = (CsvColumn)tableColumn.getUserData();
        if (!arrayList.contains(csvColumn) || csvColumn.b()) {
          arrayList1.add(tableColumn);
        } else {
          ((CsvEditorTableColumn)tableColumn).a();
          tableColumn.setText(csvColumn.getName());
        } 
        arrayList.remove(csvColumn);
      } 
    } 
    getColumns().removeAll(arrayList1);
    for (CsvColumn csvColumn : arrayList)
      getColumns().add(new CsvEditorTableColumn(this.a, csvColumn)); 
  }
  
  public TableColumn a(CsvColumn paramCsvColumn) {
    for (TableColumn tableColumn : getColumns()) {
      if (tableColumn.getUserData() == paramCsvColumn)
        return tableColumn; 
    } 
    return null;
  }
  
  public void a(int paramInt, CsvColumn paramCsvColumn) {
    for (TableColumn tableColumn : getColumns()) {
      if (tableColumn.getUserData() == paramCsvColumn)
        getSelectionModel().clearAndSelect(paramInt, tableColumn); 
    } 
  }
}
