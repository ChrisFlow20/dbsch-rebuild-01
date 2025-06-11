package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.DataTypeUtil;
import javafx.scene.control.ListCell;

class x extends ListCell {
  protected void a(Integer paramInteger, boolean paramBoolean) {
    super.updateItem(paramInteger, paramBoolean);
    setText(null);
    if (!paramBoolean && paramInteger != null)
      setText(DataTypeUtil.getTypeName(paramInteger.intValue())); 
  }
}
