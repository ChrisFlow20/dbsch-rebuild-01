package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class JsonGenerateDataJob extends UpdateStatement {
  private final GeneratorTable b;
  
  public JsonGenerateDataJob(Envoy paramEnvoy, GeneratorTable paramGeneratorTable) {
    super(paramEnvoy);
    this.b = paramGeneratorTable;
    c("Generate data");
    paramGeneratorTable.initialize();
  }
  
  public void a() {
    this.b.learnFkColumnValues(this.f);
    a(this.f);
  }
  
  private void a(Envoy paramEnvoy) {
    paramEnvoy.b(true);
    Connection connection = paramEnvoy.c();
    connection.setAutoCommit(true);
    String str = "UPDATE " + this.b.table.schema.getName() + "." + this.b.table.getName();
    paramEnvoy.a(str, this.e);
    PreparedStatement preparedStatement = connection.prepareStatement(str);
    while (this.b.insertMore() && !d()) {
      try {
        i();
        HashMap<Object, Object> hashMap = new HashMap<>();
        Map map = this.b.generateValuesSet();
        for (Column column : this.b.table.getAttributes()) {
          if (this.b.columnHasActiveGenerator(column))
            hashMap.put(column.getName(), map.get(column)); 
        } 
        a(new StatementParameter(hashMap, 4999544));
        paramEnvoy.b(false);
        a(preparedStatement);
        preparedStatement.executeUpdate();
        this.b.increaseSucceedCount(1);
        paramEnvoy.i();
      } catch (Throwable throwable) {
        preparedStatement.close();
        preparedStatement = connection.prepareStatement(str);
        this.b.increaseFailedCount();
        this.b.createFlatTextWarn(SqlUtil.getExceptionText(throwable));
        paramEnvoy.a(throwable);
      } 
      e();
    } 
    preparedStatement.close();
    i();
  }
  
  public void e() {}
}
