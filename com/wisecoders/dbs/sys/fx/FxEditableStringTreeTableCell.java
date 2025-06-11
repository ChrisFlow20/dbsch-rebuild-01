package com.wisecoders.dbs.sys.fx;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxEditableStringTreeTableCell extends TreeTableCell {
  private final boolean a;
  
  protected TextInputControl b;
  
  public FxEditableStringTreeTableCell() {
    this(false);
  }
  
  public FxEditableStringTreeTableCell(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public void startEdit() {
    super.startEdit();
    if (!isEditing() || isEmpty())
      return; 
    setText(null);
    c();
    setGraphic((Node)this.b);
    this.b.requestFocus();
  }
  
  public void cancelEdit() {
    if (this.b == null) {
      super.cancelEdit();
    } else {
      b();
    } 
    setText((String)getItem());
    setGraphic(null);
    this.b = null;
  }
  
  public void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    a();
    setText(null);
    setGraphic(null);
    if (!paramBoolean)
      setText(paramString); 
  }
  
  public void b() {
    if (this.b != null)
      commitEdit(this.b.getText()); 
  }
  
  public void a(Object paramObject, String paramString) {}
  
  public void a() {}
  
  protected void c() {
    if (this.a) {
      TextArea textArea = new TextArea(d());
      textArea.setPrefRowCount(3);
      this.b = (TextInputControl)textArea;
    } else {
      TextField textField = new TextField(d());
      textField.selectAll();
      textField.setOnAction(paramActionEvent -> b());
      this.b = (TextInputControl)textField;
    } 
    this.b.setMinWidth(getWidth() - getGraphicTextGap() * 2.0D);
    this.b.setOnKeyPressed(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ESCAPE)
            cancelEdit(); 
          if (!this.a)
            if (paramKeyEvent.getCode() == KeyCode.ENTER) {
              commitEdit(this.b.getText());
            } else if (paramKeyEvent.getCode() == KeyCode.TAB) {
              commitEdit(this.b.getText());
              TreeTableColumn treeTableColumn = a(!paramKeyEvent.isShiftDown());
              if (treeTableColumn != null)
                getTreeTableView().edit(getTableRow().getIndex(), treeTableColumn); 
            }  
        });
  }
  
  private TreeTableColumn a(boolean paramBoolean) {
    ArrayList<TreeTableColumn> arrayList = new ArrayList();
    for (TreeTableColumn treeTableColumn : getTreeTableView().getColumns())
      arrayList.addAll(a(treeTableColumn)); 
    if (arrayList.size() < 2)
      return null; 
    int i = arrayList.indexOf(getTableColumn());
    if (paramBoolean) {
      i++;
      if (i > arrayList.size() - 1)
        i = 0; 
    } else {
      i--;
      if (i < 0)
        i = arrayList.size() - 1; 
    } 
    return arrayList.get(i);
  }
  
  private List a(TreeTableColumn paramTreeTableColumn) {
    ArrayList<TreeTableColumn> arrayList = new ArrayList();
    if (paramTreeTableColumn.getColumns().isEmpty()) {
      if (paramTreeTableColumn.isEditable())
        arrayList.add(paramTreeTableColumn); 
      return arrayList;
    } 
    for (TreeTableColumn treeTableColumn : paramTreeTableColumn.getColumns())
      arrayList.addAll(a(treeTableColumn)); 
    return arrayList;
  }
  
  protected String d() {
    return (getItem() == null) ? "" : String.valueOf(getItem());
  }
}
