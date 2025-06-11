package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class FxQueryEntityEditor extends ButtonDialog$ {
  private final QueryTable a;
  
  private final TextField b = new TextField();
  
  public FxQueryEntityEditor(WorkspaceWindow paramWorkspaceWindow, QueryTable paramQueryTable) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramQueryTable;
    this.b.setPrefColumnCount(20);
  }
  
  public Node createContentPane() {
    this.b.setText(this.a.d());
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
    this.a.b(this.b.getText());
    return true;
  }
}
