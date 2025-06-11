package com.wisecoders.dbs.dbms.connect.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SamplePaintApp extends Application {
  private static final String a = SamplePaintApp.class.getResource("resources/paint_dark.css").toExternalForm();
  
  private final StackPane b = new StackPane();
  
  private final Scene c = new Scene((Parent)this.b, 300.0D, 250.0D);
  
  public void start(Stage paramStage) {
    paramStage.setTitle("Sample Canvas");
    ToggleButton toggleButton = new ToggleButton("Switch Css");
    toggleButton.setOnAction(paramActionEvent -> a());
    Button button = new Button("Show Dialog");
    button.setOnAction(paramActionEvent -> b());
    TabPane tabPane = new TabPane();
    for (byte b = 0; b < 3; b++)
      tabPane.getTabs().add(new Tab("Tab_" + b, (Node)new SamplePane())); 
    VBox vBox = new VBox();
    VBox.setVgrow((Node)tabPane, Priority.ALWAYS);
    vBox.getChildren().addAll((Object[])new Node[] { (Node)toggleButton, (Node)button, (Node)tabPane });
    this.b.getChildren().add(vBox);
    paramStage.setScene(this.c);
    paramStage.sizeToScene();
    paramStage.show();
  }
  
  private void a() {
    if (this.c.getStylesheets().contains(a)) {
      this.c.getStylesheets().remove(a);
    } else {
      this.c.getStylesheets().add(a);
    } 
  }
  
  private void b() {
    Dialog dialog = new Dialog();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(this.c.getWindow());
    dialog.setResizable(true);
    dialog.setTitle("UID Setup");
    dialog.getDialogPane().getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    dialog.showAndWait();
    Node node = dialog.getDialogPane().lookupButton(ButtonType.OK);
  }
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
}
