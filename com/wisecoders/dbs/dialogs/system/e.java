package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.model.TreeUnit;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

class e extends TreeItem {
  private String b;
  
  private final List c = new ArrayList();
  
  e(FxFindDialog paramFxFindDialog, TreeUnit paramTreeUnit) {
    super(paramTreeUnit);
    addEventHandler(TreeItem.branchExpandedEvent(), paramTreeModificationEvent -> a());
    if (paramTreeUnit instanceof com.wisecoders.dbs.schema.Schema)
      setExpanded(true); 
  }
  
  private void a() {
    if (this.c.isEmpty())
      for (byte b = 0; b < ((TreeUnit)getValue()).getChildrenCount(); b++) {
        TreeUnit treeUnit = ((TreeUnit)getValue()).getChildAt(b);
        e e1 = new e(this.a, treeUnit);
        this.c.add(e1);
      }  
    b();
  }
  
  private void b() {
    byte b = 0;
    for (e e1 : this.c) {
      if (this.a.a((TreeUnit)e1.getValue(), this.a.f.getText())) {
        e1.b = this.a.b((TreeUnit)e1.getValue(), this.a.f.getText());
        if (!getChildren().contains(e1))
          getChildren().add(b++, e1); 
      } else {
        getChildren().remove(e1);
      } 
      e1.b();
    } 
  }
  
  public boolean isLeaf() {
    return (((TreeUnit)getValue()).getChildrenCount() == 0);
  }
}
