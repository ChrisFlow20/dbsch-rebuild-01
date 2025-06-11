package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.generator.engine.plan.Category;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

class e extends TreeItem {
  private final List b = new ArrayList();
  
  e(FxGeneratorRepositoryDialog paramFxGeneratorRepositoryDialog) {
    super(null);
    b();
    setExpanded(true);
    a();
  }
  
  e(FxGeneratorRepositoryDialog paramFxGeneratorRepositoryDialog, Object paramObject) {
    super(paramObject);
  }
  
  private void b() {
    byte b = 0;
    if (getValue() == null) {
      for (Category category : Domain.b()) {
        if (b < this.b.size()) {
          e e1 = this.b.get(b);
          if (e1.getValue() != category)
            this.b.add(b, new e(this.a, category)); 
        } else {
          this.b.add(b, new e(this.a, category));
        } 
        b++;
      } 
    } else if (getValue() instanceof Category) {
      for (DefinedPattern definedPattern : ((Category)getValue()).a()) {
        if (b < this.b.size()) {
          e e1 = this.b.get(b);
          if (e1.getValue() != definedPattern)
            this.b.add(b, new e(this.a, definedPattern)); 
        } else {
          this.b.add(b, new e(this.a, definedPattern));
        } 
        b++;
      } 
    } 
    while (this.b.size() > b)
      this.b.remove(b); 
    for (e e1 : this.b)
      e1.b(); 
    a();
  }
  
  void a() {
    getChildren().retainAll(this.b);
    byte b = 0;
    for (e e1 : this.b) {
      if (a(e1)) {
        if (!getChildren().contains(e1))
          getChildren().add(b, e1); 
        b++;
        e1.a();
        continue;
      } 
      getChildren().remove(e1);
    } 
  }
  
  private boolean a(e parame) {
    if (b(parame))
      return true; 
    for (byte b = 0; b < parame.getChildren().size(); b++) {
      e e1 = (e)parame.getChildren().get(b);
      if (a(e1))
        return true; 
    } 
    return false;
  }
  
  private boolean b(e parame) {
    return (!StringUtil.isFilledTrim(this.a.a.getText()) || parame.getValue().toString().toLowerCase().contains(this.a.a.getText().toLowerCase()));
  }
  
  public boolean isLeaf() {
    return getValue() instanceof DefinedPattern;
  }
}
