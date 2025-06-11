package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.schema.Column;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;

class o extends TreeTableCell {
  private final CheckBox b;
  
  o(FxGridEditor paramFxGridEditor) {
    this.b = new CheckBox();
    this.b.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> a(paramBoolean2.booleanValue()));
  }
  
  private void a(boolean paramBoolean) {
    Object object = getItem();
    if (object instanceof Column) {
      Column column = (Column)object;
      object = (this.a.D.getWorkspace().p() != null) ? (this.a.D.getWorkspace().p()).diagram.getDepictFor(column.table) : null;
      if (object != null)
        object.setAttributeVisible(column, paramBoolean); 
    } 
  }
  
  protected void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    super.updateItem(paramAbstractUnit, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      Depict depict = (this.a.D.getWorkspace().p() != null) ? (this.a.D.getWorkspace().p()).diagram.getDepictFor(column.table) : null;
      if (depict != null) {
        this.b.setDisable((column.hasMarker(1) || column.hasMarker(32) || column.hasMarker(64)));
        this.b.setSelected(!depict.getHiddenAttributes().contains(column));
        setGraphic((Node)this.b);
      } 
    } 
  }
}
