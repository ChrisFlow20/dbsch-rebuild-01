package com.wisecoders.dbs.dialogs.system;

import com.google.gson.GsonBuilder;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;

public class TechnicalSupportTask extends Task {
  private final Dialog$ a;
  
  private final String b;
  
  private final String c;
  
  private final String d;
  
  private final List e;
  
  private static final String f = "Token token=Yf4Z3e9_GQGpP3FXiPJt6QvZNX2vk6iY8rzJ6R3GFH6VfgCVp9a_-iFWAMtkg-wZ";
  
  private static final String g = "===" + System.currentTimeMillis() + "===";
  
  private static final String h = "\r\n";
  
  private static final String i = "--";
  
  private static final String j = "UTF-8";
  
  TechnicalSupportTask(Dialog$ paramDialog$, String paramString1, String paramString2, String paramString3, List paramList) {
    this.a = paramDialog$;
    this.b = paramString1;
    this.c = paramString2;
    this.d = paramString3;
    this.e = paramList;
  }
  
  protected HttpResponseDetails a() {
    updateMessage("Contacting server...");
    HttpPost httpPost = new HttpPost("https://support.dbschema.com/api/v1/tickets");
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setHeader("Authorization", "Token token=Yf4Z3e9_GQGpP3FXiPJt6QvZNX2vk6iY8rzJ6R3GFH6VfgCVp9a_-iFWAMtkg-wZ");
    httpPost.setEntity((HttpEntity)new StringEntity(a(this.b, this.c, this.d, this.e)));
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)httpPost);
    try {
      HttpResponseDetails httpResponseDetails = new HttpResponseDetails(closeableHttpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(closeableHttpResponse.getEntity()));
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
  
  protected void failed() {
    this.a.rx.b(this.a.getDialogScene(), "Failed to create ticket, see the error below.\nPlease send your message as email to support@dbschema.com\nPlease include also a screenshot of this error.", getException());
  }
  
  protected void succeeded() {
    HttpResponseDetails httpResponseDetails = (HttpResponseDetails)getValue();
    if (httpResponseDetails != null) {
      (new Alert$(Alert.AlertType.CONFIRMATION)).a(this.a.getDialogScene()).b((httpResponseDetails.a() == 201) ? "Ticket Successfully Created" : "Server Response").c("HTTP Response \"" + httpResponseDetails.a() + "\"").a(httpResponseDetails.b(), true).showAndWait();
      for (File file : this.e) {
        if (file.getName().toLowerCase().startsWith("snapshot"))
          file.deleteOnExit(); 
      } 
      if (httpResponseDetails.a() == 201)
        this.a.close(); 
    } 
  }
  
  private String a(String paramString1, String paramString2, String paramString3, List paramList) {
    if (StringUtil.isEmptyTrim(paramString2))
      paramString2 = "support@dbschema.com"; 
    HashMap<Object, Object> hashMap1 = new HashMap<>();
    hashMap1.put("title", StringUtil.isFilledTrim(paramString1) ? paramString1 : "DbSchema Report Bug");
    hashMap1.put("group_id", Integer.valueOf(1));
    hashMap1.put("customer_id", "guess:support@dbschema.com");
    hashMap1.put("state_id", Integer.valueOf(2));
    HashMap<Object, Object> hashMap2 = new HashMap<>();
    hashMap2.put("subject", StringUtil.isFilledTrim(paramString1) ? paramString1 : "DbSchema Report Bug");
    hashMap2.put("body", paramString3);
    hashMap2.put("internal", "false");
    hashMap2.put("reply_to", paramString2);
    hashMap2.put("from", paramString2);
    hashMap2.put("content_type", "text");
    hashMap2.put("type", "web");
    hashMap1.put("article", hashMap2);
    if (!paramList.isEmpty()) {
      ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
      hashMap2.put("attachments", arrayList);
      for (File file : paramList) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        arrayList.add(hashMap);
        hashMap.put("filename", file.getName());
        hashMap.put("data", b(file));
        hashMap.put("mime-type", a(file));
      } 
      if (Snapshot.b()) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        arrayList.add(hashMap);
        hashMap.put("filename", "dialog.gif");
        hashMap.put("data", Base64.getEncoder().encodeToString(Snapshot.c()));
        hashMap.put("mime-type", "image/gif");
        Snapshot.a();
      } 
    } 
    return (new GsonBuilder()).setPrettyPrinting().create().toJson(hashMap1);
  }
  
  private static String b(File paramFile) {
    try {
      byte[] arrayOfByte = FileUtils.readFileToByteArray(paramFile);
      return Base64.getEncoder().encodeToString(arrayOfByte);
    } catch (Exception exception) {
      Log.a("Error encoding image", exception);
      return "";
    } 
  }
  
  public String a(File paramFile) {
    String str = paramFile.getName().toLowerCase();
    if (str.endsWith(".dbs"))
      return "application/xml"; 
    if (str.endsWith(".dbs.bak"))
      return "application/xml"; 
    if (str.endsWith(".png"))
      return "image/png"; 
    if (str.endsWith(".jpeg") || str.endsWith(".jpg"))
      return "image/jpeg"; 
    if (str.endsWith(".gif"))
      return "image/gif"; 
    if (str.endsWith(".doc"))
      return "application/msword"; 
    if (str.endsWith(".pdf"))
      return "application/pdf"; 
    if (str.endsWith(".csv"))
      return "text/csv"; 
    if (str.endsWith(".jar"))
      return "application/java-archive"; 
    if (str.endsWith(".zip"))
      return "application/zip"; 
    if (str.endsWith(".mp3") || str.endsWith(".mpeg"))
      return "application/mpeg"; 
    if (str.endsWith(".text"))
      return "text/plain"; 
    if (str.endsWith(".json"))
      return "application/json"; 
    if (str.endsWith(".tar"))
      return "application/x-tar"; 
    if (str.endsWith(".7z"))
      return "application/x-7z-compressed"; 
    if (str.endsWith(".ttf"))
      return "font/ttf"; 
    if (str.endsWith(".xml"))
      return "application/xml"; 
    if (str.endsWith(".html") || str.endsWith(".htm"))
      return "text/html"; 
    return "application/zip";
  }
  
  public static void a(String paramString1, String paramString2) {
    try {
      MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
      multipartEntityBuilder.addTextBody("MESSAGE", paramString2);
      multipartEntityBuilder.addTextBody("OUTPUT_LOGS", Log.a(200));
      multipartEntityBuilder.addTextBody("EMAIL", (License.a()).c);
      multipartEntityBuilder.addTextBody("PROFILE_ID", Keys.f.a());
      multipartEntityBuilder.addTextBody("DB", paramString1);
      if (Snapshot.b()) {
        multipartEntityBuilder.addBinaryBody("IMAGE", Snapshot.c(), ContentType.IMAGE_GIF, "img.gif");
        Snapshot.a();
      } 
      HttpPost httpPost = new HttpPost("https://dbschema.com/technical/feedback.php");
      httpPost.setEntity(multipartEntityBuilder.build());
      CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)httpPost);
      try {
        EntityUtils.consume(closeableHttpResponse.getEntity());
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
    } catch (Throwable throwable) {}
  }
  
  public static void b(String paramString1, String paramString2) {
    try {
      URL uRL = new URL("https://dbschema.com/technical/feedback.php");
      HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
      httpURLConnection.setConnectTimeout(2000);
      httpURLConnection.setReadTimeout(2500);
      httpURLConnection.setDoOutput(true);
      httpURLConnection.setRequestMethod("POST");
      httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; MULTIPART_BOUNDARY=" + g);
      DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
      try {
        a(dataOutputStream, "MESSAGE", paramString2);
        a(dataOutputStream, "OUTPUT_LOGS", Log.a(200));
        a(dataOutputStream, "EMAIL", (License.a()).c);
        a(dataOutputStream, "PROFILE_ID", Keys.f.a());
        a(dataOutputStream, "DB", paramString1);
        if (Snapshot.b()) {
          dataOutputStream.writeBytes("--" + g + "\r\n");
          dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"IMAGE\"; filename=\"img.gif\"\r\n");
          dataOutputStream.writeBytes("Content-Type: " + String.valueOf(ContentType.IMAGE_GIF) + "\r\n");
          dataOutputStream.writeBytes("\r\n");
          dataOutputStream.write(Snapshot.c());
        } 
        dataOutputStream.writeBytes("\r\n");
        dataOutputStream.writeBytes("--" + g + "--\r\n");
        dataOutputStream.flush();
        dataOutputStream.close();
      } catch (Throwable throwable) {
        try {
          dataOutputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
      int i = httpURLConnection.getResponseCode();
      StringBuilder stringBuilder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
      try {
        String str;
        while ((str = bufferedReader.readLine()) != null)
          stringBuilder.append(str).append("\n"); 
        bufferedReader.close();
      } catch (Throwable throwable) {
        try {
          bufferedReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {}
  }
  
  private static void a(DataOutputStream paramDataOutputStream, String paramString1, String paramString2) {
    if (paramString1 != null && paramString2 != null) {
      paramDataOutputStream.writeBytes("--" + g + "\r\n");
      paramDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + paramString1 + "\"\r\n");
      paramDataOutputStream.writeBytes("Content-Type: text/plain; charset=UTF-8\r\n");
      paramDataOutputStream.writeBytes("\r\n");
      paramDataOutputStream.write(paramString2.getBytes(StandardCharsets.UTF_8));
      paramDataOutputStream.writeBytes("\r\n");
    } 
  }
}
