package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.driver.model.DriverJar;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class FxUploadDriverTask extends Task {
  private final Rx a = new Rx(FxUploadDriverTask.class, this);
  
  protected final String c;
  
  private final Dialog$ b;
  
  private File d;
  
  private File e;
  
  protected FxUploadDriverTask(Dialog$ paramDialog$, String paramString) {
    this.c = paramString;
    this.b = paramDialog$;
    this.d = FxFileChooser.a(paramDialog$.getDialogPane().getScene(), "Choose JDBC driver file (with extension .jar)", FileOperation.a, new FileType[] { FileType.u });
    if (this.d != null) {
      String str = URLEncoder.encode(this.d.getName(), StandardCharsets.UTF_8);
      File file = Sys.e.resolve(paramString + "/" + paramString).toFile();
      if (a(paramDialog$.getDialogPane().getScene(), this.d, this.a))
        this.e = file; 
    } 
  }
  
  protected Void a() {
    if (this.d != null && this.e != null) {
      Thread.currentThread().setName("DbSchema: Upload JDBC Driver Task");
      updateMessage("Loading drivers...");
      File file = Sys.e.resolve(this.c + "/").toFile();
      if (!file.exists()) {
        Log.c("Upload Driver Task/Create folder '" + String.valueOf(file) + "'");
        file.mkdir();
      } 
      FileInputStream fileInputStream = new FileInputStream(this.d);
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(this.e);
        try {
          int i = fileInputStream.available();
          byte[] arrayOfByte = new byte[i];
          int j = fileInputStream.read(arrayOfByte, 0, i);
          if (j > 0)
            fileOutputStream.write(arrayOfByte, 0, j); 
          fileOutputStream.close();
        } catch (Throwable throwable) {
          try {
            fileOutputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
        fileInputStream.close();
      } catch (Throwable throwable) {
        try {
          fileInputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
    return null;
  }
  
  protected void succeeded() {
    if (this.d != null && this.e != null) {
      DriverManager driverManager = DriverManager.a(this.c);
      driverManager.k();
      DriverJar driverJar = driverManager.a(this.e);
      if (driverJar == null) {
        this.b.showError("No JDBC drivers found in '" + this.e.getName() + "'.");
      } else if (driverJar.c()) {
        this.b.showError("The JDBC driver requires a higher version of Java. Currently " + System.getProperty("java.version") + ".\nPlease remove the 'jre' folder from the DbSchema installation folder and \nthe Java installed on your machine will be used.");
      } else if (driverJar.b()) {
        this.a.c(this.b.getDialogScene(), "checkOutputLogs");
      } else if (driverJar.d()) {
        this.a.c(this.b.getDialogScene(), "configureDriverURL");
      } else {
        this.b.rx.d(this.b.getDialogPane().getScene(), "Upload succeeded.");
      } 
      Log.c("Loaded driver file " + String.valueOf(this.e));
    } 
  }
  
  protected void failed() {
    Throwable throwable = getException();
    Log.b(throwable);
    this.b.rx.a(this.b.getDialogPane().getScene(), throwable);
  }
  
  private boolean a(Scene paramScene, File paramFile, Rx paramRx) {
    if (!paramFile.getName().endsWith(".jar") && paramRx
      .a(paramScene, "The Java driver files usually have the extension '.jar'.\nThe file you tried to upload has a different extension"))
      return false; 
    try {
      ZipFile zipFile = new ZipFile(paramFile);
      for (Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); enumeration.hasMoreElements();)
        enumeration.nextElement(); 
      return true;
    } catch (Exception exception) {
      Log.a("Error opening driver file '" + String.valueOf(paramFile) + "'.", exception);
      paramRx.b(paramScene, "Could not open java archive file \n'" + String.valueOf(paramFile) + "'\n" + exception.getLocalizedMessage(), exception);
      return false;
    } 
  }
}
