package com.wisecoders.dbs.cli.command.sql.transfer.db;

import com.wisecoders.dbs.cli.command.sql.transfer.TransferBuffer;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferMeta;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferQueues;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferDbWriter implements Runnable {
  private final Connector a;
  
  private final Envoy b;
  
  private final TransferQueues c;
  
  private final Table d;
  
  private final AbstractConsole e;
  
  private final TransferMeta f;
  
  private final int[] g;
  
  public TransferDbWriter(AbstractConsole paramAbstractConsole, Connector paramConnector, TransferMeta paramTransferMeta, TransferQueues paramTransferQueues, Table paramTable) {
    this.a = paramConnector;
    this.b = paramConnector.startEnvoy("ETL");
    this.c = paramTransferQueues;
    this.d = paramTable;
    this.e = paramAbstractConsole;
    this.f = paramTransferMeta;
    this.g = new int[paramTransferMeta.b];
    for (byte b = 0; b < paramTransferMeta.b; b++) {
      String str = paramTransferMeta.d[b];
      Column column = (Column)paramTable.columns.getByName(str);
      if (column == null) {
        if (paramTable.columns.isEmpty())
          throw new SQLException("Execute first 'learn schema public including details'."); 
        throw new SQLException("Cannot find column " + str + " in the table " + String.valueOf(paramTable) + ". Interrupting load.");
      } 
      this.g[b] = (column.getDataType().getJavaType() == 16 || column.getDataType().getJavaType() == -7) ? 16 : paramTransferMeta.c[b];
    } 
  }
  
  public void run() {
    try {
      TransferBuffer transferBuffer;
      while ((transferBuffer = this.c.c()) != null && !TransferQueues.g()) {
        if ((Dbms.get(this.a.dbId)).multipleValuesInInserts.b()) {
          a(transferBuffer);
        } else {
          b(transferBuffer);
        } 
        this.c.a(transferBuffer);
      } 
      this.b.p();
    } catch (SQLException sQLException) {
      Log.a("Error in TransferDbWriter", sQLException);
      TransferQueues.e();
      this.e.a(sQLException.getLocalizedMessage(), new Object[0]);
      this.e.a(sQLException.getNextException(), new Object[0]);
      sQLException.printStackTrace();
      this.b.close();
    } catch (Throwable throwable) {
      Log.a("Error in TransferDbWriter", throwable);
      TransferQueues.e();
      this.e.a(throwable);
      throwable.printStackTrace();
      if (this.b != null)
        this.b.a(throwable); 
    } 
  }
  
  private void a(TransferBuffer paramTransferBuffer) {
    Object[][] arrayOfObject = new Object[paramTransferBuffer.a.b][];
    int j = -1;
    UpdateStatement updateStatement = null;
    int i;
    while ((i = paramTransferBuffer.a((Object[])arrayOfObject, paramTransferBuffer.a.b)) > 0) {
      if (updateStatement != null && j == i) {
        for (byte b = 0; b < i; b++)
          updateStatement.a(new StatementParameter(arrayOfObject[b], this.f.c[b])); 
        updateStatement.a();
      } else {
        String str = SqlUtil.getInsertQueryWithMultipleValuesSets(this.d, this.f.d, i);
        updateStatement = this.b.b(str, new Object[0]);
        for (byte b = 0; b < i; b++)
          updateStatement.a(new StatementParameter(arrayOfObject[b], this.f.c[b])); 
        updateStatement.a();
      } 
      j = i;
    } 
    this.b.p();
  }
  
  private void b(TransferBuffer paramTransferBuffer) {
    String str = SqlUtil.getInsertQueryWithMultipleValuesSets(this.d, this.f.d, 1);
    UpdateStatement updateStatement = this.b.b(str, new Object[0]);
    try {
      Object[] arrayOfObject;
      while ((arrayOfObject = paramTransferBuffer.a()) != null) {
        updateStatement.i();
        for (Object object : arrayOfObject)
          updateStatement.a(object); 
        updateStatement.m();
      } 
      updateStatement.n();
      if (updateStatement != null)
        updateStatement.close(); 
    } catch (Throwable throwable) {
      if (updateStatement != null)
        try {
          updateStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    this.b.p();
  }
  
  private void a(PreparedStatement paramPreparedStatement, Object[] paramArrayOfObject, int paramInt) {
    for (byte b = 0; b < this.g.length; b++) {
      int i = paramInt * this.g.length + b;
      Object object = paramArrayOfObject[b];
      if (object == null || (Sys.B.convertEmptyStringToNull.b() && object instanceof String && ((String)object).length() == 0)) {
        paramPreparedStatement.setNull(i + 1, this.g[b]);
      } else {
        switch (this.g[b]) {
          case 1:
          case 12:
            paramPreparedStatement.setString(i + 1, String.valueOf(object));
            break;
          case -7:
          case 16:
            paramPreparedStatement.setBoolean(i + 1, a(object));
            break;
          case 2005:
            if (object instanceof String) {
              paramPreparedStatement.setString(i + 1, (String)object);
              break;
            } 
            paramPreparedStatement.setObject(i + 1, object);
            break;
          case -6:
          case 4:
          case 5:
            if (object instanceof Integer) {
              paramPreparedStatement.setInt(i + 1, ((Number)object).intValue());
              break;
            } 
            if (object instanceof String) {
              paramPreparedStatement.setInt(i + 1, Integer.parseInt((String)object));
              break;
            } 
            paramPreparedStatement.setObject(i + 1, object);
            break;
          case -5:
            if (object instanceof Long) {
              paramPreparedStatement.setLong(i + 1, ((Long)object).longValue());
              break;
            } 
            if (object instanceof Integer) {
              paramPreparedStatement.setLong(i + 1, ((Integer)object).intValue());
              break;
            } 
            if (object instanceof String) {
              paramPreparedStatement.setLong(i + 1, Long.parseLong((String)object));
              break;
            } 
            paramPreparedStatement.setObject(i + 1, object);
            break;
          case 2:
          case 3:
          case 6:
          case 7:
          case 8:
            if (object instanceof Double) {
              paramPreparedStatement.setDouble(i + 1, ((Double)object).doubleValue());
              break;
            } 
            paramPreparedStatement.setObject(i + 1, object);
            break;
          default:
            paramPreparedStatement.setObject(i + 1, object);
            break;
        } 
      } 
      paramArrayOfObject[b] = null;
    } 
  }
  
  private boolean a(Object paramObject) {
    if (paramObject instanceof Boolean)
      return ((Boolean)paramObject).booleanValue(); 
    if (paramObject instanceof Number)
      return (((Number)paramObject).intValue() != 0); 
    if ("true".equals(paramObject))
      return true; 
    if ("1".equals(paramObject))
      return true; 
    if ("0".equals(paramObject))
      return false; 
    throw new NullPointerException("Cannot convert " + paramObject.getClass().getName() + " with value " + String.valueOf(paramObject) + " to boolean.");
  }
}
