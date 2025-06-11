package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public interface TreeUnit extends Unit {
  TreeUnit getChildAt(int paramInt);
  
  int getChildrenCount();
  
  String getName();
  
  TreeUnit getByName(String paramString);
  
  TreeUnit getParent();
  
  int getTickId();
  
  void setTouchId(int paramInt);
  
  int getTouchId();
  
  Object getUnitProperty(UnitProperty paramUnitProperty);
  
  static void touch(TreeUnit paramTreeUnit) {
    do {
      paramTreeUnit.setTouchId(paramTreeUnit.getTickId());
      paramTreeUnit = paramTreeUnit.getParent();
    } while (paramTreeUnit != null);
  }
}
