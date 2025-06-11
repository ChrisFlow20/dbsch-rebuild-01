package com.wisecoders.dbs.cli.command.python;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class PythonCommand extends Command {
  private Connector b;
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    this.a = false;
    paramAbstractConsole.c("Ok", new Object[0]);
  }
  
  public CommandType getType() {
    return CommandType.a;
  }
  
  public void interrupt() {
    super.interrupt();
    if (this.b != null) {
      this.b.closeAllEnvoysAndSsh();
      this.b = null;
    } 
  }
}
