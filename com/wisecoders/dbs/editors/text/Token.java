package com.wisecoders.dbs.editors.text;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;

public class Token implements Serializable, Comparable {
  public TokenType a;
  
  public int b;
  
  public int c;
  
  public byte d;
  
  public final short e = 0;
  
  public Token(TokenType paramTokenType, int paramInt1, int paramInt2) {
    this.a = paramTokenType;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = 0;
  }
  
  public Token a(TokenType paramTokenType, int paramInt1, int paramInt2) {
    return a(paramTokenType, paramInt1, paramInt2, (byte)0);
  }
  
  public Token a(TokenType paramTokenType, int paramInt1, int paramInt2, byte paramByte) {
    this.a = paramTokenType;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramByte;
    return this;
  }
  
  public Token(TokenType paramTokenType, int paramInt1, int paramInt2, byte paramByte) {
    this.a = paramTokenType;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramByte;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof Token) {
      Token token = (Token)paramObject;
      return (this.b == token.b && this.c == token.c && this.a
        
        .equals(token.a));
    } 
    return false;
  }
  
  public int hashCode() {
    return this.b;
  }
  
  public String toString() {
    if (this.d == 0)
      return String.format("%s (%d, %d)", new Object[] { this.a, Integer.valueOf(this.b), Integer.valueOf(this.c) }); 
    return String.format("%s (%d, %d) (%d)", new Object[] { this.a, Integer.valueOf(this.b), Integer.valueOf(this.c), Byte.valueOf(this.d) });
  }
  
  public String a(String paramString) {
    return (this.b < paramString.length() && this.b > -1 && a() <= paramString.length()) ? paramString.substring(this.b, a()) : null;
  }
  
  public int compareTo(Object paramObject) {
    Token token = (Token)paramObject;
    if (this.b != token.b)
      return this.b - token.b; 
    if (this.c == token.c)
      return this.a.compareTo((E)token.a); 
    return this.c - token.c;
  }
  
  public int a() {
    return this.b + this.c;
  }
  
  public CharSequence a(Document paramDocument) {
    Segment segment = new Segment();
    try {
      paramDocument.getText(this.b, this.c, segment);
    } catch (BadLocationException badLocationException) {
      Logger.getLogger(Token.class.getName()).log(Level.SEVERE, (String)null, badLocationException);
    } 
    return segment;
  }
  
  public String b(Document paramDocument) {
    try {
      return paramDocument.getText(this.b, this.c);
    } catch (BadLocationException badLocationException) {
      Logger.getLogger(Token.class.getName()).log(Level.SEVERE, (String)null, badLocationException);
      return "";
    } 
  }
}
