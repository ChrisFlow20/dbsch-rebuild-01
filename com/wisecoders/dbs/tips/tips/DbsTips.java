package com.wisecoders.dbs.tips.tips;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class DbsTips extends ModuleTips {
  public DbsTips() {
    a(LayoutTips.class);
    a(SyncTips.class);
    a(BrowseTips.class);
    a(QueryTips.class);
    a(SQLTips.class);
    a(DataGeneratorTips.class);
  }
  
  public String getName() {
    return "DbSchema Tips";
  }
  
  public boolean cycleTips() {
    return true;
  }
}
