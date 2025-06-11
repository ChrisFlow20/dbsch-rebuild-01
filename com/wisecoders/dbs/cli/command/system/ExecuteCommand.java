package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.command.groovy.GroovyCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.cli.console.FileConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@DoNotObfuscate
public class ExecuteCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (paramString2.isEmpty())
      throw new ParameterException(); 
    File file1 = Paths.get(paramString2, new String[0]).toFile();
    File file2 = file1.isAbsolute() ? file1 : paramAbstractConsole.k().resolve(file1.getPath()).toFile();
    executeScript(paramAbstractConsole, file2, null);
  }
  
  public void executeScript(AbstractConsole paramAbstractConsole, File paramFile, String paramString) {
    Path path = paramAbstractConsole.k();
    paramAbstractConsole.a(paramFile.toPath().resolve("."));
    if (paramFile.getName().endsWith("groovy")) {
      if (GroovyCommand.executeGroovyScript(paramAbstractConsole, paramFile, paramString))
        return; 
      paramAbstractConsole.c("Ok", new Object[0]);
    } else {
      FileConsole fileConsole = new FileConsole(paramFile);
      fileConsole.d();
    } 
    paramAbstractConsole.a(path);
  }
}
