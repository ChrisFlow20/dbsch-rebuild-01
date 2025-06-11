package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.fx.FxLoaderDialog;
import com.wisecoders.dbs.loader.model.LoaderMeta;
import com.wisecoders.dbs.sys.Log;
import javafx.concurrent.Task;

public class LoaderPreviewTask extends Task {
  private final FxLoaderDialog a;
  
  private final LoaderMeta b;
  
  private final AbstractLoader c;
  
  public LoaderPreviewTask(FxLoaderDialog paramFxLoaderDialog, LoaderMeta paramLoaderMeta, AbstractLoader paramAbstractLoader) {
    this.a = paramFxLoaderDialog;
    this.b = paramLoaderMeta;
    this.c = paramAbstractLoader;
    paramLoaderMeta.a();
    paramAbstractLoader.setMaxProcessingTime(2000L);
    paramAbstractLoader.addConsumer(paramLoaderMeta);
  }
  
  protected Void a() {
    updateMessage("Inspect data...");
    this.c.parse();
    this.b.h();
    return null;
  }
  
  protected void succeeded() {
    this.a.e.refresh();
  }
  
  public void failed() {
    Throwable throwable = getException();
    Log.a("Error in data loader task", throwable);
    this.a.showError(throwable.toString());
  }
}
