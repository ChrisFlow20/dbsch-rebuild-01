package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.fx.FxDiagramPane;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

public class FxZoomDialog extends ButtonDialog$ {
  private final FxDiagramPane a;
  
  public FxZoomDialog(FxDiagramPane paramFxDiagramPane) {
    super(paramFxDiagramPane.getWorkspace(), Modality.NONE);
    this.a = paramFxDiagramPane;
    getDialogScene().getWindow().setOnCloseRequest(paramWindowEvent -> close());
    showDialog();
  }
  
  public Node createContentPane() {
    Slider slider = new Slider(10.0D, 190.0D, 100.0D * this.a.n());
    slider.setShowTickMarks(true);
    slider.setMinorTickCount(10);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(10.0D);
    slider.setBlockIncrement(5.0D);
    slider.setOrientation(Orientation.VERTICAL);
    slider.setPrefHeight(500.0D);
    slider.valueProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          double d = Math.max(0.05D, paramNumber2.doubleValue()) / 100.0D;
          this.a.a(d);
        });
    return (Node)slider;
  }
  
  public void createButtons() {}
  
  public boolean apply() {
    return true;
  }
}
