package com.wisecoders.dbs.dbms.scripts.fx;

import javafx.scene.control.ListCell;

class b extends ListCell {
  private b(FxExportSchemaAndDataDialog paramFxExportSchemaAndDataDialog) {}
  
  protected void a(a parama, boolean paramBoolean) {
    super.updateItem(parama, paramBoolean);
    setText(null);
    if (parama != null && !paramBoolean)
      setText(this.a.rx.H(String.valueOf(parama))); 
  }
}
