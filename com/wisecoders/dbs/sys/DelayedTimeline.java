package com.wisecoders.dbs.sys;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.util.Duration;

public class DelayedTimeline {
  private final Timeline a;
  
  private final List b = new CopyOnWriteArrayList();
  
  public DelayedTimeline(Dialog paramDialog) {
    this(Duration.seconds(0.49D));
    paramDialog.setOnHiding(paramDialogEvent -> a());
  }
  
  public DelayedTimeline(Duration paramDuration) {
    this.a = new Timeline(new KeyFrame[] { new KeyFrame(paramDuration, paramActionEvent -> b(), new javafx.animation.KeyValue[0]) });
    this.a.setDelay(Duration.seconds(1.0D));
    this.a.setCycleCount(-1);
    this.a.play();
  }
  
  private synchronized void b() {
    for (Runnable runnable : this.b)
      runnable.run(); 
  }
  
  public synchronized DelayedTimelineHandler a(long paramLong, Runnable paramRunnable) {
    DelayedTimelineHandler delayedTimelineHandler = new DelayedTimelineHandler(paramLong, paramRunnable);
    this.b.add(delayedTimelineHandler);
    return delayedTimelineHandler;
  }
  
  public void a(Runnable paramRunnable) {
    this.b.add(paramRunnable);
  }
  
  public synchronized void a() {
    this.a.stop();
    this.b.clear();
  }
}
