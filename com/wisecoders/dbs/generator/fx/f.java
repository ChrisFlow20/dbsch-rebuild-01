package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.schema.Column;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

class f extends TableCell {
  private final Label a;
  
  f() {
    this.a = new Label();
    setContentDisplay(ContentDisplay.RIGHT);
    this.a.getStyleClass().add("text-gray");
  }
  
  protected void a(Column paramColumn, boolean paramBoolean) {
    super.updateItem(paramColumn, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramColumn != null) {
      setText(paramColumn.getName());
      this.a.setText(paramColumn.getTypeString());
      setGraphic((Node)this.a);
    } 
  }
}
