package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.schema.Connector;

public class SqlBase extends Dbms {
  public SqlBase() {
    super("SqlBase");
  }
  
  public String getMessageFromException(Throwable paramThrowable, Connector paramConnector) {
    return super.getMessageFromException(paramThrowable, paramConnector);
  }
}
