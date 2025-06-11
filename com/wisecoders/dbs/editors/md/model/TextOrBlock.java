package com.wisecoders.dbs.editors.md.model;

public class TextOrBlock {
  private boolean a;
  
  private String b;
  
  private Block c;
  
  public TextOrBlock(Block paramBlock) {
    this.c = paramBlock;
    this.a = true;
  }
  
  public TextOrBlock(String paramString) {
    this.b = paramString;
    this.a = false;
  }
  
  public boolean a() {
    return this.a;
  }
  
  public void a(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public String b() {
    return this.b;
  }
  
  public void a(String paramString) {
    this.b = paramString;
  }
  
  public Block c() {
    return this.c;
  }
  
  public void a(Block paramBlock) {
    this.c = paramBlock;
  }
  
  public String toString() {
    return this.a ? this.b : this.c.toString();
  }
}
