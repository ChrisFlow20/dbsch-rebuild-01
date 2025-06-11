package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Filemaker extends Dbms {
  public Filemaker() {
    super("Filemaker");
  }
  
  private static final Pattern a = Pattern.compile("\\((\\d*):\\d*\\)");
  
  private static final Pattern b = Pattern.compile("\\(\\d*:(\\d*)\\)");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, a, b, null, null);
  }
}
