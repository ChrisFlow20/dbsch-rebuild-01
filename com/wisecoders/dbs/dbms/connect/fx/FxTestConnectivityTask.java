package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Task;

public class FxTestConnectivityTask extends Task {
  private final FxConnectorEditor a;
  
  private final Connector b;
  
  private final Map c = new HashMap<>();
  
  private final Envoy e;
  
  private final JdbcUrlParam f;
  
  private final boolean g;
  
  FxTestConnectivityTask(FxConnectorEditor paramFxConnectorEditor, Connector paramConnector, JdbcUrlParam paramJdbcUrlParam, boolean paramBoolean1, boolean paramBoolean2) {
    this.b = paramConnector;
    this.a = paramFxConnectorEditor;
    this.f = paramJdbcUrlParam;
    this.g = paramBoolean1;
    this.e = paramConnector.startEnvoy("Test connectivity task");
  }
  
  protected String a() {
    Dbms dbms = Dbms.get(this.b.dbId);
    this.b.setProxy();
    updateMessage("Connecting. Please wait...");
    Log.c("Test connectivity to " + this.b.dbId);
    DatabaseMetaData databaseMetaData = this.e.a();
    if (this.f != null && dbms.getParamSource(this.f) != Dbms$ParamSource.c) {
      ArrayList arrayList = new ArrayList();
      arrayList.addAll(dbms.listParam(this.e, this.f));
      this.c.put(this.f, arrayList);
    } 
    if (this.g)
      Dbms.get(this.e.e()).loadSchemaMapping(this.e); 
    StringBuilder stringBuilder = new StringBuilder();
    String str = databaseMetaData.getDatabaseProductVersion();
    stringBuilder.append(databaseMetaData.getDatabaseProductName()).append(' ');
    if (str != null)
      stringBuilder.append(str.replaceAll("\n", "")).append(' '); 
    stringBuilder.append(databaseMetaData.getDriverName()).append(' ').append(databaseMetaData.getDriverVersion());
    Log.c("Test connectivity done.");
    return stringBuilder.toString();
  }
  
  protected void succeeded() {
    this.e.close();
  }
  
  public static int d = 0;
  
  protected void failed() {
    Throwable throwable = getException();
    d++;
    this.e.n();
    if (!isCancelled()) {
      String str = this.b.getHTMLMessageAndAdvice(throwable, null, null, ConnectivityTip.b);
      Log.a("Test Connection Failed " + str, throwable);
      this.a.showError(str, throwable, "Help", paramObject -> {
            WebBrowserExternal.a(this.a.getDialogScene(), this.b.dbId.toLowerCase() + "/", "connect.html");
            return null;
          });
    } 
  }
  
  List a(JdbcUrlParam paramJdbcUrlParam) {
    return (List)this.c.get(paramJdbcUrlParam);
  }
}
