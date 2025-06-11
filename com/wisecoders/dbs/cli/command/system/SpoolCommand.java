package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.command.groovy.GroovyTemplate;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;

@DoNotObfuscate
public class SpoolCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    String str = (paramString2.length() > 0) ? paramString2 : null;
    if (str == null)
      throw new ParameterException(); 
    str = StringUtil.removeQuotes(str);
    str = GroovyTemplate.a(str);
    if ("off".equalsIgnoreCase(str)) {
      paramAbstractConsole.b((String)null);
      paramAbstractConsole.c("Ok", new Object[0]);
    } else if (paramAbstractConsole.b(str)) {
      paramAbstractConsole.b("Ok", new Object[0]);
    } else {
      paramAbstractConsole.a("Cannot create file '%s'", new Object[] { str });
    } 
  }
}
