package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.Rect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

class a {
  private final int[] b = new int[] { 
      5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 
      55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };
  
  private final Timeline c;
  
  a(FxAbstractDiagramPane paramFxAbstractDiagramPane, Rect paramRect) {
    Rect rect1 = new Rect(paramFxAbstractDiagramPane.as);
    Rect rect2 = new Rect(rect1);
    double d1 = (paramRect.a() - rect1.a()) / 100.0D;
    double d2 = (paramRect.b() - rect1.b()) / 100.0D;
    paramFxAbstractDiagramPane.aI = 0;
    this
      .c = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(0.05D), paramActionEvent -> {
              if (this.a.aI < this.b.length) {
                paramRect1.a((int)(paramRect2.a() + paramDouble1 * this.b[this.a.aI]), (int)(paramRect2.b() + paramDouble2 * this.b[this.a.aI]));
                this.a.a(paramRect1);
                this.a.aI++;
              } else {
                a();
              } 
            }new javafx.animation.KeyValue[0]) });
    this.c.setCycleCount(this.b.length);
    this.c.play();
  }
  
  void a() {
    this.c.stop();
  }
}
