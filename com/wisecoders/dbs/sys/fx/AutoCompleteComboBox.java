package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Rx;
import java.util.Collection;
import java.util.Objects;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteComboBox {
  private final ComboBox b;
  
  private ObservableList c;
  
  String a = "";
  
  public AutoCompleteComboBox(ComboBox paramComboBox) {
    this.b = paramComboBox;
    Tooltip tooltip = new Tooltip();
    tooltip.setGraphic((Node)Rx.q("zoom.png"));
    paramComboBox.setTooltip(tooltip);
    paramComboBox.setOnKeyPressed(this::a);
    paramComboBox.setOnHidden(this::a);
  }
  
  private void a(KeyEvent paramKeyEvent) {
    ObservableList observableList = FXCollections.observableArrayList();
    KeyCode keyCode = paramKeyEvent.getCode();
    if (keyCode.isLetterKey() || keyCode.isDigitKey() || keyCode == KeyCode.UNDERSCORE || keyCode == KeyCode.MINUS) {
      this.a += this.a;
      this.a = this.a.trim().toLowerCase();
    } 
    if (keyCode == KeyCode.BACK_SPACE && !this.a.isEmpty())
      this.a = this.a.substring(0, this.a.length() - 1); 
    if (keyCode == KeyCode.ESCAPE)
      this.a = ""; 
    Object object1 = this.b.getSelectionModel().getSelectedItem();
    Object object2 = object1;
    if (this.a.isEmpty()) {
      observableList = this.c;
      this.c = null;
      this.b.getTooltip().hide();
    } else {
      if (this.c == null)
        this.c = FXCollections.observableArrayList((Collection)this.b.getItems()); 
      Objects.requireNonNull(observableList);
      this.c.stream().filter(paramObject -> paramObject.toString().toLowerCase().contains(this.a)).forEach(observableList::add);
      if (!observableList.isEmpty())
        object2 = observableList.get(0); 
      this.b.getTooltip().setText(this.a);
      Point2D point2D = this.b.localToScene(0.0D, 0.0D);
      if (object1 != null && !observableList.contains(object1))
        observableList.add(0, object1); 
      this.b.getTooltip().show((Node)this.b, point2D
          .getX() + this.b.getScene().getX() + this.b.getScene().getWindow().getX() + 10.0D, point2D
          .getY() + this.b.getScene().getY() + this.b.getScene().getWindow().getY() - 25.0D);
      this.b.show();
    } 
    if (observableList != null) {
      this.b.getItems().setAll((Collection)observableList);
      this.b.getSelectionModel().select(object2);
    } 
  }
  
  private void a(Event paramEvent) {
    this.a = "";
    this.b.getTooltip().hide();
    if (this.c != null) {
      Object object = this.b.getSelectionModel().getSelectedItem();
      this.b.getItems().setAll((Collection)this.c);
      Platform.runLater(() -> this.b.getSelectionModel().select(paramObject));
    } 
  }
}
