package com.wisecoders.dbs.config.system;

import com.install4j.api.launcher.Variables;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.PropertiesMixed;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import javafx.scene.control.Alert;

public class Sys {
  public static final String a = "UIScaling";
  
  public static final Path b;
  
  static {
    Path path;
    try {
      path = Paths.get(Log.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      String str1 = path.toString().replace("\\", "/");
      if (str1.endsWith(".jar")) {
        path = path.resolve("..").normalize();
        str1 = path.toString().replace("\\", "/");
      } 
      if (str1.endsWith("lib") || str1.endsWith("lib/")) {
        path = path.resolve("..").normalize();
      } else if (str1.endsWith("classes/")) {
        path = path.resolve("../resources/").normalize();
      } else if (str1.endsWith("classes/java/main")) {
        path = path.resolve("../../../resources/main").normalize();
      } 
    } catch (Exception exception) {
      exception.printStackTrace(System.out);
      path = Paths.get(".", new String[0]);
    } 
    b = path;
    String str = null;
    File file = b.resolve("config/DbSchema.properties").toFile();
    if (file.exists())
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          PropertiesMixed propertiesMixed = new PropertiesMixed(System.getProperties());
          propertiesMixed.load(fileInputStream);
          str = propertiesMixed.getProperty("DbSchema.config.path");
          fileInputStream.close();
        } catch (Throwable throwable) {
          try {
            fileInputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        System.out.println(iOException);
      }  
    if (StringUtil.isEmptyTrim(str))
      str = System.getProperty("user.home"); 
  }
  
  public static final Path c = Paths.get(System.getProperty("user.home"), new String[0]);
  
  public static final Path d;
  
  static {
    d = Paths.get(str, new String[0]).resolve(".DbSchema/");
  }
  
  public static final Path e = d.resolve("drivers/");
  
  public static final Path f = d.resolve("samples/");
  
  public static final Path g = d.resolve("tmp/");
  
  public static final Path h = d.resolve("logs/");
  
  public static final File i = h.resolve("logs.txt").toFile();
  
  public static final Path j = g.resolve("webViewCache/");
  
  public static final Path k = d.resolve("config/");
  
  public static final Path l = k.resolve("fonts/");
  
  public static final Path m = d.resolve("cli/");
  
  public static final Path n = d.resolve("video/");
  
  public static final Path o = d.resolve("connections/");
  
  public static final Path p = o.resolve("connections.xml");
  
  public static final Path q = c.resolve(".keystore");
  
  public static final Path r = o.resolve("passwords.xml");
  
  public static final Path s = d.resolve("scripts/");
  
  public static final Path t = m.resolve("init.sql");
  
  public static final Path u = m.resolve("history.txt");
  
  public static final Path v = b.resolve("drivers/");
  
  public static final Path w = b.resolve("samples/");
  
  public static final Path x = b.resolve("config/appearance/");
  
  public static final Path y = b.resolve("config/scripts/");
  
  public static final Path z = y.resolve("Applications/");
  
  public static final Path A = b.resolve("config/fonts/");
  
  private static Path m() {
    return (B.configFolder.a() && B.configFolder.f().isDirectory()) ? B.configFolder.f().toPath() : k;
  }
  
  public static Path a() {
    Path path = m().resolve("dbms/");
    File file = path.toFile();
    if (!file.exists())
      file.mkdir(); 
    return path;
  }
  
  public static Path b() {
    Path path = m().resolve("validation/");
    File file = path.toFile();
    if (!file.exists())
      file.mkdir(); 
    return path;
  }
  
  public static Path c() {
    Path path = m().resolve("exporters/");
    File file = path.toFile();
    if (!file.exists())
      file.mkdir(); 
    return path;
  }
  
  public static Path d() {
    Path path = m().resolve("generator/");
    File file = path.toFile();
    if (!file.exists())
      file.mkdir(); 
    return path;
  }
  
  public static Path e() {
    Path path = m().resolve("articles/");
    File file = path.toFile();
    if (!file.exists())
      file.mkdir(); 
    return path;
  }
  
  public static Path f() {
    return (B.connectionsFile.a() && B.connectionsFile.f().getParentFile().isDirectory()) ? B.connectionsFile.f().toPath() : p;
  }
  
  public static Path g() {
    return (B.passwordsFile.a() && B.passwordsFile.f().getParentFile().isDirectory()) ? B.passwordsFile.f().toPath() : r;
  }
  
  public static Path h() {
    return (B.groovyScriptsFolder.a() && B.groovyScriptsFolder.f().getParentFile().isDirectory()) ? B.groovyScriptsFolder.f().toPath() : s;
  }
  
  static {
    try {
      File file1;
      if (!(file1 = d.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = e.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = k.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = h.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = g.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = m.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = o.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = s.toFile()).exists())
        file1.mkdir(); 
      if (!(file1 = n.toFile()).exists())
        file1.mkdir(); 
    } catch (Exception exception) {
      exception.printStackTrace();
      Log.a("Cannot create <user.home>/.DbSchema folders", exception);
      (new Alert$(Alert.AlertType.ERROR))
        .a("Error Initializing Resources")
        .b("Could not write in " + String.valueOf(d) + ".")
        .c("Please edit " + String.valueOf(b.resolve("config/DbSchema.properties")) + "\nand choose a different application local settings folder.")
        
        .a(exception).show();
    } 
  }
  
  public static final Configuration B = new Configuration();
  
  public static final String C = "locale";
  
  public static boolean i() {
    String str = System.getProperty("os.name", "Windows XP").toLowerCase(Locale.ENGLISH);
    return str.startsWith("win");
  }
  
  public static boolean j() {
    String str = System.getProperty("os.name", "Windows XP").toLowerCase(Locale.ENGLISH);
    return (str.contains("nix") || str.contains("nux") || str.contains("aix"));
  }
  
  public static boolean k() {
    String str = System.getProperty("os.name", "Windows XP").toLowerCase(Locale.ENGLISH);
    return str.startsWith("mac");
  }
  
  public static void a(String paramString) {
    Pref.b("locale", String.valueOf(paramString));
  }
  
  public static String l() {
    String str = Pref.d("locale", "default");
    if ("default".equals(str))
      return (String)Variables.getInstallerVariable("sys.languageId"); 
    return str;
  }
}
