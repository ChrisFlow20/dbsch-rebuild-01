package com.wisecoders.dbs.generator.fx;

import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

class b extends TableCell {
  b(FxGeneratorEditor paramFxGeneratorEditor) {
    setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            this.a.edit(); 
        });
  }
  
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setText(null);
    if (!paramBoolean)
      setText(paramString); 
  }
}
