package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.editors.text.TokenType;
import java.util.ArrayList;
import java.util.Iterator;

public class MatcherCommaPhrase extends ArrayList implements MatcherNode {
  private boolean a = false;
  
  private MatcherException b;
  
  public MatcherToken a(String paramString1, int paramInt1, int paramInt2, TokenType paramTokenType, String paramString2) {
    MatcherToken matcherToken = new MatcherToken(paramString1, paramInt1, paramInt2, paramTokenType, paramString2);
    add((E)matcherToken);
    return matcherToken;
  }
  
  public MatcherStatement a(MatcherStatement paramMatcherStatement, String paramString1, String paramString2) {
    MatcherStatement matcherStatement = new MatcherStatement(paramMatcherStatement, paramString1, paramString2);
    add((E)matcherStatement);
    return matcherStatement;
  }
  
  public PatternPhrase a(String paramString) {
    PatternPhrase patternPhrase = b(paramString);
    if (patternPhrase != null)
      patternPhrase.c(); 
    return patternPhrase;
  }
  
  public PatternPhrase b(String paramString) {
    PatternOptionalPhrases patternOptionalPhrases = new PatternOptionalPhrases(paramString);
    for (byte b = 0; b < size(); b++) {
      PatternPhrase patternPhrase = a(patternOptionalPhrases.h(), b);
      if (patternPhrase == null) {
        patternOptionalPhrases.d();
      } else {
        return patternPhrase;
      } 
    } 
    return null;
  }
  
  public boolean c(String paramString) {
    return (b(paramString) != null);
  }
  
  public PatternPhrase d(String paramString) {
    return a(paramString, 0);
  }
  
  public PatternPhrase a(String paramString, int paramInt) {
    PatternOptionalPhrases patternOptionalPhrases = new PatternOptionalPhrases(paramString);
    PatternPhrase patternPhrase = a(patternOptionalPhrases.h(), paramInt);
    if (patternPhrase != null)
      patternPhrase.c(); 
    return patternPhrase;
  }
  
  protected PatternPhrase a(PatternPhrase paramPatternPhrase, int paramInt) {
    boolean bool1 = true;
    Pattern pattern1 = (Pattern)paramPatternPhrase.get(0), pattern2 = null;
    int i = 0;
    while (bool1 && pattern1 != null && paramInt + i < size()) {
      PatternOptionalPhrases patternOptionalPhrases;
      boolean bool3, bool4;
      ArrayList arrayList;
      MatcherNode matcherNode = (MatcherNode)get(paramInt + i);
      if (matcherNode.g()) {
        bool1 = false;
        continue;
      } 
      switch (pattern1.a()) {
        case "?":
          pattern1.a(matcherNode);
          pattern2 = pattern1;
          pattern1 = pattern1.b();
          i++;
          continue;
        case "~":
          if (matcherNode instanceof MatcherStatement) {
            pattern1.a(matcherNode);
            pattern2 = pattern1;
            pattern1 = pattern1.b();
            i++;
            continue;
          } 
          if (pattern2 != null && "*".equalsIgnoreCase(pattern2.a())) {
            pattern2.a(matcherNode);
            i++;
            continue;
          } 
          bool1 = false;
          continue;
        case "*":
          pattern2 = pattern1;
          pattern1 = pattern1.b();
          continue;
        case "[]":
          patternOptionalPhrases = (PatternOptionalPhrases)pattern1;
          bool3 = false;
          arrayList = new ArrayList(patternOptionalPhrases);
          do {
            bool4 = false;
            Iterator<PatternPhrase> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
              PatternPhrase patternPhrase1 = iterator.next();
              PatternPhrase patternPhrase2 = a(patternPhrase1, paramInt + i);
              if (patternPhrase2 == null) {
                patternPhrase1.d();
                continue;
              } 
              patternPhrase2.c();
              iterator.remove();
              i += patternPhrase2.size();
              bool4 = true;
              bool3 = true;
            } 
          } while (bool4);
          if (!bool3 && pattern1.b() != null && pattern1.b().a().equalsIgnoreCase(matcherNode.c())) {
            pattern2 = pattern1;
            pattern1 = pattern1.b();
            continue;
          } 
          if (!bool3 && pattern2 != null && "*".equalsIgnoreCase(pattern2.a())) {
            pattern2.a(matcherNode);
            i++;
            continue;
          } 
          pattern2 = pattern1;
          pattern1 = pattern1.b();
          continue;
      } 
      if (pattern1.a().equalsIgnoreCase(matcherNode.c())) {
        pattern1.a(matcherNode);
        pattern2 = pattern1;
        pattern1 = pattern1.b();
        i++;
        continue;
      } 
      if (pattern2 != null && "*".equalsIgnoreCase(pattern2.a())) {
        pattern2.a(matcherNode);
        i++;
        continue;
      } 
      bool1 = false;
    } 
    Pattern pattern3 = (pattern1 != null) ? pattern1 : pattern2;
    boolean bool2 = (pattern3 != null && "*".equalsIgnoreCase(pattern3.a())) ? true : false;
    while (bool2 && bool1 && i + paramInt < size() && !((MatcherNode)get(paramInt + i)).g()) {
      pattern3.a((MatcherNode)get(paramInt + i));
      i++;
    } 
    if (bool1 && (pattern1 == null || (bool2 && pattern1.b() == null)))
      return paramPatternPhrase; 
    return null;
  }
  
  public String a(int paramInt) {
    if (paramInt < size()) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = paramInt; i < size(); i++)
        stringBuilder.append(get(i)); 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  public String b(int paramInt) {
    if (paramInt < size())
      return String.valueOf(get(paramInt)); 
    return null;
  }
  
  public String a() {
    return "Phrase";
  }
  
  public TokenType b() {
    return null;
  }
  
  public String c() {
    return null;
  }
  
  public String d() {
    return null;
  }
  
  public String e() {
    return "";
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (MatcherNode matcherNode : this)
      stringBuilder.append(matcherNode.toString()); 
    return stringBuilder.toString();
  }
  
  public void a(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public void f() {
    this.a = false;
    for (MatcherNode matcherNode : this)
      matcherNode.f(); 
  }
  
  public boolean g() {
    return this.a;
  }
  
  public Object a(String paramString, MatcherCallback paramMatcherCallback) {
    PatternPhrase patternPhrase = d(paramString);
    if (patternPhrase != null)
      try {
        return paramMatcherCallback.call(patternPhrase);
      } catch (MatcherException matcherException) {
        this.b = matcherException;
      }  
    return null;
  }
  
  public Object b(String paramString, MatcherCallback paramMatcherCallback) {
    PatternPhrase patternPhrase = b(paramString);
    if (patternPhrase != null)
      try {
        return paramMatcherCallback.call(patternPhrase);
      } catch (MatcherException matcherException) {
        this.b = matcherException;
      }  
    return null;
  }
  
  public void c(String paramString, MatcherCallback paramMatcherCallback) {
    PatternPhrase patternPhrase = b(paramString);
    if (patternPhrase != null)
      try {
        Object object = paramMatcherCallback.call(patternPhrase);
        if (object != null)
          patternPhrase.c(); 
      } catch (MatcherException matcherException) {
        this.b = matcherException;
      }  
  }
  
  public void d(String paramString, MatcherCallback paramMatcherCallback) {
    PatternOptionalPhrases patternOptionalPhrases = new PatternOptionalPhrases(paramString);
    for (byte b = 0; b < size(); b++) {
      PatternPhrase patternPhrase = a(patternOptionalPhrases.h(), b);
      if (patternPhrase == null) {
        patternOptionalPhrases.d();
      } else {
        try {
          Object object = paramMatcherCallback.call(patternPhrase);
          if (object != null)
            patternPhrase.c(); 
        } catch (MatcherException matcherException) {
          this.b = matcherException;
        } 
      } 
    } 
  }
  
  public String h() {
    StringBuilder stringBuilder = new StringBuilder();
    for (MatcherNode matcherNode : this) {
      if (!matcherNode.g())
        stringBuilder.append(matcherNode); 
    } 
    return stringBuilder.toString().trim();
  }
  
  public String i() {
    StringBuilder stringBuilder = new StringBuilder();
    for (MatcherNode matcherNode : this) {
      if (matcherNode.g()) {
        stringBuilder.delete(0, stringBuilder.length() - 1);
        continue;
      } 
      stringBuilder.append(matcherNode);
    } 
    return stringBuilder.toString().trim();
  }
  
  public MatcherNode c(int paramInt) {
    return (paramInt + size() - 1 > -1 && paramInt + size() - 1 < size()) ? (MatcherNode)get(paramInt + size() - 1) : null;
  }
  
  public String j() {
    PatternPhrase patternPhrase = b("?");
    if (patternPhrase != null)
      return patternPhrase.f(0); 
    return null;
  }
  
  public boolean e(String paramString) {
    for (MatcherNode matcherNode : this) {
      if (paramString.equalsIgnoreCase(matcherNode.d()))
        return true; 
    } 
    return false;
  }
  
  public MatcherException k() {
    return this.b;
  }
}
