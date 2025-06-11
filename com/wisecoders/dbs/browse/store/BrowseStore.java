package com.wisecoders.dbs.browse.store;

import com.wisecoders.dbs.browse.model.BrowseResult;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import java.util.concurrent.Future;

public abstract class BrowseStore {
  protected final BrowseResult a;
  
  protected boolean b = false;
  
  protected long c = -1L;
  
  protected Future d;
  
  BrowseStore(BrowseResult paramBrowseResult) {
    this.a = paramBrowseResult;
  }
  
  public abstract void a(Envoy paramEnvoy);
  
  public void a(Future paramFuture) {
    this.d = paramFuture;
  }
  
  public abstract void b(Envoy paramEnvoy);
  
  public abstract void a(int paramInt, ResultColumn paramResultColumn, Object paramObject, Envoy paramEnvoy);
  
  public abstract void a(Integer[] paramArrayOfInteger, Envoy paramEnvoy);
  
  public boolean a() {
    return (this.c > -1L && !this.b && System.currentTimeMillis() > ((Dbms.get(this.a.c.c.getSchema().getDbId())).loadTimeout.a() + 1000) + this.c);
  }
}
