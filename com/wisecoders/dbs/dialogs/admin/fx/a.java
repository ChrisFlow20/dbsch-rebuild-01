package com.wisecoders.dbs.dialogs.admin.fx;

import com.wisecoders.dbs.project.store.Pref;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javafx.concurrent.Task;

class a extends Task {
  final String a = this.c.a.getText();
  
  final String b = this.c.b.getText();
  
  protected String a() {
    Pref.b("merchant", this.a);
    Pref.b("secret", this.b);
    BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Temp/DisableLicenses.csv"));
    try {
      String str;
      while ((str = bufferedReader.readLine()) != null) {
        str = str.trim();
        if (str.startsWith("\""))
          str = str.substring(1); 
        if (str.endsWith("\""))
          str = str.substring(0, str.length() - 2); 
        updateMessage("Subscription " + str);
        System.out.println(str);
        a("DELETE", "https://api.2checkout.com/rest/6.0/subscriptions/" + str.trim() + "/", (String)null);
      } 
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return "Done";
  }
  
  private String a(String paramString1, String paramString2, String paramString3) {
    BufferedReader bufferedReader;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String str1 = simpleDateFormat.format(new Date());
    String str2 = Fx2CheckoutDialog.a("" + this.a.length() + this.a.length() + this.a + str1.length(), this.b, "HmacMD5");
    URL uRL = new URL(paramString2);
    URLConnection uRLConnection = uRL.openConnection();
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
    httpURLConnection.setRequestMethod(paramString1);
    httpURLConnection.addRequestProperty("Accept", "application/json");
    httpURLConnection.addRequestProperty("Content-Type", "application/json");
    String str3 = "code=\"" + this.a + "\" date=\"" + str1 + "\" hash=\"" + str2 + "\"";
    httpURLConnection.addRequestProperty("X-Avangate-Authentication", str3);
    if (paramString3 != null) {
      httpURLConnection.setDoOutput(true);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
      outputStreamWriter.write(paramString3);
      outputStreamWriter.flush();
      outputStreamWriter.close();
    } 
    if (httpURLConnection.getResponseCode() > 99 && httpURLConnection.getResponseCode() < 400) {
      bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
    } else {
      bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
    } 
    StringBuilder stringBuilder = new StringBuilder();
    String str4;
    while ((str4 = bufferedReader.readLine()) != null)
      stringBuilder.append(str4); 
    return stringBuilder.toString();
  }
  
  protected void succeeded() {
    this.c.c.setText((String)getValue());
  }
  
  protected void failed() {
    this.c.c.setText(getException().getMessage());
  }
  
  private a(Fx2CheckoutDialog paramFx2CheckoutDialog) {}
}
