package com.wisecoders.dbs.cli.command.groovy;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.io.File;
import java.sql.SQLException;
import org.codehaus.groovy.control.CompilerConfiguration;

@DoNotObfuscate
public class GroovyCommand extends Command {
  private Connector b;
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    this.a = false;
    CompilerConfiguration compilerConfiguration = a.a(new File[] { paramAbstractConsole.k().toFile() });
    Binding binding = a.a(paramAbstractConsole, null);
    GroovyShell groovyShell = new GroovyShell(binding, compilerConfiguration);
    if (CLIConnectorManager.a().isEmpty()) {
      groovyShell.evaluate(paramString2);
    } else {
      for (Connector connector : CLIConnectorManager.a()) {
        if (this.a)
          return; 
        this.b = connector;
        paramAbstractConsole.a(connector);
        Envoy envoy = connector.startEnvoy("DbSchemaCLI");
        try {
          binding.setVariable("sql", envoy.d());
          binding.setVariable("connector", connector);
          groovyShell.evaluate(paramString2);
          if (!envoy.o())
            envoy.p(); 
          if (envoy != null)
            envoy.close(); 
        } catch (Throwable throwable) {
          if (envoy != null)
            try {
              envoy.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } 
    } 
    this.b = null;
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
  
  public static boolean executeGroovyScript(AbstractConsole paramAbstractConsole, File paramFile, String paramString) {
    String str = FileUtils.a(paramFile);
    CompilerConfiguration compilerConfiguration = a.a(new File[] { paramAbstractConsole.k().toFile().getParentFile(), paramFile.getParentFile() });
    Binding binding = a.a(paramAbstractConsole, paramString);
    GroovyShell groovyShell = new GroovyShell(binding, compilerConfiguration);
    if (paramFile.getName().contains(".global")) {
      byte b = 0;
      Envoy[] arrayOfEnvoy = new Envoy[CLIConnectorManager.a().size()];
      for (Connector connector : CLIConnectorManager.a())
        arrayOfEnvoy[b++] = connector.startEnvoy("DbSchemaCLI"); 
      binding.setVariable("sqls", arrayOfEnvoy);
      try {
        groovyShell.evaluate(str);
      } finally {
        for (Envoy envoy : arrayOfEnvoy) {
          try {
            envoy.p();
          } catch (SQLException sQLException) {
            Log.a("Error in Execute Command", sQLException);
            System.out.println(sQLException);
          } 
        } 
      } 
    } else {
      for (Connector connector : CLIConnectorManager.a()) {
        Envoy envoy = connector.startEnvoy("DbSchemaCLI");
        paramAbstractConsole.a(envoy);
        binding.setVariable("sql", envoy);
        binding.setVariable("connector", connector);
        try {
          Object object = groovyShell.evaluate(str);
          if ("quit".equals(object))
            return true; 
        } finally {
          envoy.p();
        } 
      } 
    } 
    return false;
  }
}
