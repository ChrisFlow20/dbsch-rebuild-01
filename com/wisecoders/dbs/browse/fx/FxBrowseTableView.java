package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.browse.model.BrowseDetailResult;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.browse.model.Cascade;
import com.wisecoders.dbs.browse.store.FxBrowseManager;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.data.filter.fx.FxFilterPopup;
import com.wisecoders.dbs.data.fx.FxUpdatableTableView;
import com.wisecoders.dbs.data.fx.RowNumberTableColumn;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.stage.WindowEvent;

public abstract class FxBrowseTableView extends TableView implements Filterable, FxUpdatableTableView {
  public final FxBrowseManager c;
  
  public final BrowseTable d;
  
  FxBrowseTableViewColumn e;
  
  FxFilterPopup f;
  
  FxBrowseTableView(FxBrowseManager paramFxBrowseManager, Entity paramEntity) {
    this(paramFxBrowseManager, new BrowseTable(paramEntity));
    paramFxBrowseManager.a(this, this.d.e, Cascade.b);
    paramFxBrowseManager.c();
  }
  
  FxBrowseTableView(FxBrowseManager paramFxBrowseManager, BrowseTable paramBrowseTable) {
    this.d = paramBrowseTable;
    this.c = paramFxBrowseManager;
    setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    paramBrowseTable.e.f.addListener(paramChange -> FxUtil.b(this::l));
    setItems(paramBrowseTable.e.g);
    getSelectionModel().setCellSelectionEnabled(true);
    getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramResultRow1, paramResultRow2) -> a((FxBrowseTableViewColumn)null));
    m();
    setEditable(true);
    setStyle("-fx-background-insets: 0;");
    paramBrowseTable.e.a(paramResultStatus -> {
          if (paramResultStatus == ResultStatus.d)
            try {
              a(true);
              g();
            } catch (Throwable throwable) {
              Log.b(throwable);
            }  
          return null;
        });
  }
  
  protected void a(FxBrowseTableViewColumn paramFxBrowseTableViewColumn) {
    this.e = paramFxBrowseTableViewColumn;
  }
  
  public TableColumn b() {
    if (this.e != null)
      return this.e; 
    for (TablePosition tablePosition : getSelectionModel().getSelectedCells()) {
      TableColumn tableColumn = tablePosition.getTableColumn();
      if (!(tableColumn instanceof RowNumberTableColumn))
        return tableColumn; 
    } 
    return null;
  }
  
  public FxBrowseTableViewColumn c() {
    return (b() instanceof FxBrowseTableViewColumn) ? (FxBrowseTableViewColumn)b() : null;
  }
  
  private void l() {
    setPlaceholder((Node)new Label("No data"));
    getColumns().clear();
    this.e = null;
    if (Sys.B.showRowNumber.b())
      getColumns().add(new RowNumberTableColumn(this.d.e)); 
    for (byte b = 0; b < this.d.e.C(); b++) {
      ResultColumn resultColumn = this.d.e.d(b);
      resultColumn.a(this.d.e.a(b));
      FxBrowseTableViewColumn fxBrowseTableViewColumn = new FxBrowseTableViewColumn(this, resultColumn);
      getColumns().add(fxBrowseTableViewColumn);
    } 
    a();
  }
  
  public void a(boolean paramBoolean) {
    for (TableColumn tableColumn : getColumns()) {
      if (tableColumn instanceof FxBrowseTableViewColumn)
        ((FxBrowseTableViewColumn)tableColumn).a(paramBoolean); 
    } 
  }
  
  void d() {
    TableColumn tableColumn = b();
    if (tableColumn instanceof FxBrowseTableViewColumn) {
      FxBrowseTableViewColumn fxBrowseTableViewColumn = (FxBrowseTableViewColumn)tableColumn;
      b(fxBrowseTableViewColumn);
    } 
  }
  
  public void b(FxBrowseTableViewColumn paramFxBrowseTableViewColumn) {
    Attribute attribute = paramFxBrowseTableViewColumn.a.a();
    if (attribute != null) {
      e();
      this.f = new FxFilterPopup(this, attribute, this.d.b(attribute));
      this.f.setOnHiding(paramWindowEvent -> {
            Objects.requireNonNull(paramFxBrowseTableViewColumn);
            Platform.runLater(paramFxBrowseTableViewColumn::a);
          });
      if (paramFxBrowseTableViewColumn.getGraphic() == null) {
        Bounds bounds = localToScreen(getBoundsInLocal());
        this.f.show((Node)this, bounds.getMinX() + bounds.getWidth() / 2.0D, bounds.getMinY());
      } else {
        Bounds bounds = paramFxBrowseTableViewColumn.getGraphic().localToScreen(paramFxBrowseTableViewColumn.getGraphic().getBoundsInLocal());
        this.f.show((Node)this, bounds.getMinX() + bounds.getWidth() / 2.0D, bounds.getMinY());
      } 
    } 
  }
  
  public void e() {
    if (this.f != null)
      this.f.hide(); 
  }
  
  void f() {
    if (b() instanceof FxBrowseTableViewColumn)
      this.d.g(((FxBrowseTableViewColumn)b()).a.a()); 
  }
  
  void a(Cascade paramCascade) {
    n();
    this.c.a(this, this.d.e, paramCascade);
    for (BrowseDetailResult browseDetailResult : this.d.f)
      this.c.a(this, browseDetailResult, Cascade.b); 
  }
  
  private void m() {
    Button button = new Button();
    button.setGraphic((Node)BootstrapIcons.arrow_repeat.glyph(new String[] { "glyph-blue", "glyph-18" }));
    button.setOnAction(paramActionEvent -> a(Cascade.b));
    setPlaceholder((Node)button);
  }
  
  private void n() {
    setPlaceholder((Node)new Label("Loading..."));
  }
  
  public void g() {
    if (getSelectionModel().getSelectedItems().isEmpty() && !getItems().isEmpty()) {
      getSelectionModel().clearSelection();
      getSelectionModel().select(0);
    } 
  }
  
  public String a(Attribute paramAttribute, String paramString) {
    this.d.a(paramAttribute, paramString);
    this.d.e.r();
    a(Cascade.b);
    return paramString;
  }
  
  public void b(Attribute paramAttribute, String paramString) {
    this.d.g(paramAttribute);
    a(Cascade.b);
  }
  
  public void a(Attribute paramAttribute, boolean paramBoolean) {
    this.d.a(paramAttribute, paramBoolean);
    a(Cascade.b);
  }
  
  public int a(Attribute paramAttribute) {
    return this.d.e(paramAttribute);
  }
  
  public void b(Attribute paramAttribute) {
    this.d.f(paramAttribute);
    a(Cascade.b);
  }
  
  public String a(String paramString) {
    return Dbms.get((this.d.getEntity().getSchema()).project.getDbId()).escapeString(paramString);
  }
  
  void h() {
    this.d.e.h(1);
    a(Cascade.b);
  }
  
  void i() {
    a(1);
  }
  
  void j() {
    a(-1);
  }
  
  void k() {
    this.d.e.h(-1);
    a(Cascade.b);
  }
  
  void a(int paramInt) {
    int i = Math.max(0, Math.min(getSelectionModel().getSelectedIndex() + paramInt, getItems().size() - 1));
    getSelectionModel().clearAndSelect(i);
    scrollTo(i);
  }
  
  public void a(Attribute paramAttribute, String paramString, boolean paramBoolean) {}
  
  public abstract void a();
}
