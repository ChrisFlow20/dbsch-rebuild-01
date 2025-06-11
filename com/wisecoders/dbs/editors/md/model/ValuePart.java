package com.wisecoders.dbs.editors.md.model;

import com.wisecoders.dbs.editors.md.Tools;
import java.util.Arrays;

public class ValuePart {
  private String a;
  
  private BlockType[] b;
  
  private int c;
  
  private String d;
  
  private String e;
  
  public ValuePart() {}
  
  public ValuePart(String paramString) {
    this.a = Tools.c(paramString);
  }
  
  public ValuePart(String paramString, BlockType... paramVarArgs) {
    this.a = Tools.c(paramString);
    this.b = paramVarArgs;
  }
  
  public String a() {
    return this.a;
  }
  
  public void a(String paramString) {
    this.a = Tools.c(paramString);
  }
  
  public BlockType[] b() {
    return this.b;
  }
  
  public void a(BlockType... paramVarArgs) {
    this.b = paramVarArgs;
  }
  
  public int c() {
    return this.c;
  }
  
  public void a(int paramInt) {
    this.c = paramInt;
  }
  
  public String d() {
    return this.d;
  }
  
  public void b(String paramString) {
    this.d = paramString;
  }
  
  public String e() {
    return this.e;
  }
  
  public void c(String paramString) {
    this.e = paramString;
  }
  
  public void a(BlockType paramBlockType) {
    if (this.b == null) {
      this.b = new BlockType[1];
    } else {
      this.b = Arrays.<BlockType>copyOf(this.b, this.b.length + 1);
    } 
    this.b[this.b.length - 1] = paramBlockType;
  }
  
  public String toString() {
    return "value:" + this.a + "|types:" + Arrays.toString((Object[])this.b);
  }
  
  public static void main(String[] paramArrayOfString) {
    ValuePart valuePart = new ValuePart();
    valuePart.a(new BlockType[] { BlockType.l, BlockType.a });
    System.out.println(Arrays.toString((Object[])valuePart.b));
    valuePart.a(BlockType.h);
    System.out.println(Arrays.toString((Object[])valuePart.b));
  }
}
