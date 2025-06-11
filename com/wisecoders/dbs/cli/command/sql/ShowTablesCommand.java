package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.sql.SQLException;

@DoNotObfuscate
public class ShowTablesCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    if ((paramConnector.getCliProject()).schemas.isEmpty())
      try {
        new Importer(paramConnector.getCliProject(), new TreeSelection(), paramEnvoy);
      } catch (Exception exception) {
        throw new SQLException("Error loading schema structure.", exception);
      }  
    Schema schema = paramConnector.getActiveCliSchema();
    if (schema == null)
      throw new SQLException("No active database set. Use 'USE DATABASE <name>' to set."); 
    return false;
  }
}
