package com.wisecoders.dbs.editors.md.filter;

import com.wisecoders.dbs.editors.md.Tools;
import com.wisecoders.dbs.editors.md.builder.CommonTextBuilder;
import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.TextOrBlock;
import java.util.ArrayList;
import java.util.List;

public abstract class SyntaxFilter {
  private SyntaxFilter a = null;
  
  public abstract List a(List paramList);
  
  public SyntaxFilter(SyntaxFilter paramSyntaxFilter) {
    this.a = paramSyntaxFilter;
  }
  
  public List d(String paramString) {
    List list = a(Tools.a(paramString));
    ArrayList<Block> arrayList = new ArrayList();
    for (TextOrBlock textOrBlock : list) {
      if (textOrBlock.a()) {
        arrayList.add(textOrBlock.c());
        continue;
      } 
      String str = textOrBlock.b();
      if (this.a == null) {
        arrayList.addAll(a(str));
        continue;
      } 
      arrayList.addAll(this.a.d(str));
    } 
    return arrayList;
  }
  
  private List a(String paramString) {
    ArrayList<Block> arrayList = new ArrayList();
    List<String> list = Tools.a(paramString);
    StringBuilder stringBuilder = new StringBuilder();
    byte b;
    int i;
    for (b = 0, i = list.size(); b < i; b++) {
      String str = list.get(b);
      if (b + 1 < i) {
        String str1 = list.get(b + 1);
        stringBuilder.append(str + "\n");
        if (str1.trim().equals("") && 
          !stringBuilder.toString().equals("")) {
          arrayList.add((new CommonTextBuilder(stringBuilder.toString())).a());
          stringBuilder = new StringBuilder();
          b++;
        } 
      } else {
        stringBuilder.append(str + "\n");
      } 
    } 
    if (!stringBuilder.toString().equals(""))
      arrayList.add((new CommonTextBuilder(stringBuilder.toString())).a()); 
    return arrayList;
  }
}
