package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.project.store.SimpleEncrypt;
import com.wisecoders.dbs.project.store.XMLWriter;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Connector$ProxyType;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.PxKey;

public class PasswordStore {
  public PasswordStore(XMLWriter paramXMLWriter) {
    paramXMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    paramXMLWriter.a(PxKey.connectors);
    for (String str : ConnectorManager.getConnectorDbIdList()) {
      paramXMLWriter.a(PxKey.dbms).e(PxKey.name, str);
      for (Connector connector : ConnectorManager.getConnectors(str)) {
        if (connector.isRememberPassword() && connector.getPassword() != null) {
          paramXMLWriter.a(PxKey.connector).e(PxKey.name, connector.getName());
          paramXMLWriter.e(PxKey.passwd, SimpleEncrypt.a(connector.getPassword()));
          if (connector.isSshEnable()) {
            paramXMLWriter.e(PxKey.sshPassword, SimpleEncrypt.a(connector.getSshPassword()));
            paramXMLWriter.e(PxKey.sshPassphrase, SimpleEncrypt.a(connector.getSshPassphrase()));
          } 
          if (connector.getProxyType() != Connector$ProxyType.a)
            paramXMLWriter.e(PxKey.proxyPassword, SimpleEncrypt.a(connector.getProxyPassword())); 
          paramXMLWriter.b(PxKey.connector);
        } 
      } 
      paramXMLWriter.b(PxKey.dbms);
    } 
    paramXMLWriter.b(PxKey.connectors);
  }
}
