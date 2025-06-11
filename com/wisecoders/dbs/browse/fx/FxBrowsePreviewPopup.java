package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Rx;
import java.util.Collection;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FxBrowsePreviewPopup extends Stage {
  double a;
  
  double b;
  
  public FxBrowsePreviewPopup(Workspace paramWorkspace, AbstractTable paramAbstractTable) {
    initOwner(paramWorkspace.getWindow());
    initModality(Modality.NONE);
    initStyle(StageStyle.UNDECORATED);
    setTitle(paramAbstractTable.getNameWithSchemaName());
    FxBrowsePreviewPopup$1 fxBrowsePreviewPopup$1 = new FxBrowsePreviewPopup$1(this, paramWorkspace, paramWorkspace);
    FxBrowsePreviewPopup$2 fxBrowsePreviewPopup$2 = new FxBrowsePreviewPopup$2(this, fxBrowsePreviewPopup$1, paramAbstractTable);
    fxBrowsePreviewPopup$2.getStyleClass().add("browse-preview-table");
    fxBrowsePreviewPopup$2.setOnMousePressed(paramMouseEvent -> {
          this.a = Double.MIN_VALUE;
          this.b = Double.MIN_VALUE;
        });
    fxBrowsePreviewPopup$2.setOnMouseDragged(paramMouseEvent -> {
          if (this.a == Double.MIN_VALUE) {
            this.a = paramMouseEvent.getScreenX();
            this.b = paramMouseEvent.getScreenY();
          } else {
            setWidth(Math.max(0.0D, getWidth() + paramMouseEvent.getScreenX() - this.a));
            setHeight(Math.max(0.0D, getHeight() + paramMouseEvent.getScreenY() - this.b));
            this.a = paramMouseEvent.getScreenX();
            this.b = paramMouseEvent.getScreenY();
          } 
        });
    BorderPane borderPane1 = new BorderPane();
    borderPane1.getStyleClass().add("browse-frame-bar");
    borderPane1.setStyle("-fx-cursor: hand");
    borderPane1.setRight((Node)a());
    borderPane1.setOnMousePressed(paramMouseEvent -> {
          this.a = Double.MIN_VALUE;
          this.b = Double.MIN_VALUE;
        });
    borderPane1.setOnMouseDragged(paramMouseEvent -> {
          if (this.a == Double.MIN_VALUE) {
            this.a = paramMouseEvent.getScreenX();
            this.b = paramMouseEvent.getScreenY();
          } else {
            setX(Math.max(0.0D, getX() + paramMouseEvent.getScreenX() - this.a));
            setY(Math.max(0.0D, getY() + paramMouseEvent.getScreenY() - this.b));
            this.a = paramMouseEvent.getScreenX();
            this.b = paramMouseEvent.getScreenY();
          } 
        });
    BorderPane borderPane2 = new BorderPane();
    borderPane2.getStyleClass().add("browse-frame-bar");
    borderPane2.setRight((Node)b());
    BorderPane borderPane3 = new BorderPane();
    borderPane3.setTop((Node)borderPane1);
    borderPane3.setCenter((Node)fxBrowsePreviewPopup$2);
    borderPane3.setBottom((Node)borderPane2);
    Scene scene = new Scene((Parent)borderPane3, 450.0D, 280.0D);
    scene.getStylesheets().addAll((Collection)paramWorkspace.getStylesheets());
    focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (!paramBoolean2.booleanValue())
            close(); 
        });
    setScene(scene);
  }
  
  protected Button a() {
    Button button = new Button(" X ");
    button.getStyleClass().add("browse-frame-button");
    button.setOnAction(paramActionEvent -> hide());
    return button;
  }
  
  protected Label b() {
    Label label = new Label(null, (Node)Rx.q("forms/grip.png"));
    label.getStyleClass().add("browse-frame-button");
    label.setStyle("-fx-cursor: se-resize");
    label.addEventHandler(MouseEvent.MOUSE_DRAGGED, paramMouseEvent -> {
          setWidth(paramMouseEvent.getSceneX());
          setHeight(paramMouseEvent.getSceneY());
        });
    return label;
  }
  
  public void a(Scene paramScene, double paramDouble1, double paramDouble2) {
    initOwner(paramScene.getWindow());
    setX(paramDouble1);
    setY(paramDouble2);
    show();
  }
  
  public void a(Scene paramScene) {
    initOwner(paramScene.getWindow());
    show();
  }
}
