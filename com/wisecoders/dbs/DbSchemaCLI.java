package com.wisecoders.dbs;

import com.wisecoders.dbs.cli.command.groovy.GroovyCommand;
import com.wisecoders.dbs.cli.console.Console;
import com.wisecoders.dbs.cli.console.FileConsole;
import com.wisecoders.dbs.cli.cron.Scheduler;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.nio.file.Paths;
import java.util.Arrays;

@DoNotObfuscate
public class DbSchemaCLI {
  public static void main(String[] paramArrayOfString) {
    try {
      Console console = new Console();
      console.g();
      System.out.println("DbSchemaCLI #" + Log.c);
      if (paramArrayOfString.length == 1 && "-cron".equalsIgnoreCase(paramArrayOfString[0])) {
        new Scheduler(console);
      } else if (paramArrayOfString.length > 0) {
        String str = paramArrayOfString[0];
        if (str.endsWith(".groovy")) {
          System.out.println("Processing Groovy file " + str);
          GroovyCommand.executeGroovyScript(console, Paths.get(str, new String[0]).toFile(), String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])paramArrayOfString, 1, paramArrayOfString.length)));
        } else {
          System.out.println("Processing file " + str);
          FileConsole fileConsole = new FileConsole(Paths.get(paramArrayOfString[0], new String[0]).toFile());
          fileConsole.d();
        } 
      } else {
        console.d();
      } 
    } catch (Throwable throwable) {
      Log.a("Error starting DbSchemaCLI", throwable);
      System.out.println(throwable);
    } 
  }
}
