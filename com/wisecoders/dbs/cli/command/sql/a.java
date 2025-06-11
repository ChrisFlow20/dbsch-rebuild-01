package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.schema.Connector;
import java.util.concurrent.Callable;

final class a extends Record implements Callable {
  private final Connector a;
  
  private final String b;
  
  private a(Connector paramConnector, String paramString) {
    this.a = paramConnector;
    this.b = paramString;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lcom/wisecoders/dbs/cli/command/sql/a;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #74	-> 0
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/cli/command/sql/a;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #74	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/cli/command/sql/a;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #74	-> 0
  }
  
  public Connector b() {
    return this.a;
  }
  
  public String c() {
    return this.b;
  }
  
  public Void a() {
    Envoy envoy = this.a.startEnvoy("DbSchemaCLI");
    try {
      UpdateStatement updateStatement = envoy.b(this.b, new Object[0]);
      updateStatement.a();
      envoy.p();
      if (envoy != null)
        envoy.close(); 
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    return null;
  }
}
