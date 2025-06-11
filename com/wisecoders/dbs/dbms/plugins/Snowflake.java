package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.Dbms$TableCache;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Snowflake extends Dbms {
  public static final String a = "Snowflake";
  
  public Snowflake() {
    super("Snowflake");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.b || paramJdbcUrlParam == JdbcUrlParam.c || paramJdbcUrlParam == JdbcUrlParam.d || paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    paramEnvoy.b(false);
    switch (Snowflake$1.a[paramJdbcUrlParam.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      default:
        break;
    } 
    String str = "SHOW DATABASES";
    ArrayList<String> arrayList = new ArrayList();
    SelectStatement selectStatement = paramEnvoy.a(str, new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      if (resultSet != null) {
        while (resultSet.next()) {
          String str1 = resultSet.getString("name");
          if (str1 != null)
            arrayList.add(str1); 
        } 
        resultSet.close();
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
    return arrayList;
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    ArrayList<Dbms$SchemaCatalogEntry> arrayList = new ArrayList();
    try {
      SelectStatement selectStatement = paramEnvoy.a("SHOW SCHEMAS", new Object[0]);
      try {
        selectStatement.c("Import catalogs and schemas");
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next())
          arrayList.add(new Dbms$SchemaCatalogEntry(resultSet.getString("name"), resultSet.getString("database_name"))); 
        resultSet.close();
        ArrayList<Dbms$SchemaCatalogEntry> arrayList1 = arrayList;
        if (selectStatement != null)
          selectStatement.close(); 
        return arrayList1;
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
      paramEnvoy.q();
      return super.listSchemasAndCatalogs(paramEnvoy);
    } 
  }
  
  private static final Pattern b = Pattern.compile("[\\w]*\\((.*)\\)");
  
  public void listTableAndViewsNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    String str = null;
    try {
      SelectStatement selectStatement = paramStructureImporter.c.a("select current_warehouse()", new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        if (resultSet.next())
          str = resultSet.getString(1); 
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
      if (str == null) {
        selectStatement = paramStructureImporter.c.a("show warehouses", new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          if (resultSet.next())
            str = resultSet.getString(1); 
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
        if (str != null)
          paramStructureImporter.c.b("USE WAREHOUSE \"" + str + "\"", new Object[0]).a(); 
      } 
    } catch (SQLException sQLException) {
      Log.b(sQLException);
    } 
    try {
      SelectStatement selectStatement = paramStructureImporter.c.a("SELECT table_name, table_type, is_transient, row_count, retention_time, comment FROM " + paramSchema
          
          .getCatalogName() + ".INFORMATION_SCHEMA.TABLES WHERE table_schema=?", new Object[] { paramSchema
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramStructureImporter.b()) {
          String str1 = resultSet.getString(1);
          if ("VIEW".equalsIgnoreCase(resultSet.getString(2))) {
            View view;
            if ((view = paramSchema.getView(str1)) == null)
              view = paramSchema.createView(str1); 
            view.setComment(resultSet.getString(6));
            continue;
          } 
          Table table;
          if ((table = paramSchema.getTable(str1)) == null)
            table = paramSchema.createTable(str1); 
          if ("yes".equalsIgnoreCase(resultSet.getString(3)))
            table.setSpecificationOptions("TRANSIENT"); 
          table.setRowCount(resultSet.getLong(4));
          int i = resultSet.getInt(5);
          if (i != 1)
            table.setOptions("DATA_RETENTION_TIME_IN_DAYS=" + i); 
          table.setComment(resultSet.getString(6));
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
    } catch (Exception exception) {
      Log.b(exception);
      super.listTableAndViewsNames(paramStructureImporter, paramSchema);
    } 
  }
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    boolean bool = false;
    Log.f("Import all columns in one operation...");
    Dbms$TableCache dbms$TableCache = new Dbms$TableCache(paramSchema);
    AbstractTable abstractTable = null;
    try {
      SelectStatement selectStatement = paramImporter.d.a("SELECT table_name, column_name, is_nullable, data_type, ifnull( ifnull(character_maximum_length, numeric_precision), datetime_precision), numeric_scale, column_default, character_set_name, collation_catalog, collation_schema, collation_name, is_identity, identity_generation, identity_start, identity_increment, comment FROM " + paramSchema





          
          .getCatalogName() + ".INFORMATION_SCHEMA.COLUMNS WHERE table_schema=? ORDER BY table_name, ordinal_position", new Object[] { paramSchema
            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a()) {
          String str = resultSet.getString(1);
          AbstractTable abstractTable1 = dbms$TableCache.a(str);
          if (abstractTable1 != null) {
            String str1 = resultSet.getString(2);
            Column column = (Column)abstractTable1.getAttributes().getByName(str1);
            String str2 = resultSet.getString(4);
            int i = resultSet.getInt(5);
            int j = resultSet.getInt(6);
            String str3 = resultSet.getString(7);
            if (("timestamp_tz".equalsIgnoreCase(str2) || "timestamp_ntz".equalsIgnoreCase(str2) || "timestamp_ltz".equalsIgnoreCase(str2)) && i == 9)
              i = DataType.UNSET; 
            if (column == null)
              column = abstractTable1.createColumn(str1, DbmsTypes.get(paramImporter.b).getType(str2)); 
            column.setLength(i);
            column.setDecimal(j);
            column.setDefaultValue(str3);
            column.setMandatory("no".equalsIgnoreCase(resultSet.getString(3)));
            column.setComment(SqlUtil.unescapeComment(resultSet.getString(16)));
            if ("yes".equalsIgnoreCase(resultSet.getString(12)))
              if (resultSet.getInt(14) != 1 || resultSet.getInt(15) != 1) {
                column.setOptions("AUTOINCREMENT START " + resultSet.getInt(14) + " INCREMENT " + resultSet.getInt(15));
              } else {
                column.setOptions("AUTOINCREMENT");
              }  
            bool = true;
            if (abstractTable1 != abstractTable) {
              paramImporter.a("Table '" + abstractTable1.ref() + "' columns");
              paramImporter.b();
              abstractTable = abstractTable1;
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
    } catch (Exception exception) {
      Log.b(exception);
      bool = false;
    } 
    if (!bool)
      super.importColumns(paramImporter, paramSchema); 
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    ArrayList<Index> arrayList = new ArrayList();
    Log.a(paramSchema, "SnowflakeImportIndexes");
    SelectStatement selectStatement = paramImporter.d.a("SELECT table_name, constraint_name   FROM " + paramSchema
        
        .getCatalogName() + ".INFORMATION_SCHEMA.TABLE_CONSTRAINTS   WHERE constraint_type = 'UNIQUE'", new Object[] { paramSchema
          
          .getCatalogName(), paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1);
        String str2 = resultSet.getString(2);
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Index index = (Index)table.indexes.getByName(str2);
          if (index == null) {
            index = table.createIndex(str2);
            index.setType(IndexType.UNIQUE_KEY);
            arrayList.add(index);
          } else if (index.getType() == IndexType.UNIQUE_INDEX) {
            index.setType(IndexType.UNIQUE_KEY);
          } 
        } 
        Log.j();
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
    try {
      selectStatement = paramImporter.d.a("SELECT table_name, clustering_key FROM " + paramSchema
          .getCatalogName() + ".INFORMATION_SCHEMA.TABLES WHERE table_schema=? AND clustering_key IS NOT NULL", new Object[] { paramSchema
            .getName() });
      try {
        selectStatement.c("Import clustering keys");
        Log.a(paramSchema, "SnowflakeImportClusterKeys");
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          Table table = (Table)paramSchema.tables.getByName(resultSet.getString(1));
          String str = resultSet.getString(2);
          if (table != null && str != null) {
            Matcher matcher = b.matcher(str);
            if (matcher.matches())
              str = matcher.group(1); 
            Index index = table.createIndex("CLUSTER_KEY_" + table.getName());
            index.setType(IndexType.PARTITION);
            for (String str1 : str.split(",")) {
              str1 = str1.trim();
              Column column = (Column)table.columns.getByName(str1);
              if (column == null)
                table.createColumn(str1, DbmsTypes.get(paramSchema.getDbId()).getDataType(12)); 
              index.addColumn(column);
            } 
          } 
          Log.j();
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
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    Log.a(paramSchema, "SnowflakeUniqueConstraints");
    for (Index index : arrayList) {
      SelectStatement selectStatement1 = paramImporter.d.a("SELECT get_ddl('TABLE','" + index
          .getEntity().ref() + "')", new Object[0]);
      try {
        ResultSet resultSet = selectStatement1.j();
        while (resultSet.next()) {
          String str = resultSet.getString(1);
          if (str != null) {
            String str1 = "constraint\\s+" + EscapeChars.forRegex(index.getName()) + "\\s+unique\\s*\\(([^\\)]*)\\)";
            Pattern pattern = Pattern.compile(str1, 2);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
              String[] arrayOfString = matcher.group(1).split(",");
              for (String str2 : arrayOfString) {
                Column column = index.getEntity().getColumnByNameOrPath(str2.trim());
                if (column != null)
                  index.addColumn(column); 
              } 
            } 
          } 
          Log.j();
        } 
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
  }
  
  private static final Pattern c = Pattern.compile("line[\\s:]*(\\d+)[\\s:]*at[\\s:]*position[\\s:]*(\\d+)");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = SqlUtil.getExceptionText(paramSQLException);
    Matcher matcher = c.matcher(str);
    if (matcher.find())
      return StringUtil.getPositionInString(paramString, Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2))); 
    return -1;
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    paramImporter.d.b("USE " + paramSchema.getCatalogName(), new Object[0]).a();
    if (paramImporter.c.isSelected(paramSchema) && paramImporter.c.allChildrenAreSelected(paramSchema.tables)) {
      paramImporter.a(paramSchema.getSymbolicName() + " '" + paramSchema.getSymbolicName() + "'");
      Log.a(paramSchema, "SnowflakeBulkReverseEngineerUsingDDL");
      paramImporter.b();
      try {
        SelectStatement selectStatement = paramImporter.d.a("SELECT GET_DDL ( 'SCHEMA', '" + paramSchema.getName() + "', true)", new Object[0]);
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
    } else {
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          Log.a(paramSchema, "SnowflakeIndividualReverseEngineerUsingDDL");
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' using DDL");
          paramImporter.b();
          try {
            SelectStatement selectStatement = paramImporter.d.a("SELECT GET_DDL( 'TABLE', '" + table.getSchema().getName() + "." + table.getName() + "', true)", new Object[0]);
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
      for (View view : paramSchema.views) {
        if (paramImporter.c.isSelected(view)) {
          Log.a(paramSchema, "SnowflakeIndividualReverseEngineerUsingDDL");
          paramImporter.a(view.getSymbolicName() + " '" + view.getSymbolicName() + "' using DDL");
          paramImporter.b();
          try {
            SelectStatement selectStatement = paramImporter.d.a("SELECT GET_DDL( 'VIEW', '" + view.getSchema().getName() + "." + view.getName() + "', true)", new Object[0]);
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
    } 
    return bool;
  }
  
  public void importViewColumns(Importer paramImporter, Schema paramSchema) {
    for (View view : paramSchema.views) {
      SelectStatement selectStatement = paramImporter.d.a("SELECT column_name, data_type, character_maximum_length, numeric_precision, numeric_scale FROM \"" + paramSchema.getCatalogName() + "\".INFORMATION_SCHEMA.COLUMNS WHERE table_schema=? AND table_name=? ORDER BY ordinal_position", new Object[] { paramSchema
            .getName(), view.getName() });
      try {
        selectStatement.a(4000);
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          Column column = (Column)view.columns.getByName(resultSet.getString(1));
          if (column == null)
            column = view.createColumn(resultSet.getString(1), DbmsTypes.get(this.dbId).getType(resultSet.getString(2))); 
          column.setLength((resultSet.getInt(3) > 0) ? resultSet.getInt(3) : resultSet.getInt(4));
          column.setDecimal(resultSet.getInt(5));
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
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance(" ");
    Envoy envoy = paramConnector.startEnvoy("Snowflake create database");
    try {
      envoy.p();
      SimpleStatement simpleStatement = (new SimpleStatement((Dbms.get("Snowflake")).databaseCreate.c_())).set(K.a, quote(paramString1.toUpperCase())).set(K.G, paramString2);
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
