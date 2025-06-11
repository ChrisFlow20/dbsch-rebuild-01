package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ForeignKeyEntry;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlServer extends Dbms {
  public static final String b = "SqlServer";
  
  public SqlServer(String paramString) {
    super(paramString, "SqlServer");
  }
  
  public SqlServer(String paramString1, String paramString2) {
    super(paramString1, paramString2);
  }
  
  public SqlServer() {
    super("SqlServer");
  }
  
  public void loadDbVersion(StructureImporter paramStructureImporter, Project paramProject) {
    SelectStatement selectStatement = paramStructureImporter.c.a("SELECT @@version", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      if (resultSet.next()) {
        Log.c("SqlServer Version is " + resultSet.getString(1));
        Matcher matcher = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)", 2).matcher(resultSet.getString(1));
        while (matcher.find())
          paramProject.setDbVersion(matcher.group(1)); 
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
  
  private static final Qx a = new Qx(SqlServer.class);
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    if (paramSchema.project.isDbVersionBelow("12.0.2000"))
      return false; 
    String str = a.a("GetSchemaDDL");
    str = str.replace("__SCHEMA_NAME__", paramSchema.getName());
    boolean bool = true;
    paramImporter.d.b("USE [" + paramSchema.getCatalogName() + "]", new Object[0]).a();
    if (paramImporter.c.isSelected(paramSchema) && paramImporter.c.allChildrenAreSelected(paramSchema.tables)) {
      Log.a(paramSchema, "SqlServerBulkReverseEngineerUsingDDL");
      paramImporter.a(paramSchema.getSymbolicName() + " '" + paramSchema.getSymbolicName() + "'");
      paramImporter.b();
      String str1 = str.replace("__TABLE_NAME__", "");
      try {
        SelectStatement selectStatement = paramImporter.d.a(str1, new Object[0]);
        try {
          String str2 = selectStatement.b(1);
          paramDDLParser.splitAndParse(str2);
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
      Log.a(paramSchema, "SqlServerIndividualReverseEngineerUsingDDL");
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
          paramImporter.b();
          String str1 = str.replace("__TABLE_NAME__", table.getName());
          try {
            SelectStatement selectStatement = paramImporter.d.a(str1, new Object[0]);
            try {
              String str2 = selectStatement.b(1);
              paramDDLParser.splitAndParse(str2);
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
    } 
    paramImporter.d.p();
    return bool;
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    String str1 = (Dbms.get("SqlServer")).importRemarksProperty.c_();
    String str2 = a.a("GetExtendedProperties").replaceAll("\\$catalog", paramSchema.getCatalogName()).replaceAll("\\$schema", paramSchema.getName()).replaceAll("\\$property", str1);
    try {
      SelectStatement selectStatement1 = paramImporter.d.a(str2, new Object[0]);
      try {
        ResultSet resultSet = selectStatement1.j();
        while (resultSet.next()) {
          Sequence sequence;
          Procedure procedure;
          Function function;
          String str3 = resultSet.getString("comment");
          String str4 = resultSet.getString("objname");
          String str5;
          Table table = ((str5 = resultSet.getString("tablename")) != null) ? paramSchema.getTable(str5) : null;
          switch (resultSet.getString("type")) {
            case "SCHEMA":
              paramSchema.setComment(str3);
            case "TABLE":
              if (table != null)
                table.setComment(str3); 
            case "SEQUENCE":
              sequence = (Sequence)paramSchema.sequences.getByName(str4);
              if (sequence != null)
                sequence.setComment(str3); 
            case "PROCEDURE":
              procedure = (Procedure)paramSchema.procedures.getByName(str4);
              if (procedure != null)
                procedure.setComment(str3); 
            case "FUNCTION":
              function = (Function)paramSchema.functions.getByName(str4);
              if (function != null)
                function.setComment(str3); 
            case "COLUMN":
              if (table != null) {
                Column column = (Column)table.columns.getByName(str4);
                if (column != null)
                  column.setComment(str3); 
              } 
            case "INDEX":
              if (table != null) {
                Index index = (Index)table.indexes.getByName(str4);
                if (index != null)
                  index.setComment(str3); 
              } 
            case "FK":
              if (table != null) {
                ForeignKey foreignKey = (ForeignKey)table.foreignKeys.getByName(str4);
                if (foreignKey != null)
                  foreignKey.setComment(str3); 
              } 
          } 
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
    } catch (SQLException sQLException) {
      SelectStatement selectStatement1 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
          .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, null, null, null, null) WHERE name=?", new Object[] { paramSchema
            .getName(), str1 });
      try {
        ResultSet resultSet = selectStatement1.j();
        while (resultSet.next())
          paramSchema.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
        resultSet.close();
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
      selectStatement1 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
          .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'table', default, null, null) WHERE name=?", new Object[] { paramSchema
            .getName(), str1 });
      try {
        ResultSet resultSet = selectStatement1.j();
        while (resultSet.next()) {
          Entity entity = (Entity)paramSchema.tables.getByName(resultSet.getString(1));
          if (entity != null)
            entity.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
        } 
        resultSet.close();
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
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          SelectStatement selectStatement2 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
              .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'table', ?, 'column', default) WHERE name=?", new Object[] { paramSchema
                .getName(), table.getName(), str1 });
          try {
            ResultSet resultSet = selectStatement2.j();
            while (resultSet.next()) {
              Column column = (Column)table.columns.getByName(resultSet.getString(1));
              if (column != null)
                column.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
            } 
            resultSet.close();
            if (selectStatement2 != null)
              selectStatement2.close(); 
          } catch (Throwable throwable) {
            if (selectStatement2 != null)
              try {
                selectStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } 
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          SelectStatement selectStatement2 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
              .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'table', ?, 'index', default) WHERE name=?", new Object[] { paramSchema
                .getName(), table.getName(), str1 });
          try {
            ResultSet resultSet = selectStatement2.j();
            while (resultSet.next()) {
              Index index = (Index)table.indexes.getByName(resultSet.getString(1));
              if (index != null)
                index.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
            } 
            resultSet.close();
            if (selectStatement2 != null)
              selectStatement2.close(); 
          } catch (Throwable throwable) {
            if (selectStatement2 != null)
              try {
                selectStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } 
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          SelectStatement selectStatement2 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
              .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'table', ?, 'constraint', default) WHERE name=?", new Object[] { paramSchema
                .getName(), table.getName(), str1 });
          try {
            ResultSet resultSet = selectStatement2.j();
            while (resultSet.next()) {
              ForeignKey foreignKey = (ForeignKey)table.foreignKeys.getByName(resultSet.getString(1));
              if (foreignKey != null)
                foreignKey.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
            } 
            resultSet.close();
            if (selectStatement2 != null)
              selectStatement2.close(); 
          } catch (Throwable throwable) {
            if (selectStatement2 != null)
              try {
                selectStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } 
      for (Sequence sequence : paramSchema.sequences) {
        if (paramImporter.c.isSelected(sequence)) {
          SelectStatement selectStatement2 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
              .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'sequence', ?, null, null) WHERE name=?", new Object[] { paramSchema
                .getName(), sequence.getName(), str1 });
          try {
            ResultSet resultSet = selectStatement2.j();
            while (resultSet.next())
              sequence.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
            resultSet.close();
            if (selectStatement2 != null)
              selectStatement2.close(); 
          } catch (Throwable throwable) {
            if (selectStatement2 != null)
              try {
                selectStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } 
      for (Function function : paramSchema.functions) {
        if (paramImporter.c.isSelected(function)) {
          SelectStatement selectStatement2 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
              .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'function', ?, null, null) WHERE name=?", new Object[] { paramSchema
                .getName(), function.getName(), str1 });
          try {
            ResultSet resultSet = selectStatement2.j();
            while (resultSet.next())
              function.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
            resultSet.close();
            if (selectStatement2 != null)
              selectStatement2.close(); 
          } catch (Throwable throwable) {
            if (selectStatement2 != null)
              try {
                selectStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } 
      for (Procedure procedure : paramSchema.procedures) {
        if (paramImporter.c.isSelected(procedure)) {
          SelectStatement selectStatement2 = paramImporter.d.a("SELECT objname, CONVERT(nvarchar(max),value) FROM [" + paramSchema
              .getCatalogName() + "].sys.fn_listextendedproperty (NULL, 'schema', ?, 'procedure', ?, null, null) WHERE name=?", new Object[] { paramSchema
                .getName(), procedure.getName(), str1 });
          try {
            ResultSet resultSet = selectStatement2.j();
            while (resultSet.next())
              procedure.setComment(SqlUtil.unescapeComment(resultSet.getString(2))); 
            resultSet.close();
            if (selectStatement2 != null)
              selectStatement2.close(); 
          } catch (Throwable throwable) {
            if (selectStatement2 != null)
              try {
                selectStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } 
    } 
    a(paramImporter, paramSchema);
    SelectStatement selectStatement = paramImporter.d.a("SELECT t.name table_name, c.name column_name, collation_name\nFROM [" + paramSchema
        .getCatalogName() + "].sys.columns c  \nINNER JOIN [" + paramSchema
        .getCatalogName() + "].sys.tables t ON (c.object_id = t.object_id) WHERE convert(sysname,schema_name(t.schema_id))=? AND collation_name is not null AND collation_name != SERVERPROPERTY('Collation') ", new Object[] { paramSchema


          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(1));
        if (table != null) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(2));
          if (column != null)
            column.setOptions("COLLATE " + resultSet.getString(3)); 
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
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if (paramString1 != null && paramString1.toUpperCase().endsWith(" IDENTITY")) {
      paramString1 = paramString1.substring(0, paramString1.length() - " IDENTITY".length());
      paramColumn.setIdentity("IDENTITY");
    } 
    if ((paramInt2 == 1073741823 || paramInt2 == Integer.MAX_VALUE) && paramInt3 == 0) {
      if ("varchar".equalsIgnoreCase(paramString1)) {
        paramString1 = "varchar(max)";
        paramInt2 = 0;
      } 
      if ("nvarchar".equalsIgnoreCase(paramString1)) {
        paramString1 = "nvarchar(max)";
        paramInt2 = 0;
      } 
      if ("varbinary".equalsIgnoreCase(paramString1)) {
        paramString1 = "varbinary(max)";
        paramInt2 = 0;
      } 
    } 
    if ("time".equalsIgnoreCase(paramString1) && paramInt2 > 7)
      if (paramInt2 == 8) {
        paramInt2 = 0;
      } else {
        paramInt2 -= 9;
      }  
    if ("datetime2".equalsIgnoreCase(paramString1) && paramInt2 > 20)
      paramInt2 -= 20; 
    if ("datetime2".equalsIgnoreCase(paramString1) && paramInt2 == 19)
      paramInt2 = 0; 
    if ("datetime2".equalsIgnoreCase(paramString1) && paramInt2 == 7)
      paramInt2 = DataType.UNSET; 
    if (paramString2 != null)
      while (paramString2.startsWith("(") && paramString2.endsWith(")"))
        paramString2 = paramString2.substring(1, paramString2.length() - 1);  
    if ("_MASK_TO_V2".equalsIgnoreCase(paramColumn.getName()))
      paramColumn.markForDeletion(); 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  private static final Pattern c = Pattern.compile("near[\\s:]*'(.+)'");
  
  private ExecutionPlan d;
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, (Pattern)null, (Pattern)null, (Pattern)null, c);
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    ArrayList<String> arrayList = new ArrayList();
    DatabaseMetaData databaseMetaData = paramEnvoy.a();
    ResultSet resultSet = databaseMetaData.getCatalogs();
    while (resultSet.next())
      arrayList.add(resultSet.getString(1)); 
    return arrayList;
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance(" ");
    Envoy envoy = paramConnector.startEnvoy("SqlServer create database");
    try {
      envoy.p();
      SimpleStatement simpleStatement = (new SimpleStatement((Dbms.get("SqlServer")).databaseCreate.c_())).set(K.a, quote(paramString1)).set(K.G, paramString2);
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
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      SelectStatement selectStatement = paramEnvoy.a("SELECT name FROM sys.databases ORDER BY name", new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          String str = resultSet.getString(1);
          arrayList.add(str);
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
      ResultSet resultSet = paramEnvoy.a().getCatalogs();
      if (resultSet != null) {
        while (resultSet.next())
          arrayList.add(resultSet.getString(1)); 
        resultSet.close();
      } 
    } 
    ArrayList<Dbms$SchemaCatalogEntry> arrayList1 = new ArrayList();
    for (String str : arrayList) {
      try {
        SelectStatement selectStatement = paramEnvoy.a("SELECT name FROM [" + str + "].sys.schemas ORDER BY name", new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            String str1 = resultSet.getString(1);
            arrayList1.add(new Dbms$SchemaCatalogEntry(str1, str));
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
    } 
    return arrayList1;
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> 
      
      ((paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff && paramAbstractDiff.pair.getUnit() instanceof Index && ((Index)paramAbstractDiff.pair.getUnit()).getType() == IndexType.INDEX2) || (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.SqlExistsDiff && paramAbstractDiff.pair.right != null && ("sp_GetDDL".equalsIgnoreCase(paramAbstractDiff.pair.right.getName()) || "sp_GetSchemaDDL".equalsIgnoreCase(paramAbstractDiff.pair.right.getName()))));
  }
  
  public String formatImportedDefaultValue(DataType paramDataType, String paramString) {
    if (paramString != null) {
      if ("NULL".equalsIgnoreCase(paramString))
        return null; 
      for (byte b = 0; b < 2; b++) {
        if (paramString.startsWith("(") && paramString.endsWith(")"))
          paramString = paramString.substring(1, paramString.length() - 1); 
      } 
    } 
    return paramString;
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    SelectStatement selectStatement = paramImporter.d.a("SELECT table_name, constraint_name FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS where table_catalog=? AND table_schema=? and constraint_type='UNIQUE' ", new Object[] { paramSchema
          
          .getCatalogName(), paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1);
        String str2 = resultSet.getString(2);
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Index index = (Index)table.indexes.getByName(str2);
          if (index != null && index.getType() == IndexType.UNIQUE_INDEX)
            index.setType(IndexType.UNIQUE_KEY); 
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
    try {
      selectStatement = paramImporter.d.a("SELECT        o.name AS TableName,        cl.name AS ColumnName,        i.name AS KeyIndexName,        c.name AS FTCatalogName,        f.name AS FileGroupName  FROM sys.objects o     INNER JOIN [" + paramSchema





          
          .getCatalogName() + "].sys.fulltext_indexes fi         ON o.[object_id] = fi.[object_id]     INNER JOIN [" + paramSchema
          
          .getCatalogName() + "].sys.fulltext_index_columns ic         ON ic.[object_id] = o.[object_id]     INNER JOIN [" + paramSchema
          
          .getCatalogName() + "].sys.columns cl         ON ic.column_id = cl.column_id            AND ic.[object_id] = cl.[object_id]     INNER JOIN [" + paramSchema

          
          .getCatalogName() + "].sys.fulltext_catalogs c         ON fi.fulltext_catalog_id = c.fulltext_catalog_id     INNER JOIN [" + paramSchema
          
          .getCatalogName() + "].sys.filegroups f         ON fi.data_space_id = f.data_space_id     INNER JOIN [" + paramSchema
          
          .getCatalogName() + "].sys.indexes i         ON fi.unique_index_id = i.index_id            AND fi.[object_id] = i.[object_id] WHERE SCHEMA_NAME(o.schema_id)=?", new Object[] { paramSchema

            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          String str1 = resultSet.getString(1);
          String str2 = "FullText-" + str1;
          Table table = paramSchema.getTable(str1);
          if (table != null) {
            Index index = table.createIndex(str2);
            index.setType(IndexType.PARTITION);
            Column column = table.getColumnByNameOrPath(resultSet.getString(2));
            if (column != null)
              index.addColumn(column); 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("KEY INDEX ").append(resultSet.getString(3));
            if (StringUtil.isFilledTrim(Integer.valueOf(4)))
              stringBuilder.append(" ON ").append(resultSet.getString(4)); 
            if (StringUtil.isFilledTrim(Integer.valueOf(5)))
              stringBuilder.append(", FILEGROUP ").append(resultSet.getString(5)); 
            index.setOptions(stringBuilder.toString());
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
    } catch (SQLException sQLException) {
      Log.b(sQLException);
    } 
  }
  
  public List importFks(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "AllForeignKeys");
    ArrayList<ForeignKeyEntry> arrayList = new ArrayList();
    try {
      SelectStatement selectStatement = paramImporter.d.a("SELECT\n            PKTABLE_QUALIFIER   = convert(sysname,db_name()),\n            PKTABLE_OWNER       = convert(sysname,schema_name(o1.schema_id)),\n            PKTABLE_NAME        = convert(sysname,o1.name),\n            PKCOLUMN_NAME       = convert(sysname,c1.name),\n            FKTABLE_QUALIFIER   = convert(sysname,db_name()),\n            FKTABLE_OWNER       = convert(sysname,schema_name(o2.schema_id)),\n            FKTABLE_NAME        = convert(sysname,o2.name),\n            FKCOLUMN_NAME       = convert(sysname,c2.name),\n            KEY_SEQ             = isnull(convert(smallint,k.constraint_column_id), convert(smallint,0)),\n            UPDATE_RULE         = convert(smallint,\n                                            case ObjectProperty(f.object_id, 'CnstIsUpdateCascade')\n                                            when 1 then 0\n                                           else        1\n                                            end),\n            DELETE_RULE         = convert(smallint,\n                                            case ObjectProperty(f.object_id, 'CnstIsDeleteCascade')\n                                            when 1 then 0\n                                            else        1\n                                            end),\n            FK_NAME             = convert(sysname,object_name(f.object_id)),\n            PK_NAME             = convert(sysname,i.name),\n            DEFERRABILITY       = convert(smallint, 7)   -- SQL_NOT_DEFERRABLE\n        from\n[" + paramSchema























          
          .getCatalogName() + "].sys.foreign_keys f\ninner join [" + paramSchema
          .getCatalogName() + "].sys.foreign_key_columns k on k.constraint_object_id = f.object_id\ninner join [" + paramSchema
          .getCatalogName() + "].sys.indexes i on f.referenced_object_id = i.object_id and f.key_index_id = i.index_id\ninner join [" + paramSchema
          .getCatalogName() + "].sys.objects o1 on o1.object_id = f.referenced_object_id\ninner join [" + paramSchema
          .getCatalogName() + "].sys.objects o2 on o2.object_id = f.parent_object_id\ninner join [" + paramSchema
          .getCatalogName() + "].sys.columns c1 on c1.object_id = f.referenced_object_id and c1.column_id = k.referenced_column_id\ninner join [" + paramSchema
          .getCatalogName() + "].sys.columns c2 on c2.object_id = f.parent_object_id and c2.column_id = k.parent_column_id\nwhere schema_name(o2.schema_id) = ?order by 1, 2, 3, 9, 4", new Object[] { paramSchema

            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          String str1 = resultSet.getString(5), str2 = resultSet.getString(6);
          Schema schema1 = paramSchema.project.getSchema(str1, str2);
          String str3 = resultSet.getString(1), str4 = resultSet.getString(2);
          Schema schema2 = paramSchema.project.getSchema(str3, str4);
          if (schema1 == null) {
            Log.f("SYNC: importing Fks failed to find schema " + ((str2 != null) ? str2 : str1) + ", therefore " + String.valueOf(paramSchema) + " will be used instead.");
            schema1 = paramSchema;
          } 
          if (schema2 == null) {
            Log.f("SYNC: importing Fks failed to find schema " + ((str4 != null) ? str4 : str3) + ", therefore " + String.valueOf(paramSchema) + " will be used instead.");
            schema2 = paramSchema;
          } 
          String str5 = resultSet.getString(3), str6 = resultSet.getString(7);
          Table table1 = (Table)schema2.tables.getByName(str5);
          Table table2 = (Table)schema1.tables.getByName(str6);
          if (table1 == null) {
            Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema1) + ". " + str6);
          } else if (table2 == null) {
            Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema1) + ". " + str6);
          } else if (paramImporter.c.isSelected(table2)) {
            Column column1 = (Column)table1.columns.getByName(resultSet.getString(4));
            Column column2 = table2.getColumnByNameOrPath(resultSet.getString(8));
            if (column2 == null && resultSet.getString(8) != null) {
              Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table2) + "." + resultSet.getString(8));
            } else if (column1 == null && resultSet.getString(4) != null) {
              Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table1) + "." + resultSet.getString(4));
            } else {
              ForeignKeyAction foreignKeyAction1 = ForeignKeyAction.decode(resultSet.getShort(11));
              ForeignKeyAction foreignKeyAction2 = ForeignKeyAction.decode(resultSet.getShort(10));
              if (foreignKeyAction1 == ForeignKeyAction.restrict)
                foreignKeyAction1 = ForeignKeyAction.noAction; 
              if (foreignKeyAction2 == ForeignKeyAction.restrict)
                foreignKeyAction2 = ForeignKeyAction.noAction; 
              arrayList.add(new ForeignKeyEntry(table1, table2, resultSet.getString(12), column1, column2, foreignKeyAction1, foreignKeyAction2, resultSet.getInt(9)));
            } 
          } 
          Log.j();
        } 
        resultSet.close();
        if (arrayList.isEmpty()) {
          List list = super.importFks(paramImporter, paramSchema);
          if (selectStatement != null)
            selectStatement.close(); 
          return list;
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
    } catch (SQLException sQLException) {
      return super.importFks(paramImporter, paramSchema);
    } 
    return arrayList;
  }
  
  public void importViewColumns(Importer paramImporter, Schema paramSchema) {
    for (View view : paramSchema.views) {
      if (paramImporter.c.isSelected(view)) {
        SelectStatement selectStatement = paramImporter.d.a("SELECT schema_name(v.schema_id) as schema_name, object_name(c.object_id) as view_name,     c.name as column_name,\n    type_name(user_type_id) as data_type,\n    c.max_length,\n    c.precision\nFROM [" + paramSchema



            
            .getCatalogName() + "].sys.columns c\nJOIN [" + paramSchema
            .getCatalogName() + "].sys.views v \n    on v.object_id = c.object_id\nWHERE schema_name(v.schema_id)=? and object_name(c.object_id)=? \n ORDER by schema_name,\n    view_name,\n    column_id", new Object[] { paramSchema




              
              .getName(), view.getName() });
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            Column column = (Column)view.columns.getByName(resultSet.getString(3));
            if (column == null)
              column = view.createColumn(resultSet.getString(3), DbmsTypes.get(this.dbId).getDataType(12)); 
            column.setDataType(DbmsTypes.get(this.dbId).getType(resultSet.getString(4)));
            column.setLength(resultSet.getInt(5));
            column.setDecimal(resultSet.getInt(6));
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
  }
  
  private void a(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "ComputedColumns");
    SelectStatement selectStatement = paramImporter.d.a("SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, CASE DATA_TYPE  WHEN 'char' THEN CASE CHARACTER_MAXIMUM_LENGTH WHEN -1 THEN CHARACTER_MAXIMUM_LENGTH ELSE CONVERT(VARCHAR(1000), CHARACTER_MAXIMUM_LENGTH) END WHEN 'nchar'THEN CASE CHARACTER_MAXIMUM_LENGTH WHEN -1 THEN CHARACTER_MAXIMUM_LENGTH ELSE CONVERT(VARCHAR(1000), CHARACTER_MAXIMUM_LENGTH) END WHEN 'varchar' THEN CASE CHARACTER_MAXIMUM_LENGTH WHEN -1 THEN CHARACTER_MAXIMUM_LENGTH ELSE CONVERT(VARCHAR(1000), CHARACTER_MAXIMUM_LENGTH) END WHEN 'nvarchar' THEN CASE CHARACTER_MAXIMUM_LENGTH WHEN -1 THEN CHARACTER_MAXIMUM_LENGTH ELSE CONVERT(VARCHAR(1000), CHARACTER_MAXIMUM_LENGTH) END WHEN 'decimal' THEN NUMERIC_PRECISION WHEN 'float' THEN NUMERIC_PRECISION END AS [Precision], CASE DATA_TYPE WHEN 'decimal' THEN NUMERIC_SCALE WHEN 'float' THEN NUMERIC_SCALE END AS [Scale] , IC.IS_NULLABLE AS [Nullable] , CC.definition AS [Caclulated Field Formula], COLUMNPROPERTY(OBJECT_ID(TABLE_NAME), COLUMN_NAME, 'IsIdentity') AS [Identity] , COLUMN_DEFAULT AS [Default Value] FROM INFORMATION_SCHEMA.COLUMNS IC\nJOIN [" + paramSchema
















        
        .getCatalogName() + "].sys.computed_columns CC \nON ( IC.COLUMN_NAME = CC.name AND IC.TABLE_NAME=Object_Name(CC.OBJECT_ID)) WHERE TABLE_SCHEMA=?", new Object[] { paramSchema

          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = (Table)paramSchema.tables.getByName(resultSet.getString(1));
        if (table != null && paramImporter.c.isSelected(table)) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(2));
          if (column == null) {
            column = table.createColumn(resultSet.getString(2), DbmsTypes.get(paramImporter.d.e()).getOrCreateDataType(-1, resultSet.getString(3)));
            if (resultSet.getInt(4) > 0)
              column.setLength(resultSet.getInt(4)); 
          } 
          String str = resultSet.getString(7);
          if (StringUtil.isFilledTrim(str)) {
            column.setSpec(AttributeSpec.computed);
            column.setDefinition(str);
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
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    String str = "/* DBS=" + (int)(Math.random() * 1.0E7D) + " */";
    Result result = new Result();
    paramEnvoy.b(str + str, result);
    SqlServer$1 sqlServer$1 = new SqlServer$1(this);
    paramEnvoy.b(this.explainPlanView.c_() + "AND text like '%" + this.explainPlanView.c_() + "%'", sqlServer$1);
    return this.d;
  }
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    SelectStatement selectStatement = paramImporter.d.a("select t1.name, t2.name as type, t1.precision, t1.scale, t1.is_nullable\nfrom [" + paramSchema
        .getCatalogName() + "].sys.types t1\njoin [" + paramSchema
        .getCatalogName() + "].sys.types t2 on t2.system_type_id = t1.system_type_id and t2.is_user_defined = 0\nwhere t1.is_user_defined = 1 and t2.name <> 'sysname'\norder by t1.name", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        UserDataType userDataType = (UserDataType)paramSchema.userDataTypes.getByName(resultSet.getString(1));
        if (userDataType == null) {
          userDataType = paramSchema.createUserDataType(resultSet.getString(1));
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("CREATE TYPE ${name} FROM ").append(resultSet.getString(2));
          if (resultSet.getInt(3) > 0) {
            stringBuilder.append("(").append(resultSet.getInt(3));
            if (resultSet.getInt(4) > 0)
              stringBuilder.append(",").append(resultSet.getInt(4)); 
            stringBuilder.append(")");
          } 
          if (!resultSet.getBoolean(5))
            stringBuilder.append(" NOT NULL"); 
          userDataType.setScript(stringBuilder.toString());
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
}
