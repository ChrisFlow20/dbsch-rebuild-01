package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Column;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SqlAnywhere extends Dbms {
  public SqlAnywhere() {
    super("SqlAnywhere");
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    DatabaseMetaData databaseMetaData = paramEnvoy.a();
    ResultSet resultSet = databaseMetaData.getSchemas();
    ArrayList<Dbms$SchemaCatalogEntry> arrayList = new ArrayList();
    if (resultSet != null) {
      while (resultSet.next())
        arrayList.add(new Dbms$SchemaCatalogEntry(resultSet.getString(1), null)); 
      resultSet.close();
    } 
    return arrayList;
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
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if ("autoincrement".equals(paramString2)) {
      paramString2 = null;
      paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_());
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {
    super.importColumnAdditions(paramColumn, paramResultSet);
    if (7 == paramColumn.getLength() && 0 == paramColumn.getDecimal() && "REAL".equalsIgnoreCase(paramColumn.getDataType().toString()))
      paramColumn.setDataType(DbmsTypes.get("SqlAnywhere").getType("FLOAT")); 
  }
  
  private static final Pattern a = Pattern.compile("line[\\s:]*(\\d+)");
  
  private static final Pattern b = Pattern.compile("near[\\s:]*'(.+)'");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, a, (Pattern)null, (Pattern)null, b);
  }
}
