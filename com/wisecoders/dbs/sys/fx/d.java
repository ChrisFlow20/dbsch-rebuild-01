package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.ColorUtil;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

class d extends ListCell {
  private final Rectangle b;
  
  private d(FxHueColorChooser paramFxHueColorChooser) {
    this.b = new Rectangle();
    this.b.setWidth(16.0D);
    this.b.setHeight(16.0D);
    this.b.setStroke((Paint)Color.GRAY);
    this.b.setStrokeWidth(1.0D);
  }
  
  protected void a(Color paramColor, boolean paramBoolean) {
    super.updateItem(paramColor, paramBoolean);
    this.b.setFill((Paint)this.a.b);
    setGraphic((Node)this.b);
    setText("#" + ColorUtil.b(this.a.b));
  }
}
