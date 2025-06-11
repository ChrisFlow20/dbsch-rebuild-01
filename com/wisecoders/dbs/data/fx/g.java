package com.wisecoders.dbs.data.fx;

import javafx.scene.control.TableCell;

class g extends TableCell {
  g(RowNumberTableColumn paramRowNumberTableColumn) {}
  
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    if (getTableRow() != null)
      setText(paramBoolean ? "" : ("" + this.a.a.s() * this.a.a.u() + getTableRow().getIndex() + 1)); 
  }
}
