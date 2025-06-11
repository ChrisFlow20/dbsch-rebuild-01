package com.wisecoders.dbs.editors.md.filter;

import com.wisecoders.dbs.editors.md.builder.HeaderBuilder;
import com.wisecoders.dbs.editors.md.model.TextOrBlock;
import java.util.ArrayList;
import java.util.List;

public class HeaderOneLineFilter extends SyntaxFilter {
  public HeaderOneLineFilter(SyntaxFilter paramSyntaxFilter) {
    super(paramSyntaxFilter);
  }
  
  public List a(List<String> paramList) {
    ArrayList<TextOrBlock> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    byte b;
    int i;
    for (b = 0, i = paramList.size(); b < i; b++) {
      String str = paramList.get(b);
      if (str.trim().startsWith("#")) {
        if (!stringBuilder.toString().equals("")) {
          arrayList.add(new TextOrBlock(stringBuilder.toString()));
          stringBuilder = new StringBuilder();
        } 
        arrayList.add(new TextOrBlock((new HeaderBuilder(str)).a()));
      } else {
        stringBuilder.append(str).append("\n");
      } 
    } 
    if (!stringBuilder.toString().equals(""))
      arrayList.add(new TextOrBlock(stringBuilder.toString())); 
    return arrayList;
  }
}
