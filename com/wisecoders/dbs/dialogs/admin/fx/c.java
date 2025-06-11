package com.wisecoders.dbs.dialogs.admin.fx;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.wisecoders.dbs.sys.StackTraceHelper;
import java.util.List;
import javafx.concurrent.Task;

class c extends Task {
  private c(FxAmazonSESDialog paramFxAmazonSESDialog) {}
  
  protected String a() {
    int i = Integer.parseInt(this.a.j.getText());
    int j = Integer.parseInt(this.a.k.getText());
    String str1 = this.a.i.getText();
    String str2 = FxAmazonSESDialog.a(str1);
    String str3 = str1.replace(".html", ".txt");
    String str4 = FxAmazonSESDialog.a(str3);
    AmazonSimpleEmailService amazonSimpleEmailService = (AmazonSimpleEmailService)((AmazonSimpleEmailServiceClientBuilder)((AmazonSimpleEmailServiceClientBuilder)AmazonSimpleEmailServiceClient.builder().withCredentials((AWSCredentialsProvider)new AWSStaticCredentialsProvider((AWSCredentials)new BasicAWSCredentials(this.a.c.getText(), this.a.d.getText())))).withRegion(this.a.a.getText())).build();
    List list = FxAmazonSESDialog.b((String)this.a.l.getValue());
    byte b = 0;
    for (String str : list) {
      this.a.a(amazonSimpleEmailService, str, str2, str4);
      b++;
      if (b % i == 0) {
        updateMessage("Sent " + b + 1 + " emails...");
        try {
          Thread.sleep(j * 1010L);
        } catch (InterruptedException interruptedException) {}
      } 
    } 
    return "Done";
  }
  
  protected void succeeded() {
    this.a.m.appendText("\n" + (String)getValue());
  }
  
  protected void failed() {
    this.a.m.appendText("\n" + getException().getMessage());
    this.a.m.appendText(StackTraceHelper.a(getException()));
    this.a.rx.a(this.a.getDialogScene(), getException());
  }
}
