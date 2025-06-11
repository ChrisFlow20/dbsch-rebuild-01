package com.wisecoders.dbs.dialogs.admin.fx;

import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;

class e extends Task {
  private e(FxSparkPostDialog paramFxSparkPostDialog) {}
  
  protected String a() {
    String str1 = this.a.e.getText();
    String str2 = FxSparkPostDialog.a(str1);
    String str3 = str1.replace(".html", ".txt");
    String str4 = FxSparkPostDialog.a(str3);
    List<String> list = FxSparkPostDialog.b((String)this.a.f.getValue());
    ArrayList<String> arrayList = new ArrayList();
    for (byte b = 0; b < list.size(); b++) {
      arrayList.add(list.get(b));
      if (b % 100 == 0 || b == list.size() - 1) {
        this.a.a(arrayList, str2, str4);
        updateMessage("Sent " + b + 1 + " emails...");
        arrayList.clear();
      } 
    } 
    return "Sent " + list.size() + " emails...";
  }
  
  protected void succeeded() {
    this.a.i.appendText("\n" + (String)getValue());
  }
  
  protected void failed() {
    this.a.i.appendText("\n" + getException().getMessage());
    this.a.rx.a(this.a.getDialogScene(), getException());
  }
}
