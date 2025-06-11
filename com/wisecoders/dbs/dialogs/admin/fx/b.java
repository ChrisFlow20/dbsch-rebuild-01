package com.wisecoders.dbs.dialogs.admin.fx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wisecoders.dbs.loader.engine.JSONLoaderCallback;
import com.wisecoders.dbs.loader.engine.JSONParser;
import com.wisecoders.dbs.project.store.Pref;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javafx.concurrent.Task;

class b extends Task {
  final String a = this.c.a.getText();
  
  final String b = this.c.b.getText();
  
  protected String a() {
    Pref.b("merchant", this.a);
    Pref.b("secret", this.b);
    byte b1 = 0, b2 = 0;
    while (true) {
      for (String str : a(a("https://api.2checkout.com/rest/6.0/subscriptions/?ExpireBefore=2022-11-01&Limit=200&Page=" + b2 + 1, (String)null))) {
        a("https://api.2checkout.com/rest/6.0/subscriptions/" + str, "{ \"ExpirationDate\": \"2023-05-16T00:00:00+02:00\", \"SubscriptionEnabled\": \"TRUE\"}");
        b1++;
      } 
      updateMessage("Page " + b2 + " activated " + b1);
      b2++;
      if (b2 >= '✐')
        return "Activated " + b1; 
    } 
  }
  
  private String a(String paramString1, String paramString2) {
    BufferedReader bufferedReader;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String str1 = simpleDateFormat.format(new Date());
    String str2 = Fx2CheckoutDialog.a("" + this.a.length() + this.a.length() + this.a + str1.length(), this.b, "HmacMD5");
    URL uRL = new URL(paramString1);
    URLConnection uRLConnection = uRL.openConnection();
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
    httpURLConnection.setRequestMethod((paramString2 != null) ? "PUT" : "GET");
    httpURLConnection.addRequestProperty("Accept", "application/json");
    httpURLConnection.addRequestProperty("Content-Type", "application/json");
    String str3 = "code=\"" + this.a + "\" date=\"" + str1 + "\" hash=\"" + str2 + "\"";
    httpURLConnection.addRequestProperty("X-Avangate-Authentication", str3);
    if (paramString2 != null) {
      httpURLConnection.setDoOutput(true);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
      outputStreamWriter.write(paramString2);
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
  
  private List a(String paramString) {
    JSONLoaderCallback jSONLoaderCallback = new JSONLoaderCallback();
    JSONParser.parse(paramString, jSONLoaderCallback);
    ArrayList<String> arrayList = new ArrayList();
    Object object = jSONLoaderCallback.a.get("Items");
    if (object instanceof List)
      for (Map map : object) {
        if (map instanceof Map) {
          Map map1 = map;
          if ("PAST_DUE".equals(map1.get("Status")) && Boolean.TRUE.equals(map1.get("SubscriptionEnabled")))
            arrayList.add((String)map1.get("SubscriptionReference")); 
        } 
      }  
    return arrayList;
  }
  
  protected void succeeded() {
    JSONLoaderCallback jSONLoaderCallback = new JSONLoaderCallback();
    JSONParser.parse((String)getValue(), jSONLoaderCallback);
    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
    this.c.c.setText(gson.toJson(jSONLoaderCallback.a));
  }
  
  protected void failed() {
    this.c.c.setText(getException().getMessage());
  }
  
  private b(Fx2CheckoutDialog paramFx2CheckoutDialog) {}
}
