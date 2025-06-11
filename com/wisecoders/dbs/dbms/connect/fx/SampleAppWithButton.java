package com.wisecoders.dbs.dbms.connect.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SampleAppWithButton extends Application {
  private final StackPane a = new StackPane();
  
  private final Scene b = new Scene((Parent)this.a, 300.0D, 250.0D);
  
  public void start(Stage paramStage) {
    paramStage.setTitle("Sample Canvas");
    Button button = new Button("Show Dialog");
    button.setOnAction(paramActionEvent -> a());
    VBox vBox = new VBox();
    vBox.getChildren().addAll((Object[])new Node[] { (Node)button });
    this.a.getChildren().add(vBox);
    paramStage.setScene(this.b);
    paramStage.sizeToScene();
    paramStage.show();
  }
  
  private void a() {
    Dialog dialog = new Dialog();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(this.b.getWindow());
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
