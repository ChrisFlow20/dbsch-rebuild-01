package com.wisecoders.dbs.sys.fx;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GridPane$ extends GridPane {
  public static final Insets b = new Insets(10.0D, 10.0D, 10.0D, 10.0D);
  
  public static final Insets c = new Insets(5.0D, 10.0D, 15.0D, 15.0D);
  
  public static final Insets d = new Insets(3.0D, 3.0D, 3.0D, 3.0D);
  
  public GridPane$ e() {
    setHgap(12.0D);
    setVgap(10.0D);
    return this;
  }
  
  public GridPane$ f() {
    setHgap(15.0D);
    setVgap(20.0D);
    return this;
  }
  
  public GridPane$ a(double paramDouble) {
    return a(paramDouble, paramDouble);
  }
  
  public GridPane$ a(double paramDouble1, double paramDouble2) {
    setHgap(paramDouble1);
    setVgap(paramDouble2);
    return this;
  }
  
  public GridPane$ g() {
    setPadding(b);
    return this;
  }
  
  public GridPane$ h() {
    setPadding(c);
    return this;
  }
  
  public GridPane$ i() {
    setPadding(d);
    return j();
  }
  
  public GridPane$ j() {
    setHgap(5.0D);
    setVgap(5.0D);
    return this;
  }
  
  public GridPane$ k() {
    setPadding(d);
    return this;
  }
  
  public GridPane$ b(double paramDouble) {
    setPadding(new Insets(paramDouble));
    return this;
  }
  
  public GridPane$ l() {
    return e().g();
  }
  
  public GridPane$ b(String paramString) {
    getStyleClass().add(paramString);
    return this;
  }
  
  public GridPane$ a(int paramInt) {
    setVgap(paramInt);
    return this;
  }
  
  public GridPane$ a(Node paramNode, String paramString) {
    String[] arrayOfString = paramString.replaceAll(" ", "").split(",");
    int i = Integer.parseInt(arrayOfString[0]);
    int j = Integer.parseInt(arrayOfString[1]);
    int k = 1, m = 1;
    if (arrayOfString.length > 2) {
      switch (arrayOfString[2]) {
        case "f":
          GridPane.setFillWidth(paramNode, Boolean.valueOf(true));
          GridPane.setHgrow(paramNode, Priority.ALWAYS);
          break;
        case "l":
          GridPane.setHalignment(paramNode, HPos.LEFT);
          break;
        case "c":
          GridPane.setHalignment(paramNode, HPos.CENTER);
          break;
        case "r":
          GridPane.setHalignment(paramNode, HPos.RIGHT);
          break;
        default:
          k = Integer.parseInt(arrayOfString[2]) - i + 1;
          break;
      } 
      switch (arrayOfString[3]) {
        case "f":
          GridPane.setFillHeight(paramNode, Boolean.valueOf(true));
          break;
        case "t":
          GridPane.setValignment(paramNode, VPos.TOP);
          break;
        case "c":
          GridPane.setValignment(paramNode, VPos.CENTER);
          break;
        case "b":
          GridPane.setValignment(paramNode, VPos.BOTTOM);
          break;
        default:
          m = Integer.parseInt(arrayOfString[3]) - j + 1;
          break;
      } 
    } 
    if (arrayOfString.length > 4) {
      switch (arrayOfString[4]) {
        case "f":
          GridPane.setFillWidth(paramNode, Boolean.valueOf(true));
          GridPane.setHgrow(paramNode, Priority.ALWAYS);
          break;
        case "l":
          GridPane.setHalignment(paramNode, HPos.LEFT);
          break;
        case "c":
          GridPane.setHalignment(paramNode, HPos.CENTER);
          break;
        case "r":
          GridPane.setHalignment(paramNode, HPos.RIGHT);
          break;
      } 
      switch (arrayOfString[5]) {
        case "f":
          GridPane.setFillHeight(paramNode, Boolean.valueOf(true));
          break;
        case "t":
          GridPane.setValignment(paramNode, VPos.TOP);
          break;
        case "c":
          GridPane.setValignment(paramNode, VPos.CENTER);
          break;
        case "b":
          GridPane.setValignment(paramNode, VPos.BOTTOM);
          break;
      } 
    } 
    add(paramNode, i, j, k, m);
    return this;
  }
  
  public GridPane$ a(int... paramVarArgs) {
    byte b = 0;
    for (int i : paramVarArgs) {
      if (b < getColumnConstraints().size()) {
        ColumnConstraints columnConstraints = (ColumnConstraints)getColumnConstraints().get(b);
        switch (i) {
          case -1:
            a(columnConstraints, Priority.ALWAYS);
            break;
          case -2:
            a(columnConstraints, Priority.NEVER);
            break;
          default:
            if (i > 0) {
              columnConstraints.setHgrow(Priority.NEVER);
              columnConstraints.setFillWidth(false);
              columnConstraints.setPrefWidth(i);
            } 
            break;
        } 
      } else {
        switch (i) {
          case -1:
            a(Priority.ALWAYS);
            break;
          case -2:
            a(Priority.NEVER);
            break;
          default:
            if (i > 0) {
              ColumnConstraints columnConstraints = new ColumnConstraints();
              columnConstraints.setHgrow(Priority.NEVER);
              columnConstraints.setFillWidth(false);
              columnConstraints.setPrefWidth(i);
              getColumnConstraints().add(columnConstraints);
            } 
            break;
        } 
      } 
      b++;
    } 
    return this;
  }
  
  public GridPane$ b(int... paramVarArgs) {
    for (int i : paramVarArgs) {
      switch (i) {
        case -1:
          b(Priority.ALWAYS);
          break;
        case -2:
          b(Priority.NEVER);
          break;
        default:
          if (i > 0) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.NEVER);
            rowConstraints.setFillHeight(false);
            rowConstraints.setPrefHeight(i);
            getRowConstraints().add(rowConstraints);
          } 
          break;
      } 
    } 
    return this;
  }
  
  public GridPane$ a(Priority paramPriority) {
    ColumnConstraints columnConstraints = new ColumnConstraints();
    a(columnConstraints, paramPriority);
    getColumnConstraints().add(columnConstraints);
    return this;
  }
  
  private void a(ColumnConstraints paramColumnConstraints, Priority paramPriority) {
    paramColumnConstraints.setHgrow(paramPriority);
    paramColumnConstraints.setFillWidth((paramPriority == Priority.ALWAYS));
  }
  
  public GridPane$ b(Priority paramPriority) {
    RowConstraints rowConstraints = new RowConstraints();
    rowConstraints.setVgrow(paramPriority);
    rowConstraints.setFillHeight((paramPriority == Priority.ALWAYS));
    getRowConstraints().add(rowConstraints);
    return this;
  }
  
  public Node a(int paramInt1, int paramInt2) {
    for (Node node : getChildren()) {
      if (getRowIndex(node).intValue() == paramInt2 && getColumnIndex(node).intValue() == paramInt1)
        return node; 
    } 
    return null;
  }
  
  public GridPane$ a(Node... paramVarArgs) {
    byte b = 0;
    for (Node node : paramVarArgs)
      add(node, b++, 0, 1, 1); 
    return this;
  }
}
