package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class l extends TreeTableCell {
  private final TextField b;
  
  protected ChangeListener a;
  
  l() {
    this.b = new TextField();
    this.a = ((paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue())
          c((AbstractUnit)getItem()); 
      });
  }
  
  protected void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    super.updateItem(paramAbstractUnit, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().removeAll((Object[])new String[] { "font-italic", "text-gray" });
    if (paramAbstractUnit != null) {
      setText(a(paramAbstractUnit));
      if (b(paramAbstractUnit))
        getStyleClass().addAll((Object[])new String[] { "font-italic", "text-gray" }); 
    } 
  }
  
  public void startEdit() {
    if (editableProperty().get() && 
      !isEmpty()) {
      super.startEdit();
      a();
      setText(null);
      setGraphic((Node)this.b);
      this.b.requestFocus();
    } 
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    a((AbstractUnit)getItem(), false);
  }
  
  protected void a() {
    this.b.setText(a((AbstractUnit)getItem()));
    this.b.selectAll();
    this.b.setOnAction(paramActionEvent -> {
          ((AbstractUnit)getItem()).setCommentTag("PhysicalName", this.b.getText());
          c((AbstractUnit)getItem());
        });
    this.b.setMinWidth(getWidth() - getGraphicTextGap() * 2.0D);
    this.b.focusedProperty().addListener(this.a);
    this.b.setOnKeyPressed(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ENTER) {
            ((AbstractUnit)getItem()).setCommentTag("PhysicalName", this.b.getText());
            c((AbstractUnit)getItem());
          } else if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            this.b.focusedProperty().removeListener(this.a);
            cancelEdit();
          } 
        });
  }
  
  protected String a(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit == null)
      return ""; 
    if (paramAbstractUnit.getCommentTag("PhysicalName") == null)
      return NamingDictionary.c.a(paramAbstractUnit.getName()); 
    return paramAbstractUnit.getCommentTag("PhysicalName");
  }
  
  protected boolean b(AbstractUnit paramAbstractUnit) {
    return (paramAbstractUnit != null && paramAbstractUnit.getCommentTag("PhysicalName") != null);
  }
  
  public void c(AbstractUnit paramAbstractUnit) {
    this.b.focusedProperty().removeListener(this.a);
    if (isEditing()) {
      super.commitEdit(paramAbstractUnit);
    } else {
      TreeTableView treeTableView = getTreeTableView();
      if (treeTableView != null) {
        TreeTablePosition treeTablePosition = new TreeTablePosition(getTreeTableView(), getTableRow().getIndex(), getTableColumn());
        TreeTableColumn.CellEditEvent cellEditEvent = new TreeTableColumn.CellEditEvent(treeTableView, treeTablePosition, TableColumn.editCommitEvent(), paramAbstractUnit);
        Event.fireEvent((EventTarget)getTableColumn(), (Event)cellEditEvent);
      } 
      a(paramAbstractUnit, false);
      if (treeTableView != null)
        treeTableView.edit(-1, null); 
    } 
  }
}
