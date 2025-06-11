package com.wisecoders.dbs.sql.indenter;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ScriptComparator {
  private final Map a = new HashMap<>();
  
  public ScriptComparator a(Pattern paramPattern, String paramString) {
    this.a.put(paramPattern, paramString);
    return this;
  }
  
  public void a() {
    this.a.clear();
  }
  
  public boolean a(String paramString1, String paramString2) {
    if (StringUtil.stringsAreEqualIgnoreCasesAndSpacesAndEndDelimiter(paramString1, paramString2))
      return false; 
    if (paramString1 != null && paramString2 != null) {
      for (Pattern pattern : this.a.keySet()) {
        paramString1 = pattern.matcher(paramString1).replaceAll((String)this.a.get(pattern));
        if (StringUtil.stringsAreEqualIgnoreCasesAndSpacesAndEndDelimiter(paramString1, paramString2))
          return false; 
      } 
      for (Pattern pattern : this.a.keySet()) {
        paramString2 = pattern.matcher(paramString2).replaceAll((String)this.a.get(pattern));
        if (StringUtil.stringsAreEqualIgnoreCasesAndSpacesAndEndDelimiter(paramString1, paramString2))
          return false; 
      } 
    } 
    return true;
  }
}
