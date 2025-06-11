package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.sys.StringUtil;
import javafx.scene.input.KeyCombination;

class f {
  private final String b;
  
  private final String c;
  
  f(FxShortcutsDialog paramFxShortcutsDialog, String paramString1, String paramString2) {
    this.b = paramString1;
    this.c = paramString2;
  }
  
  public String a() {
    return this.c;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilledTrim(paramString))
      try {
        KeyCombination.valueOf(paramString);
        this.a.b.a(this.b, paramString);
      } catch (IllegalArgumentException illegalArgumentException) {
        this.a.rx.b(this.a.getOwner().getScene(), illegalArgumentException.getLocalizedMessage(), illegalArgumentException);
      }  
  }
}
