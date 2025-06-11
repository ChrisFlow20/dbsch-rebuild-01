package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ShowVariablesCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    paramAbstractConsole.a("SET VARIABLES", new Object[0]);
    paramAbstractConsole.a("=========", new Object[0]);
  }
}
