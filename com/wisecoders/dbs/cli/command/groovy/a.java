package com.wisecoders.dbs.cli.command.groovy;

import com.wisecoders.dbs.cli.console.AbstractConsole;
import groovy.lang.Binding;
import java.io.File;
import java.util.List;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

class a {
  static CompilerConfiguration a(File... paramVarArgs) {
    CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
    compilerConfiguration.setScriptBaseClass(GroovyBaseClass.class.getName());
    List<String> list = compilerConfiguration.getClasspath();
    for (File file : paramVarArgs)
      list.add(file.getPath()); 
    compilerConfiguration.setClasspathList(list);
    ImportCustomizer importCustomizer = new ImportCustomizer();
    compilerConfiguration.addCompilationCustomizers(new CompilationCustomizer[] { (CompilationCustomizer)importCustomizer });
    return compilerConfiguration;
  }
  
  static Binding a(AbstractConsole paramAbstractConsole, String paramString) {
    Binding binding = new Binding();
    binding.setVariable("dir", paramAbstractConsole.k());
    binding.setVariable("out", paramAbstractConsole.i());
    if (paramString != null)
      binding.setVariable("args", paramString.split(" ")); 
    binding.setVariable("sys", new GroovySys());
    return binding;
  }
}
