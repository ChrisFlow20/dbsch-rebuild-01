package com.wisecoders.dbs.sys.fx;

import java.util.List;

public class ListJoiner {
  private final List[] a;
  
  public ListJoiner(List... paramVarArgs) {
    this.a = (List[])paramVarArgs.clone();
  }
  
  public int a() {
    int i = 0;
    for (List list : this.a)
      i += list.size(); 
    return i;
  }
  
  public Object a(int paramInt) {
    byte b = 0;
    while (b < this.a.length && paramInt >= this.a[b].size()) {
      paramInt -= this.a[b].size();
      b++;
    } 
    if (paramInt < this.a[b].size())
      return this.a[b].get(paramInt); 
    return null;
  }
  
  public boolean b() {
    for (List list : this.a) {
      if (list.size() > 0)
        return false; 
    } 
    return true;
  }
}
