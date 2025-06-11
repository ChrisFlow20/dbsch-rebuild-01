package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SqlServerBefore2006 extends SqlServer {
  public SqlServerBefore2006() {
    super("SqlServerBefore2006", (String)null);
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
  
  public void importFinal(Schema paramSchema) {
    for (Table table : paramSchema.tables) {
      for (ForeignKey foreignKey : table.foreignKeys) {
        if (foreignKey.getDeleteAction() == ForeignKeyAction.restrict)
          foreignKey.setDeleteAction(ForeignKeyAction.noAction); 
        if (foreignKey.getUpdateAction() == ForeignKeyAction.restrict)
          foreignKey.setUpdateAction(ForeignKeyAction.noAction); 
      } 
    } 
  }
  
  private static final Pattern a = Pattern.compile("near[\\s:]*'(.+)'");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, null, null, null, a);
  }
}
