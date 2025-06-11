package com.wisecoders.dbs.cli.command.fls.obsolete;

import com.wisecoders.dbs.project.store.AbstractContentHandler;
import java.time.LocalDate;

public class FLSUsersLoader extends AbstractContentHandler {
  private final FxKey[] a = new FxKey[10];
  
  private final Object[] b = new Object[10];
  
  protected void a(String paramString, int paramInt) {
    FxKey fxKey = FxKey.valueOf(paramString);
    this.a[paramInt] = fxKey;
    this.b[paramInt] = a((paramInt > 0) ? this.a[paramInt - 1] : null, (paramInt > 0) ? this.b[paramInt - 1] : null, fxKey);
  }
  
  private FLSUser a(FxKey paramFxKey1, Object paramObject, FxKey paramFxKey2) {
    switch (FLSUsersLoader$1.a[paramFxKey2.ordinal()]) {
      case 1:
        return FLSUsers.b(get(FxKey.profileId), get(FxKey.user));
      case 2:
        ((FLSUser)paramObject).c.put(LocalDate.parse(get(FxKey.date)), Integer.valueOf(getInt(FxKey.minutes)));
        break;
    } 
    return null;
  }
}
