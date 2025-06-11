package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.explain.ExecutionPlanNode;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeTableCell;
import javafx.scene.shape.Rectangle;

class a extends TreeTableCell {
  private final Rectangle a;
  
  a() {
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    this.a = new Rectangle();
  }
  
  protected void a(ExecutionPlanNode paramExecutionPlanNode, boolean paramBoolean) {
    super.updateItem(paramExecutionPlanNode, paramBoolean);
    setGraphic(null);
    if (!paramBoolean && paramExecutionPlanNode != null) {
      this.a.xProperty().bind((ObservableValue)widthProperty().multiply(paramExecutionPlanNode.g() / 100));
      this.a.widthProperty().bind((ObservableValue)widthProperty().multiply(paramExecutionPlanNode.h() / 100));
      this.a.setHeight(18.0D);
      setGraphic((Node)this.a);
      this.a.setStyle("-fx-fill:green;");
    } 
  }
}
