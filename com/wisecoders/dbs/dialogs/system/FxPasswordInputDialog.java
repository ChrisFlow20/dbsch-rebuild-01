package com.wisecoders.dbs.dialogs.system;

import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FxPasswordInputDialog extends Dialog {
  private final GridPane a;
  
  private final Label b;
  
  private final PasswordField c;
  
  private final String d;
  
  public FxPasswordInputDialog() {
    this("");
  }
  
  public FxPasswordInputDialog(@NamedArg("defaultValue") String paramString) {
    DialogPane dialogPane = getDialogPane();
    this.c = new PasswordField();
    this.c.setMaxWidth(Double.MAX_VALUE);
    this.c.setText(paramString);
    this.c.setPromptText("Password");
    GridPane.setHgrow((Node)this.c, Priority.ALWAYS);
    GridPane.setFillWidth((Node)this.c, Boolean.valueOf(true));
    this.b = a(dialogPane.getContentText());
    this.b.setPrefWidth(-1.0D);
    this.b.textProperty().bind((ObservableValue)dialogPane.contentTextProperty());
    this.d = paramString;
    this.a = new GridPane();
    this.a.setHgap(10.0D);
    this.a.setMaxWidth(Double.MAX_VALUE);
    this.a.setAlignment(Pos.CENTER_LEFT);
    dialogPane.contentTextProperty().addListener(paramObservable -> c());
    setTitle("Password Dialog");
    dialogPane.getStyleClass().add("text-input-dialog");
    dialogPane.getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    c();
    setResultConverter(paramButtonType -> {
          ButtonBar.ButtonData buttonData = (paramButtonType == null) ? null : paramButtonType.getButtonData();
          return (buttonData == ButtonBar.ButtonData.OK_DONE) ? this.c.getText() : null;
        });
  }
  
  public final TextField a() {
    return (TextField)this.c;
  }
  
  public final String b() {
    return this.d;
  }
  
  private void c() {
    this.a.getChildren().clear();
    this.a.add((Node)this.b, 0, 0);
    this.a.add((Node)this.c, 1, 0);
    getDialogPane().setContent((Node)this.a);
    Objects.requireNonNull(this.c);
    Platform.runLater(this.c::requestFocus);
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
