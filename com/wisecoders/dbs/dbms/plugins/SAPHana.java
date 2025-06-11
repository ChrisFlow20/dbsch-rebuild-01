package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;

public class SAPHana extends Dbms {
  public SAPHana() {
    super("SAPHana");
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    Log.a(paramSchema, "SAPHanaIndividualReverseEngineerUsingDDL");
    long l = System.currentTimeMillis();
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' using DDL");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("call get_object_definition( ?, ? )", new Object[] { table.getSchema().getName(), table.getName() });
          try {
            String str = selectStatement.b(5);
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
        if (System.currentTimeMillis() - l > 30000L) {
          l = System.currentTimeMillis();
          paramImporter.d.p();
        } 
      } 
    } 
    return bool;
  }
  
  public void importProcedures(Importer paramImporter, Schema paramSchema) {
    for (Procedure procedure : paramSchema.procedures) {
      try {
        SelectStatement selectStatement = paramImporter.d.a("call get_object_definition( ?, ?)", new Object[] { paramSchema.getName(), procedure.getName() });
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next())
            procedure.setText(resultSet.getString(5)); 
          resultSet.close();
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
      } 
    } 
  }
}
