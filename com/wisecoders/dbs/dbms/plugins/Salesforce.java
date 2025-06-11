package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import java.util.Arrays;
import java.util.List;

public class Salesforce extends Dbms {
  public Salesforce() {
    super("Salesforce");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.b) ? Dbms$ParamSource.a : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    if (paramJdbcUrlParam == JdbcUrlParam.b)
      return Arrays.asList(new String[] { "58.0", "57.0", "56.0", "55.0" }); 
    return null;
  }
}
