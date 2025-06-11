package com.wisecoders.dbs.diagram.util.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

public class TestTimestamp {
  public static void main(String[] paramArrayOfString) {
    try {
      Class<?> clazz1 = TestTimestamp.class.getClassLoader().loadClass("oracle.jdbc.driver.OracleDriver");
      Driver driver1 = clazz1.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      if (driver1 == null)
        throw new SQLException("Cannot get driver instance."); 
      Properties properties1 = new Properties();
      properties1.put("user", "dprutean");
      properties1.put("password", "dprutean");
      Connection connection1 = driver1.connect("jdbc:oracle:thin:@dbinh1.muc.ecircle.de:1521:dbinh", properties1);
      Class<?> clazz2 = TestTimestamp.class.getClassLoader().loadClass("org.postgresql.Driver");
      Driver driver2 = clazz2.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      if (driver2 == null)
        throw new SQLException("Cannot get driver instance."); 
      Properties properties2 = new Properties();
      properties2.put("user", "dprutean");
      properties2.put("password", "dprutean");
      Connection connection2 = driver1.connect("jdbc:postgresql:@dbinh2.muc.ecircle.de:5432:dprutean", properties2);
      PreparedStatement preparedStatement = connection1.prepareStatement("insert into test ( tm ) values (?)");
      preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
      preparedStatement.executeQuery();
      connection1.commit();
    } catch (Exception exception) {
      exception.printStackTrace(System.err);
    } 
  }
}
