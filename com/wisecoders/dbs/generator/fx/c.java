package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.generator.engine.plan.Warn;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;

class c extends TableCell {
  protected void a(Warn paramWarn, boolean paramBoolean) {
    super.updateItem(paramWarn, paramBoolean);
    setText(null);
    if (!paramBoolean && paramWarn != null) {
      setText(paramWarn.toString());
      setTooltip(new Tooltip(paramWarn.toString()));
    } 
  }
}
