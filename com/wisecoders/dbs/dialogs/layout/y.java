package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.Precision;
import javafx.scene.control.ListCell;

class y extends ListCell {
  protected void a(Precision paramPrecision, boolean paramBoolean) {
    super.updateItem(paramPrecision, paramBoolean);
    setText(null);
    if (!paramBoolean)
      if (paramPrecision == Precision.NONE) {
        setText("No parameters required");
      } else if (paramPrecision == Precision.LENGTH) {
        setText("Length or Precision - (x)");
      } else if (paramPrecision == Precision.DECIMAL) {
        setText("Decimal - (x,y)");
      } else if (paramPrecision == Precision.ENUMERATION) {
        setText("Enumeration - (a,b,c...)");
      }  
  }
}
