package com.wisecoders.dbs.dialogs.admin.fx;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FileCombo;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HistoryTextField;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Window;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class FxSparkPostDialog extends Dialog$ {
  private static final String a = "https://api.sparkpost.com/api/v1/transmissions";
  
  private final HistoryTextField b = new HistoryTextField("sparkPostApiKey");
  
  private final HistoryTextField c = new HistoryTextField("sparkPostSubject");
  
  private final HistoryTextField d = new HistoryTextField("sparkPostSender");
  
  private final HistoryTextField e = new HistoryTextField("sparkPostEmailBodyURL");
  
  private final FileCombo f = new FileCombo("sparkPostRecipients", new FileType[] { FileType.i, FileType.g });
  
  private final TextArea i = new TextArea();
  
  public FxSparkPostDialog(Window paramWindow) {
    super(paramWindow);
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("apiKeyLabel"), "0,0,r,c")
      .a((Node)this.b, "1,0,l,c")
      .a((Node)this.rx.e("subjectLabel"), "0,1,r,c")
      .a((Node)this.c, "1,1,l,c")
      .a((Node)this.rx.e("senderEmailLabel"), "0,2,r,c")
      .a((Node)this.d, "1,2,l,c")
      .a((Node)this.rx.e("bodyURLLabel"), "0,3,r,c")
      .a((Node)this.e, "1,3,l,c")
      .a((Node)this.rx.e("recipientsLabel"), "0,4,r,c")
      .a((Node)this.f, "1,4,l,c")
      .a((Node)this.i, "0,5,1,5,l,c");
  }
  
  public void createButtons() {
    createActionButton("send");
    createCloseButton();
  }
  
  public boolean apply() {
    return (!StringUtil.isEmpty(this.b.getText()) && 
      !StringUtil.isEmpty(this.c.getText()) && 
      !StringUtil.isEmpty(this.d.getText()) && 
      !StringUtil.isEmpty(this.e.getText()) && 
      !StringUtil.isEmpty(this.f.getValue()));
  }
  
  @Action
  public Task send() {
    e e = new e(this);
    e.messageProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.i.appendText("\n" + paramString2));
    return e;
  }
  
  private static String a(String paramString) {
    URL uRL = new URL(paramString);
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setConnectTimeout(5000);
    httpURLConnection.setReadTimeout(5000);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
    try {
      StringBuilder stringBuilder = new StringBuilder();
      String str1;
      while ((str1 = bufferedReader.readLine()) != null)
        stringBuilder.append(str1).append("\n"); 
      String str2 = stringBuilder.toString();
      bufferedReader.close();
      return str2;
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  private static List b(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
    try {
      String str;
      while ((str = bufferedReader.readLine()) != null)
        arrayList.add(str.trim()); 
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return arrayList;
  }
  
  private void a(List paramList, String paramString1, String paramString2) {
    JsonObject jsonObject1 = new JsonObject();
    JsonObject jsonObject2 = new JsonObject();
    jsonObject2.addProperty("sandbox", Boolean.valueOf(false));
    jsonObject1.add("options", (JsonElement)jsonObject2);
    JsonObject jsonObject3 = new JsonObject();
    JsonObject jsonObject4 = new JsonObject();
    jsonObject4.addProperty("email", this.d.getText());
    jsonObject3.add("from", (JsonElement)jsonObject4);
    jsonObject3.addProperty("subject", this.c.getText());
    jsonObject3.addProperty("html", paramString1);
    jsonObject3.addProperty("text", paramString2);
    jsonObject1.add("content", (JsonElement)jsonObject3);
    JsonArray jsonArray = new JsonArray();
    for (String str : paramList) {
      JsonObject jsonObject5 = new JsonObject();
      jsonObject5.addProperty("email", str);
      JsonObject jsonObject6 = new JsonObject();
      jsonObject6.add("address", (JsonElement)jsonObject5);
      jsonArray.add((JsonElement)jsonObject6);
    } 
    jsonObject1.add("recipients", (JsonElement)jsonArray);
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    try {
      HttpPost httpPost = new HttpPost("https://api.sparkpost.com/api/v1/transmissions");
      httpPost.setHeader("Content-Type", "application/json");
      httpPost.setHeader("Authorization", this.b.getText());
      httpPost.setEntity((HttpEntity)new StringEntity(jsonObject1.toString()));
      CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute((HttpUriRequest)httpPost);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
      String str;
      while ((str = bufferedReader.readLine()) != null)
        this.i.appendText(str + "\n"); 
      if (closeableHttpClient != null)
        closeableHttpClient.close(); 
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
