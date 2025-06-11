package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class DownloadCommand extends Command {
  private static final Pattern b = Pattern.compile("(\\S+)\\s*(\\S+)", 2);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (paramString2 == null)
      throw new ParameterException(); 
    paramAbstractConsole.t();
    Matcher matcher;
    if ((matcher = b.matcher(paramString2)).matches()) {
      String[] arrayOfString = matcher.group(2).split(" ");
    } else {
      throw new ParameterException("Wrong parameters");
    } 
  }
}
