package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.data.model.data.ObjectId;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;

public class FxTextFilterPanel extends FxFilterPanel {
  private final Filterable a;
  
  private final Attribute b;
  
  private final String c;
  
  private FilterPattern d;
  
  private boolean e = true;
  
  private final SplitMenuButton f = new SplitMenuButton();
  
  private final TextField g = new TextField();
  
  FxTextFilterPanel(Filterable paramFilterable, Attribute paramAttribute, String paramString) {
    this.a = paramFilterable;
    this.b = paramAttribute;
    this.c = paramString;
    this.g.setPromptText("Type text here");
    HBox$.setHgrow((Node)this.g, Priority.ALWAYS);
    this.f.setFocusTraversable(false);
    List<FilterPattern> list = Filters.a(paramAttribute.getDataType().isNumeric() ? Number.class : (
        paramAttribute.getDataType().isMongoDbObjectId() ? ObjectId.class : String.class));
    for (FilterPattern filterPattern : list) {
      if (this.c != null && this.d == null && filterPattern.b(this.c)) {
        a(filterPattern);
        List<String> list1 = filterPattern.c(this.c);
        if (list1.size() > 0) {
          this.g.setText(list1.get(0));
          this.e = false;
        } 
      } 
      MenuItem menuItem = new MenuItem(filterPattern.j);
      menuItem.setOnAction(paramActionEvent -> a(paramFilterPattern));
      this.f.getItems().add(menuItem);
    } 
    if (this.c == null && !list.isEmpty())
      a(list.get(0)); 
    getChildren().addAll((Object[])new Node[] { (Node)this.f, (Node)this.g });
  }
  
  private void a(FilterPattern paramFilterPattern) {
    this.d = paramFilterPattern;
    this.f.setText(paramFilterPattern.toString());
    this.g.setVisible((paramFilterPattern.o > 0));
    this.g.setText(null);
    this.e = true;
    this.g.requestFocus();
  }
  
  public String a() {
    if (this.d.o == 0)
      return this.d.a((String[])null); 
    String str = this.g.getText();
    if (this.e)
      str = this.a.a(str); 
    return StringUtil.isFilledTrim(str) ? this.d.a(new String[] { str }) : null;
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
    return (Node)this.g;
  }
}
