package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;

class k extends TreeTableCell {
  private final CheckBox a;
  
  k() {
    this.a = new CheckBox();
    setEditable(getItem() instanceof Column);
    this.a.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (getItem() instanceof Column)
            ((Column)getItem()).setMandatory(paramBoolean2.booleanValue()); 
        });
  }
  
  protected void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    super.updateItem(paramAbstractUnit, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      this.a.setSelected(column.isMandatory());
      setGraphic((Node)this.a);
    } 
  }
}
