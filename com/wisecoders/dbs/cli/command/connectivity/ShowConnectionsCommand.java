package com.wisecoders.dbs.cli.command.connectivity;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorGroup;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ShowConnectionsCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (CLIConnectorManager.a().size() == 0) {
      paramAbstractConsole.a("Disconnected", new Object[0]);
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      for (Connector connector : CLIConnectorManager.a())
        stringBuilder.append(connector.getName()).append(" "); 
      paramAbstractConsole.a("Connected: " + String.valueOf(stringBuilder), new Object[0]);
    } 
    paramAbstractConsole.a("\nAvailable:", new Object[0]);
    for (Connector connector : CLIConnectorManager.getConnectors())
      paramAbstractConsole.d("'" + connector.getName() + "' ", new Object[0]); 
    paramAbstractConsole.a("\nGroups:", new Object[0]);
    for (ConnectorGroup connectorGroup : CLIConnectorManager.a) {
      paramAbstractConsole.d(String.format("%-10s : ", new Object[] { connectorGroup.getName() }), new Object[0]);
      for (Connector connector : connectorGroup.getConnectors())
        paramAbstractConsole.d(connector.getName() + " ", new Object[0]); 
      paramAbstractConsole.l();
    } 
  }
}
