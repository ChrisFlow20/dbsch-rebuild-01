package com.wisecoders.dbs.dialogs.admin.fx;

import com.wisecoders.dbs.dialogs.admin.tasks.PPGTask;
import com.wisecoders.dbs.project.store.Pref;
import java.util.HashMap;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;

class d extends Task {
  private d(FxPPGDialog paramFxPPGDialog) {}
  
  protected String a() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (String str1 : this.a.e.keySet()) {
      String str2 = ((TextField)this.a.e.get(str1)).getText();
      Pref.b(str1, str2);
      hashMap.put(str1, str2);
    } 
    return ((PPGTask)this.a.b.getValue()).a(hashMap, paramString -> {
          updateMessage(paramString);
          return null;
        });
  }
  
  protected void succeeded() {
    this.a.c.appendText("\n" + (String)getValue());
  }
  
  protected void failed() {
    this.a.c.appendText("\n" + getException().getMessage());
  }
}
