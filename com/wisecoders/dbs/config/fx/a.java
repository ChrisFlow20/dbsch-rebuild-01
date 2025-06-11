package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.Property;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.VBox$;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableCell;

class a extends TreeTableCell {
  private final VBox$ a = (new VBox$()).b(0).c(0);
  
  private final Label b = new Label();
  
  private final Label c = new Label();
  
  public a(boolean paramBoolean) {
    this.b.getStyleClass().add("font-large");
    this.a.getChildren().add(this.b);
    if (paramBoolean)
      this.a.getChildren().add(this.c); 
    this.c.getStyleClass().add("text-dark-gray");
  }
  
  protected void a(Property paramProperty, boolean paramBoolean) {
    super.updateItem(paramProperty, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().removeAll((Object[])new String[] { "font-large" });
    if (paramProperty instanceof com.wisecoders.dbs.config.model.FolderProperty) {
      setText(paramProperty.m());
      getStyleClass().addAll((Object[])new String[] { "font-large" });
    } else if (paramProperty != null) {
      setGraphic((Node)this.a);
      this.b.setText(paramProperty.m());
      Rx.a((Control)this.b, paramProperty.n());
      this.c.setText(paramProperty.n());
    } 
  }
}
