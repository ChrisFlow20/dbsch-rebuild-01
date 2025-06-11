package com.wisecoders.dbs.diagram.util.test;

import com.wisecoders.dbs.config.system.Sys;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class OracleBugWithFkOwnership {
  public OracleBugWithFkOwnership() {
    try {
      Connection connection = a("oracle.jdbc.driver.OracleDriver", "TEST", "TEST", "jdbc:oracle:thin:@dbinh1.muc.ecircle.de:1521:dbinh");
      try {
        connection.createStatement().execute("DROP TABLE TABLE1");
      } catch (SQLException sQLException) {
        System.out.println(sQLException);
      } 
      try {
        connection.createStatement().execute("DROP TABLE TABLE2");
      } catch (SQLException sQLException) {
        System.out.println(sQLException);
      } 
      a(connection);
      connection.createStatement().execute("CREATE TABLE TABLE1( id number primary key, idp number, name varchar2(100))");
      connection.createStatement().execute("CREATE TABLE TABLE2( id number, idp number, name varchar2(100))");
      connection.createStatement().execute("ALTER TABLE TABLE1 ADD CONSTRAINT UNQ_TABLE1_UID UNIQUE ( IDP )");
      connection.createStatement().execute("ALTER TABLE TABLE2 ADD CONSTRAINT FK_TABLE2A FOREIGN KEY (ID ) REFERENCES TABLE1(id)");
      connection.createStatement().execute("ALTER TABLE TABLE2 ADD CONSTRAINT FK_TABLE2B FOREIGN KEY (IDP ) REFERENCES TABLE1(idp)");
      ResultSet resultSet1 = connection.getMetaData().getTables(null, "TEST", null, new String[] { "TABLE" });
      System.out.println("TABLES=================");
      while (resultSet1.next())
        System.out.println("Table " + resultSet1.getString(3)); 
      resultSet1.close();
      ResultSet resultSet2 = connection.getMetaData().getExportedKeys(null, "TEST", null);
      System.out.println("FKS=================");
      while (resultSet2.next())
        System.out.println("Fk " + resultSet2.getString(12)); 
      resultSet2.close();
      connection.close();
    } catch (Exception exception) {
      exception.printStackTrace(System.err);
    } 
  }
  
  private Connection a(String paramString1, String paramString2, String paramString3, String paramString4) {
    Class<?> clazz = OracleBugWithFkOwnership.class.getClassLoader().loadClass(paramString1);
    Driver driver = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    if (driver == null)
      throw new SQLException("Cannot get driver instance."); 
    Properties properties = new Properties();
    properties.put("user", paramString2);
    properties.put("password", paramString3);
    return driver.connect(paramString4, properties);
  }
  
  private void a(Connection paramConnection) {
    StringTokenizer stringTokenizer;
    DatabaseMetaData databaseMetaData = paramConnection.getMetaData();
    System.out.println("\nDatabase\n==============");
    System.out.println(databaseMetaData.getDatabaseProductVersion());
    System.out.println("\nJDBC\n==============");
    System.out.println(databaseMetaData.getDriverName() + ": " + databaseMetaData.getDriverName());
    System.out.println("\nConnection URL\n==============");
    System.out.println(databaseMetaData.getURL());
    Properties properties = System.getProperties();
    System.out.println("\nJVM\n===");
    System.out.println(properties.getProperty("java.vm.vendor"));
    System.out.println(properties.getProperty("java.vm.name"));
    System.out.println(properties.getProperty("java.vm.version"));
    System.out.println(properties.getProperty("java.version"));
    System.out.println("\nLOCALE\n===========");
    System.out.println(Locale.getDefault());
    System.out.println("\nBOOTSTRAP (sun.boot.class.path)\n==============================\n" + 
        System.getProperty("sun.boot.class.path"));
    System.out.println("\nEXTENSION PACKAGES (java.ext.dirs)\n=================================\n" + 
        System.getProperty("java.ext.dirs") + "\n");
    String[] arrayOfString1 = new String[5];
    byte b1 = 0;
    if (Sys.i()) {
      stringTokenizer = new StringTokenizer(System.getProperty("java.ext.dirs"), " ;");
    } else {
      stringTokenizer = new StringTokenizer(System.getProperty("java.ext.dirs"), " :");
    } 
    int i = stringTokenizer.countTokens();
    while (stringTokenizer.hasMoreTokens()) {
      arrayOfString1[b1] = stringTokenizer.nextToken();
      System.out.println(arrayOfString1[b1] + ": ");
      File file = new File(arrayOfString1[b1]);
      File[] arrayOfFile = file.listFiles();
      if (arrayOfFile != null)
        for (byte b = 0; b < arrayOfFile.length; ) {
          System.out.println("      " + arrayOfFile[b].getName());
          b++;
        }  
      b1++;
    } 
    String str1 = properties.getProperty("path.separator");
    String str2 = properties.getProperty("java.class.path");
    System.out.println("\nCLASSPATH\n=========");
    String[] arrayOfString2 = str2.split(str1);
    for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
      System.out.println(arrayOfString2[b2]); 
    String str3 = properties.getProperty("java.library.path");
    System.out.println("\nLIBRARYPATH\n===========");
    arrayOfString2 = str3.split(str1);
    for (byte b3 = 0; b3 < arrayOfString2.length; b3++)
      System.out.println(arrayOfString2[b3]); 
  }
  
  public static void main(String[] paramArrayOfString) {
    new OracleBugWithFkOwnership();
  }
}
