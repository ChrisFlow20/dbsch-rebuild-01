package com.wisecoders.dbs.dbms.connect.model.envoy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordBuffer {
  private int a = 0;
  
  private final List b = new ArrayList();
  
  public void a(Map<?, ?> paramMap) {
    if (this.a < this.b.size()) {
      HashMap hashMap = this.b.get(this.a);
      hashMap.clear();
      hashMap.putAll(paramMap);
    } else {
      this.b.add(new HashMap<>(paramMap));
    } 
    this.a++;
  }
  
  public Map a(int paramInt) {
    return this.b.get(paramInt);
  }
  
  public void a() {
    for (byte b = 0; b < this.b.size(); b++) {
      Map map = a(b);
      map.clear();
    } 
    this.a = 0;
  }
  
  public int b() {
    return this.a;
  }
}
