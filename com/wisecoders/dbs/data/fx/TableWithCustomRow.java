package com.wisecoders.dbs.data.fx;

import java.util.Random;
import java.util.function.Function;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableWithCustomRow extends Application {
  public void start(Stage paramStage) {
    TableView tableView = new TableView();
    tableView.setRowFactory(paramTableView -> new TableWithCustomRow$1(this));
    Random random = new Random();
    for (byte b = 1; b <= 100; b++)
      tableView.getItems().add(new TableWithCustomRow$Item("Item " + b, random.nextInt(100))); 
    tableView.getColumns().add(a("Item", TableWithCustomRow$Item::a));
    tableView.getColumns().add(a("Value", TableWithCustomRow$Item::c));
    Scene scene = new Scene((Parent)tableView, 800.0D, 600.0D);
    paramStage.setScene(scene);
    paramStage.show();
  }
  
  private Node a(ObjectProperty paramObjectProperty) {
    BorderPane borderPane = new BorderPane();
    Label label1 = new Label();
    VBox vBox = new VBox(5.0D, new Node[] { (Node)new Label("These are the"), (Node)label1 });
    vBox.setAlignment(Pos.CENTER_LEFT);
    vBox.setPadding(new Insets(2.0D, 2.0D, 2.0D, 16.0D));
    borderPane.setCenter((Node)vBox);
    Label label2 = new Label("Icon");
    label2.setStyle("-fx-background-color: aqua; -fx-text-fill: darkgreen; -fx-font-size:18;");
    BorderPane.setMargin((Node)label2, new Insets(6.0D));
    label2.setMinSize(40.0D, 40.0D);
    borderPane.setLeft((Node)label2);
    borderPane.setStyle("-fx-background-color: -fx-background; -fx-background: skyblue;");
    paramObjectProperty.addListener((paramObservableValue, paramTableWithCustomRow$Item1, paramTableWithCustomRow$Item2) -> {
          if (paramTableWithCustomRow$Item2 == null) {
            paramLabel.setText("");
          } else {
            paramLabel.setText("details for " + paramTableWithCustomRow$Item2.b());
          } 
        });
    return (Node)borderPane;
  }
  
  private static TableColumn a(String paramString, Function paramFunction) {
    TableColumn tableColumn = new TableColumn(paramString);
    tableColumn.setCellValueFactory(paramCellDataFeatures -> (ObservableValue)paramFunction.apply(paramCellDataFeatures.getValue()));
    tableColumn.setPrefWidth(150.0D);
    return tableColumn;
  }
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
}
