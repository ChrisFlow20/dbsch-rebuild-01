package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.SQLException;
import java.sql.Statement;

public class H2 extends Dbms {
  public static final String a = "H2";
  
  public H2() {
    super("H2");
  }
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str1 = String.valueOf(paramSQLException).toLowerCase();
    String str2 = str1.replaceAll("\\[.*\\]", "");
    int i = str2.indexOf(paramString.toLowerCase());
    if (i > -1) {
      int j = str1.indexOf("[", i);
      return a(paramString, j - i - 1);
    } 
    return -1;
  }
  
  private static int a(String paramString, int paramInt) {
    int i = Math.min(paramString.length() - 1, paramInt);
    boolean bool = false;
    while (i > -1) {
      boolean bool1 = SqlUtil.isDelimiter(paramString.charAt(i));
      if (!bool && !bool1)
        bool = true; 
      if (bool && bool1)
        return i + 1; 
      i--;
    } 
    return i + 1;
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    Envoy envoy = paramConnector.startEnvoy("H2 create database");
    try {
      paramConnector.setInstance(paramString1);
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
    paramConnector.closeAllEnvoysAndSsh();
  }
}
