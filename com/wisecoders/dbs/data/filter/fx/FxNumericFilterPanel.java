package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;

public class FxNumericFilterPanel extends FxFilterPanel {
  private final Filterable a;
  
  private final Attribute b;
  
  private final String c;
  
  private FilterPattern d;
  
  private final SplitMenuButton e = new SplitMenuButton();
  
  private final TextField f = new TextField();
  
  private final TextField g = new TextField();
  
  private final Label h = new Label("and");
  
  FxNumericFilterPanel(Filterable paramFilterable, Attribute paramAttribute, String paramString) {
    this.a = paramFilterable;
    this.b = paramAttribute;
    this.c = paramString;
    this.f.setPromptText("Type a number");
    this.g.setPromptText("Type a number");
    this.f.setPrefColumnCount(7);
    this.g.setPrefColumnCount(7);
    HBox$.setHgrow((Node)this.f, Priority.ALWAYS);
    HBox$.setHgrow((Node)this.g, Priority.ALWAYS);
    this.e.setFocusTraversable(false);
    Class clazz = (Class)(paramAttribute.getDataType().isNumeric() ? Number.class : String.class);
    List<FilterPattern> list = Filters.a(clazz);
    for (FilterPattern filterPattern : list) {
      if (this.c != null && this.d == null && filterPattern.b(this.c)) {
        a(filterPattern);
        List<String> list1 = filterPattern.c(this.c);
        if (list1.size() > 0)
          this.f.setText(list1.get(0)); 
        if (list1.size() > 1)
          this.g.setText(list1.get(1)); 
      } 
      MenuItem menuItem = new MenuItem(filterPattern.j);
      menuItem.setOnAction(paramActionEvent -> a(paramFilterPattern));
      this.e.getItems().add(menuItem);
    } 
    if (this.c == null && !list.isEmpty())
      a(list.get(0)); 
    Dialog$.setManagedVisible(new Node[] { (Node)this.f, (Node)this.h, (Node)this.g });
    getChildren().addAll((Object[])new Node[] { (Node)this.e, (Node)this.f, (Node)this.h, (Node)this.g });
  }
  
  private void a(FilterPattern paramFilterPattern) {
    this.d = paramFilterPattern;
    this.e.setText(paramFilterPattern.toString());
    this.f.setVisible((paramFilterPattern.o > 0));
    this.g.setVisible((paramFilterPattern.o > 1));
    this.h.setVisible((paramFilterPattern.o == 2));
    if (paramFilterPattern.o == 0) {
      this.f.setText(null);
      this.f.requestFocus();
    } else {
      this.g.setText(null);
      this.g.requestFocus();
    } 
  }
  
  public String a() {
    if (this.d.o == 0)
      return this.d.a((String[])null); 
    if (this.d.o == 1) {
      if (StringUtil.isEmptyTrim(this.f.getText()))
        return null; 
      return this.d.a(new String[] { this.f.getText() });
    } 
    if (StringUtil.isEmptyTrim(this.f.getText()) || StringUtil.isEmptyTrim(this.g.getText()))
      return null; 
    return this.d.a(new String[] { this.f.getText(), this.g.getText() });
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
