package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@DoNotObfuscate
public class Databricks extends Dbms {
  public static final String NAME = "Databricks";
  
  public Databricks() {
    super("Databricks");
  }
  
  public Databricks(String paramString) {
    super(paramString);
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    ArrayList<Dbms$SchemaCatalogEntry> arrayList = new ArrayList();
    try {
      ArrayList<String> arrayList1 = new ArrayList();
      SelectStatement selectStatement = paramEnvoy.a("SHOW CATALOGS", new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          arrayList1.add(resultSet.getString(1));
          Log.c("Databricks catalog: " + resultSet.getString(1));
        } 
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
      if (arrayList1.size() == 1 && "spark_catalog".equals(arrayList1.get(0)))
        arrayList1.clear(); 
      for (String str : arrayList1) {
        paramEnvoy.b("USE CATALOG '" + str + "'", new Object[0]).a();
        SelectStatement selectStatement1 = paramEnvoy.a("SHOW DATABASES", new Object[0]);
        try {
          ResultSet resultSet = selectStatement1.j();
          while (resultSet.next())
            arrayList.add(new Dbms$SchemaCatalogEntry(resultSet.getString(1), str)); 
          if (selectStatement1 != null)
            selectStatement1.close(); 
        } catch (Throwable throwable) {
          if (selectStatement1 != null)
            try {
              selectStatement1.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } 
    } catch (Exception exception) {
      try {
        SelectStatement selectStatement = paramEnvoy.a("SHOW DATABASES", new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next())
            arrayList.add(new Dbms$SchemaCatalogEntry(resultSet.getString(1), null)); 
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
      } catch (Exception exception1) {
        Log.b(exception1);
      } 
    } 
    if (arrayList.isEmpty()) {
      SelectStatement selectStatement = paramEnvoy.a("SELECT catalog_name, schema_name, comment FROM information_schema.schemata", new Object[0]);
      try {
        selectStatement.c("Import catalogs and schemas");
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          Dbms$SchemaCatalogEntry dbms$SchemaCatalogEntry = new Dbms$SchemaCatalogEntry(resultSet.getString(2), resultSet.getString(1));
          arrayList.add(dbms$SchemaCatalogEntry);
          dbms$SchemaCatalogEntry.c = resultSet.getString(3);
        } 
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
    } 
    return arrayList;
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    if (paramSchema.getCatalogName() != null)
      try {
        paramImporter.d.b("USE CATALOG " + paramSchema.getCatalogName(), new Object[0]).a();
      } catch (SQLException sQLException) {} 
    Log.a(paramSchema, "DatabricksIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("SHOW CREATE TABLE " + paramSchema.getName() + "." + table.getName(), new Object[0]);
          try {
            String str = selectStatement.b(1);
            paramDDLParser.splitAndParse(str);
            if (table.columns.isEmpty())
              table.markForDeletion(); 
            Log.j();
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
  
  public void importViews(Importer paramImporter, Schema paramSchema) {
    if (paramSchema.getCatalogName() != null)
      try {
        paramImporter.d.b("USE CATALOG " + paramSchema.getCatalogName(), new Object[0]).a();
      } catch (SQLException sQLException) {} 
    for (View view : paramSchema.views) {
      SelectStatement selectStatement = paramImporter.d.a("SHOW CREATE TABLE " + paramSchema.getName() + "." + view.getName(), new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a()) {
          view.setScript(resultSet.getString(1));
          view.deduceVirtualFks();
        } 
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
    } 
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
      str = str.replaceFirst("\\?", Matcher.quoteReplacement(stringBuilder1.toString()));
    } 
    stringBuilder.append(Dbms.get("Databricks").quote(paramColumn.getName())).append(" ").append(str);
    if (paramInt == 0 && paramColumn.isMandatory())
      stringBuilder.append(" NOT NULL"); 
    if (paramColumn.getIdentity() != null)
      stringBuilder.append(" ").append(paramColumn.getIdentity()); 
    if (paramColumn.getDefaultValue() != null)
      stringBuilder.append(" DEFAULT ").append(paramColumn.getDefaultValue()); 
    if (paramColumn.getComment() != null)
      stringBuilder.append(" COMMENT '").append(Dbms.get("Databricks").escapeString(paramColumn.getComment())).append("'"); 
    return stringBuilder.toString();
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> {
        if (paramAbstractDiff instanceof RenameDiff) {
          RenameDiff renameDiff = (RenameDiff)paramAbstractDiff;
          AbstractUnit abstractUnit = renameDiff.pair.left;
          if (abstractUnit instanceof Index) {
            Index index = (Index)abstractUnit;
            if (index.getType() == IndexType.PARTITION);
          } 
        } 
        return false;
      };
  }
}
