package com.wisecoders.dbs.cli.command.groovy;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.sys.Log;
import groovy.lang.GroovyRuntimeException;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import java.io.StringWriter;

public class GroovyTemplate {
  public static String a(String paramString) {
    DefaultGroovyBindingWithDefaultForAllVariables defaultGroovyBindingWithDefaultForAllVariables = new DefaultGroovyBindingWithDefaultForAllVariables(Sys.B.cli.f());
    SimpleTemplateEngine simpleTemplateEngine = Groovy.a.b();
    try {
      Template template = simpleTemplateEngine.createTemplate(paramString);
      StringWriter stringWriter = new StringWriter();
      template.make(defaultGroovyBindingWithDefaultForAllVariables.getVariables()).writeTo(stringWriter);
      return stringWriter.toString();
    } catch (RuntimeException runtimeException) {
      throw runtimeException;
    } catch (Throwable throwable) {
      Log.a("Error evaluating Groovy expression '" + paramString + "'.", throwable);
      throw new GroovyRuntimeException("Error evaluating Groovy expression '" + paramString + "' " + throwable.getLocalizedMessage());
    } 
  }
}
