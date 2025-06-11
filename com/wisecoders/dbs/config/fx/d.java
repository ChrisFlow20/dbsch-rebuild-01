package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.SyntaxOption;
import com.wisecoders.dbs.sys.fx.RowPane$;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

class d extends ListCell {
  private d(FxSyntaxOptionPane paramFxSyntaxOptionPane) {}
  
  protected void a(SyntaxOption paramSyntaxOption, boolean paramBoolean) {
    super.updateItem(paramSyntaxOption, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramSyntaxOption != null && !isEmpty()) {
      RowPane$ rowPane$ = (new RowPane$()).a(4).a(true);
      paramSyntaxOption.a(this.a.b, rowPane$, false, false);
      setGraphic((Node)rowPane$);
    } 
  }
}
