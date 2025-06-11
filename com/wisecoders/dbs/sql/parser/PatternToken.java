package com.wisecoders.dbs.sql.parser;

import java.util.ArrayList;
import java.util.List;

public class PatternToken implements Pattern {
  public final PatternPhrase a;
  
  private final List c = new ArrayList();
  
  public final String b;
  
  public PatternToken(PatternPhrase paramPatternPhrase, String paramString) {
    this.a = paramPatternPhrase;
    this.b = paramString;
  }
  
  public boolean g() {
    return !this.c.isEmpty();
  }
  
  public String toString() {
    return this.b;
  }
  
  public Pattern b() {
    int i = this.a.indexOf(this);
    return (i < this.a.size() - 1) ? (Pattern)this.a.get(i + 1) : null;
  }
  
  public String a() {
    return this.b;
  }
  
  public void a(MatcherNode paramMatcherNode) {
    this.c.add(paramMatcherNode);
  }
  
  public void d() {
    this.c.clear();
  }
  
  public void c() {
    for (MatcherNode matcherNode : this.c)
      matcherNode.a(true); 
  }
  
  public boolean e() {
    return ("*".equals(this.b) || "?".equals(this.b) || "~".equals(this.b));
  }
  
  public MatcherNode h() {
    return this.c.isEmpty() ? null : this.c.get(0);
  }
  
  public MatcherCommaPhrase i() {
    MatcherCommaPhrase matcherCommaPhrase = new MatcherCommaPhrase();
    matcherCommaPhrase.addAll(this.c);
    return matcherCommaPhrase;
  }
  
  public String f() {
    StringBuilder stringBuilder = new StringBuilder();
    for (MatcherNode matcherNode : this.c)
      stringBuilder.append(matcherNode); 
    return stringBuilder.toString();
  }
}
