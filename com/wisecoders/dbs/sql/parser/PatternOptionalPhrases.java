package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.editors.text.Token;
import com.wisecoders.dbs.editors.text.TokenType;
import com.wisecoders.dbs.editors.text.lexers.MatcherLexer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class PatternOptionalPhrases extends ArrayList implements Pattern {
  public PatternPhrase a;
  
  public PatternOptionalPhrases(PatternPhrase paramPatternPhrase) {
    this.a = paramPatternPhrase;
  }
  
  public PatternOptionalPhrases(String paramString) {
    this.a = null;
    StringReader stringReader = new StringReader(paramString);
    try {
      MatcherLexer matcherLexer = new MatcherLexer(stringReader);
      PatternOptionalPhrases patternOptionalPhrases = this;
      Token token;
      while ((token = matcherLexer.b()) != null && patternOptionalPhrases != null) {
        String str = paramString.substring(token.b, token.b + token.c);
        if (token.a == TokenType.a) {
          switch (str) {
            case "[":
              patternOptionalPhrases = patternOptionalPhrases.h().g();
              continue;
            case "]":
              patternOptionalPhrases = patternOptionalPhrases.a.a;
              continue;
            case "|":
              patternOptionalPhrases.g();
              continue;
          } 
          patternOptionalPhrases.h().a(str);
          continue;
        } 
        patternOptionalPhrases.h().a(str);
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void g() {
    PatternPhrase patternPhrase = new PatternPhrase(this);
    add((E)patternPhrase);
  }
  
  public PatternPhrase h() {
    if (isEmpty())
      g(); 
    return (PatternPhrase)get(size() - 1);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (PatternPhrase patternPhrase : this) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append("| "); 
      stringBuilder.append(patternPhrase);
    } 
    return ((this.a != null) ? "[" : "") + ((this.a != null) ? "[" : "") + String.valueOf(stringBuilder);
  }
  
  public Pattern i() {
    return (Pattern)h().get(0);
  }
  
  public String a() {
    return "[]";
  }
  
  public Pattern b() {
    int i = this.a.indexOf(this);
    return (i < this.a.size() - 1) ? (Pattern)this.a.get(i + 1) : null;
  }
  
  public void a(MatcherNode paramMatcherNode) {}
  
  public void d() {
    for (PatternPhrase patternPhrase : this)
      patternPhrase.d(); 
  }
  
  public void c() {
    for (PatternPhrase patternPhrase : this) {
      for (Pattern pattern : patternPhrase)
        pattern.c(); 
    } 
  }
  
  public boolean e() {
    return true;
  }
  
  public String f() {
    StringBuilder stringBuilder = new StringBuilder();
    for (PatternPhrase patternPhrase : this) {
      String str = patternPhrase.f();
      if (str != null)
        stringBuilder.append(str); 
    } 
    return stringBuilder.toString();
  }
}
