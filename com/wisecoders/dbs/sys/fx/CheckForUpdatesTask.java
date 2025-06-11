package com.wisecoders.dbs.sys.fx;

import com.install4j.api.launcher.ApplicationLauncher;
import com.install4j.api.update.ApplicationDisplayMode;
import com.install4j.api.update.UpdateChecker;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class CheckForUpdatesTask extends Task {
  private final Rx a = new Rx(CheckForUpdatesTask.class, this);
  
  private final Scene b;
  
  public CheckForUpdatesTask(Scene paramScene) {
    this.b = paramScene;
  }
  
  protected Boolean a() {
    updateMessage("Check for updates...");
    return Boolean.valueOf((UpdateChecker.getUpdateDescriptor("https://dbschema.com/download/updates.xml", ApplicationDisplayMode.GUI).getPossibleUpdateEntry() != null));
  }
  
  protected void succeeded() {
    if (((Boolean)getValue()).booleanValue()) {
      if (this.a.a(this.b, "downloadUpdates"))
        ApplicationLauncher.launchApplicationInProcess("1357", null, new CheckForUpdatesTask$1(this), ApplicationLauncher.WindowMode.FRAME, null); 
    } else {
      this.a.d(this.b, "No updates available");
    } 
  }
  
  protected void failed() {
    Log.a("Check for updates.", getException());
    this.a.a(this.b, getException());
  }
}
