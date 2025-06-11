package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;

public class CLIConnectorManager extends ConnectorManager {
  private static final List b = new ArrayList();
  
  public static final Folder a = new Folder("Connection Groups", "ConnectionGroups", null, ConnectorGroup.class, true);
  
  public static List a() {
    return b;
  }
  
  public static Connector b() {
    return b.isEmpty() ? null : b.get(0);
  }
  
  public static boolean c() {
    return (b.size() > 1);
  }
  
  private static void d() {
    for (Connector connector : b)
      connector.closeAllEnvoysAndSsh(); 
    b.clear();
  }
  
  public static ConnectorGroup a(String paramString) {
    ConnectorGroup connectorGroup = new ConnectorGroup(paramString);
    a.add(connectorGroup);
    return connectorGroup;
  }
  
  public static ConnectorGroup b(String paramString) {
    for (ConnectorGroup connectorGroup : a) {
      if (connectorGroup.getName().equals(paramString))
        return connectorGroup; 
    } 
    return null;
  }
  
  public static boolean a(String paramString1, String paramString2, boolean paramBoolean) {
    d();
    ArrayList<Connector> arrayList = new ArrayList();
    ArrayList<String> arrayList1 = new ArrayList();
    if (paramString2 != null)
      for (String str : paramString2.split(","))
        arrayList1.add(str.trim().toLowerCase());  
    for (String str : paramString1.split(",")) {
      ConnectorGroup connectorGroup = (ConnectorGroup)a.getByName(str);
      if (connectorGroup != null && !arrayList1.contains(connectorGroup.getName()))
        for (Connector connector : ConnectorManager.getConnectors()) {
          if (!arrayList1.contains(connector.getName().toLowerCase()) && !arrayList.contains(connector))
            arrayList.add(connector); 
        }  
      for (Connector connector : ConnectorManager.getConnectors()) {
        if (connector.getName().equals(str) && !arrayList1.contains(connector.getName()) && !arrayList.contains(connector))
          arrayList.add(connector); 
      } 
    } 
    for (Connector connector : arrayList) {
      if (!paramBoolean)
        try {
          Envoy envoy = connector.startEnvoy(connector.getName());
          try {
            envoy.a().getDatabaseProductVersion();
            if (connector.getCliProject().getEntityCount() == 0)
              new Thread(() -> {
                    if ((paramConnector.getCliProject()).schemas.isEmpty())
                      try {
                        Dbms.get(paramConnector.dbId).loadSchemasAndCatalogs(paramEnvoy, paramConnector.getCliProject());
                        Schema schema = paramConnector.getCliProject().getSchema((Dbms.get(paramConnector.dbId)).defaultSchema.c_());
                        if ((Dbms.get(paramConnector.dbId)).defaultSchema.j() && schema != null) {
                          TreeSelection treeSelection = new TreeSelection();
                          treeSelection.select(schema);
                          treeSelection.select(schema.procedures, false);
                          treeSelection.select(schema.functions, false);
                          treeSelection.select(schema.triggers, false);
                          new Importer(paramConnector.getCliProject(), treeSelection, paramEnvoy);
                        } 
                      } catch (Throwable throwable) {
                        Log.b(throwable);
                      }  
                  }); 
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
        } catch (Throwable throwable) {
          throw new Exception("Failed to connect to " + a(connector) + "\n" + throwable.getLocalizedMessage(), throwable);
        }  
      b.add(connector);
    } 
    return !b.isEmpty();
  }
  
  private static String a(Connector paramConnector) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramConnector.getName()).append(" dbms=").append(paramConnector.dbId);
    if (paramConnector.getCustomUrl() != null) {
      stringBuilder.append(" url=").append(paramConnector.getCustomUrl());
    } else if (paramConnector.getDriverUrl() != null) {
      stringBuilder.append(" template=").append(paramConnector.getDriverUrl()).append(" authenticate=").append(paramConnector.getDriverUrl().a());
    } 
    stringBuilder.append(" password=").append(StringUtil.isFilled(paramConnector.getPassword()));
    if (paramConnector.getDriverJarClassName() == null) {
      stringBuilder.append(" No driver. ");
    } else {
      stringBuilder.append(" class=").append(paramConnector.getDriverJarClassName()).append(" driver=").append(paramConnector.getDriverJarFileName());
    } 
    return stringBuilder.toString();
  }
}
