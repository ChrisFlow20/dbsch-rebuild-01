package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.dialogs.system.FxDownloadSampleModelsTask;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;

class c extends FxDownloadSampleModelsTask {
  public c(FxWelcome paramFxWelcome) {
    super(paramFxWelcome.d);
  }
  
  protected void succeeded() {
    this.a.a();
  }
  
  protected void failed() {
    super.failed();
    switch (FxWelcome$1.a[this.a.e.b(this.a.getScene(), "retryDownloadSamplesDialog", new String[0]).c().ordinal()]) {
      case 1:
        this.a.e.a(new FxDownloadSampleModelsTask(this.a.d));
        break;
      case 2:
        (new FxTechnicalSupportDialog(this.a.getScene().getWindow())).showDialog();
        break;
    } 
  }
}
