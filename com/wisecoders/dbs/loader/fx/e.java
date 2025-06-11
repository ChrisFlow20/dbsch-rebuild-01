package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.sys.StringUtil;
import javafx.scene.control.ListCell;

class e extends ListCell {
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setGraphic(null);
    setText(null);
    setStyle("");
    if (!paramBoolean)
      setText(StringUtil.cutOfMiddleWithDots(paramString, 60)); 
  }
}
