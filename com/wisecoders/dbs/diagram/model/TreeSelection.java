package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DoNotObfuscate
public class TreeSelection {
  private final Map a = new HashMap<>();
  
  public TreeSelection() {}
  
  public TreeSelection(List paramList) {
    for (TreeUnit treeUnit : paramList)
      select(treeUnit, true); 
  }
  
  public void select(TreeUnit paramTreeUnit) {
    this.a.put(paramTreeUnit, Boolean.valueOf(true));
  }
  
  public void select(TreeUnit paramTreeUnit, boolean paramBoolean) {
    this.a.put(paramTreeUnit, Boolean.valueOf(paramBoolean));
  }
  
  public boolean isSelected(TreeUnit paramTreeUnit) {
    if (hasSelectedChildren(paramTreeUnit))
      return true; 
    TreeUnit treeUnit = paramTreeUnit;
    while (true) {
      if (this.a.containsKey(treeUnit))
        return ((Boolean)this.a.get(treeUnit)).booleanValue(); 
      treeUnit = treeUnit.getParent();
      if (treeUnit == null)
        return false; 
    } 
  }
  
  public boolean hasSelectedChildren(TreeUnit paramTreeUnit) {
    for (TreeUnit treeUnit : this.a.keySet()) {
      if (((Boolean)this.a.get(treeUnit)).booleanValue() && SyncUtil.a(paramTreeUnit, treeUnit))
        return true; 
    } 
    return false;
  }
  
  public boolean allChildrenAreSelected(TreeUnit paramTreeUnit) {
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b);
      if (!isSelected(treeUnit))
        return false; 
    } 
    return true;
  }
  
  public int getSelectedChildrenCount(TreeUnit paramTreeUnit) {
    byte b1 = 0;
    for (byte b2 = 0; b2 < paramTreeUnit.getChildrenCount(); b2++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b2);
      if (isSelected(treeUnit))
        b1++; 
    } 
    return b1;
  }
  
  public boolean hasChildrenUnitsSelected(TreeUnit paramTreeUnit) {
    return (getSelectedChildrenCount(paramTreeUnit) > 0);
  }
  
  public boolean hasNoSelectedUnits() {
    for (TreeUnit treeUnit : this.a.keySet()) {
      if (((Boolean)this.a.get(treeUnit)).booleanValue())
        return false; 
    } 
    return true;
  }
}
