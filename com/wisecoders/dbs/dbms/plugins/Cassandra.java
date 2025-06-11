package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class Cassandra extends Dbms {
  public static final String NAME = "Cassandra";
  
  public Cassandra() {
    super("Cassandra");
  }
  
  public Cassandra(String paramString) {
    super(paramString);
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    if (paramImporter.c.isSelected(paramSchema) && paramImporter.c.allChildrenAreSelected(paramSchema.tables)) {
      paramImporter.a(paramSchema.getSymbolicName() + " '" + paramSchema.getSymbolicName() + "'");
      Log.a(paramSchema, "CassandraBulkReverseEngineerUsingDDL");
      paramImporter.b();
      try {
        SelectStatement selectStatement = paramImporter.d.a("DESC " + paramSchema.getName(), new Object[0]);
        try {
          String str = selectStatement.b(4);
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
      Log.a(paramSchema, "CassandraIndividualReverseEngineerUsingDDL");
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' using DDL");
          paramImporter.b();
          try {
            SelectStatement selectStatement = paramImporter.d.a("DESC " + paramSchema.getName() + "." + table.getName(), new Object[0]);
            try {
              String str = selectStatement.b(4);
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
          Log.j();
        } 
      } 
    } 
    return bool;
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("SELECT keyspace_name FROM system_schema.keyspaces", new Object[0]));
  }
  
  public boolean canExplainPlan() {
    return true;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    Result result = new Result();
    paramEnvoy.b("EXPLAIN PLAN FOR " + paramString, result);
    result.q();
    ExecutionPlan executionPlan = new ExecutionPlan(result);
    for (byte b = 0; b < result.A(); b++) {
      Object[] arrayOfObject = result.j(b);
      String str = (arrayOfObject.length > 6) ? String.valueOf(arrayOfObject[3]) : "";
      double d = 1.0D;
      if (arrayOfObject.length > 6)
        try {
          d = Double.parseDouble(String.valueOf(arrayOfObject[6]));
        } catch (Exception exception) {} 
      executionPlan.a(b, -1, str, "", "", d, arrayOfObject);
    } 
    return executionPlan;
  }
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    super.importColumns(paramImporter, paramSchema);
    for (Table table : paramSchema.tables) {
      if (table.columns.isEmpty())
        table.markForDeletion(); 
    } 
    paramSchema.refresh();
  }
  
  private static final Pattern a = Pattern.compile("line\\s+(\\d+):(\\d+)");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    try {
      Matcher matcher = a.matcher(str);
      if (matcher.find())
        return StringUtil.getPositionInString(paramString, Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2))); 
    } catch (NumberFormatException numberFormatException) {
      Log.a(numberFormatException);
    } 
    return -1;
  }
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "ListUserDefinedDataTypes");
    ResultSet resultSet = paramImporter.d.a().getUDTs(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, new int[0]);
    if (resultSet != null)
      while (resultSet.next()) {
        if (resultSet.getMetaData() != null && resultSet.getMetaData().getColumnCount() > 6) {
          String str1 = resultSet.getString(3);
          String str2 = resultSet.getString(8);
          UserDataType userDataType = paramSchema.createUserDataType(str1);
          userDataType.setScript("CREATE TYPE ${name} " + str2);
          userDataType.removeCreateTypeFromScript();
          userDataType.setJavaType(resultSet.getInt(5));
          String str3 = resultSet.getString(9);
          if (str3 != null)
            for (String str : str3.split("\n")) {
              String[] arrayOfString = str.split("\t");
              userDataType.createColumn(arrayOfString[0], DbmsTypes.get(this.dbId).getType(arrayOfString[1]));
            }  
        } 
        Log.j();
      }  
  }
  
  @GroovyMethod
  public String getPrimaryKeyDeclaration(Index paramIndex) {
    StringBuilder stringBuilder = new StringBuilder();
    Index index = null;
    for (Index index1 : (paramIndex.getEntity()).indexes) {
      if (index1.getType() == IndexType.PARTITION)
        index = index1; 
    } 
    if (index == null) {
      for (Column column : paramIndex.columns) {
        if (stringBuilder.length() > 0)
          stringBuilder.append(", "); 
        stringBuilder.append(column);
      } 
    } else {
      stringBuilder.append("(");
      boolean bool = false;
      for (Column column : index.columns) {
        if (bool)
          stringBuilder.append(", "); 
        stringBuilder.append(Dbms.quote(column));
        bool = true;
      } 
      stringBuilder.append(")");
      for (Column column : paramIndex.columns) {
        if (!index.columns.contains(column)) {
          if (stringBuilder.length() > 0)
            stringBuilder.append(", "); 
          stringBuilder.append(column);
        } 
      } 
    } 
    return stringBuilder.toString();
  }
}
