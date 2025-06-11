package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Rx;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxEditableStringTableCell extends TableCell {
  private final FxEditableStringTableCell$Type a;
  
  protected TextInputControl b;
  
  protected ChangeListener c;
  
  public FxEditableStringTableCell() {
    this(FxEditableStringTableCell$Type.a);
  }
  
  public FxEditableStringTableCell(FxEditableStringTableCell$Type paramFxEditableStringTableCell$Type) {
    this.c = ((paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue())
          a(this.b.getText()); 
      });
    this.a = paramFxEditableStringTableCell$Type;
  }
  
  public void startEdit() {
    if (editableProperty().get() && 
      !isEmpty()) {
      super.startEdit();
      b();
      setText(null);
      setGraphic((Node)this.b);
      this.b.requestFocus();
    } 
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    setText((String)getItem());
    setGraphic(null);
  }
  
  public void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    if (paramBoolean) {
      setText(null);
      setGraphic(null);
    } else if (isEditing()) {
      if (this.b != null) {
        this.b.setText(c());
        this.b.selectAll();
      } 
      setText(null);
      setGraphic((Node)this.b);
    } else {
      setText(c());
      setGraphic(null);
    } 
  }
  
  protected void b() {
    if (this.a == FxEditableStringTableCell$Type.b) {
      TextArea textArea = new TextArea(c());
      textArea.setPrefRowCount(3);
      this.b = (TextInputControl)textArea;
    } else {
      TextField textField = new TextField(c());
      textField.selectAll();
      if (this.a == FxEditableStringTableCell$Type.c)
        Rx.a(textField); 
      textField.setOnAction(paramActionEvent -> a(this.b.getText()));
      this.b = (TextInputControl)textField;
    } 
    this.b.setMinWidth(getWidth() - getGraphicTextGap() * 2.0D);
    this.b.focusedProperty().addListener(this.c);
    this.b.setOnKeyPressed(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ENTER && this.a != FxEditableStringTableCell$Type.b) {
            a(this.b.getText());
          } else if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            this.b.focusedProperty().removeListener(this.c);
            cancelEdit();
          } else if (paramKeyEvent.getCode() == KeyCode.TAB) {
            a(this.b.getText());
            TableColumn tableColumn = a(!paramKeyEvent.isShiftDown());
            if (tableColumn != null) {
              getTableView().edit(getTableRow().getIndex(), tableColumn);
            } else if (getTableRow().getIndex() == getTableView().getItems().size() - 1) {
              a();
            } 
          } 
        });
  }
  
  public void a() {}
  
  private TableColumn a(boolean paramBoolean) {
    ArrayList<TableColumn> arrayList = new ArrayList();
    for (TableColumn tableColumn : getTableView().getColumns())
      arrayList.addAll(a(tableColumn)); 
    int i = arrayList.indexOf(getTableColumn());
    if (paramBoolean) {
      i++;
    } else {
      i--;
    } 
    return (i > -1 && i < arrayList.size()) ? arrayList.get(i) : null;
  }
  
  private List a(TableColumn paramTableColumn) {
    ArrayList<TableColumn> arrayList = new ArrayList();
    if (paramTableColumn.getColumns().isEmpty()) {
      if (paramTableColumn.isEditable())
        arrayList.add(paramTableColumn); 
      return arrayList;
    } 
    for (TableColumn tableColumn : paramTableColumn.getColumns())
      arrayList.addAll(a(tableColumn)); 
    return arrayList;
  }
  
  protected String c() {
    return (getItem() == null) ? "" : String.valueOf(getItem());
  }
  
  public void a(String paramString) {
    this.b.focusedProperty().removeListener(this.c);
    if (isEditing()) {
      super.commitEdit(paramString);
    } else {
      TableView tableView = getTableView();
      if (tableView != null) {
        TablePosition tablePosition = new TablePosition(getTableView(), getTableRow().getIndex(), getTableColumn());
        TableColumn.CellEditEvent cellEditEvent = new TableColumn.CellEditEvent(tableView, tablePosition, TableColumn.editCommitEvent(), paramString);
        Event.fireEvent((EventTarget)getTableColumn(), (Event)cellEditEvent);
      } 
      a(paramString, false);
      if (tableView != null)
        tableView.edit(-1, null); 
    } 
  }
}
