package com.wisecoders.dbs.dbms.connect.fx;

import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SampleAppTestCtrlQ extends Application {
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
  
  public void start(Stage paramStage) {
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)new Label("Close this with Cmd-Q"));
    paramStage.setScene(new Scene((Parent)borderPane));
    paramStage.setOnCloseRequest(paramWindowEvent -> {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Confirmation Dialog");
          alert.setHeaderText("Save your work?");
          alert.setContentText("Press yes to save");
          Optional<ButtonType> optional = alert.showAndWait();
          if (optional.get() == ButtonType.OK);
        });
    paramStage.show();
  }
}
