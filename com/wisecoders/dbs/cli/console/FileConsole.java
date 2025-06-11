package com.wisecoders.dbs.cli.console;

import com.wisecoders.dbs.cli.command.Dictionary;
import com.wisecoders.dbs.cli.command.base.Command;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileConsole extends AbstractConsole {
  private final BufferedReader a;
  
  public FileConsole(File paramFile) {
    this.a = new BufferedReader(new InputStreamReader(new FileInputStream(paramFile), StandardCharsets.UTF_8));
  }
  
  public String a() {
    boolean bool;
    StringBuilder stringBuilder = new StringBuilder();
    do {
      String str = this.a.readLine();
      if (str == null)
        return !stringBuilder.isEmpty() ? stringBuilder.toString() : null; 
      if (!stringBuilder.isEmpty())
        stringBuilder.append("\n"); 
      stringBuilder.append(str);
      Command command = Dictionary.a(stringBuilder.toString().trim());
      bool = (command != null && command.getType().a(stringBuilder.toString())) ? true : false;
    } while (bool);
    return stringBuilder.toString();
  }
  
  public void b() {}
}
