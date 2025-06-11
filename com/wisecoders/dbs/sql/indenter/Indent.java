package com.wisecoders.dbs.sql.indenter;

import com.wisecoders.dbs.editors.text.DefaultLexer;
import com.wisecoders.dbs.editors.text.Token;
import com.wisecoders.dbs.sys.Log;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public abstract class Indent {
  private final StringBuilder a = new StringBuilder();
  
  public Indent(DefaultLexer paramDefaultLexer, String paramString) {
    if (paramString != null)
      try {
        for (String str : paramString.split("\n\n"))
          this.a.append(a(paramDefaultLexer, str)).append("\n\n"); 
      } catch (IOException iOException) {
        Log.b(iOException);
      }  
  }
  
  public String a(DefaultLexer paramDefaultLexer, String paramString) {
    if (paramString != null) {
      StringReader stringReader = new StringReader(paramString);
      try {
        paramDefaultLexer.a(stringReader);
        ArrayList<IndentToken> arrayList = new ArrayList();
        int i = -1;
        Token token;
        while ((token = paramDefaultLexer.b()) != null) {
          if (token.c > 0 && token.a() <= paramString.length()) {
            String str1 = paramString.substring(token.b, token.a());
            boolean bool = false;
            if (i > -1 && i < token.b) {
              String str2 = paramString.substring(i, token.b);
              if (str2.trim().length() > 0) {
                arrayList.add(new IndentToken(str2, 0));
              } else {
                bool = true;
              } 
            } 
            if (str1.endsWith("\n") || str1.endsWith("\r")) {
              arrayList.add(new IndentToken(str1.substring(0, str1.length() - 1), token.a.s));
              arrayList.add(new IndentToken("", 0, true));
            } else {
              arrayList.add((new IndentToken(str1, token.a.s)).a(bool));
            } 
            i = token.a();
          } 
        } 
        a(arrayList);
        StringBuilder stringBuilder = new StringBuilder();
        for (IndentToken indentToken : arrayList)
          stringBuilder.append(indentToken); 
        String str = stringBuilder.toString();
        stringReader.close();
        return str;
      } catch (Throwable throwable) {
        try {
          stringReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
    return null;
  }
  
  protected abstract void a(List paramList);
  
  public String toString() {
    return this.a.toString();
  }
}
