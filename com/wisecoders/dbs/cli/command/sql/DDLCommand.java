package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@DoNotObfuscate
public class DDLCommand extends SQLCommand {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (Sys.B.enableParallelDDL.b() && CLIConnectorManager.a().size() > 1) {
      a(paramAbstractConsole, paramString1, paramString2);
    } else {
      super.process(paramAbstractConsole, paramString1, paramString2);
    } 
  }
  
  private void a(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    paramAbstractConsole.c("Execute on multiple threads...", new Object[0]);
    ExecutorService executorService = Executors.newFixedThreadPool(20);
    try {
      ArrayList<Future<?>> arrayList = new ArrayList();
      for (Connector connector : CLIConnectorManager.a()) {
        Future<?> future = executorService.submit(new a(connector, paramString1 + " " + paramString1));
        arrayList.add(future);
      } 
      for (Future<?> future : arrayList)
        future.get(); 
      paramAbstractConsole.c("Done", new Object[0]);
    } catch (Exception exception) {
      Log.a("Error in DDLCommand", exception);
      paramAbstractConsole.c(exception.toString(), new Object[0]);
    } finally {
      executorService.shutdownNow();
    } 
  }
  
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    UpdateStatement updateStatement = paramEnvoy.b(paramString1, new Object[0]);
    try {
      updateStatement.a();
      paramEnvoy.p();
      if (updateStatement != null)
        updateStatement.close(); 
    } catch (Throwable throwable) {
      if (updateStatement != null)
        try {
          updateStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    paramAbstractConsole.c("Done", new Object[0]);
    return true;
  }
  
  public CommandType getType() {
    return CommandType.c;
  }
}
