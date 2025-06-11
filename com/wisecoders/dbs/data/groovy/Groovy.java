package com.wisecoders.dbs.data.groovy;

import com.wisecoders.dbs.data.model.data.GroovyScriptBaseClass;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.text.SimpleTemplateEngine;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

public class Groovy {
  public static Groovy a = new Groovy();
  
  public static Groovy b = new Groovy$1();
  
  private final CompilerConfiguration d = new CompilerConfiguration();
  
  public GroovyShell c;
  
  public Groovy() {
    this.d.setScriptBaseClass(GroovyScriptBaseClass.class.getName());
    this.d.addCompilationCustomizers(new CompilationCustomizer[] { (CompilationCustomizer)new GroovyImportCustomizer() });
    this.d.setVerbose(true);
  }
  
  private void c() {
    if (this.c == null) {
      this.c = new GroovyShell(this.d);
      a();
    } 
  }
  
  public void a() {}
  
  public Object a(Binding paramBinding, String paramString) {
    c();
    Script script = this.c.parse(paramString);
    script.setBinding(paramBinding);
    return script.run();
  }
  
  public Script b(Binding paramBinding, String paramString) {
    c();
    return this.c.parse(paramString, paramBinding);
  }
  
  public Script a(Binding paramBinding, String paramString1, String paramString2) {
    c();
    return this.c.parse(paramString1, paramString2, paramBinding);
  }
  
  public SimpleTemplateEngine b() {
    c();
    return new SimpleTemplateEngine(this.c);
  }
}
