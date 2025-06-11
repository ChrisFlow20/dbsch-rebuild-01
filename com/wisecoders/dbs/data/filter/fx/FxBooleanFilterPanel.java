package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.diagram.model.Attribute;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class FxBooleanFilterPanel extends FxFilterPanel {
  private final Filterable a;
  
  private final Attribute b;
  
  private final String c;
  
  private FilterPattern d;
  
  private boolean e = true;
  
  private final SplitMenuButton f = new SplitMenuButton();
  
  public FxBooleanFilterPanel(Filterable paramFilterable, Attribute paramAttribute, String paramString) {
    this.a = paramFilterable;
    this.b = paramAttribute;
    this.c = paramString;
    this.f.setFocusTraversable(false);
    List<FilterPattern> list = Filters.a(Boolean.class);
    for (FilterPattern filterPattern : list) {
      if (this.c != null && this.d == null && filterPattern.b(this.c))
        a(filterPattern); 
      MenuItem menuItem = new MenuItem(filterPattern.j);
      menuItem.setOnAction(paramActionEvent -> a(paramFilterPattern));
      this.f.getItems().add(menuItem);
    } 
    if (this.c == null && !list.isEmpty())
      a(list.get(0)); 
    getChildren().add(this.f);
  }
  
  private void a(FilterPattern paramFilterPattern) {
    this.d = paramFilterPattern;
    this.f.setText(paramFilterPattern.toString());
    this.e = true;
  }
  
  public String a() {
    return this.d.a((String[])null);
  }
  
  public boolean b() {
    String str = a();
    boolean bool = false;
    if ((str != null && !str.equals(this.c)) || (this.c != null && !this.c.equals(str))) {
      bool = true;
      this.a.b(this.b, this.c);
      if (str != null)
        this.a.c(this.b, str); 
    } 
    return bool;
  }
  
  public Node c() {
    return (Node)this.f;
  }
}
