package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class FxDownloadSampleModelsTask extends Task {
  private File a;
  
  private final Rx b = new Rx(FxDownloadSampleModelsTask.class, this);
  
  private final FxFrame c;
  
  public FxDownloadSampleModelsTask(FxFrame paramFxFrame) {
    this.c = paramFxFrame;
  }
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: Download Sample Projects.");
    updateMessage("Downloading sample projects from dbschema.com ...");
    NetworkProxy.a();
    File file = Sys.f.toFile();
    if (!file.exists()) {
      Log.c("Upload sample projects Task/Create folder '" + String.valueOf(file) + "'");
      file.mkdir();
    } 
    this.a = Sys.f.resolve("samples.zip").toFile();
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet("https://dbschema.com/jdbc-drivers/samples.zip"));
    try {
      HttpEntity httpEntity = closeableHttpResponse.getEntity();
      if (httpEntity != null) {
        long l = Math.max(1L, httpEntity.getContentLength());
        InputStream inputStream = httpEntity.getContent();
        try {
          FileOutputStream fileOutputStream = new FileOutputStream(this.a);
          try {
            byte[] arrayOfByte = new byte[1024];
            int j = 0;
            int i;
            while ((i = inputStream.read(arrayOfByte)) != -1) {
              fileOutputStream.write(arrayOfByte, 0, i);
              j += i;
              if (isCancelled()) {
                Void void_ = null;
                fileOutputStream.close();
                if (inputStream != null)
                  inputStream.close(); 
                if (closeableHttpResponse != null)
                  closeableHttpResponse.close(); 
                return void_;
              } 
              updateProgress(j, l + 1000L);
            } 
            fileOutputStream.close();
          } catch (Throwable throwable) {
            try {
              fileOutputStream.close();
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
    updateMessage("Installing Sample Projects...");
    FxUtil.a(this.a, file);
    return null;
  }
  
  protected void succeeded() {}
  
  protected void failed() {
    Throwable throwable = getException();
    if (this.a != null)
      FileUtils.b(this.a); 
    Log.a("Failed to download the sample projects.", throwable);
  }
}
