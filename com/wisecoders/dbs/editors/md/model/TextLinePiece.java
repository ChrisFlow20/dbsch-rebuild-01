package com.wisecoders.dbs.editors.md.model;

public class TextLinePiece {
  private int a;
  
  private int b;
  
  private String c;
  
  private String d;
  
  private TextLinePiece$PieceType e;
  
  public TextLinePiece(int paramInt1, int paramInt2, TextLinePiece$PieceType paramTextLinePiece$PieceType) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.e = paramTextLinePiece$PieceType;
  }
  
  public TextLinePiece() {}
  
  public int a() {
    return this.a;
  }
  
  public void a(int paramInt) {
    this.a = paramInt;
  }
  
  public int b() {
    return this.b;
  }
  
  public void b(int paramInt) {
    this.b = paramInt;
  }
  
  public String c() {
    return this.c;
  }
  
  public void a(String paramString) {
    this.c = paramString;
  }
  
  public String d() {
    return this.d;
  }
  
  public void b(String paramString) {
    this.d = paramString;
  }
  
  public TextLinePiece$PieceType e() {
    return this.e;
  }
  
  public void a(TextLinePiece$PieceType paramTextLinePiece$PieceType) {
    this.e = paramTextLinePiece$PieceType;
  }
}
