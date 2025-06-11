package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.UniqueArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class FxColorPicker extends Button {
  private static final double a = 200.0D;
  
  private static final double b = 20.0D;
  
  private static final double c = 5.0D;
  
  private static final double d = 2.0D;
  
  private final Color e = Color.web((Theme.a()).j ? "#34435c" : "0x82b2e3");
  
  private final ObjectProperty f = (ObjectProperty)new SimpleObjectProperty(Color.RED);
  
  public ObjectProperty a() {
    return this.f;
  }
  
  public Color b() {
    return (Color)this.f.get();
  }
  
  public void a(Color paramColor) {
    this.f.set(paramColor);
  }
  
  private final ObservableList g = FXCollections.observableArrayList();
  
  private b h;
  
  public FxColorPicker() {
    this(Color.RED);
  }
  
  public FxColorPicker(Color paramColor) {
    this.f.set(paramColor);
    setMinWidth(Double.NEGATIVE_INFINITY);
    setAlignment(Pos.BASELINE_LEFT);
    setText("Color");
    setOnAction(paramActionEvent -> {
          if (this.h == null)
            this.h = new b(this); 
          if (this.h.isShowing()) {
            this.h.hide();
            this.h = null;
          } else {
            this.h.show((Node)this);
          } 
        });
    Rectangle rectangle = new Rectangle();
    rectangle.setWidth(16.0D);
    rectangle.setHeight(16.0D);
    rectangle.setStroke((Paint)Color.LIGHTGRAY);
    rectangle.setStrokeWidth(1.0D);
    rectangle.fillProperty().bind((ObservableValue)new FxColorPicker$1(this));
    setGraphic((Node)rectangle);
  }
  
  public ObservableList c() {
    return this.g;
  }
  
  private static String b(Color paramColor) {
    int i = (int)(paramColor.getRed() * 255.0D);
    int j = (int)(paramColor.getGreen() * 255.0D);
    int k = (int)(paramColor.getBlue() * 255.0D);
    return ("#" + String.format("%02X", new Object[] { Integer.valueOf(i) }) + String.format("%02X", new Object[] { Integer.valueOf(j) }) + String.format("%02X", new Object[] { Integer.valueOf(k) })).toLowerCase();
  }
  
  public String d() {
    return b(b());
  }
  
  private static double a(double paramDouble) {
    return (paramDouble < 0.0D) ? 0.0D : ((paramDouble > 1.0D) ? 1.0D : paramDouble);
  }
  
  private LinearGradient e() {
    Stop[] arrayOfStop = new Stop[255];
    for (byte b1 = 0; b1 < 'ÿ'; b1++) {
      double d = 0.00392156862745098D * b1;
      int i = (int)(b1 / 255.0D * 360.0D);
      arrayOfStop[b1] = new Stop(d, Color.hsb(i, this.e.getSaturation(), this.e.getBrightness()));
    } 
    return new LinearGradient(0.0D, 1.0D, 1.0D, 0.0D, true, CycleMethod.NO_CYCLE, arrayOfStop);
  }
  
  public void a(Project paramProject) {
    if (paramProject != null) {
      UniqueArrayList uniqueArrayList = new UniqueArrayList();
      for (Layout layout : paramProject.layouts) {
        for (Depict depict : layout.diagram.depicts)
          uniqueArrayList.add(depict.getColor()); 
        for (Shape shape : layout.diagram.shapes)
          uniqueArrayList.add(shape.c()); 
      } 
      this.g.setAll(uniqueArrayList);
    } 
  }
}
