package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageSizer {
  private static final String a = "STAGE_X";
  
  private static final String b = "STAGE_Y";
  
  private static final String c = "STAGE_WIDTH";
  
  private static final String d = "STAGE_HEIGHT";
  
  private static final String e = "STAGE_MAXIMIZED";
  
  public static void a(Stage paramStage) {
    Pref.a("STAGE_X", paramStage.getX());
    Pref.a("STAGE_Y", paramStage.getY());
    Pref.a("STAGE_WIDTH", paramStage.getWidth());
    Pref.a("STAGE_HEIGHT", paramStage.getHeight());
    Pref.a("STAGE_MAXIMIZED", paramStage.isMaximized());
  }
  
  public static void b(Stage paramStage) {
    Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
    double d1 = Pref.b("STAGE_X", rectangle2D.getMinX());
    double d2 = Pref.b("STAGE_Y", rectangle2D.getMinY());
    boolean bool = Pref.b("STAGE_MAXIMIZED", false);
    double d3 = Math.max(500.0D, Pref.b("STAGE_WIDTH", rectangle2D.getWidth()));
    double d4 = Math.max(400.0D, Pref.b("STAGE_HEIGHT", rectangle2D.getHeight()));
    double d5 = Math.max(0.1D, Screen.getPrimary().getOutputScaleY());
    Log.c("Screen Size: [" + d3 + "," + d4 + "] outputScaleY=" + d5 + " screenResolution=" + String.valueOf(Rx.f()));
    if (Screen.getScreensForRectangle(d1 + 50.0D, d2 + 50.0D, d3 - 100.0D, d4 - 100.0D).isEmpty()) {
      d1 = rectangle2D.getMinX();
      d2 = rectangle2D.getMinY();
      d3 = rectangle2D.getWidth();
      d4 = rectangle2D.getHeight();
    } 
    paramStage.setX(d1);
    paramStage.setY(d2);
    paramStage.setWidth(d3);
    paramStage.setHeight(d4);
    if (bool)
      paramStage.setMaximized(true); 
  }
}
