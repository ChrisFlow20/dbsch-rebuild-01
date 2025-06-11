package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.jcraft.jsch.JSchException;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.SqlEvent;
import com.wisecoders.dbs.dbms.connect.model.SqlEventStatus;
import com.wisecoders.dbs.dbms.driver.model.DriverJarClass;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.sql.Sql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Envoy implements AutoCloseable {
  public final Connector a;
  
  public String b;
  
  private Connection d;
  
  private Statement e;
  
  private boolean f = false;
  
  private boolean g = false;
  
  private boolean h = true;
  
  public boolean c = true;
  
  private boolean i = false;
  
  private SqlEvent j;
  
  private Sql k;
  
  public Envoy(Connector paramConnector, String paramString) {
    this.a = paramConnector;
    this.b = paramString;
  }
  
  public Envoy a(String paramString) {
    this.b = (paramString != null) ? paramString : "";
    this.c = true;
    this.f = false;
    this.g = false;
    return this;
  }
  
  private void s() {
    this.h = false;
    r();
    if (o()) {
      DriverJarClass driverJarClass = DriverManager.a(this.a.dbId).a(this.a);
      if (driverJarClass == null)
        throw new SQLException(this.a.dbId + " is missing the JDBC driver, which should be downloaded automatically from dbschema.com to USER_HOME/.DbSchema/drivers/" + this.a.dbId + " when editing the connection."); 
      try {
        this.a.setupSSHTunnel(true);
      } catch (JSchException jSchException) {
        StringBuilder stringBuilder = new StringBuilder("SSH Tunnel Exception: ");
        stringBuilder.append(jSchException);
        if (jSchException.getLocalizedMessage() != null && jSchException.getLocalizedMessage().contains("invalid privatekey"))
          stringBuilder.append("\n\nJSch does not support this key format.\nRecent versions of OpenSSH (7.8 and newer) generate keys in new OpenSSH format by default, which starts with:\n\n-----BEGIN OPENSSH PRIVATE KEY-----\n\nYou can use ssh-keygen to convert the key to the classic OpenSSH format:\n\nssh-keygen -p -f <privateKeyFile> -m pem -P passphrase -N passphrase"); 
        throw new SQLException(stringBuilder.toString(), jSchException);
      } 
      if (StringUtil.isFilledTrim(this.a.getTimeZone())) {
        System.setProperty("user.timezone", this.a.getTimeZone());
      } else {
        System.clearProperty("user.timezone");
      } 
      Properties properties = this.a.getProperties();
      Log.c("Connect: " + this.a.dbId);
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      try {
        Thread.currentThread().setContextClassLoader(driverJarClass.e().getClass().getClassLoader());
        String str1 = this.a.getURL();
        if (StringUtil.isEmpty(str1))
          throw new SQLException("Please choose a JDBC URL."); 
        if (Pref.b("knw", 0) < 2 || Sys.B.logConnectivityDetails.b())
          Log.c("Connect " + this.a.dbId + " JDBC URL: " + str1); 
        this.d = driverJarClass.e().connect(str1, properties);
        if (this.d == null)
          throw new SQLException("Error connecting to database. Got NULL connection.\nPlease report this issue to technicians using Help / Technical Support."); 
        this.a.setHasConnected();
        String str2 = (Dbms.get(e())).initializationScript.c_();
        if (StringUtil.isFilledTrim(str2))
          for (String str : str2.split(";"))
            this.d.createStatement().execute(str);  
        if ((Dbms.get(e())).disableAutoCommit.b())
          this.d.setAutoCommit(false); 
        Log.c("Connected.");
      } finally {
        Thread.currentThread().setContextClassLoader(classLoader);
      } 
      this.k = null;
    } 
    try {
      boolean bool = (this.a.isReadOnly() && !this.i);
      if (bool != this.d.isReadOnly())
        this.d.setReadOnly(bool); 
    } catch (SQLException sQLException) {
      this.a.setReadOnly(false);
      Log.f("Cannot set connection in Read Only. Flag has been turned off.\n" + String.valueOf(sQLException));
    } 
  }
  
  public DatabaseMetaData a() {
    s();
    return this.d.getMetaData();
  }
  
  public Statement a(boolean paramBoolean) {
    s();
    this.e = paramBoolean ? this.d.createStatement(1003, 1008) : this.d.createStatement();
    return this.e;
  }
  
  protected Statement b() {
    s();
    this.e = this.d.createStatement(1005, 1007);
    return this.e;
  }
  
  public PreparedStatement b(String paramString) {
    s();
    PreparedStatement preparedStatement = this.d.prepareStatement(paramString);
    this.e = preparedStatement;
    return preparedStatement;
  }
  
  public CallableStatement c(String paramString) {
    s();
    Log.e("Execute prepareCall on " + String.valueOf(this.d));
    return this.d.prepareCall(paramString);
  }
  
  public Connection c() {
    s();
    return this.d;
  }
  
  public Sql d() {
    s();
    if (this.k == null)
      this.k = new Envoy$1(this, this.d); 
    return this.k;
  }
  
  public String e() {
    return this.a.dbId;
  }
  
  public void b(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean f() {
    return (this.j != null);
  }
  
  public boolean g() {
    return this.h;
  }
  
  public boolean h() {
    return (this.d != null);
  }
  
  void d(String paramString) {
    a(paramString, (String)null);
  }
  
  public void a(String paramString1, String paramString2) {
    if (this.c)
      this.a.addSqlEvent(this.j = new SqlEvent(paramString1, paramString2)); 
  }
  
  public void i() {
    if (this.j != null) {
      this.j.a(SqlEventStatus.b, "");
      this.j = null;
    } 
  }
  
  public void e(String paramString) {
    if (this.j != null) {
      this.j.a(SqlEventStatus.c, paramString);
      this.j = null;
    } 
  }
  
  public int a(SQLException paramSQLException, String paramString) {
    try {
      return Dbms.get(e()).getErrorPosition(paramSQLException, paramString, this.e, this);
    } catch (Throwable throwable) {
      a(throwable);
      Log.d("Cannot get error position from exception : " + String.valueOf(throwable));
    } finally {
      close();
    } 
    return -1;
  }
  
  void f(String paramString) {
    if (paramString != null) {
      paramString = paramString.trim().toLowerCase();
      switch ((Dbms.get(e())).commit.h()) {
        case 2:
          this.g = StringUtil.stringStartsWith(paramString, new String[] { "select " });
        case 1:
          this.f = (this.f || StringUtil.stringStartsWith(paramString, new String[] { "create ", "alter ", "drop ", "truncate " }));
        case 0:
          this.f = (this.f || StringUtil.stringStartsWith(paramString, new String[] { "update ", "delete ", "insert ", "merge " }));
          break;
      } 
    } 
  }
  
  void j() {
    this.f = false;
    this.g = false;
  }
  
  public boolean k() {
    return (this.f || this.g);
  }
  
  public SqlEvent l() {
    return this.j;
  }
  
  public String toString() {
    return this.b + this.b + ((this.j != null) ? SqlUtil.getTooltipForSql(this.j.b) : "");
  }
  
  public void m() {
    if (this.g && !this.f && (Dbms.get(e())).commit.h() == 2 && this.d != null) {
      this.d.rollback();
      this.g = false;
    } 
  }
  
  public void n() {
    i();
    if (this.e != null) {
      try {
        this.e.cancel();
      } catch (Exception exception) {
        Log.h();
      } 
      try {
        this.e.close();
      } catch (Exception exception) {
        Log.h();
      } 
      this.e = null;
      e("Closed");
    } 
    if (this.d != null)
      try {
        if (!this.d.isClosed()) {
          Connection connection = this.d;
          (new Envoy$2(this, "Close connection thread", connection))









            
            .start();
        } 
      } catch (Exception exception) {
        Log.h();
      }  
    this.d = null;
    this.h = true;
  }
  
  public void a(Object paramObject) {
    if (this.e != null) {
      try {
        this.e.close();
      } catch (Exception exception) {}
      this.e = null;
    } 
    e(String.valueOf(paramObject));
    if (this.d != null)
      try {
        Log.e("Execute rollback/failed on " + String.valueOf(this.d));
        this.d.rollback();
      } catch (SQLException sQLException) {
        n();
      }  
    this.f = false;
    this.g = false;
    this.h = true;
  }
  
  public boolean o() {
    if (this.d == null)
      return true; 
    try {
      return this.d.isClosed();
    } catch (SQLException sQLException) {
      return true;
    } 
  }
  
  public void close() {
    if (this.e != null) {
      try {
        this.e.close();
      } catch (Throwable throwable) {}
      this.e = null;
    } 
    try {
      m();
    } catch (Throwable throwable) {
      n();
    } 
    i();
    this.h = true;
  }
  
  public void a(String paramString, Result paramResult) {
    (new DescribeStatement(this, paramString, paramResult)).a();
  }
  
  public void g(String paramString) {
    UpdateStatement updateStatement = b(paramString, new Object[0]);
    try {
      updateStatement.a();
      if (updateStatement != null)
        updateStatement.close(); 
    } catch (Throwable throwable) {
      if (updateStatement != null)
        try {
          updateStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
  
  public String b(String paramString, Result paramResult) {
    if (this.a.isReadOnly() && SqlUtil.isUpdateStatement(paramString))
      this.a.throwSQLExceptionIfReadOnly(); 
    GeneralToResultStatement generalToResultStatement = new GeneralToResultStatement(this, paramString, paramResult);
    try {
      generalToResultStatement.a();
      String str = generalToResultStatement.e() + generalToResultStatement.e();
      generalToResultStatement.close();
      return str;
    } catch (Throwable throwable) {
      try {
        generalToResultStatement.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public void p() {
    j();
    Log.e("Execute commit ");
    if (this.d != null && !this.d.getAutoCommit())
      this.d.commit(); 
  }
  
  public void q() {
    j();
    Log.e("Execute rollback ");
    if (this.d != null && !this.d.getAutoCommit())
      this.d.rollback(); 
  }
  
  public SelectStatement a(String paramString, Object... paramVarArgs) {
    return new SelectStatement(this, paramString, paramVarArgs);
  }
  
  public UpdateStatement b(String paramString, Object... paramVarArgs) {
    return new UpdateStatement(this, paramString, paramVarArgs);
  }
  
  public static void r() {}
  
  public void c(boolean paramBoolean) {
    this.i = paramBoolean;
  }
}
