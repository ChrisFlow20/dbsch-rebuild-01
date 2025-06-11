package com.wisecoders.dbs.generator.engine.plan;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.EscapeChars;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlatTextWarn extends Warn {
  private final Pattern a;
  
  private final String e;
  
  private boolean f = false;
  
  public FlatTextWarn(Table paramTable, Column paramColumn, String paramString) {
    super(paramTable, paramColumn, paramString);
    this.a = Pattern.compile(EscapeChars.forRegex(paramString), 8);
    this.e = paramString.replaceAll("\\d+", Matcher.quoteReplacement("x"));
  }
  
  public boolean a(String paramString) {
    if (this.b.equals(paramString)) {
      a();
      return true;
    } 
    Matcher matcher = this.a.matcher(paramString);
    if (matcher.matches()) {
      a();
      this.f = true;
      return true;
    } 
    return false;
  }
  
  public String toString() {
    return this.f ? this.e : this.b;
  }
}
