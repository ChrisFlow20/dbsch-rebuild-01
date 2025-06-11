package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class SleepCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    paramAbstractConsole.a("Going to sleep forever...", new Object[0]);
    try {
      Thread.sleep(Long.MAX_VALUE);
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    } 
  }
}
