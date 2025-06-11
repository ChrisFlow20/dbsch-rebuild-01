package com.wisecoders.dbs.dialogs.git.credentials;

import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.Arrays;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class XUsernamePasswordCredentialsProvider extends CredentialsProvider {
  private String a;
  
  private char[] b;
  
  private boolean c;
  
  public XUsernamePasswordCredentialsProvider(String paramString1, String paramString2) {
    this(paramString1, paramString2.toCharArray());
  }
  
  public XUsernamePasswordCredentialsProvider(String paramString, char[] paramArrayOfchar) {
    this.a = paramString;
    this.b = paramArrayOfchar;
  }
  
  public boolean isInteractive() {
    return false;
  }
  
  public boolean supports(CredentialItem... paramVarArgs) {
    for (CredentialItem credentialItem : paramVarArgs) {
      if (!(credentialItem instanceof CredentialItem.InformationalMessage))
        if (!(credentialItem instanceof CredentialItem.Username))
          if (!(credentialItem instanceof CredentialItem.Password))
            if (!(credentialItem instanceof CredentialItem.StringType) || 
              !credentialItem.getPromptText().equals("Password: "))
              return false;    
    } 
    return true;
  }
  
  public boolean get(URIish paramURIish, CredentialItem... paramVarArgs) {
    for (CredentialItem credentialItem : paramVarArgs) {
      if (credentialItem instanceof CredentialItem.InformationalMessage) {
        FxUtil.a(() -> (new Alert$(Alert.AlertType.INFORMATION)).b(paramCredentialItem.getPromptText()).showAndWait());
      } else if (credentialItem instanceof CredentialItem.Username) {
        ((CredentialItem.Username)credentialItem).setValue(this.a);
      } else if (credentialItem instanceof CredentialItem.Password) {
        ((CredentialItem.Password)credentialItem).setValue(this.b);
      } else if (credentialItem instanceof CredentialItem.StringType) {
        if (credentialItem.getPromptText().equals("Password: ")) {
          ((CredentialItem.StringType)credentialItem).setValue(new String(this.b));
        } else {
          FxUtil.a(() -> {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setContentText(paramCredentialItem.getPromptText());
                textInputDialog.showAndWait().ifPresent(());
              });
        } 
      } else {
        if (credentialItem instanceof CredentialItem.YesNoType) {
          FxUtil.a(() -> {
                Optional<ButtonType> optional = (new Alert$(Alert.AlertType.CONFIRMATION)).b(paramCredentialItem.getPromptText()).showAndWait();
                this.c = (optional.isPresent() && optional.get() == ButtonType.OK);
              });
          return this.c;
        } 
        throw new UnsupportedCredentialItem(paramURIish, credentialItem.getClass().getName() + ":" + credentialItem.getClass().getName());
      } 
    } 
    return true;
  }
  
  public void a() {
    this.a = null;
    if (this.b != null) {
      Arrays.fill(this.b, false);
      this.b = null;
    } 
  }
}
