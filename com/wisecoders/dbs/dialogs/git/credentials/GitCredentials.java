package com.wisecoders.dbs.dialogs.git.credentials;

import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.project.store.SimpleEncrypt;
import com.wisecoders.dbs.sys.Log;
import java.util.Locale;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.SystemReader;

public class GitCredentials {
  private String a;
  
  private static final String b = "gitUser";
  
  private static final String c = "gitPassword";
  
  private static final String d = "gitSshPrivateKeyFileName";
  
  private static final String e = "gitSshParaphrase";
  
  private static final String f = "gitRememberCredentials";
  
  private String g;
  
  private String h;
  
  private String i;
  
  private String j;
  
  private boolean k = false;
  
  public void a(String paramString) {
    this.a = paramString;
  }
  
  public String a() {
    return this.a;
  }
  
  public void b(String paramString) {
    this.g = paramString;
    if (this.a != null)
      if (paramString != null && this.k) {
        Pref.a("gitUser" + this.a.hashCode(), paramString);
      } else {
        Pref.b("gitUser" + this.a.hashCode());
      }  
  }
  
  public String b() {
    if (this.g == null && this.a != null && this.k)
      this.g = Pref.c("gitUser" + this.a.hashCode(), (String)null); 
    return this.g;
  }
  
  public void c(String paramString) {
    this.h = paramString;
    if (this.a != null)
      if (this.k && paramString != null) {
        Pref.a("gitPassword" + this.a.hashCode(), SimpleEncrypt.a(paramString));
      } else {
        Pref.b("gitPassword" + this.a.hashCode());
      }  
  }
  
  public String c() {
    if (this.h == null && this.a != null && this.k)
      this.h = SimpleEncrypt.b(Pref.c("gitPassword" + this.a.hashCode(), (String)null)); 
    return (this.h != null) ? this.h : "";
  }
  
  public void d(String paramString) {
    this.j = paramString;
    if (this.a != null)
      if (paramString == null) {
        Pref.b("gitSshPrivateKeyFileName" + this.a.hashCode());
      } else {
        Pref.a("gitSshPrivateKeyFileName" + this.a.hashCode(), paramString);
      }  
  }
  
  public String d() {
    if (this.j == null && this.a != null)
      this.j = Pref.c("gitSshPrivateKeyFileName" + this.a.hashCode(), (String)null); 
    return this.j;
  }
  
  public void e(String paramString) {
    this.i = paramString;
    if (this.a != null)
      if (paramString == null) {
        Pref.b("gitSshParaphrase" + this.a.hashCode());
      } else {
        Pref.a("gitSshParaphrase" + this.a.hashCode(), SimpleEncrypt.a(paramString));
      }  
  }
  
  public String e() {
    if (this.i == null && this.a != null)
      this.i = SimpleEncrypt.b(Pref.c("gitSshParaphrase" + this.a.hashCode(), (String)null)); 
    return this.i;
  }
  
  public void a(boolean paramBoolean) {
    this.k = paramBoolean;
    if (this.a != null)
      Pref.a("gitRememberCredentials" + this.a.hashCode(), paramBoolean); 
  }
  
  public boolean f() {
    if (this.a != null)
      this.k = Pref.b("gitRememberCredentials" + this.a.hashCode(), false); 
    return this.k;
  }
  
  public void a(TransportCommand paramTransportCommand) {
    if (b() != null) {
      paramTransportCommand.setCredentialsProvider(new XUsernamePasswordCredentialsProvider(
            b(), c()));
    } else if (d() != null) {
      paramTransportCommand.setTransportConfigCallback(paramTransport -> {
            if (paramTransport instanceof SshTransport) {
              SshTransport sshTransport = (SshTransport)paramTransport;
              sshTransport.setSshSessionFactory((SshSessionFactory)new CustomJschSshSessionFactory(d(), e()));
            } 
          });
    } 
    if (this.a != null && this.a.toLowerCase(Locale.ROOT).startsWith("https"))
      try {
        StoredConfig storedConfig = SystemReader.getInstance().getUserConfig();
        storedConfig.setBoolean("http", null, "sslVerify", false);
        storedConfig.save();
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
  }
  
  public boolean g() {
    return (this.a != null && (this.a.startsWith("git@") || this.a.startsWith("ssh:")));
  }
}
