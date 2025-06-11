package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.result.Spooler$Format;
import com.wisecoders.dbs.data.task.FxSpoolTask;
import javafx.concurrent.Task;

class d extends FxSpoolTask {
  public d(FxResultPane paramFxResultPane, Spooler$Format paramSpooler$Format) {
    super(paramFxResultPane.e.getWorkspace(), paramFxResultPane.d, paramSpooler$Format);
  }
  
  public void a() {
    Task task = this.a.e.a(this.a.c, this.a);
    this.a.e.u().a(task);
  }
}
