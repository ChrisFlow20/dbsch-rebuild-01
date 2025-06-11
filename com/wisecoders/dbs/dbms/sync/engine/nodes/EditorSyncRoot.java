package com.wisecoders.dbs.dbms.sync.engine.nodes;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.SchemaMapping;
import com.wisecoders.dbs.sys.Log;
import java.sql.SQLException;

public class EditorSyncRoot extends SyncPair {
  public final boolean a;
  
  private boolean b = false;
  
  public EditorSyncRoot(AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2, AbstractUnit paramAbstractUnit3, boolean paramBoolean, SchemaMapping paramSchemaMapping) {
    super(paramAbstractUnit1, paramAbstractUnit2, null, paramAbstractUnit3, false, paramSchemaMapping);
    this.a = paramBoolean;
  }
  
  public void a(Envoy paramEnvoy) {
    Log.c("Sync: " + toString(0));
    try {
      a(paramEnvoy, SyncSide.right, (SyncAction)null);
      paramEnvoy.p();
    } catch (SQLException sQLException) {
      paramEnvoy.a(sQLException);
      if (this.b) {
        Log.b(sQLException);
      } else {
        throw sQLException;
      } 
    } 
  }
  
  public void a() {
    mergeInto(SyncSide.left, null);
  }
  
  public void a(GenericLayoutPane paramGenericLayoutPane) {
    mergeInto(SyncSide.left, null, paramGenericLayoutPane);
  }
  
  public void a(boolean paramBoolean) {
    this.b = paramBoolean;
  }
}
