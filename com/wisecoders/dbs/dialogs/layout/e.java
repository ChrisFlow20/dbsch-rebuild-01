package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

class e extends TreeItem {
  private final List b = new ArrayList();
  
  e(FxDependencyDialog paramFxDependencyDialog, DependencyNode paramDependencyNode) {
    super(paramDependencyNode);
    addEventHandler(TreeItem.branchExpandedEvent(), paramTreeModificationEvent -> {
          paramDependencyNode.a();
          b();
          a();
        });
  }
  
  private void b() {
    for (byte b = 0; b < ((DependencyNode)getValue()).f.size(); b++) {
      DependencyNode dependencyNode = ((DependencyNode)getValue()).f.get(b);
      if (b < this.b.size()) {
        e e1 = this.b.get(b);
        if (e1.getValue() != dependencyNode)
          this.b.add(b, new e(this.a, dependencyNode)); 
      } else {
        this.b.add(new e(this.a, dependencyNode));
      } 
    } 
    while (this.b.size() > ((DependencyNode)getValue()).f.size())
      this.b.remove(this.b.size() - 1); 
    a();
  }
  
  void a() {
    getChildren().retainAll(this.b);
    byte b = 0;
    for (e e1 : this.b) {
      if (a((DependencyNode)e1.getValue())) {
        if (!getChildren().contains(e1))
          getChildren().add(b, e1); 
        b++;
        e1.a();
        continue;
      } 
      getChildren().remove(e1);
    } 
  }
  
  private boolean a(DependencyNode paramDependencyNode) {
    if (b(paramDependencyNode))
      return true; 
    for (byte b = 0; b < paramDependencyNode.f.size(); b++) {
      DependencyNode dependencyNode = paramDependencyNode.f.get(b);
      if (a(dependencyNode))
        return true; 
    } 
    return false;
  }
  
  private boolean b(DependencyNode paramDependencyNode) {
    if (StringUtil.isFilledTrim(this.a.e.getText()) && !paramDependencyNode.b.getName().toLowerCase().contains(this.a.e.getText().toLowerCase()))
      return false; 
    if (this.a.d.isSelected()) {
      if (paramDependencyNode.e != null)
        return this.a.f.a(paramDependencyNode); 
      if (paramDependencyNode.d != null) {
        paramDependencyNode.a();
        boolean bool = false;
        for (DependencyNode dependencyNode : paramDependencyNode.f) {
          if (this.a.f.a(dependencyNode))
            bool = true; 
        } 
        if (bool)
          return true; 
        return bool;
      } 
    } 
    return true;
  }
  
  public boolean isLeaf() {
    return ((DependencyNode)getValue()).c();
  }
}
