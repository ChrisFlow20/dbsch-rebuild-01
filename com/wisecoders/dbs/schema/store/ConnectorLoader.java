package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.project.store.SimpleEncrypt;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Connector$Environment;
import com.wisecoders.dbs.schema.Connector$ProxyType;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.PxKey;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ConnectorLoader extends AbstractContentHandler {
  private String a;
  
  protected void a(String paramString, int paramInt) {
    PxKey pxKey = PxKey.valueOf(paramString);
    switch (ConnectorLoader$1.a[pxKey.ordinal()]) {
      case 1:
        this.a = get(PxKey.name);
        break;
      case 2:
        createConnector(this, this.a);
        break;
    } 
  }
  
  public static void createConnector(AbstractContentHandler paramAbstractContentHandler, String paramString) {
    Connector connector = ConnectorManager.createConnector(paramAbstractContentHandler
        .get(PxKey.name), paramString, paramAbstractContentHandler
        
        .get(PxKey.driver_class), paramAbstractContentHandler
        .get(PxKey.driver_jar), paramAbstractContentHandler
        .get(PxKey.driver_desc), paramAbstractContentHandler
        .get(PxKey.host), paramAbstractContentHandler
        .getInt(PxKey.port), paramAbstractContentHandler
        .get(PxKey.instance), paramAbstractContentHandler
        .get(PxKey.user), paramAbstractContentHandler
        .getBoolean(PxKey.read_only));
    connector.setCustomUrl(paramAbstractContentHandler.get(PxKey.url));
    connector.setParam(paramAbstractContentHandler.get(PxKey.db_param));
    connector.setParam2(paramAbstractContentHandler.get(PxKey.db_param2));
    connector.setParam3(paramAbstractContentHandler.get(PxKey.db_param3));
    connector.setParam4(paramAbstractContentHandler.get(PxKey.db_param4));
    connector.setParam5(paramAbstractContentHandler.get(PxKey.db_param5));
    connector.setTimeZone(paramAbstractContentHandler.get(PxKey.timezone));
    connector.setConnectionProperties(paramAbstractContentHandler.get(PxKey.connection_properties));
    if (paramAbstractContentHandler.get(PxKey.usage) != null)
      connector.setEnvironment(Connector$Environment.valueOf(paramAbstractContentHandler.get(PxKey.usage))); 
    connector.mapping.loadFromString(paramAbstractContentHandler.get(PxKey.schema_mapping));
    String str = paramAbstractContentHandler.get(PxKey.passwd);
    if (str != null && !str.isEmpty()) {
      connector.setPassword(SimpleEncrypt.b(str));
      connector.setRememberPassword(true);
    } 
    connector.setSshEnable(paramAbstractContentHandler.getBoolean(PxKey.sshEnable, false));
    connector.setSshHost(paramAbstractContentHandler.get(PxKey.sshHost));
    connector.setSshPort(paramAbstractContentHandler.getInt(PxKey.sshPort));
    connector.setSshUser(paramAbstractContentHandler.get(PxKey.sshUser));
    connector.setSshUseKey(paramAbstractContentHandler.getBoolean(PxKey.sshUseKey, false));
    connector.setSshPassword(paramAbstractContentHandler.get(PxKey.sshPassword));
    connector.setUseSystemProxy(paramAbstractContentHandler.getBoolean(PxKey.useSystemProxy, false));
    if (paramAbstractContentHandler.get(PxKey.proxyType) != null)
      connector.setProxyType(Connector$ProxyType.valueOf(paramAbstractContentHandler.get(PxKey.proxyType))); 
    connector.setProxyHost(paramAbstractContentHandler.get(PxKey.proxyHost));
    connector.setProxyPort(paramAbstractContentHandler.getInt(PxKey.proxyPort));
    connector.setProxyUser(paramAbstractContentHandler.get(PxKey.proxyUser));
    connector.setProxyPassword(paramAbstractContentHandler.get(PxKey.proxyPassword));
    connector.setVisibleInMenu(paramAbstractContentHandler.getBoolean(PxKey.visible, true));
    connector.setSshPrivateKeyFile(paramAbstractContentHandler.get(PxKey.sshPrivateKey));
    connector.setSshPassphrase(paramAbstractContentHandler.get(PxKey.sshPassphrase));
  }
}
