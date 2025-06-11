package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class LearnSchemaCommand extends SQLCommand {
  private static final Pattern b = Pattern.compile("(.*)(\\s+including\\s+details)", 2);
  
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    String str = paramString2;
    boolean bool = false;
    Matcher matcher = b.matcher(paramString2);
    if (matcher.matches()) {
      str = matcher.group(1);
      bool = (matcher.group(2) != null && matcher.group(2).length() > 0) ? true : false;
    } 
    if (str != null)
      str = str.trim(); 
    paramAbstractConsole.a("Learning...", new Object[0]);
    paramConnector.importSchema(paramConnector.getCliProject(), str);
    paramAbstractConsole.c("Ok", new Object[] { Integer.valueOf((paramConnector.getCliProject()).schemas.size()) });
    return false;
  }
}
