package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.sys.Features;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.concurrent.Task;
import javafx.util.Pair;
import javax.naming.NoPermissionException;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

public class FloatingLicenseTask extends Task {
  private final Rx b = new Rx(FloatingLicenseTask.class, this);
  
  private final FxFrame c;
  
  protected static int a = 0;
  
  private final boolean d;
  
  public FloatingLicenseTask(FxFrame paramFxFrame) {
    this.c = paramFxFrame;
    this.d = (License.a().g() != Features.a);
  }
  
  public HttpResponseDetails a() {
    updateMessage(this.b.H("ping"));
    Pair pair = b();
    if (((Boolean)pair.getKey()).booleanValue())
      return (HttpResponseDetails)pair.getValue(); 
    throw new NoPermissionException(this.b.H("noKey") + "\n" + this.b.H("noKey"));
  }
  
  protected void succeeded() {
    if (Keys.j.e() && this.d) {
      Log.c("Floating license activated");
      this.c.c();
    } 
  }
  
  protected void failed() {
    Log.a("Error in Floating License Server Task", getException());
    a++;
  }
  
  public static HttpResponseDetails a(boolean paramBoolean) {
    ArrayList<BasicNameValuePair> arrayList = new ArrayList();
    arrayList.add(new BasicNameValuePair(Keys.g.a, Keys.g.a()));
    if (Keys.j.e())
      arrayList.add(new BasicNameValuePair(Keys.j.a, Keys.j.a())); 
    if (paramBoolean)
      arrayList.add(new BasicNameValuePair("UNREGISTER", "true")); 
    HttpPost httpPost = new HttpPost("http://" + Keys.h.a() + ":" + Keys.i.b() + "/fls");
    httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity(arrayList, StandardCharsets.UTF_8));
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)httpPost);
    try {
      HttpResponseDetails httpResponseDetails = HttpResponseDetails.a(closeableHttpResponse);
      if (closeableHttpResponse != null)
        closeableHttpResponse.close(); 
      return httpResponseDetails;
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
  
  public static Pair b() {
    HttpResponseDetails httpResponseDetails = a(false);
    boolean bool = false;
    if (httpResponseDetails.b() != null) {
      Matcher matcher = Pattern.compile(Keys.j.a + "=(\\d*)").matcher(httpResponseDetails.b().trim());
      if (matcher.matches()) {
        Keys.j.a(matcher.group(1));
        a = 0;
        bool = true;
      } 
    } 
    return new Pair(Boolean.valueOf(bool), httpResponseDetails);
  }
}
