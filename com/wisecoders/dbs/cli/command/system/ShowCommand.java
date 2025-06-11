package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.model.Property;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ShowCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    for (Property property : Sys.B.cli.a) {
      paramAbstractConsole.a(String.format("%-40s  %s", new Object[] { property.m() + "=" + property.m(), (property.n() != null) ? property.n() : "" }), new Object[0]);
    } 
    paramAbstractConsole.c("Ok", new Object[0]);
  }
}
