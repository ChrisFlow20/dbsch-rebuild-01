package com.wisecoders.dbs.config.system;

import java.net.Authenticator;

public class NetworkProxy {
  public static void a() {
    if (Sys.B.useSystemProxy.b())
      System.setProperty("java.net.useSystemProxies", "true"); 
    if (Sys.B.proxyType.h() > 0 && Sys.B.proxyHost.p() && Sys.B.proxyPort.p()) {
      switch (Sys.B.proxyType.h()) {
        case 1:
          System.setProperty("http.proxyHost", Sys.B.proxyHost.c_());
          System.setProperty("http.proxyPort", Sys.B.proxyPort.c_());
          break;
        case 2:
          System.setProperty("https.proxyHost", Sys.B.proxyHost.c_());
          System.setProperty("https.proxyPort", Sys.B.proxyPort.c_());
          break;
        case 3:
          System.setProperty("socksProxyHost", Sys.B.proxyHost.c_());
          System.setProperty("socksProxyPort", Sys.B.proxyPort.c_());
          break;
      } 
      if (Sys.B.proxyUser.p() && Sys.B.proxyPassword.p()) {
        if (Sys.B.proxyType.h() == 3) {
          System.setProperty("java.net.socks.username", Sys.B.proxyUser.c_());
          System.setProperty("java.net.socks.password", Sys.B.proxyPassword.c_());
        } 
        Authenticator.setDefault(new NetworkProxy$1());
      } 
    } 
  }
}
