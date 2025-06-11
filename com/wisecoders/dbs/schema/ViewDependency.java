package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.EntityIterable;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@DoNotObfuscate
public class ViewDependency {
  private final List a;
  
  public ViewDependency(EntityIterable paramEntityIterable) {
    this(a(paramEntityIterable));
  }
  
  public ViewDependency(List paramList) {
    boolean bool;
    this.a = new UniqueArrayList();
    do {
      bool = false;
      for (View view : paramList) {
        if (!this.a.contains(view)) {
          boolean bool1 = true;
          for (View view1 : paramList) {
            if (!this.a.contains(view1) && view1 != view && view.getScript() != null && Pattern.compile("\\b" + Pattern.quote(view1.getName()) + "\\b").matcher(view.getScript()).find()) {
              bool1 = false;
              break;
            } 
          } 
          if (bool1) {
            this.a.add(view);
            bool = true;
          } 
        } 
      } 
    } while (bool);
    for (View view : paramList) {
      if (!this.a.contains(view))
        this.a.add(view); 
    } 
  }
  
  private static List a(EntityIterable paramEntityIterable) {
    ArrayList<View> arrayList = new ArrayList();
    for (AbstractTable abstractTable : paramEntityIterable) {
      if (abstractTable instanceof View)
        arrayList.add((View)abstractTable); 
    } 
    return arrayList;
  }
  
  public List getViewsInCreationOrder() {
    return this.a;
  }
}
