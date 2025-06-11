package com.wisecoders.dbs.browse.fx;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;

class b extends ListCell {
  private final CheckBox a;
  
  private b() {
    this.a = new CheckBox();
    this.a.setMnemonicParsing(false);
  }
  
  protected void a(TableColumn paramTableColumn, boolean paramBoolean) {
    super.updateItem(paramTableColumn, paramBoolean);
    setGraphic(null);
    if (!paramBoolean) {
      this.a.setMnemonicParsing(false);
      this.a.setText(FxBrowseVisibleDialog.a(paramTableColumn));
      this.a.setSelected(paramTableColumn.isVisible());
      this.a.setOnAction(paramActionEvent -> FxBrowseVisibleDialog.a(paramTableColumn, this.a.isSelected()));
      setGraphic((Node)this.a);
    } 
  }
}
