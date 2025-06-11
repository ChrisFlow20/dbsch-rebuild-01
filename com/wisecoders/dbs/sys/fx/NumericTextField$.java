package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class NumericTextField$ extends TextField {
  public NumericTextField$() {
    this(false);
  }
  
  public NumericTextField$(boolean paramBoolean) {
    UnaryOperator unaryOperator = paramChange -> {
        String str = paramChange.getText();
        return str.matches(paramBoolean ? "[0-9\\.+-]*" : "[0-9]*") ? paramChange : null;
      };
    TextFormatter textFormatter = new TextFormatter(unaryOperator);
    setTextFormatter(textFormatter);
  }
  
  public void a(int paramInt) {
    setText("" + paramInt);
  }
  
  public int a() {
    String str = getText();
    return StringUtil.isEmptyTrim(str) ? 0 : Integer.parseInt(getText());
  }
}
