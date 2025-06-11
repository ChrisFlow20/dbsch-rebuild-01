package com.wisecoders.dbs.dialogs.git.credentials;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;

public class CustomJschSshSessionFactory extends JschConfigSessionFactory {
  private final String a;
  
  private final String b;
  
  public CustomJschSshSessionFactory(String paramString1, String paramString2) {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  protected void configure(OpenSshConfig.Host paramHost, Session paramSession) {
    paramSession.setConfig("StrictHostKeyChecking", "no");
  }
  
  protected JSch createDefaultJSch(FS paramFS) {
    JSch jSch = super.createDefaultJSch(paramFS);
    jSch.addIdentity(this.a, (this.b != null && this.b.length() > 0) ? this.b.getBytes() : null);
    return jSch;
  }
}
