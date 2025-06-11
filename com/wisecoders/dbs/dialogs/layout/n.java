package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;

class n extends TreeTableCell {
  private final CheckBox a;
  
  n() {
    this.a = new CheckBox();
    setEditable((getItem() != null));
    this.a.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> ((AbstractUnit)getItem()).setVirtual(paramBoolean2.booleanValue()));
  }
  
  protected void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    super.updateItem(paramAbstractUnit, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramAbstractUnit != null) {
      this.a.setSelected(paramAbstractUnit.isVirtual());
      setGraphic((Node)this.a);
    } 
  }
}
