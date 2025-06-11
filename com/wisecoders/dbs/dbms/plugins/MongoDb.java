package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Table$TableType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.generator.JsonScriptGenerator;
import com.wisecoders.dbs.sql.generator.ScriptGenerator;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MongoDb extends Dbms {
  public static final String b = "MongoDb";
  
  public MongoDb() {
    super("MongoDb");
  }
  
  public MongoDb(String paramString) {
    super(paramString);
  }
  
  public void listTableAndViewsNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    Log.a(paramSchema, "ListTableNames");
    String[] arrayOfString = { "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY" };
    ResultSet resultSet = paramStructureImporter.c.a().getTables(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, arrayOfString);
    while (resultSet.next()) {
      String str = resultSet.getString(3);
      if (str != null && !str.startsWith("BIN$"))
        if ("VIEW".equalsIgnoreCase(resultSet.getString(4))) {
          View view = paramSchema.createView(str);
          view.setComment(SqlUtil.unescapeComment(resultSet.getString(5)));
        } else {
          Table table = paramSchema.createTable(str);
          table.setType(Table$TableType.a(resultSet.getString(4)));
          table.setComment(SqlUtil.unescapeComment(resultSet.getString(5)));
          if (resultSet.getMetaData().getColumnCount() >= 11)
            table.setVirtual(resultSet.getBoolean(11)); 
          paramStructureImporter.a("List Collections..." + paramSchema.tables.size());
        }  
      Log.j();
    } 
    resultSet.close();
    paramStructureImporter.a();
  }
  
  public void validateCreatedUnit(TreeUnit paramTreeUnit) {
    if (paramTreeUnit instanceof Table) {
      Table table = (Table)paramTreeUnit;
      if (table.columns.getByName("_id") == null) {
        Column column = table.createColumn("_id", "objectId");
        column.setMandatory(true);
        table.columns.moveFirst(column);
        Index index = table.createIndex("_id_");
        index.addColumn(column);
        index.setType(IndexType.PRIMARY_KEY);
      } 
    } 
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("show databases", new Object[0]));
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    super.importSchemaAdditions(paramImporter, paramSchema);
  }
  
  public void importFinal(Schema paramSchema) {
    for (Table table : paramSchema.tables) {
      for (ForeignKey foreignKey : table.getRelations())
        foreignKey.setVirtual(true); 
    } 
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
    if (paramString2 != null) {
      if (paramString2.startsWith("enum:")) {
        paramColumn.setEnumeration(paramString2.substring("enum:".length()).replaceAll("\"", "'"));
      } else {
        paramColumn.setTypeOptions(paramString2);
      } 
      paramColumn.setDefaultValue(null);
    } 
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance("");
    Envoy envoy = paramConnector.startEnvoy("Create Database");
    try {
      envoy.g("CREATE DATABASE '" + paramString1 + "'");
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
  
  public ScriptGenerator getScriptGenerator(List paramList) {
    return new JsonScriptGenerator(paramList);
  }
  
  private static final Pattern a = Pattern.compile("Unnamed:(\\d+):(\\d+)", 2);
  
  private static final Pattern c = Pattern.compile("at\\s+line\\s*(number)?\\s*(\\d+)\\s+at\\s+column\\s*(number)?\\s*(\\d+)");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    try {
      Matcher matcher = a.matcher(str);
      if (matcher.find())
        return StringUtil.getPositionInString(paramString, Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2))); 
    } catch (NumberFormatException numberFormatException) {
      Log.a(numberFormatException);
    } 
    try {
      Matcher matcher = c.matcher(str);
      if (matcher.find())
        return StringUtil.getPositionInString(paramString, Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(4))); 
    } catch (NumberFormatException numberFormatException) {
      Log.a(numberFormatException);
    } 
    return -1;
  }
  
  public void importViews(Importer paramImporter, Schema paramSchema) {
    for (View view : paramSchema.views) {
      SelectStatement selectStatement = paramImporter.d.a(paramSchema.getName() + ".getViewSource('" + paramSchema.getName() + "')", new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a())
          view.setScript(resultSet.getString(1)); 
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
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> 
      (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnExistsDiff && "_id".equals(paramAbstractDiff.pair.getUnit().getName()));
  }
  
  public String getSelectQuery(Entity paramEntity, boolean paramBoolean) {
    return paramEntity.ref() + ".find()";
  }
}
