package com.wisecoders.dbs.dbms.driver.fx;

import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dialogs.system.FxDownloadDriverTask;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;

class a extends FxDownloadDriverTask {
  public a(FxDriversDialog paramFxDriversDialog) {
    super(paramFxDriversDialog.a());
  }
  
  protected void succeeded() {
    DriverManager.a((String)this.a.c.getValue()).k();
    this.a.refreshDrivers();
    this.a.rx.d(this.a.getDialogPane().getScene(), "Driver Successfully Downloaded");
  }
  
  protected void failed() {
    super.failed();
    switch (FxDriversDialog$4.a[this.a.showOptionsDialog("retryDownloadDialog").c().ordinal()]) {
      case 1:
        this.a.rx.a(new a(this.a));
        break;
      case 2:
        this.a.addDriver();
        break;
      case 3:
        (new FxTechnicalSupportDialog(this.a.getDialogScene().getWindow())).showDialog();
        break;
    } 
  }
}
