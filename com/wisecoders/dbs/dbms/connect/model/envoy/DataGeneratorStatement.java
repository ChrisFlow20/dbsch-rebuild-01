package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class DataGeneratorStatement extends UpdateStatement {
  private final GeneratorTable g;
  
  private final String h;
  
  private int i;
  
  private final RecordBuffer j;
  
  private boolean k;
  
  private long l;
  
  PreparedStatement b;
  
  public DataGeneratorStatement(Envoy paramEnvoy, GeneratorTable paramGeneratorTable) {
    super(paramEnvoy);
    this.i = 1;
    this.j = new RecordBuffer();
    this.k = true;
    this.l = System.currentTimeMillis();
    this.g = paramGeneratorTable;
    paramGeneratorTable.initialize();
    this.h = paramGeneratorTable.buildSQL();
    c("Generate data");
  }
  
  public void a() {
    this.g.learnFkColumnValues(this.f);
    b(this.f);
    this.g.resetForeignKeyGenerators();
  }
  
  private void b(Envoy paramEnvoy) {
    while (this.g.insertMore() && !d()) {
      try {
        this.j.a(new HashMap<>(this.g.generateValuesSet()));
        if (this.j.b() >= this.i || this.j.b() + this.g.getSucceedCount() >= this.g.table.getGeneratorRowsCount() || 
          System.currentTimeMillis() - this.l > Sys.B.maxMillisPerChunk.a()) {
          a(paramEnvoy);
          this.l = System.currentTimeMillis();
        } 
      } catch (Throwable throwable) {
        this.g.increaseFailedCount();
        this.g.createFlatTextWarn(SqlUtil.getExceptionText(throwable));
      } 
    } 
    a(paramEnvoy);
  }
  
  public void a(Envoy paramEnvoy) {
    if (this.b == null)
      this.b = paramEnvoy.b(this.h); 
    try {
      paramEnvoy.b(this.k);
      paramEnvoy.a(this.h, this.e);
      paramEnvoy.b(this.k = false);
      for (byte b = 0; b < this.j.b(); b++) {
        a(this.b, this.j.a(b));
        this.b.addBatch();
      } 
      this.b.executeBatch();
      paramEnvoy.p();
      this.g.increaseSucceedCount(this.j.b());
      this.i = Math.min(Sys.B.maxRowsPerChunk.a(), this.i * 2);
      paramEnvoy.i();
      this.j.a();
    } catch (Exception exception) {
      for (byte b = 0; b < this.j.b(); b++) {
        try {
          a(this.b, this.j.a(b));
          this.b.executeUpdate();
          paramEnvoy.p();
          this.g.increaseSucceedCount(1);
        } catch (Exception exception1) {
          paramEnvoy.q();
          this.g.increaseFailedCount();
          this.g.createFlatTextWarn(SqlUtil.getExceptionText(exception1));
          paramEnvoy.e(exception1.getLocalizedMessage());
        } 
      } 
      this.j.a();
      this.i = 1;
    } 
    i();
    e();
  }
  
  private void a(PreparedStatement paramPreparedStatement, Map paramMap) {
    i();
    for (Column column : this.g.table.getAttributes()) {
      if (this.g.columnHasActiveGenerator(column) && !this.g.columnHasSequence(column)) {
        Object object = paramMap.get(column);
        if (object == null || object.getClass().isArray()) {
          a(new StatementParameter(object, column.getDataType()));
          continue;
        } 
        a(object);
      } 
    } 
    a(paramPreparedStatement);
  }
  
  public void e() {}
}
