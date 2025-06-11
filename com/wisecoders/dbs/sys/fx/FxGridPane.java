package com.wisecoders.dbs.sys.fx;

import java.security.InvalidParameterException;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class FxGridPane extends GridPane {
  public static final double a = -1.0D;
  
  public static final double b = -2.0D;
  
  public FxGridPane() {
    this(7, 7, (double[][])null);
  }
  
  public FxGridPane(double[][] paramArrayOfdouble) {
    this(7, 7, paramArrayOfdouble);
  }
  
  public FxGridPane(int paramInt1, int paramInt2, double[][] paramArrayOfdouble) {
    setHgap(paramInt1);
    setVgap(paramInt2);
    if (paramArrayOfdouble != null)
      if (paramArrayOfdouble.length == 2) {
        b(paramArrayOfdouble[0]);
        a(paramArrayOfdouble[0]);
      } else {
        throw new InvalidParameterException("GridPane placement double[][] is not size 2");
      }  
  }
  
  private void a(double[] paramArrayOfdouble) {
    for (double d : paramArrayOfdouble) {
      RowConstraints rowConstraints = new RowConstraints();
      if (d == -1.0D) {
        rowConstraints.setFillHeight(true);
      } else if (d > 0.0D) {
        rowConstraints.setPrefHeight(d);
      } 
      getRowConstraints().add(rowConstraints);
    } 
  }
  
  private void b(double[] paramArrayOfdouble) {
    for (double d : paramArrayOfdouble) {
      ColumnConstraints columnConstraints = new ColumnConstraints();
      if (d == -1.0D) {
        columnConstraints.setHgrow(Priority.ALWAYS);
      } else if (d == -2.0D) {
        columnConstraints.setHgrow(Priority.NEVER);
      } else if (d > 0.0D) {
        columnConstraints.setPrefWidth(d);
      } 
      getColumnConstraints().add(columnConstraints);
    } 
  }
  
  public FxGridPane a(Node paramNode, String paramString) {
    paramString = paramString.replaceAll(" ", "");
    String[] arrayOfString = paramString.split(",");
    int i = arrayOfString.length;
    HPos hPos = null;
    VPos vPos = null;
    int k = Integer.parseInt(arrayOfString[0]), j = k;
    int n = Integer.parseInt(arrayOfString[1]), m = n;
    if (i > 2) {
      hPos = a(arrayOfString[2]);
      vPos = b(arrayOfString[3]);
      if (hPos == null || vPos == null) {
        k = Integer.parseInt(arrayOfString[2]);
        n = Integer.parseInt(arrayOfString[3]);
      } 
    } 
    if (i > 4) {
      hPos = a(arrayOfString[4]);
      vPos = b(arrayOfString[5]);
    } 
    if (k != j || n != m) {
      add(paramNode, j, m, k - j + 1, n - m + 1);
    } else {
      add(paramNode, j, m);
    } 
    if (hPos != null)
      setHalignment(paramNode, hPos); 
    if (vPos != null)
      setValignment(paramNode, vPos); 
    return this;
  }
  
  private HPos a(String paramString) {
    if ("l".equalsIgnoreCase(paramString))
      return HPos.LEFT; 
    if ("c".equalsIgnoreCase(paramString))
      return HPos.CENTER; 
    if ("r".equalsIgnoreCase(paramString))
      return HPos.RIGHT; 
    return null;
  }
  
  private VPos b(String paramString) {
    if ("t".equalsIgnoreCase(paramString))
      return VPos.TOP; 
    if ("c".equalsIgnoreCase(paramString))
      return VPos.CENTER; 
    if ("b".equalsIgnoreCase(paramString))
      return VPos.BOTTOM; 
    return null;
  }
}
