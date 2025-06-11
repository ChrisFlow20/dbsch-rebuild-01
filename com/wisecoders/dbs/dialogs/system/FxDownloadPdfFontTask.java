package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.pdf.PdfBaseFonts;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class FxDownloadPdfFontTask extends Task {
  FxDownloadPdfFontTask() {
    File file = Sys.l.toFile();
    if (!file.exists()) {
      Log.c("Download PdfFont Task/Create folder '" + String.valueOf(file) + "'");
      file.mkdir();
    } 
  }
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: Download unifont.ttf Font Task");
    updateMessage("Downloading Unicode font from dbschema.com ...");
    NetworkProxy.a();
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet("https://dbschema.com/fonts/unifont.ttf"));
    try {
      HttpEntity httpEntity = closeableHttpResponse.getEntity();
      if (httpEntity != null) {
        long l = Math.max(1L, httpEntity.getContentLength());
        InputStream inputStream = httpEntity.getContent();
        try {
          FileOutputStream fileOutputStream = new FileOutputStream(PdfBaseFonts.b);
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
    return null;
  }
  
  protected void succeeded() {}
  
  protected void failed() {
    Throwable throwable = getException();
    if (PdfBaseFonts.b.exists())
      FileUtils.b(PdfBaseFonts.b); 
    Log.a("Failed to download unifont.ttf unicode font from dbschema.com .", throwable);
  }
}
