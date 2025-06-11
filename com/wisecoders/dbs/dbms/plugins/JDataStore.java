package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.FileUtils;
import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class JDataStore extends Dbms {
  public JDataStore() {
    super("JDataStore");
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if (paramString2 != null && paramString2.toLowerCase().startsWith("autoincrement")) {
      paramString2 = null;
      paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_());
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    File file = Paths.get(paramString1, new String[0]).toFile();
    if (!file.exists())
      throw new SQLException("Folder '" + paramString1 + "' does not exists. Cannot create the database."); 
    if (!file.isDirectory())
      throw new SQLException("'" + paramString1 + "' is not a folder. Cannot create the database."); 
    if (file.listFiles() != null && (file.listFiles()).length > 0)
      throw new SQLException("'" + paramString1 + "' is not a empty. Please choose an empty folder."); 
    FileUtils.b(file);
    Envoy envoy = paramConnector.startEnvoy("Derby create database");
    try {
      paramConnector.setInstance(paramString1 + ";create=true");
      envoy.p();
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
    paramConnector.closeAllEnvoysAndSsh();
  }
  
  public SyncFilter getSynchronizationFilter() {
    return new JDataStore$1(this);
  }
  
  private static final Pattern a = Pattern.compile("line[\\s:]*(\\d+)");
  
  private static final Pattern b = Pattern.compile("col[\\s:]*(\\d+)");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, a, b, (Pattern)null, (Pattern)null);
  }
}
