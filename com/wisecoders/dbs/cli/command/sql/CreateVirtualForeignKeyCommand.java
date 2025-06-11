package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class CreateVirtualForeignKeyCommand extends SQLCommand {
  private static final String b = "(.*)\\s*\\((.*)\\)";
  
  private static final Pattern c = Pattern.compile("(.*)\\s*\\((.*)\\)\\s+references\\s+(.*)\\s*\\((.*)\\)", 2);
  
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    Matcher matcher = c.matcher(paramString2);
    return false;
  }
}
