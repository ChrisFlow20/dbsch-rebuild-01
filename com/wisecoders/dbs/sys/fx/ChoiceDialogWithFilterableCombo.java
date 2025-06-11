package com.wisecoders.dbs.sys.fx;

import java.util.Collection;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ChoiceDialogWithFilterableCombo extends Dialog {
  private final GridPane b;
  
  private final Label c;
  
  public final ComboBox a;
  
  private final Object d;
  
  public ChoiceDialogWithFilterableCombo(Scene paramScene, Object paramObject, Collection paramCollection) {
    initOwner(paramScene.getWindow());
    DialogPane dialogPane = getDialogPane();
    this.b = new GridPane();
    this.b.setHgap(10.0D);
    this.b.setMaxWidth(Double.MAX_VALUE);
    this.b.setAlignment(Pos.CENTER_LEFT);
    this.c = a(dialogPane.getContentText());
    this.c.setPrefWidth(-1.0D);
    this.c.textProperty().bind((ObservableValue)dialogPane.contentTextProperty());
    dialogPane.contentTextProperty().addListener(paramObservable -> e());
    setTitle("Choice Dialog");
    dialogPane.getStyleClass().add("choice-dialog");
    dialogPane.getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    double d = 150.0D;
    this.a = new ComboBox();
    this.a.setMinWidth(150.0D);
    if (paramCollection != null)
      this.a.getItems().addAll(paramCollection); 
    this.a.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow((Node)this.a, Priority.ALWAYS);
    GridPane.setFillWidth((Node)this.a, Boolean.valueOf(true));
    this.d = this.a.getItems().contains(paramObject) ? paramObject : null;
    if (paramObject == null) {
      this.a.getSelectionModel().selectFirst();
    } else {
      this.a.getSelectionModel().select(paramObject);
    } 
    new AutoCompleteComboBox(this.a);
    e();
    setResultConverter(paramButtonType -> {
          ButtonBar.ButtonData buttonData = (paramButtonType == null) ? null : paramButtonType.getButtonData();
          return (buttonData == ButtonBar.ButtonData.OK_DONE) ? a() : null;
        });
  }
  
  public final Object a() {
    return this.a.getSelectionModel().getSelectedItem();
  }
  
  public final ReadOnlyObjectProperty b() {
    return this.a.getSelectionModel().selectedItemProperty();
  }
  
  public final void a(Object paramObject) {
    this.a.getSelectionModel().select(paramObject);
  }
  
  public final ObservableList c() {
    return this.a.getItems();
  }
  
  public final Object d() {
    return this.d;
  }
  
  private void e() {
    this.b.getChildren().clear();
    this.b.add((Node)this.c, 0, 0);
    this.b.add((Node)this.a, 1, 0);
    getDialogPane().setContent((Node)this.b);
    Objects.requireNonNull(this.a);
    Platform.runLater(this.a::requestFocus);
  }
  
  static Label a(String paramString) {
    Label label = new Label(paramString);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setMaxHeight(Double.MAX_VALUE);
    label.getStyleClass().add("content");
    label.setWrapText(true);
    label.setPrefWidth(360.0D);
    return label;
  }
}
