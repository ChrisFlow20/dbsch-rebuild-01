package com.wisecoders.dbs.dbms.reverseEngineer.tasks;

import com.wisecoders.dbs.dialogs.system.HttpsUtils;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

public class FxGetRenewedLicenseTask extends Task {
  private final FxFrame a;
  
  private final Rx b = new Rx(FxGetRenewedLicenseTask.class, this);
  
  public FxGetRenewedLicenseTask(FxFrame paramFxFrame) {
    this.a = paramFxFrame;
  }
  
  protected String a() {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    License license = License.a();
    linkedHashMap.put("SUBSCRIPTION_ID", license.b);
    linkedHashMap.put("ORDER_ID", Long.valueOf(license.e));
    linkedHashMap.put("EMAIL", license.c);
    linkedHashMap.put("SEAT", Integer.valueOf(license.g));
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<Object, Object> entry : linkedHashMap.entrySet()) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append('&'); 
      stringBuilder.append(
          URLEncoder.encode((String)entry.getKey(), StandardCharsets.UTF_8))
        .append('=')
        .append(URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8));
    } 
    HttpPost httpPost = new HttpPost(this.b.a("renew.url", new String[0]));
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-type", "application/json");
    httpPost.setEntity((HttpEntity)new StringEntity(stringBuilder.toString()));
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)httpPost);
    try {
      HttpEntity httpEntity = closeableHttpResponse.getEntity();
      if (httpEntity != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        InputStream inputStream = httpEntity.getContent();
        try {
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
          try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
              int i;
              while ((i = bufferedReader.read()) >= 0)
                stringBuilder1.append((char)i); 
              bufferedReader.close();
            } catch (Throwable throwable) {
              try {
                bufferedReader.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
            inputStreamReader.close();
          } catch (Throwable throwable) {
            try {
              inputStreamReader.close();
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
        String str1 = stringBuilder1.toString().trim();
        if (closeableHttpResponse != null)
          closeableHttpResponse.close(); 
        return str1;
      } 
      String str = null;
      if (closeableHttpResponse != null)
        closeableHttpResponse.close(); 
      return str;
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
  
  protected void succeeded() {
    String str = (String)getValue();
    if (StringUtil.isFilled(str) && str.startsWith("###") && str.endsWith("###")) {
      Keys.d.a(str);
      this.a.closeProject();
      this.a.a();
    } 
  }
}
