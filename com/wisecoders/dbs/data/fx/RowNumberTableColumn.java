package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.result.Result;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class RowNumberTableColumn extends TableColumn {
  private final Result a;
  
  public RowNumberTableColumn(Result paramResult) {
    super("#");
    this.a = paramResult;
    getStyleClass().addAll((Object[])new String[] { "column-header", "text-gray" });
    setStyle("-fx-alignment: center;-fx-font-weight:null;-fx-padding:0;");
    setCellFactory(paramTableColumn -> new g(this));
    setPrefWidth(40.0D);
  }
}
