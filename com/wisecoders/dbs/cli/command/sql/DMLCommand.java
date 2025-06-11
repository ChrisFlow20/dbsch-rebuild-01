package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.sql.Statement;

@DoNotObfuscate
public class DMLCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    Statement statement = paramEnvoy.a(true);
    a(statement);
    int i = statement.executeUpdate(paramString1);
    boolean bool = paramEnvoy.c().getAutoCommit();
    paramAbstractConsole.c((i > 0) ? ("" + i + " rows modified." + i) : "No data changed.", new Object[0]);
    return true;
  }
  
  public CommandType getType() {
    return CommandType.c;
  }
}
