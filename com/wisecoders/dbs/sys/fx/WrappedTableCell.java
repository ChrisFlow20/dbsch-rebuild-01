package com.wisecoders.dbs.sys.fx;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

public class WrappedTableCell extends TableCell {
  public WrappedTableCell() {
    Text text = new Text();
    setGraphic((Node)text);
    setPrefHeight(-1.0D);
    text.wrappingWidthProperty().bind((ObservableValue)widthProperty());
    text.textProperty().bind((ObservableValue)itemProperty());
  }
}
