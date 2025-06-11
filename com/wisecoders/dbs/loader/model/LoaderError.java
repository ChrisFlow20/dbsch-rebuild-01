package com.wisecoders.dbs.loader.model;

import com.wisecoders.dbs.sys.EscapeChars;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoaderError {
  public final String a;
  
  public int b;
  
  private final Pattern d;
  
  private final String e;
  
  private boolean f = false;
  
  public ObservableList c = FXCollections.observableArrayList();
  
  public LoaderError(String paramString) {
    this.a = paramString;
    this.d = Pattern.compile(EscapeChars.forRegex(paramString), 8);
    this.e = paramString.replaceAll("\\d+", Matcher.quoteReplacement("x"));
  }
  
  public void a(int paramInt, Map paramMap) {
    this.b++;
    if (this.c.size() < 100)
      this.c.add(new LoaderErrorSample(paramInt, paramMap)); 
  }
  
  public boolean a(String paramString) {
    if (this.a.equals(paramString))
      return true; 
    Matcher matcher = this.d.matcher(paramString);
    if (matcher.matches()) {
      this.f = true;
      return true;
    } 
    return false;
  }
  
  public String toString() {
    return this.f ? this.e : this.a;
  }
}
