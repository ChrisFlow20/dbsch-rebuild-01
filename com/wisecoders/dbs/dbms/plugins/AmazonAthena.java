package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;

@DoNotObfuscate
public class AmazonAthena extends Dbms {
  public static final String NAME = "AmazonAthena";
  
  public AmazonAthena() {
    super("AmazonAthena");
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    Log.a(paramSchema, "AmazonAthenaReverseEngineerUsingDDL");
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
      } 
    } 
    return bool;
  }
  
  @GroovyMethod
  public static String printColumn(Column paramColumn, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramInt > 0) {
      stringBuilder.append("\n");
      stringBuilder.append("\t".repeat(paramInt + 1));
    } 
    String str = paramColumn.getTypeString();
    if (paramColumn.getChildEntity() != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      boolean bool = false;
      for (Column column : (paramColumn.getChildEntity()).columns) {
        if (bool)
          stringBuilder1.append(","); 
        bool = true;
        stringBuilder1.append(printColumn(column, paramInt + 1));
      } 
      str = str.replaceFirst("\\?", stringBuilder1.toString());
    } 
    stringBuilder.append(paramColumn.getName()).append((paramInt > 0) ? ":" : " ").append(str);
    if (paramColumn.isMandatory())
      stringBuilder.append(" NOT NULL"); 
    return stringBuilder.toString();
  }
}
