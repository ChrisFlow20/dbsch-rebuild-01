package com.wisecoders.dbs.cli.command;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SRx;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class Dictionary {
  public static final List a = new ArrayList();
  
  public static final List b = new ArrayList();
  
  static {
    try {
      a("connectivity.properties", true);
      a("schema.properties", true);
      a("data.properties", true);
      a("scripting.properties", true);
      a("system.properties", true);
      a("sql.properties", false);
      a("license.properties", false);
    } catch (IOException iOException) {
      Log.a("Error opening commands.properties file. ", iOException);
      System.out.println("Error opening commands.properties file. " + iOException.toString());
    } 
  }
  
  private static void a(String paramString, boolean paramBoolean) {
    Properties properties = new Properties();
    InputStream inputStream = AbstractConsole.class.getResourceAsStream(paramString);
    try {
      if (inputStream == null)
        throw new IOException("Missing commands.properties file"); 
      SRx.a(properties, inputStream);
      if (inputStream != null)
        inputStream.close(); 
    } catch (Throwable throwable) {
      if (inputStream != null)
        try {
          inputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    a(properties, paramBoolean);
    a.sort((paramCommand1, paramCommand2) -> paramCommand2.getKeyword().length() - paramCommand1.getKeyword().length());
    b.sort((paramCommand1, paramCommand2) -> paramCommand1.getKeyword().compareToIgnoreCase(paramCommand2.getKeyword()));
  }
  
  private static void a(Properties paramProperties, boolean paramBoolean) {
    Enumeration<?> enumeration = paramProperties.propertyNames();
    while (enumeration.hasMoreElements()) {
      String str1 = (String)enumeration.nextElement();
      String str2 = paramProperties.getProperty(str1);
      if (str1.endsWith(".class")) {
        String str = str1.substring(0, str1.length() - ".class".length());
        try {
          Class<?> clazz = Dictionary.class.getClassLoader().loadClass(str2);
          Command command = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
          String str3 = paramProperties.getProperty(str + ".keyword");
          command.setKeyword((str3 != null) ? str3 : str);
          command.setUsage(paramProperties.getProperty(str + ".usage"));
          command.setShortDescription(paramProperties.getProperty(str + ".shortDescription"));
          command.setLongDescription(paramProperties.getProperty(str + ".longDescription"));
          command.setGroup(paramProperties.getProperty(str + ".group"));
          String str4 = paramProperties.getProperty(str + ".order");
          if (StringUtil.isFilled(str4))
            command.setOrder(Integer.parseInt(str4.trim())); 
          a.add(command);
          if (paramBoolean)
            b.add(command); 
        } catch (Throwable throwable) {
          System.out.println(AbstractConsole.b(throwable));
        } 
      } 
    } 
  }
  
  public static void a(Command paramCommand) {
    a.add(paramCommand);
    Collections.sort(a);
  }
  
  public static Command a(String paramString) {
    for (Command command : a) {
      String str = command.getType().b(paramString);
      if (command.matchesInput(str))
        return command; 
    } 
    return null;
  }
}
