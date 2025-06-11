package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.diagram.model.Point;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class DraggablePane extends BorderPane {
  private final Point a = new Point();
  
  public DraggablePane(Node paramNode) {
    setCenter(paramNode);
    setOnMousePressed(paramMouseEvent -> {
          this.a.a = getScene().getWindow().getX() - paramMouseEvent.getScreenX();
          this.a.b = getScene().getWindow().getY() - paramMouseEvent.getScreenY();
          setCursor(Cursor.MOVE);
        });
    setOnMouseReleased(paramMouseEvent -> setCursor(Cursor.HAND));
    setOnMouseDragged(paramMouseEvent -> {
          getScene().getWindow().setX(paramMouseEvent.getScreenX() + this.a.a);
          getScene().getWindow().setY(paramMouseEvent.getScreenY() + this.a.b);
        });
    setOnMouseEntered(paramMouseEvent -> {
          if (!paramMouseEvent.isPrimaryButtonDown())
            setCursor(Cursor.HAND); 
        });
    setOnMouseExited(paramMouseEvent -> {
          if (!paramMouseEvent.isPrimaryButtonDown())
            setCursor(Cursor.DEFAULT); 
        });
  }
}
