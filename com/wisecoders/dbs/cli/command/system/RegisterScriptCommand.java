package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.Dictionary;
import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class RegisterScriptCommand extends Command {
  public static final Pattern parameterPattern = Pattern.compile("(\\S+)\\s*as\\s*(.*)", 2);
  
  public static final Pattern commentPattern = Pattern.compile("(.*)\\s*comment\\s*(.*)", 2);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    Matcher matcher = parameterPattern.matcher(paramString2);
    if (matcher.matches()) {
      File file = paramAbstractConsole.k().resolve(matcher.group(1)).toFile();
      if (!file.exists())
        throw new FileNotFoundException("File not found " + String.valueOf(file)); 
      RegisterScriptCommand$1 registerScriptCommand$1 = new RegisterScriptCommand$1(this, file);
      String str1 = matcher.group(2).trim();
      String str2 = null;
      Matcher matcher1 = commentPattern.matcher(str1);
      if (matcher1.matches()) {
        str1 = matcher1.group(1).trim();
        str2 = matcher1.group(2).trim();
      } 
      registerScriptCommand$1.setKeyword(str1);
      registerScriptCommand$1.setUsage(str1);
      registerScriptCommand$1.setGroup("Registered scripts. Details using <command> -h");
      registerScriptCommand$1.setShortDescription(str2);
      Dictionary.a(registerScriptCommand$1);
      paramAbstractConsole.c("Ok", new Object[0]);
    } else {
      throw new ParameterException();
    } 
  }
}
