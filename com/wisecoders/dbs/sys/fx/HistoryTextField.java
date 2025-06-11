package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.project.store.Pref;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class HistoryTextField extends TextField {
  public HistoryTextField(String paramString) {
    this(paramString, (String)null);
  }
  
  public HistoryTextField(String paramString1, String paramString2) {
    String str = Pref.c(paramString1, paramString2);
    if (str != null)
      setText(str); 
    textProperty().addListener((paramObservableValue, paramString2, paramString3) -> Pref.a(paramString1, paramString3));
  }
}
