package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.data.model.data.GroovyBindingWithDefaultForAllVariables;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@DoNotObfuscate
public class SimpleStatement {
  protected final Binding a = new GroovyBindingWithDefaultForAllVariables();
  
  protected String b;
  
  private final boolean c;
  
  public SimpleStatement(String paramString) {
    this.b = (paramString != null) ? paramString : "";
    this.c = false;
  }
  
  public SimpleStatement(String paramString, boolean paramBoolean) {
    this.b = (paramString != null) ? paramString : "";
    this.c = paramBoolean;
  }
  
  public boolean isIgnorable() {
    return false;
  }
  
  public int length() {
    return (this.b != null && !this.b.isEmpty()) ? 1 : 0;
  }
  
  public boolean isEmpty() {
    return (StringUtil.isEmptyTrim(this.b) || StringUtil.isEmptyTrim(toString()));
  }
  
  public SimpleStatement set(K paramK, Object paramObject) {
    if (paramK != null && this.b != null)
      this.a.setVariable(String.valueOf(paramK), paramObject); 
    return this;
  }
  
  private static final Map d = new HashMap<>();
  
  public String toString() {
    if (this.c) {
      for (Object object : this.a.getVariables().keySet())
        this.b = this.b.replaceAll("\\$\\{" + String.valueOf(object) + "\\}", Matcher.quoteReplacement(String.valueOf(this.a.getVariables().get(object)))); 
      return this.b;
    } 
    StringWriter stringWriter = new StringWriter();
    if (this.b != null) {
      Template template = (Template)d.get(this.b);
      if (template == null) {
        SimpleTemplateEngine simpleTemplateEngine = Groovy.b.b();
        try {
          template = simpleTemplateEngine.createTemplate(this.b);
          d.put(this.b, template);
        } catch (Throwable throwable) {
          Log.f("Error evaluating '" + this.b + "'. " + throwable.getLocalizedMessage());
          throw new GroovyRuntimeException("Error evaluating: \n---------\n" + this.b + "\n---------\n" + throwable.getLocalizedMessage());
        } 
      } 
      if (template != null)
        try {
          template.make(this.a.getVariables()).writeTo(stringWriter);
        } catch (Throwable throwable) {
          Log.a("Error evaluating '" + this.b + "'.", throwable);
          throw new GroovyRuntimeException("Error evaluating: \n---------\n" + this.b + "\n---------\n" + throwable.getLocalizedMessage(), throwable);
        }  
    } 
    return stringWriter.toString();
  }
}
