package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;

class f extends TreeTableCell {
  protected void a(Tuple paramTuple, boolean paramBoolean) {
    super.updateItem(paramTuple, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().remove("text-gray");
    if (paramTuple != null && !paramBoolean) {
      setText(paramTuple.b);
      if (paramTuple.c)
        getStyleClass().add("text-gray"); 
      if (paramTuple.a != null && paramTuple.a.a() != null && paramTuple.a.a().isMandatory())
        setGraphic((Node)Rx.q("mandatory_label.png")); 
    } 
  }
}
