package com.wisecoders.dbs.sys.fx;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

class e extends ListCell {
  private final Slider b;
  
  private final GridPane$ c;
  
  private final Rectangle d;
  
  private e(FxHueColorChooser paramFxHueColorChooser) {
    this.b = new Slider(0.0D, 360.0D, this.a.a.getHue());
    this.c = (new GridPane$()).l();
    this.d = new Rectangle(60.0D, 20.0D, (Paint)this.a.a);
    this.c.a((Node)this.a.c.e("sample"), "0,0,r,c");
    this.c.a((Node)this.d, "1,0,f,c");
    this.c.a((Node)this.a.c.e("hue"), "0,1,r,c");
    this.c.a((Node)this.b, "1,1,f,c");
    this.b.valueProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          this.a.b = Color.hsb(paramNumber2.doubleValue(), this.a.a.getSaturation(), this.a.a.getBrightness());
          this.d.setFill((Paint)this.a.b);
        });
  }
  
  protected void a(Color paramColor, boolean paramBoolean) {
    super.updateItem(paramColor, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramColor != null)
      setGraphic((Node)this.c); 
  }
}
