package com.wisecoders.dbs.cli.command.connectivity;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class ConnectCommand extends Command {
  private static final Pattern b = Pattern.compile("\\s*(-q)\\s+(\\S*)", 2);
  
  private static final Pattern c = Pattern.compile("\\s*(\\S*)\\s+exclude\\s+(\\S*)", 2);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (paramString2.isEmpty())
      throw new ParameterException("Invalid connection name. Expected : " + toString()); 
    try {
      boolean bool = false;
      paramAbstractConsole.a("Not Connected > ");
      String str1 = paramString2, str2 = null;
      Matcher matcher;
      if ((matcher = b.matcher(paramString2)).matches()) {
        bool = true;
        paramString2 = matcher.group(2);
      } 
      if ((matcher = c.matcher(paramString2)).matches()) {
        str1 = matcher.group(1);
        str2 = matcher.group(2);
      } 
      if (CLIConnectorManager.a(str1, str2, bool)) {
        paramAbstractConsole.a(paramString2 + " > ");
        paramAbstractConsole.c(
            (CLIConnectorManager.a().size() == 1) ? 
            "Connected" : ("Connected " + 
            CLIConnectorManager.a().size()), new Object[0]);
      } else {
        paramAbstractConsole.a("No connection '%s' defined. Use 'show connections'. Add new connections to '%s'.", new Object[] { paramString2, Sys.t.toString() });
      } 
    } catch (Throwable throwable) {
      Log.a("Error connecting to " + paramString2, throwable);
      paramAbstractConsole.c(throwable.getLocalizedMessage(), new Object[0]);
    } 
  }
}
