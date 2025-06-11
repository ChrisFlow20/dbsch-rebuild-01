package com.wisecoders.dbs.sql.indenter;

import com.wisecoders.dbs.editors.text.DefaultLexer;
import java.util.List;

public class JsonIndent extends Indent {
  public JsonIndent(DefaultLexer paramDefaultLexer, String paramString) {
    super(paramDefaultLexer, paramString);
  }
  
  protected void a(List paramList) {
    byte b = 0;
    boolean bool = false;
    IndentToken indentToken = null;
    for (IndentToken indentToken1 : paramList) {
      if (indentToken != null) {
        if (indentToken.a(new String[] { "(", "[", "{" })) {
          b++;
          bool = false;
          indentToken1.c = true;
        } else if (indentToken.a(new String[] { ")", "]", "}" })) {
          bool = true;
          indentToken.c = true;
          indentToken1.c = true;
          indentToken.f = --b;
        } else if (indentToken.a(new String[] { ":" })) {
          bool = true;
        } else if (indentToken.a(new String[] { "," }) && bool) {
          indentToken1.c = true;
        } else if (indentToken.a(new String[] { ";" })) {
          indentToken1.c = true;
        } 
        indentToken1.f = b;
      } 
      indentToken = indentToken1;
    } 
  }
}
