package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.groovy.GroovyTemplate;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@DoNotObfuscate
public class ShellCommand extends Command {
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    Runtime runtime = Runtime.getRuntime();
    paramString2 = GroovyTemplate.a(paramString2);
    paramAbstractConsole.a(paramString2, new Object[0]);
    Process process = runtime.exec(paramString2);
    process.waitFor();
    int i = process.exitValue();
    if (i == 0) {
      a(paramAbstractConsole, process.getInputStream());
      paramAbstractConsole.a("Done", new Object[0]);
    } else {
      paramAbstractConsole.a("Got exit code 0.", new Object[0]);
      a(paramAbstractConsole, process.getInputStream());
      a(paramAbstractConsole, process.getErrorStream());
      paramAbstractConsole.a(false);
    } 
  }
  
  private void a(AbstractConsole paramAbstractConsole, InputStream paramInputStream) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    String str;
    while ((str = bufferedReader.readLine()) != null)
      paramAbstractConsole.a(str, new Object[0]); 
    paramInputStream.close();
  }
}
