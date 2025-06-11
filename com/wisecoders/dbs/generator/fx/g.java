package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;

class g extends FxEditableStringTableCell {
  g() {
    setEditable(true);
  }
  
  public void a(String paramString, boolean paramBoolean) {
    super.a(paramString, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramString != null && !isEditing())
      if (getTableRow() != null && getTableRow().getItem() != null && ((Column)getTableRow().getItem()).isMandatory()) {
        setGraphic((Node)BootstrapIcons.asterisk.glyph(new String[] { "glyph-red" }));
      } else {
        setText(paramString + " %");
      }  
  }
}
