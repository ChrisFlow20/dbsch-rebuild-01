package com.wisecoders.dbs.dbms.driver.fx;

import com.wisecoders.dbs.dbms.driver.model.DriverJar;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dbms.driver.model.JdbcUrlTemplate;
import com.wisecoders.dbs.sys.StringUtil;

class b {
  public final DriverJar a;
  
  public final JdbcUrlTemplate b;
  
  public final String c;
  
  public b(DriverJar paramDriverJar, JdbcUrlTemplate paramJdbcUrlTemplate, String paramString) {
    this.b = paramJdbcUrlTemplate;
    this.a = paramDriverJar;
    this.c = paramString;
  }
  
  public void a(String paramString) {
    if (this.b != null) {
      this.b.a(paramString);
    } else if (this.c != null && StringUtil.isFilled(paramString)) {
      DriverManager.a(this.a.a).a(this.c, paramString, null, null, null, null, null, null, null, null, "Edited", true);
    } 
  }
  
  public String toString() {
    return this.a.toString();
  }
}
