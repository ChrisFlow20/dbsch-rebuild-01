package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.sys.fx.HBox$;
import javafx.geometry.Pos;
import javafx.scene.Node;

public abstract class FxFilterPanel extends HBox$ {
  public FxFilterPanel() {
    d();
    a(Pos.CENTER_LEFT);
  }
  
  public abstract String a();
  
  public abstract Node c();
  
  public abstract boolean b();
}
