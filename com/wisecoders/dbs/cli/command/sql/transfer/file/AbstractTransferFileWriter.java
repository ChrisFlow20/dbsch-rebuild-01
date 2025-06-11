package com.wisecoders.dbs.cli.command.sql.transfer.file;

import com.wisecoders.dbs.cli.command.sql.transfer.TransferBuffer;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferMeta;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferQueues;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTransferFileWriter implements Runnable {
  private final TransferQueues d;
  
  protected final TransferMeta a;
  
  protected final int b;
  
  private String e;
  
  private Closeable f;
  
  private final Map g = new HashMap<>();
  
  protected final AbstractConsole c;
  
  private final boolean h;
  
  public AbstractTransferFileWriter(AbstractConsole paramAbstractConsole, TransferQueues paramTransferQueues, TransferMeta paramTransferMeta, boolean paramBoolean) {
    this.d = paramTransferQueues;
    this.b = 0;
    this.c = paramAbstractConsole;
    this.a = paramTransferMeta;
    this.h = paramBoolean;
  }
  
  public AbstractTransferFileWriter(AbstractConsole paramAbstractConsole, TransferQueues paramTransferQueues, TransferMeta paramTransferMeta, int paramInt, boolean paramBoolean) {
    this.d = paramTransferQueues;
    this.b = paramInt;
    this.c = paramAbstractConsole;
    this.a = paramTransferMeta;
    this.h = paramBoolean;
  }
  
  public void run() {
    try {
      TransferBuffer transferBuffer;
      while ((transferBuffer = this.d.c()) != null) {
        byte b = 0;
        Object[] arrayOfObject;
        while ((arrayOfObject = transferBuffer.a()) != null) {
          a(arrayOfObject);
          b++;
        } 
        this.d.a(transferBuffer);
      } 
    } catch (SQLException sQLException) {
      TransferQueues.e();
      this.c.a(sQLException);
      this.c.a(sQLException.getNextException());
    } catch (Throwable throwable) {
      TransferQueues.e();
      this.c.a(throwable);
    } finally {
      a();
    } 
  }
  
  public abstract void a(Object[] paramArrayOfObject);
  
  public void a() {
    b();
    if (this.h)
      c(); 
  }
  
  protected void b() {
    for (String str : this.g.keySet()) {
      try {
        ((Flushable)this.g.get(str)).flush();
      } catch (IOException iOException) {
        System.out.println(iOException);
      } 
    } 
    this.g.clear();
  }
  
  protected void c() {
    for (String str : this.g.keySet()) {
      try {
        ((Closeable)this.g.get(str)).close();
      } catch (IOException iOException) {
        System.out.println(iOException);
      } 
    } 
    this.g.clear();
  }
  
  public Closeable b(Object[] paramArrayOfObject) {
    String str = c(paramArrayOfObject);
    if (this.e != null && this.e.equals(str) && this.f != null)
      return this.f; 
    Closeable closeable;
    if ((closeable = (Closeable)this.g.get(str)) == null) {
      this.f = a(str);
      this.g.put(str, this.f);
      this.e = str;
      return this.f;
    } 
    this.f = closeable;
    this.e = str;
    return this.f;
  }
  
  public String c(Object[] paramArrayOfObject) {
    StringBuilder stringBuilder = new StringBuilder("");
    for (byte b = 0; b < this.b; b++) {
      if (stringBuilder.length() > 0)
        stringBuilder.append("_"); 
      Object object = paramArrayOfObject[b];
      if (object != null)
        stringBuilder.append(object); 
    } 
    String str = stringBuilder.toString();
    if (str.length() == 0)
      return "common"; 
    if (str.endsWith(".0"))
      return str.substring(0, str.length() - 2); 
    return str;
  }
  
  public abstract Closeable a(String paramString);
}
