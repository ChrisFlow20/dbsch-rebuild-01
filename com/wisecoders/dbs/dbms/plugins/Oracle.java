package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$TableCache;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ForeignKeyEntry;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ForeignKeyCascadeDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;

public class Oracle extends Dbms {
  public static final String a = "Oracle";
  
  public Oracle() {
    super("Oracle");
  }
  
  private static final Pattern b = Pattern.compile("(.*)\\((\\d*)\\)(.*)", 2);
  
  private static final Pattern c = Pattern.compile("(.*)\\((\\d*)\\)(.*)\\((\\d*)\\)(.*)", 2);
  
  private static final Qx d = new Qx(Oracle.class);
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    boolean bool = false;
    Log.f("Import all Oracle columns in one operation...");
    Dbms$TableCache dbms$TableCache = new Dbms$TableCache(paramSchema);
    AbstractTable abstractTable = null;
    try {
      SelectStatement selectStatement = paramImporter.d.a(d.a("Columns"), new Object[] { paramSchema.getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a()) {
          String str = resultSet.getString("tableName$");
          AbstractTable abstractTable1 = dbms$TableCache.a(str);
          if (abstractTable1 != null) {
            String str1 = resultSet.getString("columnName$");
            Column column = (Column)abstractTable1.getAttributes().getByName(str1);
            if (column == null)
              column = abstractTable1.createColumn(str1, DbmsTypes.get(paramImporter.b).getOrCreateDataType(resultSet.getInt("javaDataType$"), resultSet.getString("typeName$"))); 
            String str2 = resultSet.getString("dataDefault$");
            if (resultSet.getBoolean("virtual$")) {
              if (resultSet.getBoolean("hidden$")) {
                column.rename(str2);
                column.setSpec(AttributeSpec.functional);
              } else if (str2 != null) {
                column.setDefinition(str2);
                column.setSpec(AttributeSpec.computed);
              } 
              column.setOptions("VIRTUAL");
            } else {
              String str3 = resultSet.getString("typeName$");
              int i = resultSet.getInt("columnSize$");
              int j = resultSet.getInt("dataLength$");
              int k = resultSet.getInt("decimalDigits$");
              if ("NUMBER".equalsIgnoreCase(str3) || "NUMERIC".equalsIgnoreCase(str3)) {
                if (i == DataType.UNSET && k == 0) {
                  str3 = "INTEGER";
                } else if (k == 0) {
                  k = DataType.UNSET;
                } 
              } else if (i == DataType.UNSET) {
                i = j;
              } 
              if ("C".equals(resultSet.getString("charUsed$"))) {
                i = resultSet.getInt("charLength$");
                if (resultSet.getBoolean("useChar$"))
                  column.setTypeOptions("CHAR"); 
              } 
              setImportedColumnType(column, resultSet.getInt("javaDataType$"), str3, i, k, str2);
              column.setMandatory((0 == resultSet.getInt("nullable$")));
            } 
            column.setComment(SqlUtil.unescapeComment(resultSet.getString("remarks$")));
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
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    Matcher matcher = c.matcher(paramString1);
    while (matcher.find()) {
      paramString1 = matcher.group(1) + matcher.group(1) + matcher.group(3);
      String str = matcher.group(2);
      if (StringUtil.isFilledTrim(str))
        paramInt2 = Integer.parseInt(str); 
    } 
    matcher = b.matcher(paramString1);
    while (matcher.find()) {
      paramString1 = matcher.group(1) + matcher.group(1);
      String str = matcher.group(2);
      if (StringUtil.isFilledTrim(str))
        paramInt2 = Integer.parseInt(str); 
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    if (!a(paramImporter, paramSchema))
      super.importIndexes(paramImporter, paramSchema); 
  }
  
  public boolean a(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "AllIndexes");
    try {
      SelectStatement selectStatement = paramImporter.d.a(d.a("Indexes"), new Object[] { paramSchema.getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        Table table1 = null, table2 = null;
        Index index = null;
        while (resultSet.next()) {
          String str1 = resultSet.getString("tableName$");
          String str2 = resultSet.getString("indexName$");
          String str3 = resultSet.getString("columnName$");
          String str4 = resultSet.getString("unique$");
          String str5 = resultSet.getString("expression$");
          String str6 = resultSet.getString("constraintType$");
          Log.d("Import " + str6 + " Index " + str2 + " on " + str1 + " (" + str3 + " )");
          if (index == null || !index.getName().equals(str2) || !table1.getName().equals(str1)) {
            index = null;
            table1 = paramSchema.getTable(str1);
            if (table1 != null) {
              index = table1.createIndex(str2);
              if ("P".equalsIgnoreCase(str6)) {
                index.setType(IndexType.PRIMARY_KEY);
              } else if ("U".equalsIgnoreCase(str6)) {
                index.setType(IndexType.UNIQUE_KEY);
              } else if ("UNIQUE".equalsIgnoreCase(str4)) {
                index.setType(IndexType.UNIQUE_INDEX);
              } 
              if (table2 != table1) {
                paramImporter.a("Table '" + table1.ref() + "' indexes");
                paramImporter.b();
                table2 = table1;
              } 
            } 
          } 
          if (table1 != null) {
            Column column = (Column)table1.columns.getByName(str3);
            if (column == null && str5 != null) {
              column = (Column)table1.columns.getByName(str5);
              if (column == null)
                column = table1.createColumn(str5, DbmsTypes.get("Oracle").getType("varchar2"), AttributeSpec.functional); 
            } 
            if (column != null)
              index.addColumn(column); 
          } 
          Log.j();
        } 
        resultSet.close();
        boolean bool = true;
        if (selectStatement != null)
          selectStatement.close(); 
        return bool;
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
      return false;
    } 
  }
  
  public List importFks(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "AllForeignKeys");
    ArrayList<ForeignKeyEntry> arrayList = new ArrayList();
    try {
      SelectStatement selectStatement = paramImporter.d.a(d.a("ForeignKeys"), new Object[] { paramSchema.getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          String str1, str2;
          Schema schema1 = paramSchema.project.getSchemaUsingMetaDataCatalogAndSchemaName(
              str1 = resultSet.getString("fkTableCatalog$"), 
              str2 = resultSet.getString("fkTableSchema$"));
          String str3, str4;
          Schema schema2 = paramSchema.project.getSchemaUsingMetaDataCatalogAndSchemaName(
              str3 = resultSet.getString("pkTableCatalog$"), 
              str4 = resultSet.getString("pkTableSchema$"));
          if (schema1 == null) {
            Log.f("SYNC: importing Fks failed to find schema " + ((str2 != null) ? str2 : str1) + ", therefore " + String.valueOf(paramSchema) + " will be used instead.");
            schema1 = paramSchema;
          } 
          if (schema2 == null) {
            Log.f("SYNC: importing Fks failed to find schema " + ((str4 != null) ? str4 : str3) + ", therefore " + String.valueOf(paramSchema) + " will be used instead.");
            schema2 = paramSchema;
          } 
          String str5;
          Table table1 = (Table)schema2.tables.getByName(str5 = resultSet.getString("pkTableName$"));
          String str6;
          Table table2 = (Table)schema1.tables.getByName(str6 = resultSet.getString("fkTableName$"));
          if (table1 == null) {
            Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema1) + ". " + str5);
          } else if (table2 == null) {
            Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema1) + ". " + str6);
          } else if (paramImporter.c.isSelected(table2)) {
            String str7;
            Column column1 = (Column)table1.columns.getByName(str7 = resultSet.getString("pkColumnName$"));
            String str8;
            Column column2 = table2.getColumnByNameOrPath(str8 = resultSet.getString("fkColumnName$"));
            if (column2 == null && str8 != null) {
              Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table2) + "." + str8);
            } else if (column1 == null && str7 != null) {
              Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table1) + "." + str7);
            } else {
              ForeignKeyAction foreignKeyAction1 = ForeignKeyAction.decode(resultSet.getShort("deleteRule$"));
              ForeignKeyAction foreignKeyAction2 = ForeignKeyAction.decode(resultSet.getShort("updateRule$"));
              if (foreignKeyAction1 == ForeignKeyAction.restrict)
                foreignKeyAction1 = ForeignKeyAction.noAction; 
              if (foreignKeyAction2 == ForeignKeyAction.restrict)
                foreignKeyAction2 = ForeignKeyAction.noAction; 
              arrayList.add(new ForeignKeyEntry(table1, table2, resultSet.getString("fkName$"), column1, column2, foreignKeyAction1, foreignKeyAction2, resultSet.getInt("keySequence$")));
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
  
  public void importFinal(Schema paramSchema) {
    for (Trigger trigger : paramSchema.triggers)
      a(trigger); 
    for (Function function : paramSchema.functions)
      a(function); 
    for (Procedure procedure : paramSchema.procedures)
      a(procedure); 
  }
  
  private void a(Sql paramSql) {
    String str = paramSql.getText();
    if (StringUtil.isFilledTrim(str)) {
      str = str.replaceAll("/\\* \\*/", "\n");
      if (!str.toLowerCase().startsWith("create"))
        str = "CREATE OR REPLACE " + str; 
      paramSql.setText(str);
    } 
  }
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    int i = -1;
    if (paramString != null) {
      String str = d.a("ErrorPosition");
      CallableStatement callableStatement = paramEnvoy.c(str);
      callableStatement.setString(1, paramString);
      callableStatement.registerOutParameter(2, 4);
      callableStatement.execute();
      i = callableStatement.getInt(2);
      callableStatement.close();
      paramEnvoy.close();
    } 
    return i;
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    try {
      paramEnvoy.g("DELETE FROM plan_table");
      paramEnvoy.p();
    } catch (SQLException sQLException) {
      (new Alert$(Alert.AlertType.ERROR))
        .a("Missing Oracle Explain Table")
        .b("On server please execute the script '$ORACLE_HOME/dbms/utlxplan.sql'.")
        .c("This will create the table PLAN_TABLE in the database, required by explain plan command.").a(sQLException).show();
      throw sQLException;
    } 
    paramEnvoy.g("EXPLAIN PLAN FOR " + paramString);
    paramEnvoy.p();
    Result result = new Result();
    paramEnvoy.b(this.explainPlanView.c_(), result);
    result.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result);
    for (byte b = 0; b < result.A(); b++) {
      Object[] arrayOfObject = result.j(b);
      String str1 = (String)arrayOfObject[0], str2 = (String)arrayOfObject[1], str3 = (String)arrayOfObject[2];
      Number number1 = (Number)arrayOfObject[8], number2 = (Number)arrayOfObject[9], number3 = (Number)arrayOfObject[3];
      StringBuilder stringBuilder = new StringBuilder();
      if (str1 != null)
        stringBuilder.append(str1).append(" "); 
      if (str2 != null)
        stringBuilder.append(str2).append("."); 
      if (str3 != null)
        stringBuilder.append(str3); 
      executionPlan.a((number1 != null) ? number1.intValue() : -1, 
          (number2 != null) ? number2.intValue() : -1, stringBuilder
          .toString(), str1, str3, 
          (number3 != null) ? number3.doubleValue() : -1.0D, arrayOfObject);
    } 
    return executionPlan;
  }
  
  public String formatQueryForExecution(String paramString) {
    paramString = super.formatQueryForExecution(paramString);
    if ("showerrors".equalsIgnoreCase(paramString.replaceAll("\n", "").replaceAll(" ", "")))
      paramString = "SELECT * FROM USER_ERRORS"; 
    return paramString;
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> {
        if (paramAbstractDiff instanceof ForeignKeyCascadeDiff) {
          ForeignKeyCascadeDiff foreignKeyCascadeDiff = (ForeignKeyCascadeDiff)paramAbstractDiff;
          SyncPair syncPair = foreignKeyCascadeDiff.pair;
          if (((ForeignKey)syncPair.left).getUpdateAction() != ((ForeignKey)syncPair.right).getUpdateAction())
            return true; 
        } 
        return (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff) ? ((paramAbstractDiff.pair.left.getName().startsWith("SYS_") && paramAbstractDiff.pair.right.getName().startsWith("SYS_"))) : false;
      };
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "SchemaComments");
    SelectStatement selectStatement = paramImporter.d.a(d.a("TableComments"), new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString("tableName$");
        String str2 = resultSet.getString("tableType$");
        String str3 = resultSet.getString("comments$");
        Log.d("Import " + str2 + " Table " + str1 + " comment: " + str3);
        AbstractTable abstractTable = (AbstractTable)("view".equalsIgnoreCase(str2) ? paramSchema.getView(str1) : paramSchema.getTable(str1));
        if (abstractTable != null)
          abstractTable.setComment(StringUtil.unescapeNewLine(str3)); 
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
    selectStatement = paramImporter.d.a(d.a("ColumnComments"), new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString("tableName$");
        String str2 = resultSet.getString("columnName$");
        String str3 = resultSet.getString("comments$");
        Log.d("Import Table " + str1 + " column " + str2 + " comment: " + str3);
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Column column = table.getColumnByNameOrPath(str2);
          if (column != null)
            column.setComment(StringUtil.unescapeNewLine(str3)); 
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
    selectStatement = paramImporter.d.a(d.a("MatViewsComments"), new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString("mViewName$");
        String str2 = resultSet.getString("comments$");
        Log.d("Import MaterializedView " + str1 + " comment: " + str2);
        MaterializedView materializedView = paramSchema.getMaterializedView(str1);
        if (materializedView != null)
          materializedView.setComment(str2); 
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
  
  public void listFunctionNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    super.listFunctionNames(paramStructureImporter, paramSchema);
    for (Function function : paramSchema.functions) {
      for (Procedure procedure : paramSchema.procedures) {
        if (function.getName().equals(procedure.getName()))
          procedure.markForDeletion(); 
      } 
    } 
  }
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    ArrayList<String> arrayList = new ArrayList();
    SelectStatement selectStatement = paramImporter.d.a("SELECT type_name FROM all_types WHERE owner = ?", new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next())
        arrayList.add(resultSet.getString(1)); 
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
    for (String str : arrayList) {
      SelectStatement selectStatement1 = paramImporter.d.a("SELECT DBMS_METADATA.GET_DDL('TYPE', ?, ?) AS DDL FROM DUAL", new Object[] { str, paramSchema.getName() });
      try {
        ResultSet resultSet = selectStatement1.j();
        while (resultSet.next()) {
          UserDataType userDataType = (UserDataType)paramSchema.userDataTypes.getByName(resultSet.getString(1));
          if (userDataType == null) {
            userDataType = paramSchema.createUserDataType(str);
            userDataType.setScript(resultSet.getString(1).trim());
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
    } 
  }
}
