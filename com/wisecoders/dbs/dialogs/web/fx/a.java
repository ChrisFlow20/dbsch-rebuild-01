package com.wisecoders.dbs.dialogs.web.fx;

import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.system.HttpsUtils;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

class a extends Task {
  private final FxVideoDialog$MediaEntry b;
  
  public a(FxVideoDialog paramFxVideoDialog, FxVideoDialog$MediaEntry paramFxVideoDialog$MediaEntry) {
    this.b = paramFxVideoDialog$MediaEntry;
    paramFxVideoDialog.f.setVisible(true);
    paramFxVideoDialog.b.a(paramFxVideoDialog.f);
    paramFxVideoDialog.c();
    paramFxVideoDialog.d();
    NetworkProxy.a();
    paramFxVideoDialog.n = true;
  }
  
  public Void a() {
    Thread.currentThread().setName("DbSchema: Download " + String.valueOf(this.b) + " Video Task.");
    updateMessage("Downloading video from dbschema.com...");
    File file = Sys.n.toFile();
    if (!file.exists()) {
      Log.c("Create Video Folder '" + String.valueOf(file) + "'");
      if (!file.mkdir())
        throw new IOException("Cannot create video download folder"); 
    } 
    FileUtils.b(this.b.f);
    if (this.b.e.exists())
      return null; 
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet("https://dbschema.com/video/" + this.b.d));
    try {
      HttpEntity httpEntity = closeableHttpResponse.getEntity();
      if (httpEntity != null) {
        long l = Math.max(1L, httpEntity.getContentLength());
        try {
          InputStream inputStream = httpEntity.getContent();
          try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.b.f));
            try {
              byte[] arrayOfByte = new byte[16384];
              int j = 0;
              int i;
              while ((i = inputStream.read(arrayOfByte)) != -1 && !isCancelled()) {
                bufferedOutputStream.write(arrayOfByte, 0, i);
                j += i;
                if (isCancelled()) {
                  Void void_1 = null;
                  bufferedOutputStream.close();
                  if (inputStream != null)
                    inputStream.close(); 
                  if (closeableHttpResponse != null)
                    closeableHttpResponse.close(); 
                  return void_1;
                } 
                updateProgress(j, l + 1000L);
              } 
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
          FileUtils.b(this.b.f);
          throw throwable;
        } 
        FileUtils.b(this.b.f, this.b.e);
        Void void_ = null;
        if (closeableHttpResponse != null)
          closeableHttpResponse.close(); 
        return void_;
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
    return null;
  }
  
  protected void succeeded() {
    this.a.n = true;
    this.a.a(this.b);
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.a.b.a(this.a.getDialogScene(), throwable);
    Log.a("Failed to download " + String.valueOf(this.b) + " video file from dbschema.com .", throwable);
  }
}
