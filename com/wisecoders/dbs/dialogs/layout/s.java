package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.schema.Column;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;

class s extends TableCell {
  private s(FxIndexEditor paramFxIndexEditor) {}
  
  protected void a(Column paramColumn, boolean paramBoolean) {
    super.updateItem(paramColumn, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramColumn != null) {
      FxSyntaxOptionComboBox fxSyntaxOptionComboBox = new FxSyntaxOptionComboBox(this.a, this.a.i);
      fxSyntaxOptionComboBox.setValue(this.a.f.get(paramColumn));
      if (this.a.a)
        fxSyntaxOptionComboBox.d(); 
      fxSyntaxOptionComboBox.setVisible(true);
      fxSyntaxOptionComboBox.getEditor().textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.a.f.put(paramColumn, paramString2));
      setGraphic((Node)fxSyntaxOptionComboBox);
    } 
  }
}
