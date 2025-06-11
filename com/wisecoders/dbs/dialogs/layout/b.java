package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.sys.UniqueArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

class b extends TableCell {
  private final ComboBox b;
  
  b(FxCommentPanel paramFxCommentPanel) {
    this.b = new ComboBox();
    this.b.setPrefWidth(Double.MAX_VALUE);
    this.b.setEditable(true);
    this.b.setOnShowing(paramEvent -> {
          this.b.getItems().clear();
          UniqueArrayList uniqueArrayList = new UniqueArrayList();
          this.a.a(this.a.c.m(), uniqueArrayList);
          uniqueArrayList.addAll(this.a.b(this.a.b));
          this.b.getItems().addAll(uniqueArrayList);
        });
    this.b.setOnAction(paramActionEvent -> commitEdit(this.b.getValue()));
  }
  
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      setText(null);
      this.b.setValue(getTableRow().getItem());
      setGraphic((Node)this.b);
      Platform.runLater(() -> {
            this.b.getEditor().requestFocus();
            this.b.getEditor().selectAll();
          });
    } 
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    setText((String)getItem());
    setGraphic(null);
  }
  
  public void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setGraphic(null);
    getStyleClass().remove("text-gray");
    if (!paramBoolean) {
      setText(paramString);
      if (!this.a.l.containsKey(paramString))
        getStyleClass().add("text-gray"); 
    } 
  }
}
