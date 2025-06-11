package com.wisecoders.dbs.dbms.store;

import com.wisecoders.dbs.dbms.Advice;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsAdvices;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.InputStream;

public class AdvicesLoader extends AbstractContentHandler {
  private final DbmsAdvices a;
  
  private Advice b;
  
  private final AdvicesLoader$AxKey[] c;
  
  public AdvicesLoader(DbmsAdvices paramDbmsAdvices) {
    this.c = new AdvicesLoader$AxKey[10];
    this.a = paramDbmsAdvices;
    a("/dbms/" + StringUtil.escapeForFileName(paramDbmsAdvices.a) + "/advices.xml", ("MySql".equals(paramDbmsAdvices.a) || "PostgreSQL".equals(paramDbmsAdvices.a)));
    String str = Dbms.getInheritedDbms(paramDbmsAdvices.a);
    if (str != null)
      a("/dbms/" + StringUtil.escapeForFileName(str) + "/advices.xml", false); 
    a("resources/advices.xml", true);
  }
  
  private void a(String paramString, boolean paramBoolean) {
    try {
      InputStream inputStream = Dbms.class.getResourceAsStream(paramString);
      try {
        if (inputStream != null) {
          parse(inputStream);
        } else if (paramBoolean) {
          Log.f("CANNOT FIND WARN FILE " + paramString + " !!!");
        } 
        if (inputStream != null)
          inputStream.close(); 
      } catch (Throwable throwable) {
        if (inputStream != null)
          try {
            inputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.c(exception);
    } 
  }
  
  protected void a(String paramString, int paramInt) {
    AdvicesLoader$AxKey advicesLoader$AxKey = AdvicesLoader$AxKey.valueOf(paramString);
    this.c[paramInt] = advicesLoader$AxKey;
    a((paramInt > 0) ? this.c[paramInt - 1] : null, advicesLoader$AxKey);
  }
  
  private void a(AdvicesLoader$AxKey paramAdvicesLoader$AxKey1, AdvicesLoader$AxKey paramAdvicesLoader$AxKey2) {
    if (paramAdvicesLoader$AxKey1 != null)
      switch (AdvicesLoader$1.a[paramAdvicesLoader$AxKey1.ordinal()]) {
        case 4:
          switch (AdvicesLoader$1.a[paramAdvicesLoader$AxKey2.ordinal()]) {
            case 1:
              this.a.a(this.b = new Advice());
              break;
          } 
          break;
        case 1:
          switch (AdvicesLoader$1.a[paramAdvicesLoader$AxKey2.ordinal()]) {
            case 2:
              if (getBoolean(AdvicesLoader$AxKey.checkLocalhost))
                this.b.c(); 
              if (getBoolean(AdvicesLoader$AxKey.checkRemote))
                this.b.b(); 
              if (getBoolean(AdvicesLoader$AxKey.checkJavaNot16))
                this.b.d(); 
              if (getBoolean(AdvicesLoader$AxKey.checkWindows))
                this.b.e(); 
              if (getBoolean(AdvicesLoader$AxKey.checkMac))
                this.b.f(); 
              if (get(AdvicesLoader$AxKey.checkUserOtherThan) != null)
                this.b.c(get(AdvicesLoader$AxKey.checkUserOtherThan)); 
              this.b.b(getBody());
              break;
            case 3:
              this.b.a(getBody());
              break;
          } 
          break;
      }  
  }
}
