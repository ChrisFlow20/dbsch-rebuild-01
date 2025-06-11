package com.wisecoders.dbs.sql.parser;

import java.util.ArrayList;

public class PatternPhrase extends ArrayList implements Pattern {
  public PatternOptionalPhrases a;
  
  public boolean b = false;
  
  public PatternPhrase(PatternOptionalPhrases paramPatternOptionalPhrases) {
    this.a = paramPatternOptionalPhrases;
  }
  
  public PatternToken a(String paramString) {
    PatternToken patternToken = new PatternToken(this, paramString);
    add((E)patternToken);
    return patternToken;
  }
  
  public PatternOptionalPhrases g() {
    PatternOptionalPhrases patternOptionalPhrases = new PatternOptionalPhrases(this);
    add((E)patternOptionalPhrases);
    return patternOptionalPhrases;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Pattern pattern : this) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(" "); 
      stringBuilder.append(pattern);
    } 
    return stringBuilder.toString();
  }
  
  public void c() {
    this.b = true;
    for (Pattern pattern : this)
      pattern.c(); 
  }
  
  public void d() {
    this.b = false;
    for (Pattern pattern : this)
      pattern.d(); 
  }
  
  public Pattern a(int paramInt) {
    int i = 0;
    for (Pattern pattern : this) {
      if (pattern instanceof PatternOptionalPhrases) {
        PatternOptionalPhrases patternOptionalPhrases = (PatternOptionalPhrases)pattern;
        if (i == paramInt)
          return patternOptionalPhrases; 
        i++;
        continue;
      } 
      PatternToken patternToken = (PatternToken)pattern;
      if (patternToken.e()) {
        if (i == paramInt)
          return patternToken; 
        i++;
      } 
    } 
    return null;
  }
  
  public String b(int paramInt) {
    Pattern pattern = a(paramInt);
    return (pattern != null) ? pattern.f() : null;
  }
  
  public PatternOptionalPhrases c(int paramInt) {
    Pattern pattern = a(paramInt);
    return (pattern instanceof PatternOptionalPhrases) ? (PatternOptionalPhrases)pattern : null;
  }
  
  public MatcherCommaPhrase d(int paramInt) {
    Pattern pattern = a(paramInt);
    return (pattern instanceof PatternToken) ? ((PatternToken)pattern).i() : null;
  }
  
  public MatcherStatement e(int paramInt) {
    Pattern pattern = a(paramInt);
    if (pattern instanceof PatternToken) {
      PatternToken patternToken = (PatternToken)pattern;
      MatcherNode matcherNode = patternToken.h();
      return (matcherNode instanceof MatcherStatement) ? (MatcherStatement)matcherNode : null;
    } 
    return null;
  }
  
  public String f(int paramInt) {
    Pattern pattern = a(paramInt);
    if (pattern instanceof PatternToken)
      return DDLParserUtil.a(pattern.f()); 
    return null;
  }
  
  public String g(int paramInt) {
    Pattern pattern = a(paramInt);
    if (pattern instanceof PatternToken)
      return DDLParserUtil.b(pattern.f()); 
    return null;
  }
  
  public int h() {
    byte b = 0;
    for (Pattern pattern : this) {
      if (pattern.e())
        b++; 
    } 
    return b;
  }
  
  public String a() {
    return "";
  }
  
  public Pattern b() {
    int i = this.a.indexOf(this);
    return (i < this.a.size() - 1) ? (Pattern)this.a.get(i + 1) : null;
  }
  
  public void a(MatcherNode paramMatcherNode) {}
  
  public boolean e() {
    return false;
  }
  
  public String f() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Pattern pattern : this) {
      if (this.b) {
        if (pattern.e()) {
          String str = pattern.f();
          if (str != null)
            stringBuilder.append(str); 
          continue;
        } 
        stringBuilder.append(pattern.a());
      } 
    } 
    return stringBuilder.toString();
  }
}
