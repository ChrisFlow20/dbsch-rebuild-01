package com.wisecoders.dbs.diagram.util.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class TestConnection {
  private static final String[] a = new String[] { "TABLE" };
  
  public static void main(String[] paramArrayOfString) {
    try {
      if (paramArrayOfString.length != 4) {
        System.out.println("Test database connection.\n Usage: TestConnection <driver> <connString> <user> <password>");
        System.out.println("java -cp /kit/drivers/oracle10g-10.2.0.1.jar:. TestConnection oracle.jdbc.OracleDriver jdbc:oracle:thin:@dbknw1:1521:dbinh appekm appekm");
        return;
      } 
      Class<?> clazz = TestConnection.class.getClassLoader().loadClass(paramArrayOfString[0]);
      Driver driver = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      if (driver == null)
        throw new SQLException("Cannot get driver instance."); 
      Properties properties = new Properties();
      properties.put("user", paramArrayOfString[2]);
      properties.put("password", paramArrayOfString[3]);
      properties.put("oracle.jdbc.ReadTimeout", "10000");
      Connection connection = driver.connect(paramArrayOfString[1], properties);
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet resultSet = databaseMetaData.getCatalogs();
      while (resultSet.next())
        System.out.println("Catalog: " + resultSet.getString(1)); 
      resultSet = databaseMetaData.getSchemas();
      while (resultSet.next())
        System.out.println("Schemas: " + resultSet.getString(1)); 
      resultSet.close();
      String str1 = "CAT1";
      String str2 = "SCH1";
      resultSet = databaseMetaData.getTables(str1, str2, null, a);
      ArrayList<String> arrayList = new ArrayList();
      HashMap<Object, Object> hashMap = new HashMap<>();
      while (resultSet.next()) {
        if ("TABLE".equals(resultSet.getString(4)))
          arrayList.add(resultSet.getString(3)); 
      } 
      resultSet.close();
      for (String str : arrayList) {
        try {
          resultSet = databaseMetaData.getColumns(str1, str2, str, null);
          System.out.print(str + " : ");
          while (resultSet.next()) {
            String str3 = resultSet.getString(4);
            System.out.print(str3 + ", ");
            hashMap.put(str, str3);
          } 
          resultSet.close();
          System.out.println();
          System.out.print("Foreign Keys ( getImportedKeys ) ");
          resultSet = databaseMetaData.getImportedKeys(str1, str2, str);
          while (resultSet.next())
            System.out.print("seq: " + resultSet.getShort(9) + " " + resultSet
                .getString(12) + " " + resultSet
                .getString(3) + " ( " + resultSet.getString(4) + ") -> " + resultSet
                .getString(7) + " ( " + resultSet.getString(8) + " ( "); 
          resultSet.close();
          System.out.println();
          System.out.print("Foreign Keys ( getExportedKeys ) ");
          resultSet = databaseMetaData.getExportedKeys(str1, str2, str);
          while (resultSet.next())
            System.out.print("seq: " + resultSet.getShort(9) + " " + resultSet
                .getString(12) + " " + resultSet
                .getString(3) + " ( " + resultSet.getString(4) + ") -> " + resultSet
                .getString(7) + " ( " + resultSet.getString(8) + " ( "); 
          resultSet.close();
          System.out.println();
        } catch (SQLException sQLException) {
          System.out.println("Exception by reading columns / fks for " + str);
          System.out.println(sQLException);
        } finally {
          try {
            if (resultSet != null)
              resultSet.close(); 
          } catch (SQLException sQLException) {}
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace(System.err);
    } 
  }
}
