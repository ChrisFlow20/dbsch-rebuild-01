package com.wisecoders.dbs.editors.md.builder;

import com.wisecoders.dbs.editors.md.model.Analyzer;
import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.BlockType;
import java.util.List;

public class HeaderBuilder implements BlockBuilder {
  private String a;
  
  public HeaderBuilder(String paramString) {
    this.a = paramString;
  }
  
  public Block a() {
    this.a = this.a.trim();
    int i = this.a.lastIndexOf("#");
    this.a = this.a.substring(i + 1).trim();
    return a(i);
  }
  
  public Block a(int paramInt) {
    Block block = new Block();
    List list = Analyzer.b(this.a);
    block.a(BlockType.h);
    block.a(list);
    block.a(paramInt + 1);
    return block;
  }
  
  public boolean b() {
    return this.a.startsWith("#");
  }
  
  public static int a(String paramString) {
    if (!paramString.startsWith("-") && !paramString.startsWith("="))
      return 0; 
    String str = paramString.replaceAll("-", "").trim();
    if (str.length() == 0)
      return 2; 
    str = paramString.replaceAll("=", "").trim();
    if (str.length() == 0)
      return 1; 
    return 0;
  }
}
