package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ForeignKeyEntry;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sqlite extends Dbms {
  public Sqlite() {
    super("Sqlite");
    if (StringUtil.isEmptyTrim(System.getProperty("java.io.tmpdir")))
      try {
        System.setProperty("java.io.tmpdir", System.getProperty("user.home"));
      } catch (Throwable throwable) {} 
  }
  
  public static final Pattern a = Pattern.compile("\\((.*)\\)", 2);
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if (paramString1 != null) {
      Matcher matcher = a.matcher(paramString1);
      StringBuilder stringBuilder = new StringBuilder();
      while (matcher.find()) {
        matcher.appendReplacement(stringBuilder, "");
        try {
          String[] arrayOfString = matcher.group(1).split("[,]");
          paramInt2 = (arrayOfString.length > 0) ? Integer.parseInt(arrayOfString[0].trim()) : DataType.UNSET;
          paramInt3 = (arrayOfString.length > 1) ? Integer.parseInt(arrayOfString[1].trim()) : DataType.UNSET;
        } catch (Exception exception) {
          Log.a("Error formatting Sqlite data type ", exception);
        } 
      } 
      matcher.appendTail(stringBuilder);
      paramString1 = stringBuilder.toString();
    } 
    if (paramInt2 == 2000000000 && paramInt3 == 10) {
      paramInt2 = DataType.UNSET;
      paramInt3 = DataType.UNSET;
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {
    if (paramResultSet.getMetaData().getColumnCount() > 23 && ("YES".equals(paramResultSet.getString(23)) || paramResultSet.getBoolean(23)))
      paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_()); 
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    Envoy envoy = paramConnector.startEnvoy("Sqlite create database");
    try {
      if (!paramString1.contains("/") && !paramString1.contains("\\") && !paramString1.contains(":"))
        paramString1 = System.getProperty("user.home") + System.getProperty("user.home") + File.separator; 
      paramConnector.setInstance(paramString1);
      envoy.p();
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
    paramConnector.closeAllEnvoysAndSsh();
  }
  
  public void listFunctionNames(StructureImporter paramStructureImporter, Schema paramSchema) {}
  
  public List importFks(Importer paramImporter, Schema paramSchema) {
    List<ForeignKeyEntry> list = super.importFks(paramImporter, paramSchema);
    if (paramSchema.tables.size() > 1 && list.isEmpty())
      for (Table table : paramSchema.tables) {
        SelectStatement selectStatement = paramImporter.d.a("SELECT * FROM pragma_foreign_key_list('" + table.getName() + "');", new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          ForeignKey foreignKey = null;
          while (resultSet.next()) {
            Table table1 = paramSchema.getTable(resultSet.getString("table"));
            if (table1 != null) {
              if (resultSet.getInt("seq") == 0)
                foreignKey = null; 
              Column column1 = table.getColumnByNameOrPath(resultSet.getString("from"));
              Column column2 = table1.getColumnByNameOrPath(resultSet.getString("to"));
              if (column2 != null && column1 != null) {
                if (foreignKey == null || foreignKey.getTargetEntity() != table1)
                  foreignKey = table.createRelation("fk_" + table.foreignKeys.size()); 
                foreignKey.addColumns(column1, column2);
                String str1 = resultSet.getString("on_delete");
                if (str1 != null)
                  foreignKey.setDeleteAction(ForeignKeyAction.fromString(str1)); 
                String str2 = resultSet.getString("on_update");
                if (str2 != null)
                  foreignKey.setDeleteAction(ForeignKeyAction.fromString(str2)); 
                list.add(new ForeignKeyEntry(table, table1, "fk_" + table.foreignKeys.size(), column2, column1, ForeignKeyAction.fromString(str1), ForeignKeyAction.fromString(str2), list.size()));
              } 
            } 
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
    return list;
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> ((paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff && paramAbstractDiff.pair.getUnit() instanceof ForeignKey) || paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.ForeignKeyCascadeDiff);
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    Result result = new Result();
    paramEnvoy.b("EXPLAIN QUERY PLAN " + paramString, result);
    result.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result);
    for (byte b = 0; b < result.A(); b++) {
      Number number1 = (Number)result.a(b, 0), number2 = (Number)result.a(b, 1), number3 = (Number)result.a(b, 2);
      String str = (String)result.a(b, 3);
      executionPlan.a((number1 != null) ? number1.intValue() : -1, 
          (number2 != null) ? number2.intValue() : -1, str, str, null, 1.0D, result

          
          .j(b));
    } 
    return executionPlan;
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    Log.a(paramSchema, "SqliteIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("SELECT sql FROM SQLITE_SCHEMA WHERE ( TYPE='table' OR TYPE='index' OR TYPE='trigger' ) AND tbl_name=? ", new Object[] { table.getName() });
          try {
            ResultSet resultSet = selectStatement.j();
            while (resultSet.next())
              paramDDLParser.splitAndParse(resultSet.getString(1)); 
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
}
