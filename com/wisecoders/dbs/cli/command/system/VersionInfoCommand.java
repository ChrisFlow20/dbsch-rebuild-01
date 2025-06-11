package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;

@DoNotObfuscate
public class VersionInfoCommand extends Command {
  public static final int buildNumber;
  
  static {
    int i = -1;
    try {
      String str = VersionInfoCommand.class.getPackage().getImplementationVersion();
      if (str != null && str.length() > 0)
        i = Integer.parseInt(str); 
    } catch (Throwable throwable) {
      Log.a("Error in version command", throwable);
      System.out.println(throwable);
    } 
    buildNumber = i;
  }
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    String str1 = "{build}. {java.version} {java.vendor} {java.home} \n https://www.dbschema.com<br>(c) Copyright Wise Coders Solutions. All rights reserved.";
    String str2 = "DbSchemaCLI version " + VersionInfoCommand.class.getPackage().getSpecificationVersion() + " #" + buildNumber;
    str1 = str1.replace("{build}", str2);
    str1 = str1.replace("{java.version}", System.getProperty("java.version"));
    str1 = str1.replace("{java.vendor}", System.getProperty("java.vendor"));
    str1 = str1.replace("{java.home}", System.getProperty("java.home"));
    System.out.println(str1);
  }
}
