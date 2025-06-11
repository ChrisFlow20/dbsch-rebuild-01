package com.wisecoders.dbs.cli.command.sql.transfer.db;

import com.wisecoders.dbs.cli.command.sql.transfer.TransferBuffer;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferMeta;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferQueues;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.sys.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TransferDbReader implements Runnable {
  public final TransferQueues a;
  
  public final TransferMeta b;
  
  private final PreparedStatement c;
  
  private final Envoy d;
  
  public TransferDbReader(Envoy paramEnvoy, TransferQueues paramTransferQueues, String paramString1, String paramString2) {
    if (paramEnvoy == null)
      throw new SQLException("Source connection not set."); 
    this.d = paramEnvoy;
    this.c = paramEnvoy.b(paramString2);
    ResultSetMetaData resultSetMetaData = this.c.getMetaData();
    if (resultSetMetaData == null)
      throw new SQLException("Invalid statement: '" + paramString2 + "'"); 
    this.b = new TransferMeta(paramString1, resultSetMetaData);
    this.c.setFetchSize(this.b.a());
    this.a = paramTransferQueues;
  }
  
  public void run() {
    if (!TransferQueues.g()) {
      this.a.a();
      try {
        ResultSet resultSet = this.c.executeQuery();
        resultSet.setFetchSize(this.b.a());
        boolean bool = true;
        TransferBuffer transferBuffer = null;
        while (resultSet.next() && bool) {
          if (transferBuffer == null) {
            transferBuffer = this.a.b();
          } else if (transferBuffer.e()) {
            this.a.b(transferBuffer);
            transferBuffer = this.a.b();
            if (TransferQueues.g())
              bool = false; 
          } 
          if (transferBuffer == null) {
            bool = false;
            continue;
          } 
          Object[] arrayOfObject = transferBuffer.b();
          if (arrayOfObject == null)
            arrayOfObject = new Object[this.b.b]; 
          for (byte b = 0; b < this.b.b; b++) {
            switch (this.b.c[b]) {
              case 1:
              case 12:
                arrayOfObject[b] = resultSet.getString(b + 1);
                break;
              case -7:
              case 16:
                arrayOfObject[b] = Boolean.valueOf(resultSet.getBoolean(b + 1));
                break;
              case -2:
                arrayOfObject[b] = a(resultSet.getBinaryStream(b + 1));
                break;
              case 2005:
                arrayOfObject[b] = a(resultSet.getCharacterStream(b + 1));
                break;
              case 93:
                arrayOfObject[b] = resultSet.getTimestamp(b + 1);
                break;
              case 91:
                arrayOfObject[b] = resultSet.getDate(b + 1);
                break;
              default:
                arrayOfObject[b] = resultSet.getObject(b + 1);
                break;
            } 
            if (resultSet.wasNull())
              arrayOfObject[b] = null; 
          } 
          transferBuffer.a(arrayOfObject);
        } 
        if (transferBuffer != null)
          this.a.b(transferBuffer); 
        resultSet.close();
        this.c.close();
        this.a.d();
      } catch (SQLException sQLException) {
        Log.a("Error in TransferDbReader", sQLException);
        sQLException.printStackTrace();
        TransferQueues.e();
        try {
          this.d.q();
        } catch (SQLException sQLException1) {}
      } catch (Throwable throwable) {
        Log.a("Error in TransferDbReader", throwable);
        throwable.printStackTrace();
        TransferQueues.e();
      } finally {
        if (this.c != null)
          try {
            this.c.close();
          } catch (SQLException sQLException) {} 
      } 
    } 
  }
  
  private static String a(Reader paramReader) {
    if (paramReader != null) {
      char[] arrayOfChar = new char[512];
      StringBuffer stringBuffer = new StringBuffer(512);
      try {
        i = paramReader.read(arrayOfChar);
      } catch (IOException iOException) {
        if ("Underlying input stream returned zero bytes".equals(iOException.getMessage()))
          return null; 
        throw iOException;
      } 
      int i;
      for (; i != -1; i = paramReader.read(arrayOfChar))
        stringBuffer.append(arrayOfChar, 0, i); 
      return stringBuffer.toString();
    } 
    return null;
  }
  
  private static byte[] a(InputStream paramInputStream) {
    if (paramInputStream != null) {
      byte[] arrayOfByte = new byte[512];
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      try {
        int i;
        while ((i = paramInputStream.read(arrayOfByte)) > 0)
          byteArrayOutputStream.write(arrayOfByte, 0, i); 
      } catch (IOException iOException) {
        if ("Underlying input stream returned zero bytes".equals(iOException.getMessage()))
          return null; 
        throw iOException;
      } 
      return byteArrayOutputStream.toByteArray();
    } 
    return null;
  }
}
