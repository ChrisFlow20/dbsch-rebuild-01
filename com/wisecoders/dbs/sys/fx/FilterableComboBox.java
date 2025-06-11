package com.wisecoders.dbs.sys.fx;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FilterableComboBox extends ComboBox {
  public final ObservableList b = FXCollections.observableArrayList();
  
  public FilterableComboBox() {
    FilteredList filteredList = new FilteredList(this.b, paramString -> true);
    getEditor().textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          TextField textField = getEditor();
          String str = (String)getSelectionModel().getSelectedItem();
          Platform.runLater(());
        });
    setItems((ObservableList)filteredList);
  }
}
