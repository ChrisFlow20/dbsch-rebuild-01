package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class DescribeCommand extends SQLCommand {
  private final boolean b;
  
  public DescribeCommand() {
    this.b = false;
  }
  
  public DescribeCommand(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    if (paramString2.length() == 0)
      throw new ParameterException(); 
    Connector connector = CLIConnectorManager.b();
    return false;
  }
}
