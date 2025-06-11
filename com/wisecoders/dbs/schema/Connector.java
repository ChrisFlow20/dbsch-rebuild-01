package com.wisecoders.dbs.schema;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsAdvices;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.SqlEvent;
import com.wisecoders.dbs.dbms.connect.model.envoy.DataGeneratorStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.JsonGenerateDataJob;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.dbms.driver.model.DriverJarClass;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dbms.driver.model.JdbcUrlTemplate;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.project.store.SimpleEncrypt;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.net.Authenticator;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.util.Callback;

@DoNotObfuscate
public final class Connector {
  private final List a = new CopyOnWriteArrayList();
  
  private final List b = new CopyOnWriteArrayList();
  
  public final String dbId;
  
  private String c;
  
  private String d;
  
  private String e;
  
  private String f;
  
  private String g;
  
  private String h;
  
  private String i;
  
  private String j;
  
  private String k;
  
  private String l;
  
  private String m;
  
  private String n;
  
  private String o;
  
  private String p;
  
  private String q;
  
  private String r;
  
  private String s;
  
  private String t;
  
  private String u;
  
  private String v;
  
  private String w;
  
  private int x;
  
  private boolean y;
  
  private Connector$ProxyType z = Connector$ProxyType.a;
  
  private String A;
  
  private String B;
  
  private JdbcUrlTemplate C;
  
  private int D;
  
  private int E = 22;
  
  private boolean F = false;
  
  private boolean G = false;
  
  private boolean H = false;
  
  private boolean I = false;
  
  private String J;
  
  private boolean K = false;
  
  private long L = -1L;
  
  private Connector$Environment M = Connector$Environment.Normal;
  
  public final SchemaMapping mapping = new SchemaMapping();
  
  private boolean N = true;
  
  private boolean O;
  
  private Session P;
  
  public Envoy startEnvoy(String paramString) {
    if (this.C != null && this.C.r() && !this.a.isEmpty())
      return ((Envoy)this.a.get(0)).a(paramString); 
    for (Envoy envoy1 : this.a) {
      if (envoy1.g())
        return envoy1.a(paramString); 
    } 
    Envoy envoy = new Envoy(this, paramString);
    this.a.add(envoy);
    return envoy;
  }
  
  public void setName(String paramString) {
    this.c = paramString;
  }
  
  public String getName() {
    return this.c;
  }
  
  public String getUserName() {
    return this.f;
  }
  
  public boolean setUser(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.f, paramString);
    this.f = paramString;
    return bool;
  }
  
  public String getPassword() {
    return this.g;
  }
  
  public boolean setPassword(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.g, paramString);
    this.g = paramString;
    return bool;
  }
  
  public boolean setParam(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.h, paramString);
    this.h = paramString;
    return bool;
  }
  
  public boolean setParam2(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.i, paramString);
    this.i = paramString;
    return bool;
  }
  
  public boolean setParam3(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.j, paramString);
    this.j = paramString;
    return bool;
  }
  
  public boolean setParam4(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.k, paramString);
    this.k = paramString;
    return bool;
  }
  
  public boolean setParam5(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.l, paramString);
    this.l = paramString;
    return bool;
  }
  
  public String getParameter() {
    return this.h;
  }
  
  public String getParameter2() {
    return this.i;
  }
  
  public String getParameter3() {
    return this.j;
  }
  
  public String getParameter4() {
    return this.k;
  }
  
  public String getParameter5() {
    return this.l;
  }
  
  public String getHost() {
    return this.d;
  }
  
  public boolean setHost(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.d, paramString);
    this.d = paramString;
    return bool;
  }
  
  public int getPort() {
    return this.D;
  }
  
  public boolean setPort(int paramInt) {
    boolean bool = (this.D != paramInt) ? true : false;
    this.D = paramInt;
    return bool;
  }
  
  public boolean isLocalhost() {
    return ("localhost".equalsIgnoreCase(this.d) || "127.0.0.1".equals(this.d));
  }
  
  public boolean isLocalhostAndDefaultPort() {
    return (isLocalhost() && (this.D == (Dbms.get(this.dbId)).defaultPort.a() || (this.C != null && this.C
      .b(4) && this.D == -1)));
  }
  
  public String getInstance() {
    return this.e;
  }
  
  public boolean setUrlTemplateName(String paramString) {
    boolean bool = !StringUtil.areEqual(paramString, this.n) ? true : false;
    this.n = paramString;
    return bool;
  }
  
  public String getUrlTemplateName() {
    return this.n;
  }
  
  public boolean setInstance(String paramString) {
    boolean bool;
    if (paramString == null || paramString.isEmpty()) {
      bool = (this.e != null) ? true : false;
      this.e = null;
    } else {
      bool = !paramString.equals(this.e) ? true : false;
      this.e = paramString;
    } 
    return bool;
  }
  
  public void closeAllEnvoysAndSsh() {
    for (Envoy envoy : this.a)
      envoy.n(); 
    a();
  }
  
  public boolean setReadOnly(boolean paramBoolean) {
    boolean bool = (this.F != paramBoolean) ? true : false;
    this.F = paramBoolean;
    return bool;
  }
  
  public boolean isReadOnly() {
    return this.F;
  }
  
  public void throwSQLExceptionIfReadOnly() {
    if (this.F)
      throw new SQLException("Connection is Read-Only. Check the connection properties."); 
  }
  
  public int getPhysicalConnectedCount() {
    byte b = 0;
    for (Envoy envoy : this.a) {
      if (envoy.h())
        b++; 
    } 
    return b;
  }
  
  public boolean isProcessing() {
    for (Envoy envoy : this.a) {
      if (envoy.f())
        return true; 
    } 
    return false;
  }
  
  public boolean setRememberPassword(boolean paramBoolean) {
    boolean bool = (this.G != paramBoolean) ? true : false;
    this.G = paramBoolean;
    return bool;
  }
  
  public boolean isRememberPassword() {
    return this.G;
  }
  
  public boolean setDriverJarClass(String paramString1, String paramString2) {
    boolean bool = (!StringUtil.areEqual(this.A, paramString1) || !StringUtil.areEqual(this.B, paramString2)) ? true : false;
    this.A = paramString1;
    this.B = paramString2;
    return bool;
  }
  
  public boolean setDriverUrl(JdbcUrlTemplate paramJdbcUrlTemplate) {
    boolean bool = (this.C == null || this.C != paramJdbcUrlTemplate) ? true : false;
    this.C = paramJdbcUrlTemplate;
    return bool;
  }
  
  public String getDriverJarClassName() {
    return this.B;
  }
  
  public String getDriverJarFileName() {
    return this.A;
  }
  
  public JdbcUrlTemplate getDriverUrl() {
    return this.C;
  }
  
  public boolean needsEdit() {
    return (this.C == null) ? ((this.m == null)) : (
      (!this.G && this.C
      .a() && this.g == null && !this.K));
  }
  
  public void loadDriverForSampleProject() {
    DriverManager.a(this.dbId).k();
    if (!DriverManager.a(this.dbId).i().isEmpty()) {
      DriverJarClass driverJarClass = DriverManager.a(this.dbId).i().get(0);
      setDriverJarClass((driverJarClass.b()).b.getName(), driverJarClass.a());
    } 
  }
  
  public String getURL() {
    if (this.C == null)
      return this.m; 
    String str = this.H ? this.C.a("localhost", getSSHLocalPort(), this.e, this.h, this.i, this.j, this.k, this.l, this.f, this.g) : this.C.a(this.d, this.D, this.e, this.h, this.i, this.j, this.k, this.l, this.f, this.g);
    if (this.C != null && this.C.s() && this.D == (Dbms.get(this.dbId)).defaultPort.a())
      str = str.replaceFirst(":" + this.D, ""); 
    return str;
  }
  
  public boolean setConnectionProperties(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.o, paramString);
    this.o = paramString;
    return bool;
  }
  
  public String getConnectionProperties() {
    return this.o;
  }
  
  public Properties getProperties() {
    Properties properties = new Properties();
    if (this.C == null || this.C.a()) {
      if (this.f != null)
        properties.put("user", this.f); 
      if (this.g != null) {
        properties.put("password", this.g);
      } else if ((Dbms.get(this.dbId)).allowEmptyPassword.b()) {
        properties.put("password", "");
      } 
    } 
    if (StringUtil.isFilledTrim(this.o))
      for (String str : this.o.split(";")) {
        String[] arrayOfString = str.split("=");
        if (arrayOfString.length == 2)
          properties.put(arrayOfString[0], arrayOfString[1]); 
      }  
    return properties;
  }
  
  public Connector(String paramString1, String paramString2) {
    this.O = false;
    this.R = -1;
    this.dbId = paramString1;
    this.c = paramString2;
    Dbms dbms = Dbms.get(paramString1);
    this.d = "localhost";
    this.D = dbms.defaultPort.a();
    String str = dbms.defaultUser.c_();
    if ("SYSUSER".equals(str))
      str = System.getProperty("user.name"); 
    this.f = str;
    this.g = dbms.defaultPassword.c_();
  }
  
  public Connector(String paramString1, String paramString2, String paramString3, String paramString4, JdbcUrlTemplate paramJdbcUrlTemplate, String paramString5, int paramInt, String paramString6, String paramString7, boolean paramBoolean) {
    this.O = false;
    this.R = -1;
    this.c = paramString1;
    this.dbId = paramString2;
    this.A = paramString4;
    this.B = paramString3;
    this.C = paramJdbcUrlTemplate;
    setHost(paramString5);
    this.D = paramInt;
    this.e = paramString6;
    this.f = paramString7;
    this.F = paramBoolean;
  }
  
  public void markForDeletion() {
    this.O = true;
  }
  
  public boolean isMarkedForDeletion() {
    return this.O;
  }
  
  public void refresh() {
    if (this.O)
      closeAllEnvoysAndSsh(); 
    if (this.C == null || !this.C.r())
      this.a.removeIf(Envoy::o); 
  }
  
  public boolean setCustomUrl(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.m, paramString);
    this.m = paramString;
    return bool;
  }
  
  public String getCustomUrl() {
    return this.m;
  }
  
  public void learnDbmsIfRequired() {
    if (DbmsTypes.get(this.dbId).isLearnDataTypes())
      try {
        learnDbmsTemplate();
      } catch (Exception exception) {
        Log.c(exception);
      }  
  }
  
  public void learnDbmsTemplate() {
    Envoy envoy = startEnvoy("Schema import check connectivity");
    try {
      DatabaseMetaData databaseMetaData = envoy.a();
      Log.c("LEARN DATABASE: " + envoy.e() + " Vendor: " + envoy.e() + " Product: " + databaseMetaData.getDatabaseProductName());
      Log.a("System", "learnDatabase");
      Dbms dbms = Dbms.get(this.dbId);
      dbms.learnDataTypes(envoy);
      StringBuilder stringBuilder = new StringBuilder();
      ResultSet resultSet = databaseMetaData.getTableTypes();
      while (resultSet.next())
        stringBuilder.append(!stringBuilder.isEmpty() ? "," : "").append(resultSet.getString(1)); 
      resultSet.close();
      dbms.tableTypes.b(stringBuilder.toString());
      dbms.reserved.b(databaseMetaData.getSQLKeywords());
      dbms.stringFunctions.b(databaseMetaData.getStringFunctions());
      dbms.dateTimeFunctions.b(databaseMetaData.getTimeDateFunctions());
      dbms.identifierDelimiter.a(databaseMetaData.getIdentifierQuoteString());
      dbms.maxSchemaNameLength.a(databaseMetaData.getMaxSchemaNameLength());
      dbms.maxTableNameLength.a(databaseMetaData.getMaxTableNameLength());
      dbms.maxColumnNameLength.a(databaseMetaData.getMaxColumnNameLength());
      if (databaseMetaData.storesLowerCaseIdentifiers()) {
        dbms.identifierCases.a(LetterCase.a);
      } else if (databaseMetaData.storesMixedCaseIdentifiers()) {
        dbms.identifierCases.a(LetterCase.d);
      } else if (databaseMetaData.storesUpperCaseIdentifiers()) {
        dbms.identifierCases.a(LetterCase.b);
      } 
      if (databaseMetaData.storesLowerCaseQuotedIdentifiers()) {
        dbms.quotedIdentifierCases.a(LetterCase.a);
      } else if (databaseMetaData.storesMixedCaseQuotedIdentifiers()) {
        dbms.quotedIdentifierCases.a(LetterCase.d);
      } else if (databaseMetaData.storesUpperCaseQuotedIdentifiers()) {
        dbms.quotedIdentifierCases.a(LetterCase.b);
      } 
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
  }
  
  public void setHasConnected() {
    this.K = true;
  }
  
  public boolean shouldAutoSynchronize() {
    return (this.L > -1L && System.currentTimeMillis() - this.L > Sys.B.autoRefreshDelay.a() * 1000L);
  }
  
  public void resetResyncFlag() {
    this.L = -1L;
  }
  
  public String getSshHost() {
    return this.p;
  }
  
  public void setSshHost(String paramString) {
    this.p = paramString;
  }
  
  public String getSshUser() {
    return this.q;
  }
  
  public void setSshUser(String paramString) {
    this.q = paramString;
  }
  
  public String getSshPassword() {
    return this.r;
  }
  
  public void setSshPassword(String paramString) {
    this.r = paramString;
  }
  
  public String getSshPrivateKeyFile() {
    return this.s;
  }
  
  public void setSshPrivateKeyFile(String paramString) {
    this.s = paramString;
  }
  
  public String getSshPassphrase() {
    return this.t;
  }
  
  public void setSshPassphrase(String paramString) {
    this.t = paramString;
  }
  
  public int getSshPort() {
    return this.E;
  }
  
  public void setSshPort(int paramInt) {
    this.E = paramInt;
  }
  
  public boolean isSshEnable() {
    return this.H;
  }
  
  public void setSshEnable(boolean paramBoolean) {
    this.H = paramBoolean;
  }
  
  public boolean isSshUseKey() {
    return this.I;
  }
  
  public void setSshUseKey(boolean paramBoolean) {
    this.I = paramBoolean;
  }
  
  public Session setupSSHTunnel(boolean paramBoolean) {
    if (paramBoolean && this.P != null && this.P.isConnected())
      return this.P; 
    a();
    b();
    return c();
  }
  
  public Session pingSSHTunnel() {
    a();
    return b();
  }
  
  private void a() {
    if (this.P != null) {
      this.P.disconnect();
      this.P = null;
    } 
  }
  
  private Session b() {
    if (this.H && this.p != null)
      try {
        Log.c("Setup SSH Tunnel " + this.q + "@" + this.p + ":" + this.E);
        if (JSch.getConfig("server_host_key") != null && !JSch.getConfig("server_host_key").contains("ssh-rsa")) {
          JSch.setConfig("server_host_key", JSch.getConfig("server_host_key") + ",ssh-rsa");
          JSch.setConfig("PubkeyAcceptedAlgorithms", JSch.getConfig("PubkeyAcceptedAlgorithms") + ",ssh-rsa");
        } 
        JSch jSch = new JSch();
        JSch.setLogger(new Connector$1(this));
        this.P = jSch.getSession(this.q, this.p, this.E);
        this.P.setUserInfo(new Connector$JCraftUserInfo(this));
        Log.c("Got session.");
        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put("StrictHostKeyChecking", "no");
        this.P.setConfig(hashtable);
        if (this.I) {
          Log.c("Add identity key");
          if (StringUtil.isEmptyTrim(this.t)) {
            jSch.addIdentity(this.s);
          } else {
            jSch.addIdentity(this.s, this.t);
          } 
        } else {
          Log.c("Add password " + this.g);
          this.P.setPassword(this.r);
        } 
        this.P.connect();
        Log.c("Session connected.");
        return this.P;
      } catch (JSchException jSchException) {
        if (this.P != null) {
          this.P.disconnect();
          this.P = null;
        } 
        throw jSchException;
      }  
    return null;
  }
  
  private Session c() {
    if (this.H && this.P != null)
      try {
        Log.c("SSH setPortForwardingL( localPort=" + getSSHLocalPort() + " remoteHost=" + this.d + " remotePort=" + this.D);
        int i = this.P.setPortForwardingL(getSSHLocalPort(), this.d, this.D);
        Log.c("Port forwarded successful to localhost port " + i);
        return this.P;
      } catch (JSchException jSchException) {
        if (this.P != null) {
          this.P.disconnect();
          this.P = null;
        } 
        throw jSchException;
      }  
    return null;
  }
  
  private static int Q = -1;
  
  private int R;
  
  private Project S;
  
  public String activeCatalogName;
  
  public int getSSHLocalPort() {
    if (Q == -1) {
      Q = Sys.B.sshLocalPort.a();
      this.R = Q;
    } else if (this.R == -1) {
      this.R = ++Q;
    } 
    return this.R;
  }
  
  public void schemaChanged() {
    this.L = System.currentTimeMillis();
  }
  
  public void delaySchemaChanged() {
    if (this.L > 0L)
      this.L = System.currentTimeMillis(); 
  }
  
  public synchronized void addSqlEvent(SqlEvent paramSqlEvent) {
    this.b.add(0, paramSqlEvent);
  }
  
  public synchronized void transferSqlEventsTo(List paramList) {
    paramList.addAll(0, this.b);
    this.b.clear();
  }
  
  @GroovyMethod
  public Schema importSchema(Project paramProject, String paramString) {
    Envoy envoy = startEnvoy("Script reverse engineering task");
    try {
      Dbms.get(envoy.e()).loadSchemasAndCatalogs(envoy, paramProject);
      TreeSelection treeSelection = new TreeSelection();
      Schema schema1 = paramProject.getSchema(paramString);
      if (schema1 == null)
        throw new SQLException("Schema " + paramString + " cannot be found in the database."); 
      new StructureImporter(schema1, envoy);
      treeSelection.select(schema1);
      new Importer(paramProject, treeSelection, envoy);
      SyncUtil.a(paramProject, treeSelection);
      Schema schema2 = schema1;
      if (envoy != null)
        envoy.close(); 
      return schema2;
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
  
  @GroovyMethod
  public List importSchemes(Project paramProject, String... paramVarArgs) {
    return importSchemes(paramProject, null, paramVarArgs);
  }
  
  @GroovyMethod
  public List importSchemes(Project paramProject, Callback paramCallback, String... paramVarArgs) {
    DriverManager.a(this.dbId).k();
    Envoy envoy = startEnvoy("Script reverse engineering task");
    try {
      Dbms.get(envoy.e()).loadSchemasAndCatalogs(envoy, paramProject);
      TreeSelection treeSelection = new TreeSelection();
      ArrayList<Schema> arrayList1 = new ArrayList();
      for (String str : paramVarArgs) {
        Schema schema = paramProject.getSchema(str);
        if (schema == null)
          throw new SQLException("Schema " + str + " cannot be found in the database."); 
        new StructureImporter(schema, envoy);
        treeSelection.select(schema);
        arrayList1.add(schema);
      } 
      if (paramCallback != null)
        paramCallback.call(treeSelection); 
      new Importer(paramProject, treeSelection, envoy);
      SyncUtil.a(paramProject, treeSelection);
      ArrayList<Schema> arrayList2 = arrayList1;
      if (envoy != null)
        envoy.close(); 
      return arrayList2;
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
  
  public boolean isMongo() {
    return "MongoDb".equalsIgnoreCase(this.dbId);
  }
  
  public String getTimeZone() {
    return this.J;
  }
  
  public boolean setTimeZone(String paramString) {
    if ("".equals(paramString))
      paramString = null; 
    boolean bool = StringUtil.areEqual(this.J, paramString);
    this.J = paramString;
    return bool;
  }
  
  public boolean setEnvironment(Connector$Environment paramConnector$Environment) {
    boolean bool = (this.M != paramConnector$Environment) ? true : false;
    if (paramConnector$Environment != null)
      this.M = paramConnector$Environment; 
    return bool;
  }
  
  @GroovyMethod
  public Connector$Environment getEnvironment() {
    return this.M;
  }
  
  public String getProxyHost() {
    return this.u;
  }
  
  public void setProxyHost(String paramString) {
    this.u = paramString;
  }
  
  public int getProxyPort() {
    return this.x;
  }
  
  public void setProxyPort(int paramInt) {
    this.x = paramInt;
  }
  
  public String getProxyUser() {
    return this.v;
  }
  
  public void setProxyUser(String paramString) {
    this.v = paramString;
  }
  
  public String getProxyPassword() {
    return this.w;
  }
  
  public void setProxyPassword(String paramString) {
    this.w = paramString;
  }
  
  public Connector$ProxyType getProxyType() {
    return this.z;
  }
  
  public void setProxyType(Connector$ProxyType paramConnector$ProxyType) {
    this.z = paramConnector$ProxyType;
  }
  
  public boolean isUseSystemProxy() {
    return this.y;
  }
  
  public void setUseSystemProxy(boolean paramBoolean) {
    this.y = paramBoolean;
  }
  
  public void setProxy() {
    NetworkProxy.a();
    if (this.y)
      System.setProperty("java.net.useSystemProxies", "true"); 
    switch (Connector$3.a[this.z.ordinal()]) {
      case 1:
        if (this.u != null) {
          System.setProperty("http.proxyHost", this.u);
          System.setProperty("http.proxyPort", "" + this.x);
        } 
        break;
      case 2:
        if (this.u != null) {
          System.setProperty("https.proxyHost", this.u);
          System.setProperty("https.proxyPort", "" + this.x);
        } 
        break;
      case 3:
        if (this.u != null) {
          System.setProperty("socksProxyHost", this.u);
          System.setProperty("socksProxyPort", "" + this.x);
        } 
        break;
    } 
    if (this.v != null) {
      if (this.z == Connector$ProxyType.d) {
        System.setProperty("java.net.socks.username", this.v);
        System.setProperty("java.net.socks.password", this.w);
      } 
      Authenticator.setDefault(new Connector$2(this));
    } 
  }
  
  public String getStatusString() {
    return (this.a.isEmpty() ? "Inactive" : "Connected") + (this.a.isEmpty() ? "Inactive" : "Connected");
  }
  
  public boolean hasActiveEnvoys() {
    for (Envoy envoy : this.a) {
      if (!envoy.g())
        return true; 
    } 
    return false;
  }
  
  public String getStatistics() {
    StringBuilder stringBuilder = new StringBuilder();
    byte b1 = 0, b2 = 0;
    for (Envoy envoy : this.a) {
      if (envoy.g()) {
        b2++;
        continue;
      } 
      stringBuilder.append("\n").append(envoy.b);
      b1++;
    } 
    return ((b2 > 0) ? ("" + b2 + " idle") : "") + ((b2 > 0) ? ("" + b2 + " idle") : "") + ((b1 > 0) ? ("" + b1 + " active") : "");
  }
  
  public String getHTMLMessageAndAdvice(Throwable paramThrowable, String paramString1, String paramString2, ConnectivityTip paramConnectivityTip) {
    return DbmsAdvices.a(this.dbId).a(paramThrowable, (paramString1 != null) ? paramString1 : "Error from the Database", paramString2, this, paramConnectivityTip);
  }
  
  public String getPlainMessageAndAdvice(Throwable paramThrowable) {
    return DbmsAdvices.a(this.dbId).a(paramThrowable, this);
  }
  
  @GroovyMethod
  public void generateData(GeneratorTable paramGeneratorTable) {
    Envoy envoy = startEnvoy("Data Generator");
    try {
      UpdateStatement updateStatement = (UpdateStatement)(isMongo() ? new JsonGenerateDataJob(envoy, paramGeneratorTable) : new DataGeneratorStatement(envoy, paramGeneratorTable));
      updateStatement.a();
      updateStatement.close();
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
  }
  
  public void loadFromHistory(String paramString) {
    if (StringUtil.isFilledTrim(paramString)) {
      setName(paramString);
      this.d = Pref.c(paramString + "host", "localhost");
      Dbms dbms = Dbms.get(this.dbId);
      this.D = Pref.b(paramString + "port", dbms.defaultPort.a());
      this.f = Pref.a(paramString + "user");
      this.e = Pref.a(paramString + "instance");
      this.h = Pref.a(paramString + "param");
      this.i = Pref.a(paramString + "param2");
      this.j = Pref.a(paramString + "param3");
      this.k = Pref.a(paramString + "param4");
      this.l = Pref.a(paramString + "param5");
      this.n = Pref.a(paramString + "urlDesc");
      this.F = Pref.b(paramString + "readOnly", false);
      this.G = Pref.b(paramString + "remember", false);
      this.m = Pref.a(paramString + "customUrl");
      String str = Pref.c(paramString + "user", "");
      if (StringUtil.isFilledTrim(str)) {
        String str1 = Pref.a(paramString + "password" + paramString);
        this.g = SimpleEncrypt.b(str1);
      } 
      if ("localhost".equals(this.d)) {
        if (StringUtil.isEmptyTrim(str)) {
          String str1 = dbms.defaultUser.c_();
          if ("SYSUSER".equals(str1))
            str1 = System.getProperty("user.name"); 
          this.f = str1;
          this.g = dbms.defaultPassword.c_();
        } 
        if (StringUtil.isEmptyTrim(this.e)) {
          this.e = dbms.defaultInstance.c_();
          this.h = dbms.defaultUrlParam.c_();
          this.i = dbms.defaultUrlParam2.c_();
          this.j = dbms.defaultUrlParam3.c_();
          this.k = dbms.defaultUrlParam4.c_();
          this.l = dbms.defaultUrlParam5.c_();
        } 
      } 
      this.H = false;
      this.p = Pref.a(paramString + "sshHost");
      this.E = Pref.b(paramString + "sshPort", 22);
      this.q = Pref.a(paramString + "sshUser");
      this.I = Pref.b(paramString + "sshUseKey", false);
      this.r = Pref.a(paramString + "sshPassword");
      this.s = Pref.a(paramString + "sshPublicKey");
      this.t = Pref.a(paramString + "sshPassphrase");
    } 
  }
  
  public void setVisibleInMenu(boolean paramBoolean) {
    this.N = paramBoolean;
  }
  
  public boolean isVisibleInMenu() {
    return this.N;
  }
  
  public Project getCliProject() {
    if (this.S == null)
      this.S = new Project(getName(), this.dbId); 
    return this.S;
  }
  
  public Schema getActiveCliSchema() {
    Project project = getCliProject();
    if (this.activeCatalogName != null) {
      Schema schema = project.getSchema(this.activeCatalogName);
      if (schema != null)
        return schema; 
    } 
    return (project.schemas.size() > 0) ? (Schema)project.schemas.get(0) : null;
  }
  
  public void setActiveCliSchema(String paramString) {
    this.activeCatalogName = paramString;
  }
}
