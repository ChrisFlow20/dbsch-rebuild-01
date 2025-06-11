package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.Dictionary;
import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.ArrayList;
import java.util.Comparator;

@DoNotObfuscate
public class HelpCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    ArrayList<String> arrayList = new ArrayList();
    for (Command command : Dictionary.a) {
      if (command.getGroup() != null && !arrayList.contains(command.getGroup()))
        arrayList.add(command.getGroup()); 
    } 
    arrayList.sort(String::compareToIgnoreCase);
    paramAbstractConsole.a("Define connections in '" + String.valueOf(Sys.t) + "' file.:\nSample script:\n  connect prod1,prod2;\n  show tables;\n  spool /tmp/result.csv\n  select * from mytable;\n  -- Upload result on ftp server\n  upload ftp:myserver /tmp/result.csv;\n  -- Transfer data\n  transfer mytable from db1 using select * from src_table;\n", new Object[0]);
    for (String str : arrayList) {
      ArrayList<Command> arrayList1 = new ArrayList();
      for (Command command : Dictionary.a) {
        if (command.getUsage() != null && str.equals(command.getGroup()))
          arrayList1.add(command); 
      } 
      arrayList1.sort(Comparator.comparingInt(Command::getOrder));
      paramAbstractConsole.a(str + " Commands", new Object[0]);
      for (Command command : arrayList1) {
        paramAbstractConsole.a(String.format("  %-30s %s %s", new Object[] { command.getUsage(), command.getShortDescription(), (command.getLongDescription() != null) ? command.getLongDescription() : "" }), new Object[0]);
      } 
      paramAbstractConsole.l();
    } 
    paramAbstractConsole.a("All other commands will be executed in the database.", new Object[0]);
  }
}
