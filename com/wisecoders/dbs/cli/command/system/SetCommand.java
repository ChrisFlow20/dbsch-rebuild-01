package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class SetCommand extends Command {
  public static final Pattern parameterPattern = Pattern.compile("([^=\\s]+)\\s*=\\s*(.*)", 42);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    Matcher matcher = parameterPattern.matcher(paramString2);
    if (matcher.matches()) {
      if (Sys.B.root.a(matcher.group(1), matcher.group(2))) {
        paramAbstractConsole.c("Property Set", new Object[0]);
      } else {
        paramAbstractConsole.c("Property not found", new Object[0]);
      } 
    } else {
      throw new ParameterException();
    } 
  }
}
