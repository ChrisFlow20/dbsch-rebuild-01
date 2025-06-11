package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import java.io.Writer;

public class DumpStatement extends AbstractStatement {
  private final Envoy c;
  
  private final AbstractTable d;
  
  private final Writer e;
  
  private final boolean f;
  
  private String g;
  
  public final Result b;
  
  public DumpStatement(Envoy paramEnvoy, AbstractTable paramAbstractTable, boolean paramBoolean, Writer paramWriter) {
    this.b = new DumpStatement$1(this);
    this.c = paramEnvoy;
    this.d = paramAbstractTable;
    this.e = paramWriter;
    this.f = paramBoolean;
    this.b.x();
    this.b.q();
  }
  
  public void a() {
    this.g = this.c.e();
    this.b.i(2147483647);
    GeneralToResultStatement generalToResultStatement = new GeneralToResultStatement(this.c, Dbms.get(this.g).getSelectQuery(this.d, this.f), this.b);
    try {
      generalToResultStatement.a();
      generalToResultStatement.close();
    } catch (Throwable throwable) {
      try {
        generalToResultStatement.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public String b() {
    return Dbms.get(this.g).getSelectQuery(this.d, true);
  }
  
  public void close() {}
}
