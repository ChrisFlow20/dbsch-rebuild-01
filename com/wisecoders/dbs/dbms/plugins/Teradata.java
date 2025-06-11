package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Teradata extends Dbms {
  public Teradata() {
    super("Teradata");
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if (paramString2 != null && paramString2.toLowerCase().startsWith("autoincrement")) {
      paramString2 = null;
      paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_());
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {
    if (paramResultSet.getMetaData().getColumnCount() > 18 && "NO".equalsIgnoreCase(paramResultSet.getString(18)))
      paramColumn.setMandatory(true); 
  }
  
  public void importViewColumns(Importer paramImporter, Schema paramSchema) {
    for (View view : paramSchema.views) {
      SelectStatement selectStatement = paramImporter.d.a("SELECT ColumnName, ColumnType FROM dbc.columnsV WHERE DatabaseName=? and TableName =?", new Object[] { paramSchema
            .getName(), view.getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        DbmsTypes dbmsTypes = DbmsTypes.get(this.dbId);
        while (resultSet.next()) {
          DataType dataType = dbmsTypes.getDataType(12);
          String str = resultSet.getString(2);
          if (str != null)
            switch (str) {
              case "D":
                dataType = dbmsTypes.getType("Decimal");
                break;
              case "BF":
                dataType = dbmsTypes.getType("Byte");
                break;
              case "DA":
                dataType = dbmsTypes.getType("Date");
                break;
              case "N":
                dataType = dbmsTypes.getType("Number");
                break;
              case "F":
                dataType = dbmsTypes.getType("Float");
                break;
              case "I":
                dataType = dbmsTypes.getType("Integer");
                break;
            }  
          view.createColumn(resultSet.getString(1), dataType);
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
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("SELECT DatabaseName FROM DBC.Databases", new Object[0]));
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    SelectStatement selectStatement = paramImporter.d.a("SELECT tablename, indextype, columnname FROM dbc.indices where Databasename=? ORDER BY tablename, indextype", new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      ArrayList<Column> arrayList = new ArrayList();
      String str = null;
      Table table = null;
      while (resultSet.next()) {
        Table table1 = paramSchema.getTable(resultSet.getString(1));
        String str1 = resultSet.getString(2);
        if ((table != null && table != table1) || (str != null && !str.equals(str1)))
          a(table, str, arrayList); 
        str = str1;
        table = table1;
        if (table1 != null) {
          Column column = table1.getColumnByNameOrPath(resultSet.getString(2));
          if (column != null)
            arrayList.add(column); 
        } 
      } 
      a(table, str, arrayList);
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
  
  private void a(Table paramTable, String paramString, List<?> paramList) {
    if (paramTable != null && paramString != null && !paramList.isEmpty())
      for (Index index : paramTable.indexes) {
        if (("P".equalsIgnoreCase(paramString) || "Q".equalsIgnoreCase(paramString)) && 
          index.getType() == IndexType.UNIQUE_KEY && index.columns.containsAll(paramList) && index.columns.size() == paramList.size())
          switch (paramString) {
            case "P":
              index.setOptions("UNIQUE");
          }  
      }  
  }
}
