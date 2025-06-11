package com.wisecoders.dbs.sys.fx;

import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class TooltipTableCell extends TableCell {
  public TooltipTableCell() {
    setOnMouseEntered(paramMouseEvent -> {
          Text text = new Text(getText());
          text.setFont(getFont());
          double d = text.getLayoutBounds().getWidth() + 10.0D;
          if (d > getWidth()) {
            setManaged(false);
            setPrefWidth(d);
          } 
        });
    setOnMouseExited(paramMouseEvent -> a());
  }
  
  public void a() {
    if (!isManaged()) {
      setManaged(true);
      setPrefWidth(-1.0D);
    } 
  }
}
