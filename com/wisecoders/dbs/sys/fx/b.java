package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.PopupWindow;
import org.controlsfx.control.PopOver;

class b extends PopOver {
  private final Rectangle b = new Rectangle();
  
  private final TextField c = new TextField();
  
  public b(FxColorPicker paramFxColorPicker) {
    setAutoHide(true);
    Rectangle rectangle = new Rectangle();
    rectangle.setX(7.0D);
    rectangle.setY(7.0D);
    rectangle.setWidth(200.0D);
    rectangle.setHeight(20.0D);
    rectangle.setFill((Paint)paramFxColorPicker.e());
    rectangle.setStroke((Paint)Color.GRAY);
    rectangle.setStrokeWidth(1.0D);
    EventHandler eventHandler = paramMouseEvent -> {
        double d = Math.min(200.0D, Math.max(0.0D, paramMouseEvent.getX() - paramRectangle.getX()));
        this.a.f.set(Color.hsb(FxColorPicker.a(d / 200.0D) * 360.0D, FxColorPicker.a(((Color)this.a.f.getValue()).getSaturation()), FxColorPicker.a(((Color)this.a.f.get()).getBrightness())));
        this.b.setX(d);
      };
    rectangle.setOnMouseDragged(eventHandler);
    rectangle.setOnMouseClicked(eventHandler);
    this.b.setX(0.0D);
    this.b.setY(0.0D);
    this.b.setWidth(10.0D);
    this.b.setHeight(30.0D);
    this.b.setArcWidth(4.0D);
    this.b.setArcHeight(4.0D);
    this.b.setStroke((Paint)Color.WHITE);
    this.b.setMouseTransparent(true);
    this.b.setFill(null);
    this.b.setEffect((Effect)new DropShadow(2.0D, 0.0D, 1.0D, Color.BLACK));
    a();
    Group group = new Group();
    group.getChildren().addAll((Object[])new Node[] { (Node)rectangle, (Node)this.b });
    Rx rx = new Rx(FxColorPicker.class, this);
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)rx.e("hue"), "0,0,r,c");
    gridPane$.a((Node)rx.e("inUse"), "0,1,r,c");
    gridPane$.a((Node)rx.e("hex"), "0,2,r,c");
    int i = 0;
    for (Color color : paramFxColorPicker.g) {
      Rectangle rectangle1 = new Rectangle();
      rectangle1.setWidth(20.0D);
      rectangle1.setHeight(20.0D);
      rectangle1.setFill((Paint)Color.hsb(color.getHue(), paramFxColorPicker.e.getSaturation(), paramFxColorPicker.e.getBrightness()));
      rectangle1.setStroke((Paint)Color.GRAY);
      rectangle1.setStrokeWidth(1.0D);
      rectangle1.setOnMouseClicked(paramMouseEvent -> {
            this.a.a(paramColor);
            a();
          });
      gridPane$.a((Node)rectangle1, "" + ++i + ",1,l,c");
    } 
    i = Math.max(i, 1);
    gridPane$.a((Node)group, "1,0," + i + ",0,f,c");
    gridPane$.a((Node)this.c, "1,2," + i + ",2,l,c");
    setContentNode((Node)gridPane$);
    setDetachable(false);
    setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    this.c.setText(paramFxColorPicker.d());
    this.c.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          try {
            if (paramString2 != null && paramString2.length() == 7) {
              this.a.f.setValue(Color.valueOf(paramString2.substring(1)));
              a();
            } 
          } catch (Exception exception) {}
        });
    paramFxColorPicker.f.addListener((paramObservableValue, paramColor1, paramColor2) -> {
          String str = FxColorPicker.b(paramColor2);
          if (!StringUtil.areEqual(str, this.c.getText())) {
            this.c.setText(str);
            this.a.setText(str);
            a();
          } 
        });
  }
  
  private void a() {
    this.b.setX(5.0D + 200.0D * this.a.b().getHue() / 360.0D);
  }
}
