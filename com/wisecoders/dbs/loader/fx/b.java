package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.schema.Column;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

class b extends ListCell {
  final Label a;
  
  private b() {
    this.a = new Label();
    this.a.getStyleClass().add("text-gray");
    setContentDisplay(ContentDisplay.RIGHT);
  }
  
  protected void a(Column paramColumn, boolean paramBoolean) {
    super.updateItem(paramColumn, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramColumn == null) {
      setText("Skip Loading");
    } else {
      setText(paramColumn.getName());
      this.a.setText(paramColumn.getTypeString());
      setGraphic((Node)this.a);
    } 
  }
}
