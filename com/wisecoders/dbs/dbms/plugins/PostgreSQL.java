package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ForeignKeyImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ImporterUtils;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnDataTypeDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnDefaultValueDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.TableConstraintDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.AbstractFunction;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostgreSQL extends Dbms {
  public static final String b = "PostgreSQL";
  
  private static final Qx a = new Qx(PostgreSQL.class);
  
  public PostgreSQL() {
    super("PostgreSQL");
    a();
  }
  
  protected PostgreSQL(String paramString) {
    super(paramString, "PostgreSQL");
    a();
  }
  
  private void a() {
    this.scriptComparator.a(Pattern.compile("(\\W)enum(\\W)"), "$1ENUM$2");
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    if (paramSchema.project.isDbVersionBelow("12"))
      return false; 
    String str1 = a.a("GetSchemaDDL").replace("__SCHEMA_NAME__", paramSchema.getName()).replace("__IMP_PARTITION_TBL__", "" + this.reverseEngineerPartitionTables.b());
    boolean bool = true;
    String str2 = "SELECT line_prefix||line_text||line_suffix FROM ddl_table ORDER BY object_id, typ, line_no";
    if (paramImporter.c.isSelected(paramSchema) && paramImporter.c.allChildrenAreSelected(paramSchema.tables)) {
      Log.a(paramSchema, "PostgresBulkReverseEngineerUsingDDL");
      paramImporter.a(paramSchema.getSymbolicName() + " '" + paramSchema.getSymbolicName() + "'");
      paramImporter.b();
      String str = str1.replace("__TABLE_NAME__", "");
      paramImporter.d.b(str, new Object[0]).a();
      try {
        SelectStatement selectStatement = paramImporter.d.a("SELECT line_prefix||line_text||line_suffix FROM ddl_table ORDER BY object_id, typ, line_no", new Object[0]);
        try {
          String str3 = selectStatement.b(1);
          paramDDLParser.splitAndParse(str3);
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
      Log.a(paramSchema, "PostgresIndividualReverseEngineerUsingDDL");
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
          paramImporter.b();
          String str = str1.replace("__TABLE_NAME__", table.getName());
          paramImporter.d.b(str, new Object[0]).a();
          try {
            SelectStatement selectStatement = paramImporter.d.a("SELECT line_prefix||line_text||line_suffix FROM ddl_table ORDER BY object_id, typ, line_no", new Object[0]);
            try {
              String str3 = selectStatement.b(1);
              paramDDLParser.splitAndParse(str3);
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
    } 
    paramImporter.d.p();
    return bool;
  }
  
  public void listTableAndViewsNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    try {
      SelectStatement selectStatement = paramStructureImporter.c.a("SELECT n.nspname, c.relname, d.description, \n  CASE c.relkind WHEN 'r' THEN 'table' WHEN 'v' THEN 'view' WHEN 'm' THEN 'materialized view' WHEN 'i' THEN 'index' WHEN 'S' THEN 'sequence' WHEN 's' THEN 'special' WHEN 'f' THEN 'foreign table' END,relispartition \nFROM pg_catalog.pg_class c LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace\nLEFT OUTER JOIN pg_catalog.pg_description d ON ( d.objoid = c.oid AND d.objsubid IS NULL )\nWHERE c.relkind IN ('r','v','f','m') AND n.nspname !~ '^pg_toast' AND n.nspname ~ '^(" + paramSchema



          
          .getName() + ")$' ORDER BY 1,2", new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramStructureImporter.b()) {
          String str1 = resultSet.getString(2);
          String str2 = resultSet.getString(4);
          if (this.reverseEngineerPartitionTables.b() || !resultSet.getBoolean(5)) {
            switch (str2) {
              case "view":
              
              case "materialized view":
              
              default:
                break;
            } 
            Table table = paramSchema.getOrCreateTable(str1);
            table.setComment(resultSet.getString(3));
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
      super.listTableAndViewsNames(paramStructureImporter, paramSchema);
    } 
  }
  
  public void importMaterializedViews(Importer paramImporter, Schema paramSchema) {
    try {
      SelectStatement selectStatement = paramImporter.d.a("SELECT t.relname, a.attname,\n       pg_catalog.format_type(a.atttypid, a.atttypmod),\n       a.attnotnull\nFROM pg_attribute a\n  JOIN pg_class t on a.attrelid = t.oid\n  JOIN pg_namespace s on t.relnamespace = s.oid\nWHERE\n      t.relkind = 'm'\n  AND a.attnum > 0\n  AND NOT a.attisdropped\n  AND s.nspname = ?\nORDER BY a.attnum", new Object[] { paramSchema











            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a()) {
          MaterializedView materializedView = (MaterializedView)paramSchema.materializedViews.getByName(resultSet.getString(1));
          if (materializedView == null)
            materializedView = paramSchema.createMaterializedView(resultSet.getString(1)); 
          Column column = materializedView.createColumn(resultSet.getString(2), DbmsTypes.get(paramImporter.d.e()).getType(resultSet.getString(3)));
          column.setMandatory(resultSet.getBoolean(4));
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
    } catch (Throwable throwable) {
      Log.a("Error loading Materialized view columns", throwable);
      super.importMaterializedViews(paramImporter, paramSchema);
    } 
  }
  
  public String extractDelimiterFromCommand(String paramString) {
    if (paramString.endsWith("$") && paramString.indexOf("$") < paramString.length() - 1)
      return paramString.substring(paramString.indexOf("$")); 
    return null;
  }
  
  public void learnDataTypes(Envoy paramEnvoy) {}
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if (paramInt2 == 131089 || paramInt2 == Integer.MAX_VALUE)
      paramInt2 = DataType.UNSET; 
    if ("bpchar".equalsIgnoreCase(paramString1))
      paramString1 = "char"; 
    if (paramString2 != null && paramString2.toLowerCase().startsWith("nextval('" + paramColumn.table.getName() + "_" + paramColumn.getName() + "_seq)"))
      switch (paramString1) {
        case "bigint":
          paramString1 = "bigserial";
          paramString2 = null;
          break;
        case "int":
          paramString1 = "serial";
          paramString2 = null;
          break;
        case "smallint":
          paramString1 = "smallserial";
          paramString2 = null;
          break;
      }  
    if (("decimal".equals(paramString1) || "numeric".equals(paramString1)) && 
      paramInt2 == 0) {
      paramInt2 = DataType.UNSET;
      paramInt3 = DataType.UNSET;
    } 
    if ("timestamp".equals(paramString1) && paramInt2 == 29)
      paramInt2 = DataType.UNSET; 
    if ("timestamptz".equals(paramString1) && paramInt2 == 35)
      paramInt2 = DataType.UNSET; 
    if (paramInt1 == -1) {
      Log.c("Omit imported data type " + paramInt1 + " " + paramString1);
    } else {
      super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
    } 
  }
  
  public void loadDbVersion(StructureImporter paramStructureImporter, Project paramProject) {
    SelectStatement selectStatement = paramStructureImporter.c.a("SELECT version()", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      if (resultSet.next()) {
        Log.c("Postgres Version is " + resultSet.getString(1));
        Matcher matcher = Pattern.compile("postgresql ([\\.\\d]+).*", 2).matcher(resultSet.getString(1));
        if (matcher.matches())
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
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    SelectStatement selectStatement = paramImporter.d.a("SELECT table_catalog, table_schema, table_name, column_name, column_default, is_identity, identity_generation, generation_expression FROM information_schema.columns WHERE table_schema=? AND ( identity_generation is not null or column_default like 'nextval%' or generation_expression is not null )", new Object[] { paramSchema

          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(3));
        if (table != null) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(4));
          if (column != null) {
            column.setDefaultValue((String)null);
            String str1 = resultSet.getString(5);
            String str2 = resultSet.getString(8);
            if (str2 != null) {
              if (!str2.trim().startsWith("("))
                str2 = "(" + str2 + ")"; 
              if (column.getOptions() == null)
                column.setOptions(("BY DEFAULT".equalsIgnoreCase(resultSet.getString(7)) ? "GENERATED BY DEFAULT AS " : "GENERATED ALWAYS AS ") + ("BY DEFAULT".equalsIgnoreCase(resultSet.getString(7)) ? "GENERATED BY DEFAULT AS " : "GENERATED ALWAYS AS ") + " STORED"); 
              continue;
            } 
            if (resultSet.getBoolean(6) && column.getIdentity() == null) {
              column.setIdentity("BY DEFAULT".equalsIgnoreCase(resultSet.getString(7)) ? "GENERATED BY DEFAULT AS IDENTITY" : "GENERATED ALWAYS AS IDENTITY");
              continue;
            } 
            if (str1 != null && column.getDefaultValue() == null && !column.getDataType().getName().endsWith("serial")) {
              if (str1.startsWith("nextval(") && paramSchema.project.isDbVersionBelow("10"))
                switch (column.getDataType().getName()) {
                  case "integer":
                    column.setDataType(DbmsTypes.get(paramSchema.getDbId()).getType("serial"));
                    str1 = null;
                    break;
                  case "bigint":
                    column.setDataType(DbmsTypes.get(paramSchema.getDbId()).getType("bigserial"));
                    str1 = null;
                    break;
                }  
              column.setDefaultValue(str1);
            } 
          } 
        } 
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
    selectStatement = paramImporter.d.a("SELECT table_catalog, table_schema, table_name, column_name, collation_name\nFROM information_schema.columns where table_schema=? AND collation_name is not null AND collation_name != 'C'", new Object[] { paramSchema
          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(3));
        if (table != null) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(4));
          if (column != null)
            column.setTypeOptions("COLLATE \"" + resultSet.getString(5) + "\""); 
        } 
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
    selectStatement = paramImporter.d.a("SELECT table_name, constraint_name, constraint_type, is_deferrable, initially_deferred FROM \ninformation_schema.table_constraints WHERE table_schema=?", new Object[] { paramSchema
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        if (resultSet.getBoolean(4) || resultSet.getBoolean(5)) {
          String str = resultSet.getBoolean(4) ? "DEFERRABLE " : "";
          if (resultSet.getBoolean(5))
            str = str + "INITIALLY DEFERRED"; 
          str = str.trim();
          Table table = paramSchema.getTable(resultSet.getString(1));
          if (table != null) {
            ForeignKey foreignKey;
            switch (resultSet.getString(3)) {
              case "FOREIGN KEY":
                foreignKey = (ForeignKey)table.getRelations().getByName(resultSet.getString(2));
                if (foreignKey != null)
                  foreignKey.setOptions(str); 
                continue;
              case "CHECK":
                continue;
            } 
            Index index = (Index)table.indexes.getByName(resultSet.getString(2));
            if (index != null)
              index.setOptions(str); 
          } 
        } 
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
    if (paramSchema.project.isDbVersionHigherEqual("10"))
      try {
        selectStatement = paramImporter.d.a("SELECT c.relname as table_name, \n       pg_get_partkeydef(c.oid) as partition_key \nFROM   pg_class c\nWHERE  c.relnamespace::regnamespace::text=? AND c.relkind = 'p'", new Object[] { paramSchema


              
              .getName() });
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            Table table = paramSchema.getTable(resultSet.getString(1));
            if (table != null)
              table.setOptions(((table.getOptions() != null) ? (table.getOptions() + " ") : "") + "PARTITION BY " + ((table.getOptions() != null) ? (table.getOptions() + " ") : "")); 
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
      } catch (SQLException sQLException) {
        Log.b(sQLException);
      }  
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {
    if (paramResultSet.getMetaData().getColumnCount() >= 23) {
      if ("SERIAL".equalsIgnoreCase(paramColumn.getDataType().getName()))
        paramColumn.setDataType(DbmsTypes.get(this.dbId).getOrCreateDataType(4, "integer")); 
      if ("BIGSERIAL".equalsIgnoreCase(paramColumn.getDataType().getName()))
        paramColumn.setDataType(DbmsTypes.get(this.dbId).getOrCreateDataType(-5, "bigint")); 
    } 
  }
  
  public String formatImportedDefaultValue(DataType paramDataType, String paramString) {
    if (paramString != null) {
      if ("NULL".equalsIgnoreCase(paramString))
        return null; 
      if (paramString.toLowerCase().contains("nextval") && ("SERIAL"
        .equalsIgnoreCase(paramDataType.toString()) || "BIGSERIAL".equalsIgnoreCase(paramDataType.toString())))
        return null; 
    } 
    return paramString;
  }
  
  public void listProcedureNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    if (paramSchema.project.isDbVersionHigherEqual("11"))
      try {
        SelectStatement selectStatement = paramStructureImporter.c.a(a("SELECT p.proname, p.oid FROM pg_proc p LEFT JOIN pg_namespace n on p.pronamespace = n.oid LEFT JOIN pg_type t on t.oid = p.prorettype WHERE n.nspname ='${schema}' and p.prokind = 'p' ORDER BY proname", paramSchema), new Object[0]);
        try {
          ImporterUtils.a(paramSchema, selectStatement.j());
          paramStructureImporter.a();
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
    paramStructureImporter.a();
  }
  
  public void listFunctionNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    boolean bool = false;
    if (this.loadFunctionName.p())
      try {
        SelectStatement selectStatement = paramStructureImporter.c.a(a("SELECT p.proname, p.oid FROM pg_proc p LEFT JOIN pg_namespace n on p.pronamespace = n.oid LEFT JOIN pg_type t on t.oid = p.prorettype WHERE n.nspname ='${schema}' " + (


              
              paramSchema.project.isDbVersionBelow("11") ? "AND NOT p.proisagg" : "AND prokind in ( 'f','a','w')") + "ORDER BY proname", paramSchema), new Object[0]);
        try {
          ImporterUtils.b(paramSchema, selectStatement.j());
          bool = true;
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
    if (!bool)
      try {
        ImporterUtils.c(paramStructureImporter, paramSchema);
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
    paramStructureImporter.a();
  }
  
  public void importProcedures(Importer paramImporter, Schema paramSchema) {
    for (Procedure procedure : paramSchema.procedures)
      a(paramImporter, paramSchema, procedure); 
  }
  
  public void importFunctions(Importer paramImporter, Schema paramSchema) {
    for (Function function : paramSchema.functions)
      a(paramImporter, paramSchema, function); 
  }
  
  private void a(Importer paramImporter, Schema paramSchema, AbstractFunction paramAbstractFunction) {
    if (paramImporter.c.isSelected(paramAbstractFunction)) {
      paramImporter.a("Function '" + paramAbstractFunction.ref() + "'");
      try {
        SelectStatement selectStatement = paramImporter.d.a("SELECT pg_get_functiondef(?), obj_description(?) ", new Object[0]);
        try {
          String str1 = paramAbstractFunction.getString(UnitProperty.d);
          if (str1.lastIndexOf("_") > -1)
            str1 = str1.substring(str1.lastIndexOf("_") + 1); 
          selectStatement.a(new StatementParameter(Long.valueOf(Long.parseLong(str1)), -5));
          selectStatement.a(new StatementParameter(Long.valueOf(Long.parseLong(str1)), -5));
          ImporterUtils.a(paramAbstractFunction, selectStatement.j());
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
        paramImporter.d.close();
        SelectStatement selectStatement = paramImporter.d.a("SELECT 'create function ' || proname || ' ' || replace(p.prosrc,E'\\x09',' ')    FROM pg_catalog.pg_proc p        LEFT JOIN pg_catalog.pg_namespace n ON n.oid = p.pronamespace        LEFT JOIN pg_catalog.pg_language l ON l.oid = p.prolang        JOIN pg_catalog.pg_roles r ON r.oid = p.proowner    WHERE p.prorettype <> 'pg_catalog.cstring'::pg_catalog.regtype        AND (p.proargtypes[0] IS NULL        OR   p.proargtypes[0] <> 'pg_catalog.cstring'::pg_catalog.regtype) " + (






            
            paramSchema.project.isDbVersionBelow("11") ? "AND NOT p.proisagg" : "AND prokind<> 'a'") + "       AND n.nspname=? AND proname=?", new Object[] { paramSchema
              
              .getName(), paramAbstractFunction.getName() });
        try {
          ImporterUtils.a(paramAbstractFunction, selectStatement.j());
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
    String str = paramAbstractFunction.getText();
    if (str != null) {
      str = str.replaceAll("/\\* \\*/", "\n");
      if (!str.trim().endsWith(";"))
        str = str + ";"; 
      paramAbstractFunction.setText(str);
    } 
  }
  
  public void importProcedureParameters(Importer paramImporter, Schema paramSchema) {
    if (!paramSchema.procedures.isEmpty())
      try {
        SelectStatement selectStatement = paramImporter.d.a("select proc.specific_schema as procedure_schema,        proc.specific_name,        proc.routine_name as procedure_name,        proc.external_language,        args.parameter_name,        args.parameter_mode,        args.data_type,        args.ordinal_position from information_schema.routines proc left join information_schema.parameters args           on proc.specific_schema = args.specific_schema           and proc.specific_name = args.specific_name where proc.routine_schema=?       and proc.routine_type = 'PROCEDURE' order by procedure_schema,          specific_name,          procedure_name,          args.ordinal_position", new Object[] { paramSchema
















              
              .getName() });
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            for (Procedure procedure : paramSchema.procedures) {
              String str = procedure.getString(UnitProperty.d);
              if (resultSet.getString(2).equals(str) || resultSet.getString(2).equals(procedure.getName() + "_" + procedure.getName())) {
                if (resultSet.getString(6) == null) {
                  procedure.addResultParameter(resultSet.getString(5), -1, resultSet.getString(7), resultSet.getInt(8));
                  continue;
                } 
                procedure.addInputParameter(resultSet.getString(5), -1, resultSet.getString(7), SqlUtil.decodeInOut(resultSet.getString(6)), resultSet.getInt(8));
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
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
  }
  
  public void importFunctionParameters(Importer paramImporter, Schema paramSchema) {
    if (!paramSchema.functions.isEmpty())
      try {
        SelectStatement selectStatement = paramImporter.d.a("select proc.specific_schema as procedure_schema,        proc.specific_name,        proc.routine_name as procedure_name,        proc.external_language,        args.parameter_name,        args.parameter_mode,        args.data_type,        args.ordinal_position from information_schema.routines proc left join information_schema.parameters args           on proc.specific_schema = args.specific_schema           and proc.specific_name = args.specific_name where proc.routine_schema=?       and proc.routine_type = 'FUNCTION' order by procedure_schema,          specific_name,          procedure_name,          args.ordinal_position", new Object[] { paramSchema















              
              .getName() });
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            for (Function function : paramSchema.functions) {
              String str = function.getString(UnitProperty.d);
              if (resultSet.getString(2).equals(str) || resultSet.getString(2).equals(function.getName() + "_" + function.getName())) {
                if (resultSet.getString(6) == null) {
                  function.addResultParameter(resultSet.getString(5), -1, resultSet.getString(7), resultSet.getInt(8));
                  continue;
                } 
                function.addInputParameter(resultSet.getString(5), -1, resultSet.getString(7), SqlUtil.decodeInOut(resultSet.getString(6)), resultSet.getInt(8));
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
      } catch (Throwable throwable) {
        Log.b(throwable);
        try {
          ImporterUtils.b(paramImporter, paramSchema);
        } catch (Throwable throwable1) {
          Log.b(throwable1);
        } 
      }  
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    Result result = new Result();
    paramEnvoy.b("explain " + paramString, result);
    result.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result);
    for (byte b = 0; b < result.A(); b++) {
      Object[] arrayOfObject = result.j(b);
      int i = 0, j = -1;
      String str1 = (String)result.a(b, 0), str2 = null, str3 = null;
      Matcher matcher = Pattern.compile("(\\s*)(->)?(\\s*)(.*)", 2).matcher(str1);
      if (matcher.matches()) {
        i = matcher.group(1).length();
        str1 = matcher.group(4);
        Matcher matcher1 = Pattern.compile("(.*)\\((.*)\\)", 2).matcher(str1);
        if (matcher1.matches()) {
          str3 = matcher1.group(1);
          String str = matcher1.group(2);
          Matcher matcher2 = Pattern.compile("(.*) on (.*)", 2).matcher(str3);
          if (matcher2.matches())
            str2 = matcher2.group(2); 
          Matcher matcher3 = Pattern.compile("cost=(.*)\\.\\.(\\d+)(.*)", 2).matcher(str);
          if (matcher3.matches())
            j = Integer.parseInt(matcher3.group(2)); 
        } 
      } 
      executionPlan.a(i, str1, str3, str2, j, arrayOfObject);
    } 
    return executionPlan;
  }
  
  private static final Pattern c = Pattern.compile("Position: (.*)", 2);
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, (Pattern)null, (Pattern)null, c, (Pattern)null);
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("select datname from pg_database", new Object[0]));
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> {
        if (paramAbstractDiff instanceof ColumnDataTypeDiff) {
          ColumnDataTypeDiff columnDataTypeDiff = (ColumnDataTypeDiff)paramAbstractDiff;
          if (a(columnDataTypeDiff.getColumn(SyncSide.left), columnDataTypeDiff.getColumn(SyncSide.right)) || a(columnDataTypeDiff.getColumn(SyncSide.right), columnDataTypeDiff.getColumn(SyncSide.left)))
            return true; 
        } 
        if (paramAbstractDiff instanceof ColumnDefaultValueDiff) {
          ColumnDefaultValueDiff columnDefaultValueDiff = (ColumnDefaultValueDiff)paramAbstractDiff;
          Column column1 = (Column)columnDefaultValueDiff.pair.getUnit(SyncSide.left);
          Column column2 = (Column)columnDefaultValueDiff.pair.getUnit(SyncSide.right);
          if (("serial".equals(column1.getDataType().getName()) || "bigserial".equals(column1.getDataType().getName()) || "smallserial".equals(column1.getDataType().getName())) && column1.getDefaultValue() == null && column2.getDefaultValue() != null && (column2.getDefaultValue().toLowerCase().startsWith("nextval") || column2.getDefaultValue().toLowerCase().startsWith("generated")))
            return true; 
          ArrayList<String> arrayList = new ArrayList();
          arrayList.add("now()");
          arrayList.add("current_timestamp");
          arrayList.add("current_time");
          arrayList.add("NOW()");
          arrayList.add("CURRENT_TIMESTAMP");
          arrayList.add("CURRENT_TIME");
          if (arrayList.contains(((Column)columnDefaultValueDiff.pair.left).getDefaultValue()) && arrayList.contains(((Column)columnDefaultValueDiff.pair.right).getDefaultValue()))
            return true; 
          String str1 = StringUtil.cutOfSuffix(((Column)columnDefaultValueDiff.pair.left).getDefaultValue(), "::character varying");
          String str2 = StringUtil.cutOfSuffix(((Column)columnDefaultValueDiff.pair.right).getDefaultValue(), "::character varying");
          return (str1 != null && str1.equals(str2));
        } 
        if (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.SequenceExistsDiff && paramAbstractDiff.pair.left == null && paramAbstractDiff.pair.leftParent instanceof Schema && paramAbstractDiff.pair.right != null) {
          for (Table table : ((Schema)paramAbstractDiff.pair.leftParent).tables) {
            for (Column column : table.columns) {
              if (column.getDataType().getName().toLowerCase().endsWith("serial") && (table.getName() + "_" + table.getName() + "_seq").equalsIgnoreCase(paramAbstractDiff.pair.right.getName()))
                return true; 
            } 
          } 
          return false;
        } 
        if (paramAbstractDiff instanceof TableConstraintDiff) {
          TableConstraintDiff tableConstraintDiff = (TableConstraintDiff)paramAbstractDiff;
          return tableConstraintDiff.leftUnitConditionTextIsInsideRightUnitConditionText();
        } 
        return (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.CommentDiff && paramAbstractDiff.pair.right instanceof ForeignKey);
      };
  }
  
  private boolean a(Column paramColumn1, Column paramColumn2) {
    return (a(paramColumn1, paramColumn2, "serial", "integer") || 
      a(paramColumn1, paramColumn2, "bigserial", "bigint") || 
      a(paramColumn1, paramColumn2, "smallserial", "smallint"));
  }
  
  private boolean a(Column paramColumn1, Column paramColumn2, String paramString1, String paramString2) {
    return (paramString1.equalsIgnoreCase(paramColumn1.getDataType().getName()) && paramString2.equalsIgnoreCase(paramColumn2.getDataType().getName()) && ((paramColumn2
      .getTypeOptions() != null && paramColumn2.getTypeOptions().toLowerCase().startsWith("generated")) || (paramColumn2
      .getDefaultValue() != null && paramColumn2.getDefaultValue().toLowerCase().startsWith("nextval"))));
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance("");
    paramString1 = paramString1.toLowerCase();
    Envoy envoy = paramConnector.startEnvoy("Create Database");
    try {
      Connection connection = envoy.c();
      if (!connection.getAutoCommit())
        connection.setAutoCommit(true); 
      SimpleStatement simpleStatement = (new SimpleStatement((Dbms.get("PostgreSQL")).databaseCreate.c_())).set(K.a, quote(paramString1)).set(K.G, paramString2);
      Log.c("Execute: " + String.valueOf(simpleStatement));
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
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "UserDefinedTypesA");
    SelectStatement selectStatement = paramImporter.d.a("SELECT n.nspname as domain_schema,\n    t.typname as domain_name,\n            pg_catalog.format_type(t.typbasetype, t.typtypmod) as data_type,\n    not t.typnotnull as nullable,\n    t.typdefault as default_value,\n    c.conname as constraint_name,\n            pg_catalog.pg_get_constraintdef(c.oid, true) as constraint_definition,\n    obj_description(t.oid) as remarks\n    FROM pg_catalog.pg_type t\n    LEFT JOIN pg_catalog.pg_namespace n ON n.oid = t.typnamespace\n    LEFT JOIN pg_catalog.pg_constraint c ON t.oid = c.contypid\n    WHERE t.typtype = 'd'\n    AND n.nspname = ?", new Object[] { paramSchema












          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1);
        String str2 = resultSet.getString(2);
        String str3 = resultSet.getString(3);
        boolean bool = resultSet.getBoolean(4);
        String str4 = resultSet.getString(5);
        String str5 = resultSet.getString(6);
        String str6 = resultSet.getString(7);
        String str7 = resultSet.getString(8);
        Log.d("Import domain " + str2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE DOMAIN ${name} ");
        if (str3 != null)
          stringBuilder.append(str3).append(" "); 
        if (!bool)
          stringBuilder.append("NOT NULL "); 
        if (str4 != null)
          stringBuilder.append("DEFAULT ").append(str4).append(" "); 
        if (str5 != null)
          stringBuilder.append("CONSTRAINT ").append(str5).append(" "); 
        if (str6 != null)
          stringBuilder.append(StringUtil.escapeForGroovy(str6)); 
        UserDataType userDataType = paramSchema.createUserDataType(str2);
        userDataType.setScript(stringBuilder.toString());
        userDataType.setComment(str7);
        DataType dataType = DbmsTypes.get(this.dbId).getType(str3);
        if (dataType != null)
          userDataType.setJavaType(dataType.getJavaType()); 
        Log.j();
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
    Log.a(paramSchema, "UserDefinedTypesB");
    selectStatement = paramImporter.d.a("SELECT n.nspname as enum_schema, \n  t.typname as enum_name, \n  e.enumlabel as enum_value \nFROM pg_type t \n  JOIN pg_enum e on (t.oid = e.enumtypid) \n  JOIN pg_catalog.pg_namespace n ON (n.oid = t.typnamespace) \nWHERE n.nspname=?", new Object[] { paramSchema






          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      String str = null;
      StringBuilder stringBuilder = new StringBuilder();
      while (resultSet.next()) {
        String str1 = resultSet.getString(2);
        String str2 = resultSet.getString(3);
        Log.d("Import enum " + str1);
        if (StringUtil.areEqual(str, str1)) {
          stringBuilder.append(", '").append(str2).append("' ");
        } else {
          if (stringBuilder.length() > 0) {
            UserDataType userDataType = paramSchema.createUserDataType(str);
            userDataType.setScript("CREATE TYPE ${name} AS ENUM ( " + String.valueOf(stringBuilder) + " )");
            stringBuilder.delete(0, stringBuilder.length());
          } 
          stringBuilder.append("'").append(str2).append("'");
        } 
        str = str1;
        Log.j();
      } 
      if (!stringBuilder.isEmpty()) {
        UserDataType userDataType = paramSchema.createUserDataType(str);
        userDataType.setScript("CREATE TYPE ${name} AS ENUM ( " + String.valueOf(stringBuilder) + " )");
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
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "AllPgIndexes");
    byte b = 0;
    if (paramSchema.project.isDbVersionHigherEqual("9.6"))
      try {
        SelectStatement selectStatement1 = paramImporter.d.a("SELECT   tmp.TABLE_SCHEM,   tmp.TABLE_NAME,   tmp.INDEX_NAME,   tmp.IS_PK,   tmp.NON_UNIQUE,   tmp.TYPE,   trim(both '\\\"' from pg_catalog.pg_get_indexdef(tmp.CI_OID, tmp.ORDINAL_POSITION, false)) AS COLUMN_NAME,   tmp.ORDINAL_POSITION,   CASE tmp.AM_NAME     WHEN 'btree' THEN CASE tmp.I_INDOPTION[tmp.ORDINAL_POSITION - 1] & 1       WHEN 1 THEN 'D' ELSE 'A' END     ELSE NULL   END AS ASC_OR_DESC,   tmp.CARDINALITY,   tmp.PAGES,   tmp.FILTER_CONDITION,  tmp.DESCRIPTION FROM (   SELECT NULL AS TABLE_CAT, n.nspname AS TABLE_SCHEM,     ct.relname AS TABLE_NAME, i.indisprimary AS IS_PK, NOT i.indisunique AS NON_UNIQUE,     NULL AS INDEX_QUALIFIER,     ci.relname AS INDEX_NAME,     CASE i.indisclustered       WHEN true THEN 1       ELSE CASE am.amname       WHEN 'hash' THEN 2       ELSE 3       END      END AS TYPE,      (information_schema._pg_expandarray(i.indkey)).n AS ORDINAL_POSITION,     ci.reltuples AS CARDINALITY,     ci.relpages AS PAGES,     pg_catalog.pg_get_expr(i.indpred, i.indrelid) AS FILTER_CONDITION,     ci.oid AS CI_OID,     i.indoption AS I_INDOPTION,     am.amname AS AM_NAME,     pg_catalog.obj_description(ci.oid, 'pg_class') AS DESCRIPTION FROM pg_catalog.pg_class ct   JOIN pg_catalog.pg_namespace n ON (ct.relnamespace = n.oid)   JOIN pg_catalog.pg_index i ON (ct.oid = i.indrelid)   JOIN pg_catalog.pg_class ci ON (ci.oid = i.indexrelid)   JOIN pg_catalog.pg_am am ON (ci.relam = am.oid)   WHERE n.nspname = ? ) AS tmp", new Object[] { paramSchema












































              
              .getName() });
        try {
          ResultSet resultSet = selectStatement1.j();
          Index index = null;
          while (resultSet.next()) {
            String str1 = resultSet.getString(2);
            String str2 = resultSet.getString(3);
            IndexType indexType = resultSet.getBoolean(4) ? IndexType.PRIMARY_KEY : (resultSet.getBoolean(5) ? IndexType.NORMAL : IndexType.UNIQUE_INDEX);
            String str3 = resultSet.getString(7);
            int i = resultSet.getInt(8);
            boolean bool = "D".equals(resultSet.getString(9));
            String str4 = resultSet.getString(12);
            String str5 = resultSet.getString(13);
            if (i == 0 || index == null || !index.getName().equals(str2) || !index.getEntity().getName().equals(str1)) {
              index = null;
              Table table = paramSchema.getTable(str1);
              if (table != null) {
                index = table.createIndex(str2);
                index.setType(indexType);
                if (str4 != null)
                  index.setOptions("WHERE " + str4); 
                Log.j();
                b++;
              } 
            } 
            if (index != null) {
              Column column = index.getEntity().getColumnByNameOrPath(str3);
              if (column == null) {
                column = index.getEntity().createColumn(str3, DbmsTypes.get(paramImporter.b).getDataType(12));
                column.setSpec(AttributeSpec.functional);
              } 
              index.addColumn(column);
              if (bool)
                index.setColumnOptions(column, "DESC"); 
              if (str5 != null)
                index.setComment(str5); 
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
      } catch (Exception exception) {
        Log.b(exception);
      }  
    if (b == 0)
      super.importIndexes(paramImporter, paramSchema); 
    Log.a(paramSchema, "AllPgUniqueKeys");
    SelectStatement selectStatement = paramImporter.d.a("SELECT rel.relname, conname FROM pg_catalog.pg_constraint con\n     INNER JOIN pg_catalog.pg_class rel ON rel.oid = con.conrelid\n     INNER JOIN pg_catalog.pg_namespace nsp ON nsp.oid = connamespace\nWHERE nsp.nspname = ? ", new Object[] { paramSchema




          
          .getName() });
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
  
  public List importFks(Importer paramImporter, Schema paramSchema) {
    List list = ForeignKeyImporter.a(paramImporter, paramSchema);
    if (list.isEmpty())
      return super.importFks(paramImporter, paramSchema); 
    return list;
  }
}
