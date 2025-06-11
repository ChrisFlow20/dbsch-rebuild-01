package com.wisecoders.dbs.dbms.connect.fx;

import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SampleHorizontalList extends Application {
  private TilePane a;
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
  
  private ArrayList b = new ArrayList();
  
  public void start(Stage paramStage) {
    String[] arrayOfString = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
    this.a = new TilePane();
    this.a.setStyle("-fx-background-color: white ;");
    this.a.setVgap(10.0D);
    this.a.setHgap(10.0D);
    this.a.setPrefColumns(5);
    for (String str : arrayOfString)
      a(str); 
    paramStage.setScene(new Scene((Parent)this.a));
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
  
  private void a(String paramString) {
    Label label = new Label(paramString);
    label.setPrefSize(100.0D, 50.0D);
    label.setOnMouseClicked(paramMouseEvent -> {
          if (this.b.contains(paramLabel)) {
            if (paramMouseEvent.isControlDown()) {
              a(paramLabel);
            } else {
              a();
            } 
          } else {
            if (!paramMouseEvent.isControlDown())
              a(); 
            b(paramLabel);
            b();
          } 
        });
    this.a.getChildren().add(label);
  }
  
  private void a(Label paramLabel) {
    if (paramLabel != null) {
      paramLabel.setStyle("-fx-background-color: white ;");
      this.b.remove(paramLabel);
    } 
  }
  
  private void b(Label paramLabel) {
    paramLabel.setStyle("-fx-background-color: #AFEEEE ;");
    this.b.add(paramLabel);
  }
  
  private void a() {
    for (Label label : this.b)
      label.setStyle("-fx-background-color: white ;"); 
    this.b.clear();
  }
  
  private void b() {}
}
