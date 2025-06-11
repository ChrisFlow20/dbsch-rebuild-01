package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class FxDownloadDriverTask extends Task {
  private final String a;
  
  public FxDownloadDriverTask(String paramString) {
    this.a = paramString;
    NetworkProxy.a();
  }
  
  public Void a() {
    Thread.currentThread().setName("DbSchema: Download " + this.a + " JDBC Drivers Task.");
    updateMessage("Downloading driver from dbschema.com ...");
    String str1 = DriverManager.a(this.a).o();
    String str2 = StringUtil.isFilledTrim(str1) ? str1 : this.a;
    a(str2);
    return null;
  }
  
  public void a(String paramString) {
    Path path = Sys.e.resolve(this.a + "/");
    File file1 = path.toFile();
    if (!file1.exists()) {
      Log.c("Download Driver '" + String.valueOf(file1) + "'");
      file1.mkdir();
    } 
    File file2 = path.resolve("driver.zip").toFile();
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet("https://dbschema.com/jdbc-drivers/" + paramString + "JdbcDriver.zip"));
    try {
      HttpEntity httpEntity = closeableHttpResponse.getEntity();
      if (httpEntity != null) {
        long l = Math.max(1L, httpEntity.getContentLength());
        try {
          InputStream inputStream = httpEntity.getContent();
          try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
            try {
              byte[] arrayOfByte = new byte[16384];
              int j = 0;
              int i;
              while ((i = inputStream.read(arrayOfByte)) != -1) {
                bufferedOutputStream.write(arrayOfByte, 0, i);
                j += i;
                if (isCancelled()) {
                  bufferedOutputStream.close();
                  if (inputStream != null)
                    inputStream.close(); 
                  if (closeableHttpResponse != null)
                    closeableHttpResponse.close(); 
                  return;
                } 
                updateProgress(j, l + 1000L);
              } 
              bufferedOutputStream.flush();
              bufferedOutputStream.close();
            } catch (Throwable throwable) {
              try {
                bufferedOutputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
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
        } catch (Throwable throwable) {
          FileUtils.b(file2);
          throw throwable;
        } 
      } 
      if (closeableHttpResponse != null)
        closeableHttpResponse.close(); 
    } catch (Throwable throwable) {
      if (closeableHttpResponse != null)
        try {
          closeableHttpResponse.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    updateMessage("Installing JDBC driver...");
    FxUtil.a(file2, file1);
  }
  
  protected void succeeded() {}
  
  protected void failed() {
    Throwable throwable = getException();
    Log.a("Failed to download " + this.a + " JDBC driver from DbSchema repository.", throwable);
  }
}
