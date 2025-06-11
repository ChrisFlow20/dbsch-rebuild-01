package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.Unit;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.DataFormat;

public class FxDnd {
  public static final DataFormat a = new DataFormat(new String[] { "com.dbschema.Unit" });
  
  public static List b = new ArrayList();
  
  public static void a(List paramList) {
    b.clear();
    b.addAll(paramList);
  }
  
  public static void a() {
    b.clear();
  }
  
  public static void a(Unit paramUnit) {
    if (paramUnit != null)
      b.add(paramUnit); 
  }
  
  public static boolean b() {
    for (Unit unit : b) {
      if (!(unit instanceof com.wisecoders.dbs.schema.Table))
        return false; 
    } 
    return !b.isEmpty();
  }
}
