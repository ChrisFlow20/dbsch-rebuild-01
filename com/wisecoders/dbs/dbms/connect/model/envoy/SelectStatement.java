package com.wisecoders.dbs.dbms.connect.model.envoy;

import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.BlobInputStream;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SelectStatement extends AbstractStatement {
  protected String c = "";
  
  protected final List d = new ArrayList();
  
  private int b = -1;
  
  private ResultSet g;
  
  protected String e;
  
  protected final Envoy f;
  
  private PreparedStatement h;
  
  public SelectStatement(Envoy paramEnvoy) {
    this.f = paramEnvoy;
  }
  
  public SelectStatement(Envoy paramEnvoy, String paramString, Object... paramVarArgs) {
    this.f = paramEnvoy;
    this.c = paramString;
    this.d.addAll(Arrays.asList(paramVarArgs));
  }
  
  public void a(String paramString) {
    this.c = paramString;
  }
  
  public void b(String paramString) {
    this.c += this.c;
  }
  
  public String f() {
    return this.c;
  }
  
  public void a(int paramInt) {
    this.b = paramInt;
  }
  
  public boolean g() {
    return (this.b > 0);
  }
  
  public int h() {
    return this.b;
  }
  
  public void a(Object paramObject) {
    this.d.add(paramObject);
  }
  
  public void a(List paramList) {
    this.d.addAll(paramList);
  }
  
  public void i() {
    this.d.clear();
  }
  
  public void a() {
    this.f.f(this.c);
    Log.e("Execute DirectSql  " + this.c);
    try {
      Connection connection = this.f.c();
      this.f.a(this.c, this.e);
      this.h = a(connection);
      this.h.setFetchSize(10);
      if (this.b > 0)
        try {
          this.h.setQueryTimeout((int)Math.ceil(this.b / 1000.0D));
        } catch (Exception exception) {
          Log.c("Error calling ps.setQueryTimeout(). " + String.valueOf(exception));
        }  
      this.g = this.h.executeQuery();
    } catch (Throwable throwable) {
      this.f.a(throwable);
      throw throwable;
    } 
  }
  
  public ResultSet j() {
    a();
    return this.g;
  }
  
  public void close() {
    if (this.h != null) {
      this.h.close();
      this.h = null;
    } 
    this.f.close();
  }
  
  PreparedStatement a(Connection paramConnection) {
    PreparedStatement preparedStatement = paramConnection.prepareStatement(this.c);
    a(preparedStatement);
    return preparedStatement;
  }
  
  protected void a(PreparedStatement paramPreparedStatement) {
    byte b = 1;
    for (Object object : this.d) {
      Object object1 = (object instanceof StatementParameter) ? ((StatementParameter)object).a : object;
      DataType dataType = (object instanceof StatementParameter) ? ((StatementParameter)object).c : null;
      if (object1 instanceof String) {
        String str = (String)object1;
        paramPreparedStatement.setString(b, str);
      } else if (object1 instanceof Timestamp) {
        Timestamp timestamp = (Timestamp)object1;
        paramPreparedStatement.setTimestamp(b, timestamp);
      } else if (object1 instanceof Date) {
        Date date = (Date)object1;
        paramPreparedStatement.setDate(b, date);
      } else if (object1 instanceof Time) {
        Time time = (Time)object1;
        paramPreparedStatement.setTime(b, time);
      } else if (object1 instanceof Date) {
        Date date = (Date)object1;
        long l = date.getTime();
        paramPreparedStatement.setDate(b, new Date(l));
      } else if (object1 instanceof Integer) {
        Integer integer = (Integer)object1;
        paramPreparedStatement.setInt(b, integer.intValue());
      } else if (object1 instanceof Long) {
        Long long_ = (Long)object1;
        paramPreparedStatement.setLong(b, long_.longValue());
      } else if (object1 instanceof Float) {
        Float float_ = (Float)object1;
        paramPreparedStatement.setFloat(b, float_.floatValue());
      } else if (object1 instanceof Boolean) {
        Boolean bool = (Boolean)object1;
        paramPreparedStatement.setBoolean(b, bool.booleanValue());
      } else if (object1 != null && object1.getClass().isArray() && dataType != null) {
        Array array = paramPreparedStatement.getConnection().createArrayOf(dataType.getBaseTypeName(), (Object[])object1);
        paramPreparedStatement.setArray(b, array);
      } else if (object1 instanceof File) {
        File file = (File)object1;
        try {
          FileInputStream fileInputStream = new FileInputStream(file);
          if (dataType != null && (dataType.getJavaType() == 2005 || dataType.getJavaType() == 2011)) {
            paramPreparedStatement.setAsciiStream(b, fileInputStream);
          } else {
            paramPreparedStatement.setBinaryStream(b, fileInputStream);
          } 
        } catch (FileNotFoundException fileNotFoundException) {
          throw new SQLException(fileNotFoundException);
        } 
      } else if (object1 instanceof InputStream) {
        InputStream inputStream = (InputStream)object1;
        if (dataType != null && (dataType.getJavaType() == 2005 || dataType.getJavaType() == 2011)) {
          if (object1 instanceof BlobInputStream) {
            BlobInputStream blobInputStream = (BlobInputStream)object1;
            paramPreparedStatement.setAsciiStream(b, inputStream, (int)blobInputStream.b);
          } else {
            paramPreparedStatement.setAsciiStream(b, inputStream);
          } 
        } else if (object1 instanceof BlobInputStream) {
          BlobInputStream blobInputStream = (BlobInputStream)object1;
          paramPreparedStatement.setBinaryStream(b, inputStream, (int)blobInputStream.b);
        } else {
          paramPreparedStatement.setBinaryStream(b, inputStream);
        } 
      } else if (object1 == null && dataType != null) {
        paramPreparedStatement.setNull(b, dataType.getJavaType());
      } else {
        paramPreparedStatement.setObject(b, object1);
      } 
      b++;
    } 
  }
  
  public String k() {
    return this.e;
  }
  
  public void c(String paramString) {
    this.e = paramString;
  }
  
  public String b() {
    StringBuilder stringBuilder = new StringBuilder(this.c);
    stringBuilder.append(" [ ");
    for (Object object : this.d)
      stringBuilder.append(object).append(", "); 
    stringBuilder.append(" ] ");
    return stringBuilder.toString();
  }
  
  public String l() {
    String str = this.c;
    str = str.replace("\n", " ").replace("\t", " ");
    for (Object object : this.d) {
      String str1;
      Object object1 = (object instanceof StatementParameter) ? ((StatementParameter)object).a : object;
      if (object1 == null) {
        str1 = "''";
      } else if (object instanceof Number) {
        str1 = String.valueOf(object);
      } else {
        str1 = "'" + String.valueOf(object) + "'";
      } 
      str = str.replaceFirst("\\?", str1);
    } 
    return str;
  }
  
  public String b(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    ResultSet resultSet = j();
    while (resultSet.next())
      stringBuilder.append(resultSet.getString(paramInt)).append("\n"); 
    return stringBuilder.toString();
  }
}
