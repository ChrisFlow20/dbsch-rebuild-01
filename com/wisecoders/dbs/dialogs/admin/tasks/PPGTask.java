package com.wisecoders.dbs.dialogs.admin.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;
import javafx.util.Callback;

public abstract class PPGTask {
  public abstract String a();
  
  public abstract Map b();
  
  public abstract String a(Map paramMap, Callback paramCallback);
  
  public String a(String paramString1, String paramString2, String paramString3) {
    BufferedReader bufferedReader;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    URL uRL = new URL(paramString2);
    URLConnection uRLConnection = uRL.openConnection();
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
    httpURLConnection.setRequestMethod(paramString1);
    httpURLConnection.addRequestProperty("Accept", "application/json");
    httpURLConnection.addRequestProperty("Content-Type", "application/json");
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
    String str;
    while ((str = bufferedReader.readLine()) != null)
      stringBuilder.append(str); 
    return stringBuilder.toString();
  }
}
