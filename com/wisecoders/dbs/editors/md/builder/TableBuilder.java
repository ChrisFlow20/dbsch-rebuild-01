package com.wisecoders.dbs.editors.md.builder;

import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.BlockType;
import com.wisecoders.dbs.editors.md.model.TableCellAlign;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableBuilder implements BlockBuilder {
  private List a;
  
  private Map b;
  
  public TableBuilder(List paramList, Map paramMap) {
    this.a = paramList;
    this.b = paramMap;
  }
  
  public Block a() {
    Block block = new Block();
    block.a(BlockType.b);
    block.b(a(this.a, this.b));
    return block;
  }
  
  private List a(List paramList, Map paramMap) {
    ArrayList<ArrayList<Block>> arrayList = new ArrayList();
    for (List<String> list : (Iterable<List<String>>)paramList) {
      ArrayList<Block> arrayList1 = new ArrayList();
      byte b;
      int i;
      for (b = 0, i = list.size(); b < i; b++) {
        String str = list.get(b);
        Block block = (new CommonTextBuilder(str)).a();
        TableCellAlign tableCellAlign = (TableCellAlign)paramMap.get(Integer.valueOf(b));
        block.a((tableCellAlign == null) ? TableCellAlign.d : tableCellAlign);
        arrayList1.add(block);
      } 
      arrayList.add(arrayList1);
    } 
    return arrayList;
  }
  
  public boolean b() {
    return false;
  }
}
