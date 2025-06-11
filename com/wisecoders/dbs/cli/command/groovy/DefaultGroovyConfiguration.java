package com.wisecoders.dbs.cli.command.groovy;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class DefaultGroovyConfiguration extends CompilerConfiguration {
  public DefaultGroovyConfiguration() {
    a();
    setVerbose(true);
  }
  
  private void a() {
    ImportCustomizer importCustomizer = new ImportCustomizer();
    addCompilationCustomizers(new CompilationCustomizer[] { (CompilationCustomizer)importCustomizer });
  }
}
