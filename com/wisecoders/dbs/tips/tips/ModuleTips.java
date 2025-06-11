package com.wisecoders.dbs.tips.tips;

public abstract class ModuleTips extends Tips {
  public String getDialogTitle() {
    return "Tip of the Day";
  }
  
  public boolean cycleTips() {
    return false;
  }
  
  public boolean goToFirst() {
    return false;
  }
}
