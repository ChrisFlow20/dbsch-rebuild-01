package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.sys.Log;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Segment;

public abstract class DefaultLexer implements Lexer {
  protected int a;
  
  protected int b;
  
  protected int c;
  
  private final Token d = new Token(TokenType.p, 0, 0);
  
  private static final int e = 100;
  
  public abstract int a();
  
  protected Token a(TokenType paramTokenType, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.a = paramInt3;
    this.b = paramInt4;
    return this.d.a(paramTokenType, paramInt1 + this.c, paramInt2);
  }
  
  protected Token a(TokenType paramTokenType, int paramInt1, int paramInt2) {
    return this.d.a(paramTokenType, paramInt1 + this.c, paramInt2);
  }
  
  protected Token a(TokenType paramTokenType) {
    return this.d.a(paramTokenType, e() + this.c, c());
  }
  
  protected Token a(TokenType paramTokenType, int paramInt) {
    return this.d.a(paramTokenType, e() + this.c, c(), (byte)paramInt);
  }
  
  public void a(Segment paramSegment, int paramInt, List<Token> paramList) {
    try {
      CharArrayReader charArrayReader = new CharArrayReader(paramSegment.array, paramSegment.offset, paramSegment.count);
      a(charArrayReader);
      this.c = paramInt;
      for (Token token = b(); token != null; token = b())
        paramList.add(token); 
    } catch (IOException iOException) {
      Logger.getLogger(DefaultLexer.class.getName()).log(Level.SEVERE, (String)null, iOException);
    } 
  }
  
  private int f = 0;
  
  private Token g;
  
  public abstract void a(Reader paramReader);
  
  public abstract Token b();
  
  public abstract char a(int paramInt);
  
  public abstract int c();
  
  public abstract String d();
  
  public abstract int e();
  
  public int f() {
    return this.f;
  }
  
  void g() {
    this.f++;
  }
  
  void a(StyledEditorModel paramStyledEditorModel, int paramInt) {
    try {
      this.f = 0;
      int i = paramInt;
      byte b = 0;
      while (i > 0 && ((TextLine)paramStyledEditorModel.a.get(i - 1)).d() && b++ < 100) {
        this.f += ((TextLine)paramStyledEditorModel.a.get(i - 1)).c() + 1;
        i--;
      } 
      a(paramStyledEditorModel.c(i));
      this.g = b();
      j();
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
  }
  
  public Token a(StyledEditorModel paramStyledEditorModel, Position paramPosition) {
    a(paramStyledEditorModel.a(paramPosition.getLine(), paramPosition.getLine()));
    try {
      while ((this.g = b()) != null && this.g.b + this.g.c < paramPosition.getCharacter());
      return this.g;
    } catch (Throwable throwable) {
      Log.b(throwable);
      return null;
    } 
  }
  
  String b(StyledEditorModel paramStyledEditorModel, Position paramPosition) {
    Token token = a(paramStyledEditorModel, paramPosition);
    if (token != null && token.a != TokenType.b && token.a != TokenType.a && token.a != TokenType.c)
      return paramStyledEditorModel.a(paramPosition.getLine()).b(token.b, token.b + token.c); 
    return null;
  }
  
  Token h() {
    j();
    return (this.g != null && this.g.b <= this.f && this.g.b + this.g.c >= this.f) ? this.g : null;
  }
  
  private void j() {
    try {
      while (this.g != null && this.g.b + this.g.c <= this.f)
        this.g = b(); 
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
  }
  
  boolean i() {
    return (this.g != null && this.g.b + this.g.c > this.f + 1);
  }
}
