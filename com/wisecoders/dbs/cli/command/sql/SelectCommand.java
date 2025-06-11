package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.sql.ResultSet;

@DoNotObfuscate
public class SelectCommand extends SQLCommand {
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    SelectStatement selectStatement = paramEnvoy.a(paramString1, new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      if (resultSet != null) {
        paramAbstractConsole.a(resultSet, paramConnector, (paramInt == 0 || !Sys.B.spoolDb.b()));
        resultSet.close();
      } 
      paramEnvoy.p();
      if (selectStatement != null)
        selectStatement.close(); 
    } catch (Throwable throwable) {
      if (selectStatement != null)
        try {
          selectStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    return true;
  }
  
  public CommandType getType() {
    return CommandType.c;
  }
}
