package com.wisecoders.dbs.editors.md.filter;

import com.wisecoders.dbs.editors.md.builder.HeaderBuilder;
import com.wisecoders.dbs.editors.md.model.TextOrBlock;
import java.util.ArrayList;
import java.util.List;

public class HeaderNextLineFilter extends SyntaxFilter {
  public HeaderNextLineFilter(SyntaxFilter paramSyntaxFilter) {
    super(paramSyntaxFilter);
  }
  
  public List a(List<String> paramList) {
    ArrayList<TextOrBlock> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    byte b;
    int i;
    for (b = 0, i = paramList.size(); b < i; b++) {
      String str = paramList.get(b);
      if (b + 1 < i) {
        String str1 = paramList.get(b + 1);
        int j = HeaderBuilder.a(str1);
        if (j > 0) {
          if (!stringBuilder.toString().equals("")) {
            arrayList.add(new TextOrBlock(stringBuilder.toString()));
            stringBuilder = new StringBuilder();
          } 
          arrayList.add(new TextOrBlock((new HeaderBuilder(str)).a(j)));
          b++;
        } else {
          stringBuilder.append(str).append("\n");
        } 
      } else {
        stringBuilder.append(str).append("\n");
      } 
    } 
    if (!stringBuilder.toString().equals(""))
      arrayList.add(new TextOrBlock(stringBuilder.toString())); 
    return arrayList;
  }
}
