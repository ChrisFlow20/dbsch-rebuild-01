package com.wisecoders.dbs.dbms.connect.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorEditor;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javafx.concurrent.Task;

public class FxPingTask extends Task {
  private boolean a = false;
  
  private final FxConnectorEditor b;
  
  private final Connector c;
  
  public FxPingTask(FxConnectorEditor paramFxConnectorEditor, Connector paramConnector) {
    this.b = paramFxConnectorEditor;
    this.c = paramConnector;
  }
  
  protected String a() {
    updateMessage("Connecting. Please wait...");
    if (this.c.getPort() > -1)
      try {
        Socket socket = new Socket();
        try {
          if (this.c.isSshEnable())
            this.c.setupSSHTunnel(false); 
          String str = this.c.isSshEnable() ? "localhost" : this.c.getHost();
          int i = this.c.isSshEnable() ? this.c.getSSHLocalPort() : this.c.getPort();
          InetSocketAddress inetSocketAddress = new InetSocketAddress(str, i);
          socket.connect(inetSocketAddress, 5000);
          if (socket.isConnected()) {
            String str1 = "Ping succeed ! Port " + this.c.getPort() + " on " + this.c.getHost() + " is reachable!";
            socket.close();
            return str1;
          } 
          socket.close();
        } catch (Throwable throwable) {
          try {
            socket.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        this.a = (c() || b());
        throw iOException;
      }  
    if (c() || b())
      return "Operating system ping succeed !"; 
    return "<html>Operating system ping fails.<br>This information may be not always relevant, as the server should have the ping service up and running,<br>which is not always the case.";
  }
  
  private boolean b() {
    return InetAddress.getByName(this.c.getHost()).isReachable(3000);
  }
  
  private boolean c() {
    try {
      String str = Sys.i() ? ("ping -n 1 " + this.c.getHost()) : ("ping -c 1 " + this.c.getHost());
      Process process = Runtime.getRuntime().exec(str);
      process.waitFor();
      return (process.exitValue() == 0);
    } catch (Exception exception) {
      Log.b(exception);
      return false;
    } 
  }
  
  protected void succeeded() {
    this.b.showInformation((String)getValue(), new String[0]);
  }
  
  public void failed() {
    Throwable throwable = getException();
    Log.f("Ping Failed " + String.valueOf(throwable));
    if (throwable instanceof java.net.UnknownHostException) {
      this.b.showError("<html>Unknown host '" + this.c.getHost() + "'.<br>" + String.valueOf(throwable));
    } else if (throwable instanceof java.net.SocketTimeoutException) {
      this.b.showError("<html>No software is listening on port " + this.c.getPort() + " or firewall blocks it.<br>" + throwable
          .getLocalizedMessage() + "<br>" + 
          a(this.c.dbId, true));
    } else if (throwable instanceof IOException && throwable.getMessage().equals("Connection refused")) {
      this.b.showError("<html>No software is listening on port " + this.c.getPort() + " is closed or firewalls blocks connection.<br>" + 
          a(this.c.dbId, true));
    } else if ("localhost".equals(this.c.getHost()) || "127.0.0.1".equals(this.c.getHost())) {
      this.b.showError("<html>" + throwable.getLocalizedMessage() + "<br>Database port " + this.c.getPort() + " is not reachable.<br>" + a(this.c.dbId, false));
    } else if (this.a) {
      this.b.showError("<html><b>Host is fine</b> but no software is listening on port " + this.c.getPort() + ".<br>" + a(this.c.dbId, true));
    } else {
      this.b.showError("<html>Cannot connect. Got exception:<br>" + throwable.toString() + "<br><br>DbSchema Advice<br><br>- Check the database server hostname by executing 'hostname' in server command prompt. On Windows press WinKey+Break.<br>- If no DNS is configured in your network, please use the IP. Get the server IP by executing on database server 'ipconfig' (Windows) or 'ifconfig' (Linux) in command prompt.<br>- Firewalls may block connectivity. Check in Help how to create rule or disable.<br>- Check the connectivity using 'ping' and 'telnet'. Details in Help.<br>", throwable);
    } 
  }
  
  private String a(String paramString, boolean paramBoolean) {
    if (paramString.startsWith("SqlServer"))
      return "<h3>DbSchema Advice</h3>1. Is SqlServer up and running ?<br>2. <a href='documentation:SqlServer/index.html#tcpip'>Enable TCP/IP in SqlServer Configuration Manager</a>.<br>3. Check the firewall to allow connections on port 1433."; 
    if (paramString.startsWith("MySql")) {
      if (paramBoolean)
        return "<h3>DbSchema Advice</h3>1. Is the database started ? Check also <a href='documentation:MySql#service'>services</a>.<br>2. Please enable remote connections in MySql Server. Details in <a href='documentation:MySql'>Help</a>.<br>3. <a href='documentation:MySql#firewall'>Configure the firewall</a> to allow access<br>4. Check the port value.<br>"; 
      return "<h3>DbSchema Advice</h3>1. Is the database up and running ? <br>2. <a href='documentation:MySql#firewall'>Configure the firewall</a> to allow access.<br>3. Check the port value.<br>";
    } 
    if (paramBoolean)
      return "<h3>DbSchema Advice</h3>1. Is the database up an running ? <br>2. Make sure that <b>remote connections</b> are enabled on server. Press 'Learn How to Connect' button.<br>3. <a href='documentation:connect.html#firewall'>Configure the firewall</a> to allow access. <br>4. Check the port value.<br>"; 
    return "<h3>DbSchema Advice</h3>1. Is the database started ? <br>2. The firewall may block the access. Please <a href='documentation:connect.html#firewall'>configure or disable the firewall</a>. <br>3. Check the port value.<br>";
  }
}
