package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import java.util.Arrays;
import java.util.List;

public class InfluxDb extends Dbms {
  public InfluxDb() {
    super("InfluxDB");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a || paramJdbcUrlParam == JdbcUrlParam.c) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    if (paramJdbcUrlParam == JdbcUrlParam.c)
      return Arrays.asList(new String[] { "-30", "-60", "-90", "-180", "-368" }); 
    return a(paramEnvoy, paramEnvoy.a("list organizations", new Object[0]));
  }
}
