package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.sys.fx.FilterableComboBox;
import javafx.beans.value.ObservableValue;

class c extends FilterableComboBox {
  public c(FxConnectorEditor paramFxConnectorEditor, JdbcUrlParam paramJdbcUrlParam) {
    setEditable(true);
    setMaxWidth(Double.MAX_VALUE);
    showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue())
            this.a.a(paramJdbcUrlParam, (String)null); 
        });
  }
}
