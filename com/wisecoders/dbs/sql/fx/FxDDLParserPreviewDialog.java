package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.sql.parser.MatcherCommaPhrase;
import com.wisecoders.dbs.sql.parser.MatcherNode;
import com.wisecoders.dbs.sql.parser.MatcherStatement;
import com.wisecoders.dbs.sql.parser.PatternPhrase;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class FxDDLParserPreviewDialog extends Dialog$ {
  private final TreeTableView a = new TreeTableView();
  
  private final TextField b = new TextField();
  
  private final MatcherStatement c;
  
  public FxDDLParserPreviewDialog(FxSqlEditor paramFxSqlEditor) {
    super(paramFxSqlEditor.getWorkspace());
    String str = paramFxSqlEditor.i.t();
    this.c = new MatcherStatement(str, paramFxSqlEditor.d());
    this.a.setRoot(new a(this.c));
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Token");
    treeTableColumn1.setMaxWidth(5.36870912E10D);
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new FxDDLParserPreviewDialog$1(this));
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Type");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((MatcherNode)paramCellDataFeatures.getValue().getValue()).e()));
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2 });
    this.a.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    Rx.b(this.a, new double[] { 0.3D, 0.7D });
    this.b.setText("LOCATION ?");
  }
  
  public Node createContentPane() {
    this.a.setPrefSize(600.0D, 600.0D);
    GridPane$ gridPane$ = (new GridPane$()).l().b(new int[] { -1, -2, -2 }).a(new int[] { -2, -1 });
    gridPane$.a((Node)this.a, "0,0,1,0,f,f");
    gridPane$.a((Node)this.rx.e("patternField"), "0,1,r,c");
    gridPane$.a((Node)this.b, "1,1,f,c");
    gridPane$.a((Node)this.rx.j("findPattern"), "1,2,l,c");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public void findPattern() {
    StringBuilder stringBuilder = new StringBuilder();
    for (MatcherCommaPhrase matcherCommaPhrase : this.c) {
      PatternPhrase patternPhrase = matcherCommaPhrase.b(this.b.getText());
      if (patternPhrase != null)
        stringBuilder.append("Matches: ").append(patternPhrase).append("\n"); 
    } 
    this.rx.a(getDialogScene(), (stringBuilder.length() > 0) ? stringBuilder.toString() : "No Match", new String[0]);
  }
}
