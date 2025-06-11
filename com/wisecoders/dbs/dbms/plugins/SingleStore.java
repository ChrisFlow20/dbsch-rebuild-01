package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import java.util.List;

public class SingleStore extends MySql {
  public static final String a = "SingleStore";
  
  public SingleStore() {
    super("SingleStore");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, new SelectStatement(paramEnvoy, "show databases", new Object[0]));
  }
}
