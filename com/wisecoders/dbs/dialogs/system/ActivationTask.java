package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import javafx.concurrent.Task;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ActivationTask extends Task {
  private final ActivationTask$Mode a;
  
  private final Rx b = new Rx(ActivationTask.class, this);
  
  public ActivationTask(ActivationTask$Mode paramActivationTask$Mode) {
    this.a = paramActivationTask$Mode;
  }
  
  protected HttpResponseDetails a() {
    updateMessage(this.b.H("validating"));
    License license = License.a();
    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    multipartEntityBuilder.addTextBody("GATEWAY", license.a);
    multipartEntityBuilder.addTextBody("SUBSCRIPTION_ID", license.b);
    multipartEntityBuilder.addTextBody("SEAT", "" + license.g);
    multipartEntityBuilder.addTextBody(Keys.f.a, Keys.f.a());
    multipartEntityBuilder.addTextBody(Keys.g.a, Keys.g.a());
    multipartEntityBuilder.addTextBody("EMAIL", license.c);
    multipartEntityBuilder.addTextBody("SECURE", license.b(true));
    if (this.a == ActivationTask$Mode.b)
      multipartEntityBuilder.addTextBody("UNREGISTER", "true"); 
    HttpPost httpPost = new HttpPost(this.b.a("activate.url", new String[0]));
    httpPost.setEntity(multipartEntityBuilder.build());
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(7000).setSocketTimeout(15000).build();
    CloseableHttpClient closeableHttpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    try {
      CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute((HttpUriRequest)httpPost);
      try {
        HttpResponseDetails httpResponseDetails1 = HttpResponseDetails.a(closeableHttpResponse);
        Log.c("Activate license using " + license.c((this.a == ActivationTask$Mode.b)));
        Log.c("Response Code: " + httpResponseDetails1.a());
        Log.c("Response Message: " + httpResponseDetails1.b());
        HttpResponseDetails httpResponseDetails2 = httpResponseDetails1;
        if (closeableHttpResponse != null)
          closeableHttpResponse.close(); 
        if (closeableHttpClient != null)
          closeableHttpClient.close(); 
        return httpResponseDetails2;
      } catch (Throwable throwable) {
        if (closeableHttpResponse != null)
          try {
            closeableHttpResponse.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Throwable throwable) {
      if (closeableHttpClient != null)
        try {
          closeableHttpClient.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
