package com.wisecoders.dbs.dialogs.admin.fx;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class FxAmazonSESDialog extends Dialog$ {
  private final HistoryTextField a = new HistoryTextField("sesRegion");
  
  private final HistoryTextField b = new HistoryTextField("sesEndpoint");
  
  private final HistoryTextField c = new HistoryTextField("awsAccessKey");
  
  private final HistoryTextField d = new HistoryTextField("awsSecretKey");
  
  private final HistoryTextField e = new HistoryTextField("sparkPostSubject");
  
  private final HistoryTextField f = new HistoryTextField("sparkPostSender");
  
  private final HistoryTextField i = new HistoryTextField("sparkPostEmailBodyURL");
  
  private final HistoryTextField j = new HistoryTextField("emailsPerBatch");
  
  private final HistoryTextField k = new HistoryTextField("batchWait");
  
  private final FileCombo l = new FileCombo("sparkPostRecipients", new FileType[] { FileType.i, FileType.g });
  
  private final TextArea m = new TextArea();
  
  public FxAmazonSESDialog(Window paramWindow) {
    super(paramWindow);
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("sesRegionLabel"), "0,0,r,c")
      .a((Node)this.a, "1,0,l,c")
      .a((Node)this.rx.e("sesEndpointLabel"), "0,1,r,c")
      .a((Node)this.b, "1,1,l,c")
      .a((Node)this.rx.e("awsAccessKeyLabel"), "0,2,r,c")
      .a((Node)this.c, "1,2,l,c")
      .a((Node)this.rx.e("awsSecretKeyLabel"), "0,3,r,c")
      .a((Node)this.d, "1,3,l,c")
      .a((Node)this.rx.e("subjectLabel"), "0,4,r,c")
      .a((Node)this.e, "1,4,l,c")
      .a((Node)this.rx.e("senderEmailLabel"), "0,5,r,c")
      .a((Node)this.f, "1,5,l,c")
      .a((Node)this.rx.e("bodyURLLabel"), "0,6,r,c")
      .a((Node)this.i, "1,6,l,c")
      .a((Node)this.rx.e("recipientsLabel"), "0,7,r,c")
      .a((Node)this.l, "1,7,l,c")
      .a((Node)this.rx.e("emailsPerBatchLabel"), "0,8,r,c")
      .a((Node)this.j, "1,8,l,c")
      .a((Node)this.rx.e("batchWaitLabel"), "0,9,r,c")
      .a((Node)this.k, "1,9,l,c")
      .a((Node)this.m, "0,10,1,10,l,c");
  }
  
  public void createButtons() {
    createActionButton("send");
    createCloseButton();
  }
  
  public boolean apply() {
    return (!StringUtil.isEmpty(this.a.getText()) && 
      !StringUtil.isEmpty(this.b.getText()) && 
      !StringUtil.isEmpty(this.d.getText()) && 
      !StringUtil.isEmpty(this.c.getText()) && 
      !StringUtil.isEmpty(this.e.getText()) && 
      !StringUtil.isEmpty(this.f.getText()) && 
      !StringUtil.isEmpty(this.i.getText()) && 
      !StringUtil.isEmpty(this.l.getValue()) && 
      !StringUtil.isEmpty(this.j.getText()) && 
      !StringUtil.isEmpty(this.k.getText()));
  }
  
  @Action
  public Task send() {
    c c = new c(this);
    c.messageProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.m.appendText("\n" + paramString2));
    return c;
  }
  
  private static String a(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    URL uRL = new URL(paramString);
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setConnectTimeout(5000);
    httpURLConnection.setReadTimeout(5000);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
    try {
      String str;
      while ((str = bufferedReader.readLine()) != null)
        stringBuilder.append(str); 
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return stringBuilder.toString();
  }
  
  private static List b(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
    try {
      String str;
      while ((str = bufferedReader.readLine()) != null) {
        String str1 = str.trim();
        if (!str1.isEmpty())
          arrayList.add(str1); 
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
    return arrayList;
  }
  
  private void a(AmazonSimpleEmailService paramAmazonSimpleEmailService, String paramString1, String paramString2, String paramString3) {
    Destination destination = new Destination();
    destination.withToAddresses(new String[] { paramString1 });
    SendEmailRequest sendEmailRequest = (new SendEmailRequest()).withSource(this.f.getText()).withDestination(destination).withMessage((new Message())
        .withSubject((new Content()).withData(this.e.getText()))
        .withBody((new Body())
          .withHtml((new Content()).withData(paramString2))
          .withText((new Content()).withData(paramString3))));
    paramAmazonSimpleEmailService.sendEmail(sendEmailRequest);
  }
}
