package com.wisecoders.dbs.data.task;

import com.wisecoders.dbs.data.model.result.Spooler;
import com.wisecoders.dbs.data.model.result.Spooler$Format;
import com.wisecoders.dbs.data.model.result.SqlResult;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import java.io.File;
import java.io.IOException;

public abstract class FxSpoolTask {
  private final Rx a = new Rx(FxSpoolTask.class, this);
  
  public FxSpoolTask(Workspace paramWorkspace, SqlResult paramSqlResult, Spooler$Format paramSpooler$Format) {
    File file = FxFileChooser.a(paramWorkspace, "Save result to file", FileOperation.b, new FileType[] { paramSpooler$Format.k });
    if (file != null)
      try {
        paramSqlResult.a(new Spooler(paramSpooler$Format, file));
        a();
      } catch (IOException iOException) {
        Log.b(iOException);
        this.a.a(paramWorkspace, iOException);
      }  
  }
  
  public abstract void a();
}
