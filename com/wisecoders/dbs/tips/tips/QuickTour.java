package com.wisecoders.dbs.tips.tips;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class QuickTour extends Tips {
  private final boolean b;
  
  public QuickTour(QuickTour$Type paramQuickTour$Type, boolean paramBoolean) {
    this.b = paramBoolean;
    if (paramQuickTour$Type == QuickTour$Type.a) {
      a(MongoTips.class);
    } else {
      a(QuickTour.class);
    } 
  }
  
  public String getName() {
    return "Quick tour";
  }
  
  public String getDialogTitle() {
    return "Quick Tour";
  }
  
  public boolean cycleTips() {
    return false;
  }
  
  public boolean goToFirst() {
    return this.b;
  }
}
