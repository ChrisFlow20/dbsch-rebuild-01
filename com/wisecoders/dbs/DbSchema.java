package com.wisecoders.dbs;

import com.install4j.api.launcher.StartupNotification;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.system.HttpsUtils;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.scripting.ScriptEngine;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StackTraceHelper;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

public class DbSchema {
  private static final String[] a = new String[] { "1.5", "1.6", "1.7", "1.8", "9", "10" };
  
  private static void a() {
    StartupNotification.registerStartupListener(paramString -> {
          if (paramString != null) {
            File file = Paths.get(paramString, new String[0]).toFile();
            if (file.exists())
              DbSchemaApp.a = file; 
          } 
        });
  }
  
  private static void b() {
    String str = System.getProperty("java.version");
    for (String str1 : a) {
      if (str.startsWith(str1))
        JOptionPane.showMessageDialog(null, "Please update your Java or use one DbSchema installer with build-in OpenJdk.\nOpenJRE 11 or later is required. Currently Java: " + str); 
    } 
  }
  
  private static void c() {
    try {
      Class.forName("javafx.application.Application");
    } catch (ClassNotFoundException classNotFoundException) {
      String str = "JavaFx is missing ! Please install it.";
      if (Sys.j())
        str = str + "\nPlease install 'openjfx' package."; 
      JOptionPane.showMessageDialog(null, str);
    } catch (Throwable throwable) {}
  }
  
  private static void d() {
    String str = Pref.a("UIScaling");
    if (str != null && !str.isEmpty())
      System.setProperty((Sys.j() || Sys.k()) ? "glass.gtk.uiScale" : "glass.win.uiScale", str); 
  }
  
  public static void main(String[] paramArrayOfString) {
    a();
    b();
    d();
    c();
    Keys.j.c();
    if (StringUtil.isFilledTrim(System.getenv("DBSCHEMA_FLS_HOST"))) {
      Keys.h.a(System.getenv("DBSCHEMA_FLS_HOST"));
      Keys.i.a(System.getenv("DBSCHEMA_FLS_PORT"));
    } 
    if (paramArrayOfString != null && paramArrayOfString.length > 0 && "-x".equals(paramArrayOfString[0])) {
      new ScriptEngine(Arrays.<String>copyOfRange(paramArrayOfString, 1, paramArrayOfString.length));
      System.exit(0);
    } else {
      try {
        DbSchemaApp.launch(DbSchemaApp.class, paramArrayOfString);
      } catch (Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DbSchema could not start.\n");
        stringBuilder.append("Please report this issue to support@dbschema.com.\n");
        stringBuilder.append(StackTraceHelper.a(throwable)).append("\n");
        JOptionPane.showMessageDialog(null, stringBuilder.toString());
        try {
          CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet("https://dbschema.com/technical/feedback.php?MESSAGE=ERROR&OUTPUT_LOGS=" + URLEncoder.encode(stringBuilder.toString(), StandardCharsets.UTF_8)));
          try {
            Log.c("Ticket Created with Response Status: " + closeableHttpResponse.getStatusLine().getStatusCode());
            EntityUtils.consume(closeableHttpResponse.getEntity());
            if (closeableHttpResponse != null)
              closeableHttpResponse.close(); 
          } catch (Throwable throwable1) {
            if (closeableHttpResponse != null)
              try {
                closeableHttpResponse.close();
              } catch (Throwable throwable2) {
                throwable1.addSuppressed(throwable2);
              }  
            throw throwable1;
          } 
        } catch (IOException iOException) {
          Log.b(throwable);
        } 
      } 
    } 
  }
}
