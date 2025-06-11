package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.loader.model.LoaderColumn;
import com.wisecoders.dbs.sys.fx.HBox$;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

class d extends TableCell {
  final Label a;
  
  final Label b;
  
  private final HBox c;
  
  private d() {
    this.a = new Label();
    this.b = new Label();
    this.c = (new HBox$()).a(5.0D);
    this.b.getStyleClass().add("text-gray");
    this.c.getChildren().addAll((Object[])new Node[] { (Node)this.a, (Node)this.b });
    this.a.setMinWidth(Double.NEGATIVE_INFINITY);
  }
  
  protected void a(LoaderColumn paramLoaderColumn, boolean paramBoolean) {
    super.updateItem(paramLoaderColumn, paramBoolean);
    setText(null);
    setGraphic((Node)this.c);
    if (paramLoaderColumn != null) {
      this.a.setText(paramLoaderColumn.a);
      this.b.setText((paramLoaderColumn.d() != null) ? paramLoaderColumn.d() : null);
    } 
  }
}
