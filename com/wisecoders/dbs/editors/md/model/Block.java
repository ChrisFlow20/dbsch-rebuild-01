package com.wisecoders.dbs.editors.md.model;

import java.util.Arrays;
import java.util.List;

public class Block {
  private String a;
  
  private BlockType b;
  
  private ValuePart[] c;
  
  private int d;
  
  private List e;
  
  private List f;
  
  private Block g;
  
  private TableCellAlign h;
  
  public String a() {
    return this.a;
  }
  
  public void a(String paramString) {
    this.a = paramString;
  }
  
  public BlockType b() {
    return this.b;
  }
  
  public void a(BlockType paramBlockType) {
    this.b = paramBlockType;
  }
  
  public ValuePart[] c() {
    return this.c;
  }
  
  public void a(ValuePart... paramVarArgs) {
    this.c = paramVarArgs;
  }
  
  public void a(List<ValuePart> paramList) {
    this.c = new ValuePart[paramList.size()];
    byte b;
    int i;
    for (b = 0, i = paramList.size(); b < i; b++)
      this.c[b] = paramList.get(b); 
  }
  
  public int d() {
    return this.d;
  }
  
  public void a(int paramInt) {
    this.d = paramInt;
  }
  
  public List e() {
    return this.e;
  }
  
  public void b(List paramList) {
    this.e = paramList;
  }
  
  public List f() {
    return this.f;
  }
  
  public void c(List paramList) {
    this.f = paramList;
  }
  
  public Block g() {
    return this.g;
  }
  
  public void a(Block paramBlock) {
    this.g = paramBlock;
  }
  
  public TableCellAlign h() {
    return this.h;
  }
  
  public void a(TableCellAlign paramTableCellAlign) {
    this.h = paramTableCellAlign;
  }
  
  public String toString() {
    return "mdToken:" + this.a + "blockType:" + String.valueOf(this.b) + "|valueParts:" + Arrays.toString((Object[])this.c) + "|level:" + this.d + "|listData:" + String.valueOf(this.f);
  }
}
