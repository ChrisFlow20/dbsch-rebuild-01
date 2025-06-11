package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Pattern;

public class MySql extends Dbms {
  public static final String b = "MySql";
  
  public MySql() {
    super("MySql");
  }
  
  protected MySql(String paramString) {
    super(paramString, "MySql");
  }
  
  protected MySql(String paramString1, String paramString2) {
    super(paramString1, paramString2);
  }
  
  public void loadDbVersion(StructureImporter paramStructureImporter, Project paramProject) {
    SelectStatement selectStatement = paramStructureImporter.c.a("SELECT version()", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      if (resultSet.next()) {
        Log.c("MySql Version is " + resultSet.getString(1));
        paramProject.setDbVersion(resultSet.getString(1));
        if (paramProject.isDbVersionBelow("5.8"))
          this.root.q("/dbms/MySql/settings5.7.properties"); 
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
  
  public void setDDLImportedColumnTypes(Table paramTable) {
    for (Column column : paramTable.columns) {
      if (!(column.getDataType() instanceof com.wisecoders.dbs.schema.UserDataType)) {
        String str1 = column.getDataType().getName();
        String str2 = column.getDefaultValue();
        if ("tinyint".equals(str1) && column.getLength() == 1)
          str1 = "boolean"; 
        if ("0".equals(str2) && "boolean".equals(str1))
          str2 = "false"; 
        if ("1".equals(str2) && "boolean".equals(str1))
          str2 = "true"; 
        setImportedColumnType(column, column.getDataType().getJavaType(), str1, column.getLength(), column.getDecimal(), str2);
      } 
    } 
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    int i;
    if (paramString1 != null && (i = paramString1.toLowerCase().indexOf(" unsigned")) > 0) {
      paramString1 = paramString1.substring(0, i);
      paramColumn.setUnsigned(true);
    } 
    if ("tinyint".equalsIgnoreCase(paramString1) && paramInt2 == 1) {
      paramString1 = "boolean";
      paramInt2 = DataType.UNSET;
    } 
    if ("boolean".equalsIgnoreCase(paramString1) && paramString2 != null)
      if ("0".equals(paramString2)) {
        paramString2 = "false";
      } else if ("1".equals(paramString2)) {
        paramString2 = "true";
      }  
    if ("datetime".equalsIgnoreCase(paramString1) || "timestamp".equalsIgnoreCase(paramString1)) {
      if (paramInt2 == 19)
        paramInt2 = DataType.UNSET; 
      if (paramInt2 > 20)
        paramInt2 -= 20; 
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public String formatImportedDefaultValue(DataType paramDataType, String paramString) {
    if (paramString != null) {
      if ("NULL".equalsIgnoreCase(paramString))
        return null; 
      if ((paramDataType.isText() || paramDataType.isChar()) && !paramString.startsWith("'"))
        return "'" + paramString + "'"; 
      if (paramDataType.isDate() && !paramString.startsWith("'") && paramString.length() > 0 && 
        StringUtil.isNumber(paramString.charAt(0)))
        return "'" + paramString + "'"; 
      if ("b'0'".equals(paramString))
        return "0"; 
      if ("b'1'".equals(paramString))
        return "1"; 
    } 
    return paramString;
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {
    super.importColumnAdditions(paramColumn, paramResultSet);
    if (paramResultSet.getMetaData().getColumnCount() >= 23)
      if ("yes".equalsIgnoreCase(paramResultSet.getString(23)))
        paramColumn.setIdentity("AUTO_INCREMENT");  
    if ("auto_increment".equals(paramColumn.getComment())) {
      paramColumn.setIdentity("AUTO_INCREMENT");
      paramColumn.setComment(null);
    } 
    if (12 == paramColumn.getLength() && ("FLOAT"
      .equals(paramColumn.getDataType().toString()) || "REAL"
      .equals(paramColumn.getDataType().toString())))
      paramColumn.setLength(DataType.UNSET); 
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    for (Table table : paramSchema.tables) {
      for (Column column : table.columns) {
        if (paramImporter.c.isSelected(table) && column.getDataType().getPrecision() == Precision.ENUMERATION) {
          SelectStatement selectStatement = paramImporter.d.a("SELECT column_type FROM information_schema.columns WHERE table_schema=? AND table_name=? AND column_name=?", new Object[] { paramSchema
                
                .getName(), table.getName(), column.getName() });
          try {
            ResultSet resultSet = selectStatement.j();
            if (resultSet.next()) {
              String str = resultSet.getString(1);
              if (str != null) {
                str = str.trim();
                int i = str.indexOf("(");
                int j = str.lastIndexOf(")");
                if (i > -1 && j > i)
                  str = str.substring(i + 1, j); 
                column.setEnumeration(str);
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
    } 
  }
  
  private static final Pattern a = Pattern.compile("line[\\s:]*(\\d*)");
  
  private static final Pattern c = Pattern.compile("near '(.*)'", 42);
  
  private static final String d = "DEFINER=`[^`]*`@`[^`]*`";
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, a, (Pattern)null, (Pattern)null, c);
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("show databases", new Object[0]));
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance("");
    SimpleStatement simpleStatement = (new SimpleStatement((Dbms.get("MySql")).databaseCreate.c_())).set(K.a, quote(paramString1)).set(K.G, paramString2);
    Envoy envoy = paramConnector.startEnvoy("Create Database");
    try {
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
  
  public SyncFilter getSynchronizationFilter() {
    return new MySql$1(this);
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    Log.a(paramSchema, "Indexes");
    try {
      SelectStatement selectStatement = paramImporter.d.a("SELECT table_name, column_name, index_name, index_type \nFROM information_Schema.STATISTICS \nWHERE table_schema=? AND ( index_type = 'FULLTEXT' || index_type='SPATIAL' ) ", new Object[] { paramSchema


            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        Table table1 = null, table2 = null;
        Index index = null;
        while (resultSet.next()) {
          String str1 = resultSet.getString(1);
          String str2 = resultSet.getString(2);
          if (index == null || !table1.getName().equals(str1)) {
            index = null;
            table1 = paramSchema.getTable(str1);
            if (table1 != null) {
              index = (Index)table1.indexes.getByName(resultSet.getString(3));
              if (index != null && index.getType() == IndexType.NORMAL)
                index.markForDeletion(); 
              index = table1.createIndex(resultSet.getString(3));
              index.setType("SPATIAL".equalsIgnoreCase(resultSet.getString(4)) ? IndexType.SORT : IndexType.PARTITION);
              if (table2 != table1) {
                paramImporter.a("Table '" + table1.ref() + "' indexes");
                paramImporter.b();
                table2 = table1;
              } 
            } 
          } 
          if (table1 != null) {
            Column column = (Column)table1.columns.getByName(str2);
            if (column != null)
              index.addColumn(column); 
          } 
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
    } catch (Exception exception) {
      Log.b(exception);
    } 
  }
  
  public void importProcedures(Importer paramImporter, Schema paramSchema) {
    for (Procedure procedure : paramSchema.procedures) {
      if (paramImporter.c.isSelected(procedure)) {
        paramImporter.a("Procedure '" + procedure.ref() + "'");
        SelectStatement selectStatement = paramImporter.d.a("SHOW CREATE PROCEDURE " + procedure.ref(), new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            String str = resultSet.getString(3);
            str = str.replaceFirst("DEFINER=`[^`]*`@`[^`]*`", "");
            str = str.replaceFirst("^CREATE\\s+PROCEDURE\\s+`[^`]*`", "CREATE PROCEDURE \\${nameWithSchemaName}");
            procedure.setText(str);
          } 
          resultSet.close();
          paramImporter.b();
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
  
  public void importFunctions(Importer paramImporter, Schema paramSchema) {
    for (Function function : paramSchema.functions) {
      if (paramImporter.c.isSelected(function)) {
        paramImporter.a("Function '" + function.ref() + "'");
        SelectStatement selectStatement = paramImporter.d.a("SHOW CREATE FUNCTION " + function.ref(), new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          while (resultSet.next()) {
            String str = resultSet.getString(3);
            str = str.replaceFirst("DEFINER=`[^`]*`@`[^`]*`", "");
            str = str.replaceFirst("^CREATE\\s+FUNCTION\\s+`[^`]*`", "CREATE FUNCTION \\${nameWithSchemaName}");
            function.setText(str);
          } 
          resultSet.close();
          paramImporter.b();
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
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    paramImporter.d.g("USE " + paramSchema.ref());
    Log.a(paramSchema, "MySqlIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("SHOW CREATE TABLE " + table.ref(), new Object[0]);
          try {
            String str = selectStatement.b(2);
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
