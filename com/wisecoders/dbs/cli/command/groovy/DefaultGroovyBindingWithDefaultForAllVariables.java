package com.wisecoders.dbs.cli.command.groovy;

import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DefaultGroovyBindingWithDefaultForAllVariables extends Binding {
  public DefaultGroovyBindingWithDefaultForAllVariables(Map paramMap) {
    super(paramMap);
    setVariable("date", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
  }
  
  public Object getVariable(String paramString) {
    try {
      return super.getVariable(paramString);
    } catch (MissingPropertyException missingPropertyException) {
      return null;
    } 
  }
  
  public boolean hasVariable(String paramString) {
    return true;
  }
}
