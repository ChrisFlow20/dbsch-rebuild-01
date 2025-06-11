package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class AutoCommitCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    boolean bool = ("on".equalsIgnoreCase(paramString2) || "true".equalsIgnoreCase(paramString2)) ? true : false;
    paramEnvoy.c().setAutoCommit(bool);
    paramAbstractConsole.c("Autocommit set " + (bool ? "on" : "off"), new Object[0]);
    return true;
  }
}
