package com.wisecoders.dbs.cli.command.connectivity;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorGroup;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class ConnectionGroupCommand extends Command {
  private static final Pattern b = Pattern.compile("(\\S+)\\s*(\\S+)", 2);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    Matcher matcher;
    if ((matcher = b.matcher(paramString2)).matches()) {
      ConnectorGroup connectorGroup = CLIConnectorManager.a(matcher.group(1));
      StringTokenizer stringTokenizer = new StringTokenizer(matcher.group(2), " ,;");
      while (stringTokenizer.hasMoreTokens()) {
        String str = stringTokenizer.nextToken();
        Connector connector = CLIConnectorManager.getByName(str);
        if (connector == null) {
          ConnectorGroup connectorGroup1 = CLIConnectorManager.b(str);
          if (connectorGroup1 == null)
            throw new ParameterException("Connector '" + str + "' not defined"); 
          connectorGroup.addAll(connectorGroup1);
          continue;
        } 
        connectorGroup.addConnector(connector);
      } 
      if (connectorGroup.isEmpty())
        throw new ParameterException("Group cannot be empty"); 
      paramAbstractConsole.c("Ok", new Object[0]);
    } else {
      throw new ParameterException("Connection list cannot be matched");
    } 
  }
}
