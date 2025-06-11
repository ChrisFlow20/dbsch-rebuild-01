package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.Entity;
import javafx.scene.control.ListCell;

class p extends ListCell {
  private p(FxGroupEditor paramFxGroupEditor) {}
  
  protected void a(Entity paramEntity, boolean paramBoolean) {
    super.updateItem(paramEntity, paramBoolean);
    setText(null);
    if (!paramBoolean && paramEntity != null)
      setText(this.a.k.isSelected() ? paramEntity.getNameWithSchemaName() : paramEntity.getName()); 
  }
}
