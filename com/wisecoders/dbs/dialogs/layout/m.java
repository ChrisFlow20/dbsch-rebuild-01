package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Table;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;

class m extends TreeTableCell {
  private Column a;
  
  private final CheckBox b;
  
  m() {
    this.b = new CheckBox();
    setEditable(getItem() instanceof Column);
    this.b.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> a(paramBoolean2.booleanValue()));
  }
  
  private void a(boolean paramBoolean) {
    if (this.a != null) {
      Index index = (this.a.table instanceof Table) ? ((Table)this.a.table).getPrimaryKey() : null;
      if (paramBoolean) {
        String str = (this.a.table.getSchema()).nameFinder.a(this.a.table, this.a, IndexType.PRIMARY_KEY);
        if (index == null) {
          index = ((Table)this.a.table).createIndex(str);
          index.setType(IndexType.PRIMARY_KEY);
        } 
        index.addColumn(this.a);
        this.a.setMandatory(true);
      } else if (index != null) {
        index.columns.remove(this.a);
      } 
    } 
  }
  
  protected void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    super.updateItem(paramAbstractUnit, paramBoolean);
    setText(null);
    setGraphic(null);
    this.a = null;
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      Index index = (column.table instanceof Table) ? ((Table)column.table).getPrimaryKey() : null;
      this.b.setSelected((index != null && index.columns.contains(column)));
      setGraphic((Node)this.b);
      this.a = column;
    } 
  }
}
