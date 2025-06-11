package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.StringUtil;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class IntTextField extends TextField {
  public IntTextField() {
    textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (!paramString2.matches("\\d*"))
            setText(paramString1); 
        });
  }
  
  public int a() {
    if (StringUtil.isEmpty(getText()))
      return -1; 
    return Integer.parseInt(getText());
  }
  
  public void a(int paramInt) {
    setText("" + paramInt);
  }
}
