package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ShowSchemasCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    return false;
  }
}
