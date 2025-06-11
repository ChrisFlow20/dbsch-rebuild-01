package com.wisecoders.dbs.dialogs.admin.fx;

import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Fx2CheckoutDialog extends Dialog$ {
  private final TextField a = new TextField();
  
  private final TextField b = new TextField();
  
  private final TextArea c = new TextArea();
  
  private final Rx d = new Rx(Fx2CheckoutDialog.class, this);
  
  public Fx2CheckoutDialog(Window paramWindow) {
    super(paramWindow);
    this.a.setText(Pref.d("merchant", (String)null));
    this.b.setText(Pref.d("secret", (String)null));
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)this.d.e("merchantLabel"), "0,0,r,c");
    gridPane$.a((Node)this.a, "1,0,f,c");
    gridPane$.a((Node)this.d.e("secretLabel"), "0,1,r,c");
    gridPane$.a((Node)this.b, "1,1,f,c");
    gridPane$.a((Node)this.c, "0,2,1,2,f,f");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createActionButton("send");
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public Task send() {
    return new a(this);
  }
  
  public static String a(String paramString1, String paramString2, String paramString3) {
    String str = null;
    try {
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString2.getBytes(StandardCharsets.UTF_8), paramString3);
      Mac mac = Mac.getInstance(paramString3);
      mac.init(secretKeySpec);
      byte[] arrayOfByte = mac.doFinal(paramString1.getBytes(StandardCharsets.US_ASCII));
      StringBuilder stringBuilder = new StringBuilder();
      for (byte b : arrayOfByte) {
        String str1 = Integer.toHexString(0xFF & b);
        if (str1.length() == 1)
          stringBuilder.append('0'); 
        stringBuilder.append(str1);
      } 
      str = stringBuilder.toString();
    } catch (InvalidKeyException|java.security.NoSuchAlgorithmException invalidKeyException) {
      System.out.println(invalidKeyException);
    } 
    return str;
  }
}
