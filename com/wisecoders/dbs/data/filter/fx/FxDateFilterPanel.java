package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.DateTimePicker;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.Priority;

public class FxDateFilterPanel extends FxFilterPanel {
  private final Filterable a;
  
  private final Attribute b;
  
  private final String c;
  
  private final SplitMenuButton d = new SplitMenuButton();
  
  private final DateTimePicker e = new DateTimePicker();
  
  private final DateTimePicker f = new DateTimePicker();
  
  private final Label g = new Label("and");
  
  private FilterPattern h;
  
  FxDateFilterPanel(Filterable paramFilterable, Attribute paramAttribute, String paramString) {
    this.a = paramFilterable;
    this.b = paramAttribute;
    this.c = paramString;
    this.d.setFocusTraversable(false);
    List<FilterPattern> list = Filters.a(Date.class);
    for (FilterPattern filterPattern : list) {
      if (paramString != null && this.h == null && filterPattern.b(paramString)) {
        a(filterPattern);
        List<CharSequence> list1 = filterPattern.c(paramString);
        try {
          if (list1.size() > 0)
            this.e.a(LocalDateTime.parse(list1.get(0), FilterPattern.b)); 
          if (list1.size() > 1)
            this.f.a(LocalDateTime.parse(list1.get(1), FilterPattern.b)); 
        } catch (DateTimeParseException dateTimeParseException) {
          Log.a("Error in Date filter", dateTimeParseException);
        } 
      } 
      MenuItem menuItem = new MenuItem(filterPattern.j);
      menuItem.setOnAction(paramActionEvent -> a(paramFilterPattern));
      this.d.getItems().add(menuItem);
    } 
    if (paramString == null && !list.isEmpty())
      a(list.get(0)); 
    HBox$.setHgrow((Node)this.e, Priority.ALWAYS);
    HBox$.setHgrow((Node)this.f, Priority.ALWAYS);
    Dialog$.setManagedVisible(new Node[] { (Node)this.e, (Node)this.g, (Node)this.f });
    getChildren().addAll((Object[])new Node[] { (Node)this.d, (Node)this.e, (Node)this.g, (Node)this.f });
  }
  
  private void a(FilterPattern paramFilterPattern) {
    this.h = paramFilterPattern;
    this.d.setText(paramFilterPattern.toString());
    this.e.setVisible((paramFilterPattern.o > 0));
    this.f.setVisible((paramFilterPattern.o > 1));
    this.g.setVisible((paramFilterPattern.o == 2));
    if (paramFilterPattern.o == 0) {
      this.e.a((LocalDateTime)null);
      this.f.a((LocalDateTime)null);
      this.e.requestFocus();
    } else if (paramFilterPattern.o == 1) {
      this.f.a((LocalDateTime)null);
    } 
  }
  
  public String a() {
    if (this.h.o == 0)
      return this.h.a((String[])null); 
    if (this.h.o == 1) {
      LocalDateTime localDateTime = this.e.b();
      if (localDateTime == null)
        return null; 
      return this.h.a(new String[] { FilterPattern.b.format(localDateTime) });
    } 
    if (this.h.o == 2) {
      LocalDateTime localDateTime1 = this.e.b(), localDateTime2 = this.f.b();
      if (localDateTime1 == null || localDateTime2 == null)
        return null; 
      return this.h.a(new String[] { FilterPattern.b.format(localDateTime1), FilterPattern.b.format(localDateTime2) });
    } 
    return null;
  }
  
  public boolean b() {
    String str = a();
    boolean bool = false;
    if (!StringUtil.areEqual(this.c, str)) {
      bool = true;
      this.a.b(this.b, this.c);
      if (str != null)
        this.a.c(this.b, str); 
    } 
    return bool;
  }
  
  public Node c() {
    return (Node)this.e;
  }
}
