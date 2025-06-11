package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxQuickMenu;
import com.wisecoders.dbs.sys.fx.RowPane$;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxFilterPopup extends FxQuickMenu {
  private final Filterable a;
  
  private final Attribute f;
  
  private final FxFilterPanel g;
  
  private final String h;
  
  private final Rx i = new Rx(FxFilterPopup.class, this);
  
  public FxFilterPopup(Filterable paramFilterable, Attribute paramAttribute, String paramString) {
    this.a = paramFilterable;
    this.h = paramString;
    this.f = paramAttribute;
    setHideOnEscape(true);
    setAutoHide(true);
    setConsumeAutoHidingEvents(false);
    this.i.a("flagOrderAsc", () -> (paramFilterable.a(paramAttribute) == 1));
    this.i.a("flagOrderDesc", () -> (paramFilterable.a(paramAttribute) == -1));
    this

      
      .g = paramAttribute.getDataType().isDate() ? new FxDateFilterPanel(paramFilterable, paramAttribute, paramString) : (paramAttribute.getDataType().isBoolean() ? new FxBooleanFilterPanel(paramFilterable, paramAttribute, paramString) : (paramAttribute.getDataType().isNumeric() ? new FxNumericFilterPanel(paramFilterable, paramAttribute, paramString) : new FxTextFilterPanel(paramFilterable, paramAttribute, paramString)));
    RowPane$ rowPane$ = (new RowPane$()).g().a(Pos.CENTER).a((Node)new Label(paramAttribute.getName())).a(Pos.CENTER_LEFT).a((Node)this.g).a(new Node[] { (Node)new Label("Order"), (Node)this.i.n("orderAsc"), (Node)this.i.n("orderDesc") }).a(Pos.CENTER_RIGHT).a(new Node[] { (Node)this.i.j("ok"), (Node)this.i.j("delete"), (Node)this.i.j("cancel") });
    this.e.getChildren().add(rowPane$);
    addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ENTER) {
            ok();
            paramKeyEvent.consume();
          } 
        });
  }
  
  @Action
  public void ok() {
    a();
    hide();
  }
  
  protected void a() {
    this.g.b();
  }
  
  @Action
  public void delete() {
    this.a.b(this.f);
    this.a.b(this.f, this.h);
    hide();
  }
  
  @Action
  public void cancel() {
    hide();
  }
  
  @Action(d = "flagOrderAsc")
  public void orderAsc() {
    boolean bool = (this.a.a(this.f) == 1) ? true : false;
    if (bool) {
      this.a.b(this.f);
    } else {
      this.a.a(this.f, true);
    } 
    this.i.b();
  }
  
  @Action(d = "flagOrderDesc")
  public void orderDesc() {
    boolean bool = (this.a.a(this.f) == -1) ? true : false;
    if (bool) {
      this.a.b(this.f);
    } else {
      this.a.a(this.f, false);
    } 
    this.i.b();
  }
}
