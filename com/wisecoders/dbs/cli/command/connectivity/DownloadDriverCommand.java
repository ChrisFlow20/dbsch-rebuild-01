package com.wisecoders.dbs.cli.command.connectivity;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.system.HttpsUtils;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

@DoNotObfuscate
public class DownloadDriverCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    paramAbstractConsole.t();
    if (StringUtil.isEmpty(paramString2)) {
      paramAbstractConsole.a("Usage: download driver <db_name>\nPlease visit https://www.dbschema.com/drivers.html for a list of available database names and drivers.\nAlternative you can place the JDBC driver files in the user home directory .DbSchema/drivers/<DB_NAME>/\nSample: C:/Users/<MyUser>/.DbSchema/drivers/MySql", new Object[0]);
    } else {
      String str = paramString2.trim();
      downloadDriver(str);
      paramAbstractConsole.c("Done. ", new Object[0]);
    } 
  }
  
  public static void downloadDriver(String paramString) {
    Path path = Sys.e.resolve(paramString + "/");
    File file1 = path.toFile();
    if (!file1.exists()) {
      Log.c("Download Driver '" + String.valueOf(file1) + "'");
      file1.mkdir();
    } 
    File file2 = path.resolve("driver.zip").toFile();
    if (file2.exists())
      file2.delete(); 
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet("https://dbschema.com/jdbc-drivers/" + paramString + "JdbcDriver.zip"));
    try {
      HttpEntity httpEntity = closeableHttpResponse.getEntity();
      if (httpEntity != null) {
        try {
          InputStream inputStream = httpEntity.getContent();
          try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            try {
              FileOutputStream fileOutputStream = new FileOutputStream(file2);
              try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                try {
                  byte[] arrayOfByte = new byte[16384];
                  int i;
                  while ((i = bufferedInputStream.read(arrayOfByte)) != -1)
                    bufferedOutputStream.write(arrayOfByte, 0, i); 
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
                fileOutputStream.close();
              } catch (Throwable throwable) {
                try {
                  fileOutputStream.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                } 
                throw throwable;
              } 
              bufferedInputStream.close();
            } catch (Throwable throwable) {
              try {
                bufferedInputStream.close();
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
          if (file2.exists())
            FileUtils.b(file2); 
          throw throwable;
        } 
        FxUtil.a(file2, file1);
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
  }
  
  public static void downloadH2DriverIfMissing() {
    String str = "H2";
    File file = Sys.e.resolve("H2/").toFile();
    if (!file.exists() || !file.isDirectory() || file.listFiles() == null || (file.listFiles()).length == 0)
      downloadDriver("H2"); 
  }
}
