package com.wisecoders.dbs.dialogs.system;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

class g extends TextFieldTableCell {
  private g(FxShortcutsDialog paramFxShortcutsDialog) {
    setEditable(true);
    setConverter((StringConverter)new DefaultStringConverter());
  }
  
  public void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setText(null);
    if (paramString != null && !paramBoolean) {
      setText(paramString);
      if (getTableRow() != null && getTableRow().getItem() != null && this.a.b.F(((f)getTableRow().getItem()).b))
        getStyleClass().add("edited-cell"); 
    } 
  }
}
