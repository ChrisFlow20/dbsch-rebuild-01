package com.wisecoders.dbs.dialogs.web.fx;

import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

class b extends ListCell {
  private final GridPane$ a;
  
  private final Label b;
  
  private b() {
    this.a = new GridPane$();
    this.b = new Label();
    this.a.setHgap(10.0D);
    this.a.setVgap(3.0D);
    this.b.getStyleClass().addAll((Object[])new String[] { "font-large" });
    this.a.a((Node)this.b, "1,0,l,c");
  }
  
  protected void a(c paramc, boolean paramBoolean) {
    super.updateItem(paramc, paramBoolean);
    setGraphic(null);
    setText(null);
    if (!paramBoolean && paramc != null) {
      this.b.setText(paramc.b);
      setGraphic((Node)this.a);
    } 
  }
}
