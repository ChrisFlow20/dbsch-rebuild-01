package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Window;

class r extends Dialog$ {
  private final ComboBox b = new ComboBox();
  
  public r(FxIndexEditor paramFxIndexEditor, Window paramWindow, ArrayList paramArrayList) {
    super(paramWindow);
    paramFxIndexEditor.rx.a("addColumnDialog", this);
    a(this.b, paramArrayList);
    new AutoCompleteComboBox(this.b);
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (Column)getResult() : null);
  }
  
  private void a(ComboBox paramComboBox, List paramList) {
    for (Column column : paramList) {
      paramComboBox.getItems().add(column);
      if (column.hasChildEntity())
        a(paramComboBox, (column.getChildEntity()).columns); 
    } 
  }
  
  public Node createContentPane() {
    return (Node)(new VBox$()).l().i().d((Node)this.b);
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    setResult(this.b.getValue());
    return true;
  }
}
