package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.data.model.result.ResultArrayRow;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DescribeStatement extends AbstractStatement {
  private final Envoy b;
  
  private final String c;
  
  private final Result d;
  
  public DescribeStatement(Envoy paramEnvoy, String paramString, Result paramResult) {
    this.b = paramEnvoy;
    this.c = paramString;
    this.d = paramResult;
  }
  
  public void a() {
    String str = "SELECT * FROM " + this.c + " WHERE 0=1";
    try {
      SelectStatement selectStatement = this.b.a(str, new Object[0]);
      try {
        selectStatement.c("Describe table");
        ResultSet resultSet = selectStatement.j();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        if (resultSetMetaData != null) {
          ResultColumn resultColumn1 = this.d.a("Column", 12, false);
          ResultColumn resultColumn2 = this.d.a("Type", 12, false);
          this.d.q();
          for (byte b = 1; b <= resultSetMetaData.getColumnCount(); b++) {
            ResultArrayRow resultArrayRow = new ResultArrayRow(2);
            resultArrayRow.a(resultColumn1, resultSetMetaData.getColumnName(b));
            resultArrayRow.a(resultColumn2, resultSetMetaData.getColumnTypeName(b) + "( " + resultSetMetaData.getColumnTypeName(b) + " ) ");
            this.d.a(resultArrayRow);
          } 
        } 
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
    } catch (Throwable throwable) {
      this.b.a(throwable);
      throw throwable;
    } 
  }
  
  public String b() {
    return "SELECT * FROM " + this.c + " WHERE 0=1";
  }
  
  public void close() {}
}
