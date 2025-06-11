package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.sql.Statement;

@DoNotObfuscate
public class PLSQLCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    String str = paramString1.substring(0, paramString1.length() - 1);
    Statement statement = paramEnvoy.a(false);
    a(statement);
    if (statement.execute(str)) {
      paramAbstractConsole.c("Done", new Object[0]);
    } else {
      paramAbstractConsole.c("Modified " + statement.getUpdateCount() + " rows", new Object[0]);
    } 
    return true;
  }
  
  public CommandType getType() {
    return CommandType.a;
  }
}
