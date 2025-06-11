package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Rx;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class FxHueColorChooser extends ComboBox {
  private Color a = Color.RED;
  
  private Color b = this.a;
  
  private final Rx c = new Rx(FxHueColorChooser.class, this);
  
  public void a(Color paramColor) {
    this.a = paramColor;
    this.b = paramColor;
    setValue(this.a);
  }
  
  public FxHueColorChooser() {
    setButtonCell(new d(this));
    setCellFactory(paramListView -> new e(this));
    getItems().add(this.a);
    setValue(this.a);
    setOnHiding(paramEvent -> {
          setValue(this.b);
          Platform.runLater(());
        });
  }
}
