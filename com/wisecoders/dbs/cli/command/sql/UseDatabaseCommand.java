package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class UseDatabaseCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    if (paramString2.length() == 0)
      throw new ParameterException(); 
    String str = paramString2.trim();
    for (Dbms$SchemaCatalogEntry dbms$SchemaCatalogEntry : Dbms.get(paramConnector.dbId).listSchemasAndCatalogs(paramEnvoy)) {
      if (str.equalsIgnoreCase(dbms$SchemaCatalogEntry.b)) {
        paramConnector.setActiveCliSchema(dbms$SchemaCatalogEntry.b);
        if (paramConnector.getCliProject().getSchema(dbms$SchemaCatalogEntry.b, null) == null) {
          TreeSelection treeSelection = new TreeSelection();
          treeSelection.select((paramConnector.getCliProject()).schemas);
          new Importer(paramConnector.getCliProject(), treeSelection, paramEnvoy);
        } 
        paramAbstractConsole.a("Current database switched to " + dbms$SchemaCatalogEntry.b, new Object[0]);
        return true;
      } 
    } 
    paramAbstractConsole.a("No schema '" + str + "s' found. Use 'show schemas' to list available options.", new Object[0]);
    return true;
  }
}
