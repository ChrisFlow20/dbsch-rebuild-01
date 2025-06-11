package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.dbms.driver.model.JdbcUrlTemplate;
import com.wisecoders.dbs.project.store.XMLWriter;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Connector$Environment;
import com.wisecoders.dbs.schema.Connector$ProxyType;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.PxKey;
import com.wisecoders.dbs.sys.StringUtil;

public class ConnectorStore {
  public ConnectorStore(XMLWriter paramXMLWriter) {
    paramXMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    paramXMLWriter.a(PxKey.connectors);
    for (String str : ConnectorManager.getConnectorDbIdList()) {
      paramXMLWriter.a(PxKey.dbms).e(PxKey.name, str);
      for (Connector connector : ConnectorManager.getConnectors(str)) {
        paramXMLWriter.a(PxKey.connector).e(PxKey.name, connector.getName());
        paramXMLWriter.e(PxKey.driver_class, connector.getDriverJarClassName());
        paramXMLWriter.e(PxKey.driver_jar, connector.getDriverJarFileName());
        JdbcUrlTemplate jdbcUrlTemplate = connector.getDriverUrl();
        if (jdbcUrlTemplate != null)
          paramXMLWriter.e(PxKey.driver_desc, jdbcUrlTemplate.h()); 
        paramXMLWriter.e(PxKey.url, connector.getCustomUrl());
        paramXMLWriter.e(PxKey.host, connector.getHost());
        paramXMLWriter.e(PxKey.port, Integer.valueOf(connector.getPort()));
        paramXMLWriter.e(PxKey.instance, connector.getInstance());
        paramXMLWriter.e(PxKey.user, connector.getUserName());
        paramXMLWriter.e(PxKey.db_param, connector.getParameter());
        paramXMLWriter.e(PxKey.db_param2, connector.getParameter2());
        paramXMLWriter.e(PxKey.db_param3, connector.getParameter3());
        paramXMLWriter.e(PxKey.db_param4, connector.getParameter4());
        paramXMLWriter.e(PxKey.db_param5, connector.getParameter5());
        paramXMLWriter.e(PxKey.timezone, connector.getTimeZone());
        paramXMLWriter.e(PxKey.connection_properties, connector.getConnectionProperties());
        if (connector.getEnvironment() != Connector$Environment.Normal)
          paramXMLWriter.e(PxKey.usage, connector.getEnvironment()); 
        if (connector.isReadOnly())
          paramXMLWriter.c(PxKey.read_only); 
        if (StringUtil.isFilledTrim(connector.mapping))
          paramXMLWriter.e(PxKey.schema_mapping, connector.mapping.toString()); 
        if (connector.isSshEnable()) {
          paramXMLWriter.e(PxKey.sshEnable, Boolean.valueOf(true));
          paramXMLWriter.e(PxKey.sshHost, connector.getSshHost());
          paramXMLWriter.e(PxKey.sshPort, Integer.valueOf(connector.getSshPort()));
          paramXMLWriter.e(PxKey.sshUser, connector.getSshUser());
          paramXMLWriter.e(PxKey.sshUseKey, Boolean.valueOf(connector.isSshUseKey()));
          paramXMLWriter.e(PxKey.sshPrivateKey, connector.getSshPrivateKeyFile());
        } 
        paramXMLWriter.e(PxKey.useSystemProxy, Boolean.valueOf(connector.isUseSystemProxy()));
        if (connector.getProxyType() != Connector$ProxyType.a) {
          paramXMLWriter.e(PxKey.proxyType, connector.getProxyType());
          paramXMLWriter.e(PxKey.proxyHost, connector.getProxyHost());
          paramXMLWriter.e(PxKey.proxyPort, Integer.valueOf(connector.getProxyPort()));
          paramXMLWriter.e(PxKey.proxyUser, connector.getProxyUser());
        } 
        if (!connector.isVisibleInMenu())
          paramXMLWriter.e(PxKey.visible, Boolean.valueOf(false)); 
        paramXMLWriter.b(PxKey.connector);
      } 
      paramXMLWriter.b(PxKey.dbms);
    } 
    paramXMLWriter.b(PxKey.connectors);
  }
}
