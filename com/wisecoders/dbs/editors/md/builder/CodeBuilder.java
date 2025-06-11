package com.wisecoders.dbs.editors.md.builder;

import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.BlockType;
import com.wisecoders.dbs.editors.md.model.ValuePart;

public class CodeBuilder implements BlockBuilder {
  private String a;
  
  public CodeBuilder(String paramString) {
    this.a = paramString;
  }
  
  public Block a() {
    Block block = new Block();
    block.a(BlockType.a);
    block.a(new ValuePart[] { new ValuePart(this.a) });
    return block;
  }
  
  public boolean b() {
    return false;
  }
}
