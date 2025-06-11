package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import java.sql.ResultSet;
import java.util.List;

public class Exasol extends Dbms {
  public Exasol() {
    super("Exasol");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("SELECT object_name FROM exa_all_objects WHERE object_type='SCHEMA'", new Object[0]));
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance(paramString1);
    Envoy envoy = paramConnector.startEnvoy("Create Database");
    if (envoy != null)
      envoy.close(); 
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if ("decimal".equalsIgnoreCase(paramString1) && paramInt2 == 18 && paramInt3 == 0)
      paramString1 = "integer"; 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {
    if (paramResultSet.getMetaData().getColumnCount() >= 23)
      if ("yes".equalsIgnoreCase(paramResultSet.getString(23)))
        paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_());  
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    Log.a(paramSchema, "PartitionAndDistributionKeys");
    SelectStatement selectStatement1 = paramImporter.d.a("SELECT column_table, column_name \n  FROM sys.exa_all_columns WHERE column_schema=? AND column_partition_key_ordinal_position > 0 ORDER BY column_table, column_partition_key_ordinal_position", new Object[] { paramSchema
          
          .getName() });
    a(paramImporter, paramSchema, selectStatement1, "Part_", IndexType.PARTITION);
    SelectStatement selectStatement2 = paramImporter.d.a("SELECT column_table, column_name \n  FROM sys.exa_all_columns WHERE column_schema=? AND column_is_distribution_key=true ORDER BY column_table, column_ordinal_position", new Object[] { paramSchema
          
          .getName() });
    a(paramImporter, paramSchema, selectStatement2, "Dist_", IndexType.CLUSTER);
  }
  
  public static void a(Importer paramImporter, Schema paramSchema, SelectStatement paramSelectStatement, String paramString, IndexType paramIndexType) {
    try {
      ResultSet resultSet = paramSelectStatement.j();
      Table table = null;
      Index index = null;
      while (resultSet.next()) {
        String str1 = resultSet.getString(1);
        String str2 = resultSet.getString(2);
        if (index == null || !table.getName().equals(str1)) {
          index = null;
          table = paramSchema.getTable(str1);
          if (table != null) {
            index = table.createIndex(paramString + paramString);
            index.setType(paramIndexType);
            paramImporter.a("Table '" + table.ref() + "' distribution keys");
            paramImporter.b();
          } 
        } 
        if (index != null) {
          Column column = (Column)table.columns.getByName(str2);
          if (column != null)
            index.addColumn(column); 
        } 
      } 
      resultSet.close();
    } catch (Exception exception) {
      Log.b(exception);
    } 
  }
  
  public SyncFilter getSynchronizationFilter() {
    return new Exasol$1(this);
  }
  
  private static final Qx a = new Qx(Exasol.class);
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    paramImporter.d.g("CREATE SCHEMA IF NOT EXISTS exa_toolbox");
    paramImporter.d.g(a.a("GetSchemaDDL"));
    paramImporter.d.p();
    boolean bool = true;
    Log.a(paramSchema, "ExasolIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("execute script exa_toolbox.create_table_ddl('" + paramSchema.getName() + "', '" + table.getName() + "', '" + paramSchema.getName() + "', '" + table.getName() + "', true)", new Object[0]);
          try {
            String str = selectStatement.b(1);
            paramDDLParser.splitAndParse(str);
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
