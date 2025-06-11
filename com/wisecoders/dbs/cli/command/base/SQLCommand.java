package com.wisecoders.dbs.cli.command.base;

import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.sql.SQLException;
import java.sql.Statement;

@DoNotObfuscate
public abstract class SQLCommand extends Command {
  private Connector b;
  
  private Statement c;
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    int i = CLIConnectorManager.a().size();
    if (i == 0)
      throw new SQLException("Not connected"); 
    byte b = 0;
    paramAbstractConsole.m();
    for (Connector connector : CLIConnectorManager.a()) {
      if (this.a)
        return; 
      this.b = connector;
      if (i > 1)
        paramAbstractConsole.b(connector.getName(), new Object[0]); 
      boolean bool = a(paramAbstractConsole, paramString1, paramString2, connector, b++);
      if (!bool)
        return; 
    } 
  }
  
  private boolean a(AbstractConsole paramAbstractConsole, String paramString1, String paramString2, Connector paramConnector, int paramInt) {
    boolean bool;
    Envoy envoy = null;
    try {
      envoy = paramConnector.startEnvoy("DbSchemaCLI");
      bool = process(paramAbstractConsole, paramConnector, envoy, paramString1 + " " + paramString1, paramString2, paramInt);
      this.b = null;
      envoy.close();
    } catch (SQLException sQLException) {
      Log.a("Error in SqlCommand", sQLException);
      envoy.a(sQLException);
      throw a(paramConnector, envoy, paramString1, sQLException);
    } 
    return bool;
  }
  
  public abstract boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt);
  
  public synchronized void interrupt() {
    super.interrupt();
    boolean bool = (this.c != null) ? true : false;
    if (bool) {
      Statement statement = this.c;
      this.c = null;
      (new SQLCommand$1(this, "Close connection thread", statement))









        
        .start();
      try {
        wait(1000L);
      } catch (InterruptedException interruptedException) {}
    } 
    if (this.b != null) {
      System.out.println("Disconnect...");
      this.b.closeAllEnvoysAndSsh();
    } 
  }
  
  private SQLException a(Connector paramConnector, Envoy paramEnvoy, String paramString, SQLException paramSQLException) {
    int i = -1;
    if (paramEnvoy != null)
      i = Dbms.get(paramConnector.dbId).getErrorPosition(paramSQLException, paramString, null, paramEnvoy); 
    if (i > -1 && i < paramString.length())
      return new SQLException(paramSQLException.toString() + "\n" + paramSQLException.toString() + "???" + paramString
          .substring(0, i), 
          paramSQLException); 
    return paramSQLException;
  }
  
  protected void a(Statement paramStatement) {
    this.c = paramStatement;
  }
}
