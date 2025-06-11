package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.editors.text.Token;
import com.wisecoders.dbs.editors.text.TokenType;
import com.wisecoders.dbs.editors.text.lexers.StatementLexer;
import com.wisecoders.dbs.sys.Log;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MatcherStatement extends ArrayList implements MatcherNode {
  public MatcherStatement a;
  
  public String b;
  
  private boolean c = false;
  
  private final String d;
  
  public MatcherStatement(MatcherStatement paramMatcherStatement, String paramString1, String paramString2) {
    this.a = paramMatcherStatement;
    this.b = paramString1;
    this.d = paramString2;
  }
  
  public MatcherStatement(String paramString1, String paramString2) {
    this.a = null;
    this.b = null;
    this.d = "";
    StringReader stringReader = new StringReader(paramString1);
    int i = 0;
    try {
      StatementLexer statementLexer = new StatementLexer(stringReader);
      MatcherStatement matcherStatement = this;
      Token token;
      while ((token = statementLexer.b()) != null) {
        String str = paramString1.substring(token.b, token.b + token.c);
        if (token.a == TokenType.a) {
          if (Dbms.get(paramString2).isStartBracketInDDL(str)) {
            matcherStatement = matcherStatement.i().a(matcherStatement, str, paramString1.substring(i, token.b));
          } else if (Dbms.get(paramString2).isEndBracketInDDL(str)) {
            if (matcherStatement.a != null)
              matcherStatement = matcherStatement.a; 
          } else if (",".equals(str)) {
            matcherStatement.h();
          } else {
            matcherStatement.i().a(str, token.b, token.c, token.a, paramString1.substring(i, token.b));
          } 
        } else if (token.a == TokenType.i || token.a == TokenType.j) {
          Log.h();
        } else {
          matcherStatement.i().a(str, token.b, token.c, token.a, paramString1.substring(i, token.b));
        } 
        i = token.b + token.c;
      } 
    } catch (IOException iOException) {
      Log.b(iOException);
    } 
    stringReader.close();
  }
  
  public String a(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : paramString.split("\\r?\\n")) {
      for (MatcherCommaPhrase matcherCommaPhrase : this) {
        PatternPhrase patternPhrase = matcherCommaPhrase.b(str);
        if (patternPhrase != null) {
          stringBuilder.append("\n");
          stringBuilder.append(patternPhrase);
          return patternPhrase.toString();
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  public void h() {
    MatcherCommaPhrase matcherCommaPhrase = new MatcherCommaPhrase();
    add((E)matcherCommaPhrase);
  }
  
  public MatcherCommaPhrase i() {
    if (isEmpty())
      h(); 
    return (MatcherCommaPhrase)get(size() - 1);
  }
  
  public boolean b(String paramString) {
    return (c(paramString) != null);
  }
  
  public PatternPhrase c(String paramString) {
    for (MatcherCommaPhrase matcherCommaPhrase : this) {
      PatternPhrase patternPhrase = matcherCommaPhrase.b(paramString);
      if (patternPhrase != null)
        return patternPhrase; 
    } 
    return null;
  }
  
  public void a(String paramString, MatcherCallback paramMatcherCallback) {
    for (MatcherCommaPhrase matcherCommaPhrase : this)
      matcherCommaPhrase.c(paramString, paramMatcherCallback); 
  }
  
  public boolean b(String paramString, MatcherCallback paramMatcherCallback) {
    boolean bool = false;
    f();
    for (MatcherCommaPhrase matcherCommaPhrase : this) {
      if (matcherCommaPhrase.b(paramString, paramMatcherCallback) != null)
        bool = true; 
    } 
    return bool;
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
  
  public String a() {
    return "(...)";
  }
  
  public String e() {
    return "";
  }
  
  public String j() {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (MatcherCommaPhrase matcherCommaPhrase : this) {
      if (bool)
        stringBuilder.append(", "); 
      stringBuilder.append(matcherCommaPhrase);
      bool = true;
    } 
    return stringBuilder.toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.d);
    if (this.a != null)
      stringBuilder.append(this.b); 
    boolean bool = false;
    for (MatcherCommaPhrase matcherCommaPhrase : this) {
      if (bool)
        stringBuilder.append(","); 
      stringBuilder.append(matcherCommaPhrase);
      bool = true;
    } 
    if (this.a != null)
      stringBuilder.append(n()); 
    return stringBuilder.toString();
  }
  
  private String n() {
    return "<".equals(this.b) ? ">" : ("{".equals(this.b) ? "}" : ")");
  }
  
  public void a(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean g() {
    return this.c;
  }
  
  public String k() {
    StringBuilder stringBuilder = new StringBuilder();
    for (MatcherCommaPhrase matcherCommaPhrase : this) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(", "); 
      if (!matcherCommaPhrase.g())
        for (MatcherNode matcherNode : matcherCommaPhrase)
          stringBuilder.append(matcherNode.toString());  
    } 
    return stringBuilder.toString();
  }
  
  public void f() {
    this.c = false;
    for (MatcherCommaPhrase matcherCommaPhrase : this)
      matcherCommaPhrase.f(); 
  }
  
  public void l() {
    while (size() > 1) {
      MatcherCommaPhrase matcherCommaPhrase1 = (MatcherCommaPhrase)get(0), matcherCommaPhrase2 = (MatcherCommaPhrase)get(1);
      matcherCommaPhrase1.a(",", 0, 0, TokenType.a, "");
      matcherCommaPhrase1.addAll(matcherCommaPhrase2);
      remove(matcherCommaPhrase2);
    } 
  }
  
  public MatcherException m() {
    for (MatcherCommaPhrase matcherCommaPhrase : this) {
      if (matcherCommaPhrase.k() != null)
        return matcherCommaPhrase.k(); 
    } 
    return null;
  }
}
