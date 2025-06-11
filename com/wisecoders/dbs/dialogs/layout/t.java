package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.LineTextType;
import javafx.scene.control.ListCell;

class t extends ListCell {
  t(FxLayoutEditor paramFxLayoutEditor) {}
  
  protected void a(LineTextType paramLineTextType, boolean paramBoolean) {
    super.updateItem(paramLineTextType, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramLineTextType != null && !paramBoolean) {
      String str = this.a.rx.H("lineType." + String.valueOf(paramLineTextType));
      setText((str != null) ? str : paramLineTextType.e);
    } 
  }
}
