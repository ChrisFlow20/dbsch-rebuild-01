package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class EchoCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    paramAbstractConsole.d("Parameters : " + paramString2, new Object[0]);
    if ("on".equalsIgnoreCase(paramString2)) {
      Sys.B.enableEcho.a(Boolean.valueOf(true));
      paramAbstractConsole.c("Echo On", new Object[0]);
    } else if ("off".equalsIgnoreCase(paramString2)) {
      Sys.B.enableEcho.a(Boolean.valueOf(false));
      paramAbstractConsole.c("Echo Off", new Object[0]);
    } else {
      throw new ParameterException();
    } 
  }
}
