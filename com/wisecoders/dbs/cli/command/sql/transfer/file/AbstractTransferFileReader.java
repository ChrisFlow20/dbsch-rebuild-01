package com.wisecoders.dbs.cli.command.sql.transfer.file;

import com.wisecoders.dbs.cli.command.sql.transfer.TransferBuffer;
import com.wisecoders.dbs.cli.command.sql.transfer.TransferQueues;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractTransferFileReader implements Runnable {
  private final AbstractConsole d;
  
  public final TransferQueues a;
  
  protected final int b;
  
  protected final Closeable c;
  
  public AbstractTransferFileReader(AbstractConsole paramAbstractConsole, TransferQueues paramTransferQueues, Closeable paramCloseable, int paramInt) {
    this.b = paramInt;
    this.c = paramCloseable;
    this.d = paramAbstractConsole;
    this.a = paramTransferQueues;
  }
  
  public void run() {
    if (TransferQueues.g())
      return; 
    this.a.a();
    try {
      boolean bool = true;
      TransferBuffer transferBuffer = null;
      while (bool) {
        Object[] arrayOfObject = new Object[this.b];
        if (a(arrayOfObject)) {
          if (transferBuffer == null) {
            transferBuffer = this.a.b();
          } else if (transferBuffer.e()) {
            this.a.b(transferBuffer);
            transferBuffer = this.a.b();
          } 
          if (transferBuffer == null) {
            bool = false;
            continue;
          } 
          transferBuffer.a(arrayOfObject);
          continue;
        } 
        bool = false;
      } 
      if (transferBuffer != null)
        this.a.b(transferBuffer); 
      this.a.d();
    } catch (EOFException eOFException) {
      this.a.d();
    } catch (SQLException sQLException) {
      TransferQueues.e();
      this.d.a(sQLException);
      this.d.a(sQLException.getNextException());
    } catch (Throwable throwable) {
      TransferQueues.e();
      this.d.a(throwable);
    } finally {
      try {
        this.c.close();
      } catch (IOException iOException) {}
    } 
  }
  
  public abstract boolean a(Object[] paramArrayOfObject);
}
