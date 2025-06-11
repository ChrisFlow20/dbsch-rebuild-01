package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Window;
import javafx.util.Pair;

class h extends Dialog$ {
  private final ComboBox b = new ComboBox();
  
  private final ComboBox c = new ComboBox();
  
  public h(FxForeignKeyEditor paramFxForeignKeyEditor, Window paramWindow, ArrayList paramArrayList1, ArrayList paramArrayList2) {
    super(paramWindow);
    paramFxForeignKeyEditor.rx.a("addColumnsDialog", this);
    a(this.b, paramArrayList1);
    a(this.c, paramArrayList2);
    new AutoCompleteComboBox(this.b);
    new AutoCompleteComboBox(this.c);
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (Pair)getResult() : null);
  }
  
  private void a(ComboBox paramComboBox, List paramList) {
    for (Column column : paramList) {
      paramComboBox.getItems().add(column);
      if (column.hasChildEntity())
        a(paramComboBox, (column.getChildEntity()).columns); 
    } 
  }
  
  public Node createContentPane() {
    VBox$ vBox$ = (new VBox$()).l().i();
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)(new FlowPane$())
          .a().a(new Node[] { (Node)new Label("Table " + this.a.a.getNameWithSchemaName() + " Column "), (Node)this.b }), (Node)new Label("REFERENCES"), (Node)(new FlowPane$())
          
          .a().a(new Node[] { (Node)new Label("Table " + this.a.d.getNameWithSchemaName() + " Column "), (Node)this.c }) });
    return (Node)vBox$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public Column a() {
    return (Column)this.b.getValue();
  }
  
  public Column b() {
    return (Column)this.c.getValue();
  }
  
  public boolean apply() {
    setResult(new Pair(this.b.getValue(), this.c.getValue()));
    return true;
  }
}
