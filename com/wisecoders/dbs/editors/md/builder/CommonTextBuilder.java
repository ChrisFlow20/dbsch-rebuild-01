package com.wisecoders.dbs.editors.md.builder;

import com.wisecoders.dbs.editors.md.Tools;
import com.wisecoders.dbs.editors.md.model.Analyzer;
import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.BlockType;
import java.util.ArrayList;
import java.util.List;

public class CommonTextBuilder implements BlockBuilder {
  private String a;
  
  public CommonTextBuilder(String paramString) {
    this.a = paramString;
  }
  
  public Block a() {
    Block block = new Block();
    block.a(BlockType.j);
    List list = Tools.a(this.a);
    ArrayList arrayList = new ArrayList();
    for (String str : list)
      arrayList.addAll(Analyzer.b(str)); 
    block.a(arrayList);
    return block;
  }
  
  public boolean b() {
    return true;
  }
}
