package com.wisecoders.dbs.cli.command.fls.obsolete;

import com.wisecoders.dbs.project.store.XMLWriter;
import java.io.Writer;
import java.time.LocalDate;

public class FLSUsersStore {
  public FLSUsersStore(Writer paramWriter) {
    XMLWriter xMLWriter = new XMLWriter(paramWriter);
    xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    xMLWriter.a(FxKey.users);
    for (FLSUser fLSUser : FLSUsers.a) {
      xMLWriter.a(FxKey.user).e(FxKey.profileId, fLSUser.a).e(FxKey.name, fLSUser.b);
      for (LocalDate localDate : fLSUser.c.keySet())
        xMLWriter.a(FxKey.activity).e(FxKey.date, localDate).e(FxKey.minutes, fLSUser.c.get(localDate)).b(FxKey.activity); 
      xMLWriter.b(FxKey.user);
    } 
    xMLWriter.b(FxKey.users);
    xMLWriter.b();
  }
}
