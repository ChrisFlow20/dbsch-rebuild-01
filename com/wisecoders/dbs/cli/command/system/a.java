package com.wisecoders.dbs.cli.command.system;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;

class a implements UIKeyboardInteractive, UserInfo {
  final AbstractConsole a;
  
  final CommandLine b;
  
  a(AbstractConsole paramAbstractConsole, CommandLine paramCommandLine) {
    this.a = paramAbstractConsole;
    this.b = paramCommandLine;
  }
  
  public String getPassword() {
    return this.b.getOptionValue("p");
  }
  
  public boolean promptYesNo(String paramString) {
    return false;
  }
  
  public String getPassphrase() {
    return this.b.getOptionValue("kp");
  }
  
  public boolean promptPassphrase(String paramString) {
    return false;
  }
  
  public boolean promptPassword(String paramString) {
    return false;
  }
  
  public void showMessage(String paramString) {}
  
  public String[] promptKeyboardInteractive(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, boolean[] paramArrayOfboolean) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramString1 != null)
      stringBuilder.append("Destination: ").append(paramString1).append("\n"); 
    if (paramString2 != null)
      stringBuilder.append("Name: ").append(paramString2).append("\n"); 
    if (paramString3 != null)
      stringBuilder.append("Instruction: ").append(paramString3).append("\n"); 
    String[] arrayOfString = new String[paramArrayOfString.length];
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      try {
        arrayOfString[b] = this.a.a();
      } catch (IOException iOException) {
        this.a.d(iOException.toString(), new Object[0]);
      } 
    } 
    return arrayOfString;
  }
}
