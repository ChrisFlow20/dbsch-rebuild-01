package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class UploadCommand extends Command {
  private static final Pattern b = Pattern.compile("(-d)?\\s*(\\S+)\\s*(\\S+)", 2);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (paramString2 == null)
      throw new ParameterException(); 
    paramAbstractConsole.t();
    try {
      Matcher matcher;
      if ((matcher = b.matcher(paramString2)).matches()) {
        boolean bool = (matcher.group(1) != null) ? true : false;
        String str = matcher.group(2);
        String[] arrayOfString = matcher.group(3).split(" ");
      } 
    } catch (Throwable throwable) {
      Log.a("Error in upload command", throwable);
      throwable.printStackTrace();
      throw throwable;
    } 
  }
}
