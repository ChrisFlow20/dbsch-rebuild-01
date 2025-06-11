package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.project.store.SimpleEncrypt;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.PxKey;

public class PasswordLoader extends AbstractContentHandler {
  private String a;
  
  protected void a(String paramString, int paramInt) {
    PxKey pxKey = PxKey.valueOf(paramString);
    switch (PasswordLoader$1.a[pxKey.ordinal()]) {
      case 1:
        this.a = get(PxKey.name);
        break;
      case 2:
        a(this, this.a);
        break;
    } 
  }
  
  static void a(AbstractContentHandler paramAbstractContentHandler, String paramString) {
    Connector connector = ConnectorManager.getConnectors(paramString, paramAbstractContentHandler.get(PxKey.name));
    String str = paramAbstractContentHandler.get(PxKey.passwd);
    if (connector != null) {
      if (str != null && !str.isEmpty()) {
        connector.setPassword(SimpleEncrypt.b(str));
        connector.setRememberPassword(true);
      } 
      connector.setSshPassword(SimpleEncrypt.b(paramAbstractContentHandler.get(PxKey.sshPassword)));
      connector.setProxyPassword(SimpleEncrypt.b(paramAbstractContentHandler.get(PxKey.proxyPassword)));
      connector.setSshPassphrase(SimpleEncrypt.b(paramAbstractContentHandler.get(PxKey.sshPassphrase)));
    } 
  }
}
