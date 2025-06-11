package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class QuitCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    paramAbstractConsole.t();
    paramAbstractConsole.c();
    System.exit(paramAbstractConsole.q() ? 0 : 1);
  }
}
