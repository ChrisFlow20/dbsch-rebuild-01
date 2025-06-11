package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FxExplainResultPane extends Tab {
  private final FxMessageCombo a = new FxExplainResultPane$1(this);
  
  private final TreeTableView b = new TreeTableView();
  
  private boolean c = false;
  
  private final TreeTableColumn d;
  
  private final TreeTableColumn e;
  
  FxExplainResultPane(FxAbstractSqlEditor paramFxAbstractSqlEditor, String paramString) {
    this.d = new TreeTableColumn("Operation");
    this.e = new TreeTableColumn("Duration");
    setText(paramString);
    a();
    HBox hBox = new HBox();
    hBox.getStyleClass().add("tool-bar");
    hBox.getChildren().addAll((Object[])new Node[] { (Node)this.a });
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)hBox);
    borderPane.setCenter((Node)this.b);
    setContent((Node)borderPane);
  }
  
  private void a() {
    this.d.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    this.e.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    this.e.setCellFactory(paramTreeTableColumn -> new a());
    this.b.getColumns().addAll((Object[])new TreeTableColumn[] { this.d, this.e });
    this.b.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
  }
  
  public void a(String paramString) {
    this.a.a(paramString);
  }
  
  public void a(String paramString, Throwable paramThrowable) {
    this.a.a(paramString, paramThrowable);
  }
  
  public void a(ExecutionPlan paramExecutionPlan) {
    paramExecutionPlan.a();
    this.b.setRoot(new b(paramExecutionPlan));
  }
  
  public void a(boolean paramBoolean) {
    this.c = paramBoolean;
  }
}
