package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;

public class ClickHouse extends Dbms {
  public static final String a = "ClickHouse";
  
  public ClickHouse() {
    super("ClickHouse");
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    Log.a(paramSchema, "ClickHouseIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("SHOW CREATE TABLE " + table.ref(), new Object[0]);
          try {
            String str = selectStatement.b(1);
            paramDDLParser.splitAndParse(str);
            if (selectStatement != null)
              selectStatement.close(); 
          } catch (Throwable throwable) {
            if (selectStatement != null)
              try {
                selectStatement.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } catch (Exception exception) {
          Log.b(exception);
          bool = false;
        } 
        Log.j();
      } 
    } 
    return bool;
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance(" ");
    Envoy envoy = paramConnector.startEnvoy("ClickHouse create database");
    try {
      envoy.p();
      SimpleStatement simpleStatement = (new SimpleStatement((Dbms.get("ClickHouse")).databaseCreate.c_())).set(K.a, quote(paramString1.toUpperCase())).set(K.G, paramString2);
      envoy.g(simpleStatement.toString());
      if (envoy != null)
        envoy.close(); 
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    paramConnector.setInstance(paramString1);
  }
}
