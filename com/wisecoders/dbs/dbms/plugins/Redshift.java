package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ImporterUtils;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redshift extends Dbms {
  public static final String a = "Redshift";
  
  public Redshift() {
    super("Redshift");
  }
  
  public void listTableAndViewsNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    super.listTableAndViewsNames(paramStructureImporter, paramSchema);
    SelectStatement selectStatement = paramStructureImporter.c.a("select distinct tablename from pg_table_def where schemaname=?", new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next() && !paramStructureImporter.b())
        paramSchema.getOrCreateTable(resultSet.getString(1)); 
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
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    super.importColumns(paramImporter, paramSchema);
    UpdateStatement updateStatement = paramImporter.d.b("set search_path=\"$user\"," + paramSchema.getName(), new Object[0]);
    try {
      updateStatement.a();
      if (updateStatement != null)
        updateStatement.close(); 
    } catch (Throwable throwable) {
      if (updateStatement != null)
        try {
          updateStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    SelectStatement selectStatement = paramImporter.d.a("select tablename, \"column\", distkey, sortkey, encoding, type from pg_table_def where schemaname=? order by tablename, sortkey", new Object[] { paramSchema
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next() && !paramImporter.a()) {
        Table table = (Table)paramSchema.tables.getByName(resultSet.getString(1));
        if (table != null && paramImporter.c.isSelected(table)) {
          Column column = (Column)table.getAttributes().getByName(resultSet.getString(2));
          if (column != null) {
            if (resultSet.getBoolean(3)) {
              Index index = table.getIndexByType(IndexType.PARTITION);
              if (index == null) {
                index = table.createIndex("Distribution");
                index.setType(IndexType.PARTITION);
              } 
              index.addColumn(column);
            } 
            int i = resultSet.getInt(4);
            if (i != 0) {
              Index index = table.getIndexByType(IndexType.SORT);
              if (index == null) {
                index = table.createIndex("Sorting");
                index.setType(IndexType.SORT);
                if (i < 0) {
                  index.setOptions("INTERLEAVED");
                } else {
                  index.setOptions("COMPOUND");
                } 
              } 
              index.addColumn(column);
            } 
            String str = resultSet.getString(5);
            if (StringUtil.isFilledTrim(str) && !"none".equalsIgnoreCase(str))
              column.setOptions("encode " + str); 
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
    try {
      selectStatement = paramImporter.d.a("select trim(nspname) as schemaname,trim(relname) as tablename,\nCASE WHEN reldiststyle = 0 THEN 'EVEN'::text \n     WHEN reldiststyle = 1 THEN 'KEY'::text \n     WHEN reldiststyle = 8 THEN 'ALL'::text \n     WHEN releffectivediststyle = 10 THEN 'AUTO(ALL)'::text \n     WHEN releffectivediststyle = 11 THEN 'AUTO(EVEN)'::text ELSE ''::text END as diststyle,\nreloid as tableid,reldiststyle,releffectivediststyle, relcreationtime \nfrom pg_class_info a left join pg_namespace b on a.relnamespace=b.oid where trim(nspname)=?", new Object[] { paramSchema





            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a()) {
          Table table = (Table)paramSchema.tables.getByName(resultSet.getString(2));
          if (table != null) {
            String str = resultSet.getString(3);
            Index index = table.getIndexByType(IndexType.PARTITION);
            if (StringUtil.isFilledTrim(str) && !"KEY".equals(str) && index != null)
              index.setOptions("DISTSTYLE " + str); 
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
    } catch (SQLException sQLException) {
      Log.b(sQLException);
    } 
  }
  
  public void loadDbVersion(StructureImporter paramStructureImporter, Project paramProject) {
    SelectStatement selectStatement = paramStructureImporter.c.a("SELECT version()", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      if (resultSet.next()) {
        Log.c("Redshift version is " + resultSet.getString(1));
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
    SelectStatement selectStatement = paramImporter.d.a("SELECT table_catalog, table_schema, table_name, column_name, column_default FROM information_schema.columns WHERE table_schema=? AND column_default like 'default_identity%' ", new Object[] { paramSchema

          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(3));
        if (table != null) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(4));
          if (column != null)
            column.setIdentity("GENERATED BY DEFAULT AS IDENTITY"); 
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
    selectStatement = paramImporter.d.a("SELECT table_name, constraint_name, column_name FROM    pg_constraint AS pgc    JOIN pg_namespace nsp ON nsp.oid = pgc.connamespace    JOIN pg_class cls ON pgc.conrelid = cls.oid    JOIN information_schema.constraint_column_usage ccu ON pgc.conname = ccu.constraint_name AND nsp.nspname = ccu.constraint_schema WHERE contype='u' AND table_schema=?", new Object[] { paramSchema




          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(1));
        if (table != null) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(3));
          if (column != null) {
            Index index = (Index)table.indexes.getByName(resultSet.getString(2));
            if (index == null) {
              index = table.createIndex(resultSet.getString(2));
              index.setType(IndexType.UNIQUE_KEY);
            } 
            index.addColumn(column);
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
  
  private static final Pattern b = Pattern.compile("Position:\\s*(\\d*)", 2);
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, null, null, b, null);
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
  
  public void importProcedures(Importer paramImporter, Schema paramSchema) {
    for (Procedure procedure : paramSchema.procedures) {
      if (paramImporter.c.isSelected(procedure)) {
        paramImporter.a("Function '" + procedure.ref() + "'");
        SelectStatement selectStatement = paramImporter.d.a("SELECT pg_get_functiondef(?) ", new Object[0]);
        try {
          String str1 = procedure.getString(UnitProperty.d);
          if (str1.indexOf("_") > 0)
            str1 = str1.substring(str1.lastIndexOf("_") + 1); 
          selectStatement.a(new StatementParameter(Long.valueOf(Long.parseLong(str1)), -5));
          ImporterUtils.a(procedure, selectStatement.j());
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
      String str = procedure.getText();
      if (str != null) {
        str = str.replaceAll("/\\* \\*/", "\n");
        if (!str.trim().endsWith(";"))
          str = str + ";"; 
        procedure.setText(str);
      } 
    } 
  }
  
  private static final Qx c = new Qx(Redshift.class);
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    String str = c.a("GetSchemaDDL");
    boolean bool = true;
    if (paramImporter.c.isSelected(paramSchema) && paramImporter.c.allChildrenAreSelected(paramSchema.tables)) {
      paramImporter.a(paramSchema.getSymbolicName() + " '" + paramSchema.getSymbolicName() + "'");
      Log.a(paramSchema, "RedshiftBulkReverseEngineerUsingDDL");
      paramImporter.b();
      try {
        SelectStatement selectStatement = paramImporter.d.a("SELECT ddl FROM ( " + str + ") WHERE schemaname=?", new Object[] { paramSchema.getName() });
        try {
          String str1 = selectStatement.b(1);
          paramDDLParser.splitAndParse(str1);
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
      Log.a(paramSchema, "RedshiftIndividualReverseEngineerUsingDDL");
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
          paramImporter.b();
          try {
            SelectStatement selectStatement = paramImporter.d.a("SELECT ddl FROM ( " + str + ") WHERE schemaname=? and tablename=?", new Object[] { paramSchema.getName(), table.getName() });
            try {
              String str1 = selectStatement.b(1);
              paramDDLParser.splitAndParse(str1);
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
}
