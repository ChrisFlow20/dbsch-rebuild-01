package com.wisecoders.dbs.data.model.data;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import groovy.lang.Binding;
import java.util.Map;

@DoNotObfuscate
public class GroovyBindingWithDefaultForAllVariables extends Binding {
  public GroovyBindingWithDefaultForAllVariables() {}
  
  public GroovyBindingWithDefaultForAllVariables(Map paramMap) {
    setVariables(paramMap);
  }
  
  public void setVariables(Map paramMap) {
    getVariables().clear();
    getVariables().putAll(paramMap);
  }
  
  public void clear() {
    getVariables().clear();
  }
  
  public Object getVariable(String paramString) {
    if (!getVariables().containsKey(paramString))
      return null; 
    return getVariables().get(paramString);
  }
  
  public boolean hasVariable(String paramString) {
    return true;
  }
}
