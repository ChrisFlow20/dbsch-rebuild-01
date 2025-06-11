package com.wisecoders.dbs.sql.indenter;

import com.wisecoders.dbs.editors.text.DefaultLexer;
import java.util.List;

public class SqlIndent extends Indent {
  public SqlIndent(DefaultLexer paramDefaultLexer, String paramString) {
    super(paramDefaultLexer, paramString);
  }
  
  protected void a(List paramList) {
    IndentToken indentToken = null;
    byte b = 0;
    for (IndentToken indentToken1 : paramList) {
      if (indentToken != null) {
        if (indentToken.b(new String[] { ";", "/" }))
          indentToken1.c = true; 
        if (indentToken.a(new String[] { "(" })) {
          b++;
        } else if (indentToken.a(new String[] { ")" })) {
          b--;
        } else if (!indentToken.a(new String[] { "," })) {
          if (indentToken.a(new String[] { ";" })) {
            indentToken1.c = true;
          } else if (indentToken.a(new String[] { "AND", "OR" })) {
            indentToken.c = indentToken.d = true;
          } else if (indentToken.a(new String[] { "SELECT", "WHERE" })) {
            indentToken.c = indentToken1.d = true;
          } else if (indentToken.a(new String[] { "FROM", "ORDER", "GROUP", "HAVING", "UNION", "INTERSECT" })) {
            indentToken.c = true;
            if (!indentToken1.a(new String[] { "BY" }))
              indentToken1.c = indentToken1.d = true; 
          } else if (indentToken.a(new String[] { "JOIN" })) {
            indentToken1.c = indentToken1.d = true;
          } else if (indentToken.a(new String[] { "BY" })) {
            indentToken1.c = indentToken1.d = true;
          } 
        } 
      } 
      indentToken = indentToken1;
      indentToken1.f = b;
    } 
  }
}
