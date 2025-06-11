package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
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

public class SAPAdaptiveServer extends Dbms {
  public SAPAdaptiveServer() {
    super("SAPAdaptiveServer");
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
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    ArrayList arrayList = new ArrayList();
    ArrayList<String> arrayList1 = new ArrayList();
    ArrayList<Dbms$SchemaCatalogEntry> arrayList2 = new ArrayList();
    DatabaseMetaData databaseMetaData = paramEnvoy.a();
    ResultSet resultSet1 = databaseMetaData.getCatalogs();
    if (resultSet1 != null) {
      while (resultSet1.next())
        arrayList1.add(resultSet1.getString(1)); 
      resultSet1.close();
    } 
    ResultSet resultSet2 = databaseMetaData.getSchemas();
    if (resultSet2 != null)
      while (resultSet2.next()) {
        for (String str : arrayList1)
          arrayList2.add(new Dbms$SchemaCatalogEntry(resultSet2.getString(1), str)); 
      }  
    return arrayList2;
  }
  
  private static final Pattern a = Pattern.compile("line[\\s:]*(\\d+)");
  
  private static final Pattern b = Pattern.compile("near[\\s:]*'(.+)'");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, a, (Pattern)null, (Pattern)null, b);
  }
}
