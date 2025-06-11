package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.project.store.CipherUtil;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.project.store.XMLWriter;
import com.wisecoders.dbs.schema.store.ConnectorLoader;
import com.wisecoders.dbs.schema.store.ConnectorStore;
import com.wisecoders.dbs.schema.store.PasswordLoader;
import com.wisecoders.dbs.schema.store.PasswordStore;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

@DoNotObfuscate
public class ConnectorManager {
  private static final ObservableList a = FXCollections.observableArrayList();
  
  private static final String b = "PassKeyDbSchemaSecret";
  
  public static List getConnectorDbIdList() {
    a();
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (Connector connector : a)
      uniqueArrayList.add(connector.dbId); 
    return uniqueArrayList;
  }
  
  public static ObservableList getConnectors() {
    a();
    return a;
  }
  
  public static List getConnectors(String paramString) {
    a();
    ArrayList<Connector> arrayList = new ArrayList();
    for (Connector connector : a) {
      if (connector.dbId.equals(paramString))
        arrayList.add(connector); 
    } 
    return arrayList;
  }
  
  public static Connector getConnectors(String paramString1, String paramString2) {
    a();
    for (Connector connector : a) {
      if (connector.dbId.equals(paramString1) && connector.getName().equals(paramString2))
        return connector; 
    } 
    return null;
  }
  
  public static Connector getByName(String paramString) {
    a();
    for (Connector connector : a) {
      if (connector.getName().equals(paramString))
        return connector; 
    } 
    return null;
  }
  
  public static Connector createConnector(String paramString) {
    Connector connector = new Connector(paramString, proposeName(paramString, paramString));
    a(connector);
    return connector;
  }
  
  private static void a(Connector paramConnector) {
    if (!a.contains(paramConnector)) {
      Connector connector = getConnectors(paramConnector.dbId, paramConnector.getName());
      if (connector != null)
        a.remove(connector); 
      a.add(paramConnector);
      a.sort((paramConnector1, paramConnector2) -> paramConnector1.getName().compareToIgnoreCase(paramConnector2.getName()));
    } 
  }
  
  private static boolean c = false;
  
  private static final String d = "aliases";
  
  private static final String e = ",";
  
  private static void a() {
    if (!c) {
      c = true;
      File file1 = Sys.f().toFile();
      if (file1.exists()) {
        try {
          CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(file1), CipherUtil.b("PassKeyDbSchemaSecret"));
          try {
            (new ConnectorLoader()).parse(cipherInputStream);
            cipherInputStream.close();
          } catch (Throwable throwable) {
            try {
              cipherInputStream.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
        } catch (Exception exception) {
          try {
            FileInputStream fileInputStream = new FileInputStream(file1);
            try {
              (new ConnectorLoader()).parse(fileInputStream);
              fileInputStream.close();
            } catch (Throwable throwable) {
              try {
                fileInputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } catch (Exception exception1) {
            Log.b(exception1);
          } 
        } 
      } else {
        String str = Pref.a("aliases");
        if (StringUtil.isFilledTrim(str))
          for (String str1 : str.split(",")) {
            String str2 = Pref.a(str1 + "rdbms");
            if (str2 != null && Pref.a(str1 + "host") != null)
              createConnector(str2).loadFromHistory(str1); 
          }  
      } 
      File file2 = Sys.g().toFile();
      if (file2.exists())
        try {
          CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(file2), CipherUtil.b("PassKeyDbSchemaSecret"));
          try {
            (new PasswordLoader()).parse(cipherInputStream);
            cipherInputStream.close();
          } catch (Throwable throwable) {
            try {
              cipherInputStream.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
        } catch (Exception exception) {
          Log.b(exception);
        }  
    } 
  }
  
  @GroovyMethod
  public static Connector createConnector(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, String paramString7, String paramString8, boolean paramBoolean) {
    DriverManager driverManager = DriverManager.a(paramString2);
    Connector connector = new Connector(paramString1, paramString2, paramString3, paramString4, driverManager.c(paramString3, paramString5), paramString6, paramInt, paramString7, paramString8, paramBoolean);
    a(connector);
    return connector;
  }
  
  @GroovyMethod
  public static Connector createConnector(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
    DriverManager driverManager = DriverManager.a(paramString2);
    Connector connector = new Connector(paramString1, paramString2, paramString3, null, null, null, -1, null, paramString5, false);
    connector.setCustomUrl(paramString4);
    connector.setPassword(paramString6);
    a(connector);
    return connector;
  }
  
  @GroovyMethod
  public static Connector createConnector(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6) {
    DriverManager driverManager = DriverManager.a(paramString2);
    Connector connector = new Connector(paramString1, paramString2, paramString4, null, driverManager.c(paramString4, null), paramString3, paramInt, paramString5, paramString6, false);
    a.add(connector);
    return connector;
  }
  
  public static Connector duplicateConnector(Connector paramConnector) {
    Connector connector = new Connector(paramConnector.getName(), paramConnector.dbId, paramConnector.getDriverJarClassName(), paramConnector.getDriverJarFileName(), paramConnector.getDriverUrl(), paramConnector.getHost(), paramConnector.getPort(), paramConnector.getInstance(), paramConnector.getUserName(), paramConnector.isReadOnly());
    connector.setPassword(paramConnector.getPassword());
    a(connector);
    return connector;
  }
  
  public static void refresh() {
    for (Connector connector : a) {
      if (connector.isMarkedForDeletion())
        connector.closeAllEnvoysAndSsh(); 
    } 
    a.removeIf(Connector::isMarkedForDeletion);
  }
  
  private static final Pattern f = Pattern.compile("(.+)_(\\d+)", 2);
  
  public static final DecimalFormat decimalFormat000 = new DecimalFormat("000");
  
  public static String proposeName(String paramString1, String paramString2) {
    int i = 1;
    if (getConnectors(paramString1, paramString2) == null)
      return paramString2; 
    Matcher matcher = f.matcher(paramString2);
    if (matcher.matches()) {
      paramString2 = matcher.group(1);
      String str1 = matcher.group(2);
      i = Integer.parseInt(str1);
    } 
    String str;
    while (getConnectors(paramString1, str = paramString2 + "_" + paramString2) != null)
      i++; 
    return str;
  }
  
  public static void saveConnectors() {
    refresh();
    try {
      XMLWriter xMLWriter = new XMLWriter(new OutputStreamWriter(new CipherOutputStream(new FileOutputStream(Sys.f().toFile()), CipherUtil.a("PassKeyDbSchemaSecret")), StandardCharsets.UTF_8));
      try {
        new ConnectorStore(xMLWriter);
        xMLWriter.close();
      } catch (Throwable throwable) {
        try {
          xMLWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.b(exception);
    } 
  }
  
  public static void savePasswords() {
    try {
      XMLWriter xMLWriter = new XMLWriter(new OutputStreamWriter(new CipherOutputStream(new FileOutputStream(Sys.g().toFile()), CipherUtil.a("PassKeyDbSchemaSecret")), StandardCharsets.UTF_8));
      try {
        new PasswordStore(xMLWriter);
        xMLWriter.close();
      } catch (Throwable throwable) {
        try {
          xMLWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.b(exception);
    } 
  }
  
  public static Connector createH2LocalConnector(String paramString1, String paramString2) {
    Connector connector = createConnector(paramString1, "H2", "org.h2.Driver", null, null, null, -1, "", "", false);
    connector.loadDriverForSampleProject();
    connector.setCustomUrl("jdbc:h2:file:" + paramString2);
    return connector;
  }
  
  public static void resetConnectors(String paramString) {
    for (Connector connector : a) {
      if (connector.dbId.equals(paramString))
        connector.closeAllEnvoysAndSsh(); 
    } 
  }
}
