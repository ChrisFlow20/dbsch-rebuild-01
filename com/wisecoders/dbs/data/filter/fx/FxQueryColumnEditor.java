package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class FxQueryColumnEditor extends ButtonDialog$ {
  private final AbstractQueryColumn a;
  
  private final TextField b = new TextField();
  
  public FxQueryColumnEditor(WorkspaceWindow paramWorkspaceWindow, AbstractQueryColumn paramAbstractQueryColumn) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramAbstractQueryColumn;
    this.b.setPrefColumnCount(20);
  }
  
  public Node createContentPane() {
    this.b.setText(this.a.b());
    HBox$ hBox$ = (new HBox$()).d().g();
    hBox$.getChildren().addAll((Object[])new Node[] { (Node)this.rx.e("alias"), (Node)this.b });
    setInitFocusedNode((Node)this.b);
    return (Node)hBox$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("query-builder.html");
  }
  
  public boolean apply() {
    this.a.a(this.b.getText());
    return true;
  }
}
