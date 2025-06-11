package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dialogs.system.FxDownloadDriverTask;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;

class b extends FxDownloadDriverTask {
  b(FxConnectorEditor paramFxConnectorEditor) {
    super(paramFxConnectorEditor.F.dbId);
  }
  
  protected void succeeded() {
    this.a.aT = this.a.F.dbId;
    new d(this.a, false, false);
  }
  
  protected void failed() {
    super.failed();
    switch (FxConnectorEditor$4.b[this.a.showOptionsDialog("retryDownloadDialog").c().ordinal()]) {
      case 1:
        this.a.rx.a(new b(this.a));
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
