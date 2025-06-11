package com.wisecoders.dbs.editors.md.filter;

import com.wisecoders.dbs.editors.md.builder.MultiListBuilder;
import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.TextOrBlock;
import java.util.ArrayList;
import java.util.List;

public class ListFilter extends SyntaxFilter {
  public ListFilter(SyntaxFilter paramSyntaxFilter) {
    super(paramSyntaxFilter);
  }
  
  public List a(List<String> paramList) {
    ArrayList<TextOrBlock> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0, j = paramList.size(); i < j; i++) {
      String str = paramList.get(i);
      if (!a(str)) {
        if (!str.trim().equals(""))
          stringBuilder.append(str + "\n"); 
      } else {
        boolean bool = b(str);
        boolean bool1 = false;
        StringBuilder stringBuilder1 = (new StringBuilder(str)).append("\n");
        for (int k = i + 1; k < j; k++) {
          str = paramList.get(k);
          if (str.trim().equals("")) {
            if (bool) {
              i = k - 1;
              break;
            } 
            bool1 = true;
          } else {
            if (!a(str) && bool1) {
              i = k - 1;
              break;
            } 
            stringBuilder1.append(str).append("\n");
            bool1 = false;
          } 
          if (k == j - 1)
            i = k; 
        } 
        if (!stringBuilder.toString().equals("")) {
          arrayList.add(new TextOrBlock(stringBuilder.toString()));
          stringBuilder = new StringBuilder();
        } 
        arrayList.add(new TextOrBlock(c(stringBuilder1.toString())));
      } 
    } 
    if (!stringBuilder.toString().equals(""))
      arrayList.add(new TextOrBlock(stringBuilder.toString())); 
    return arrayList;
  }
  
  protected boolean a(String paramString) {
    return MultiListBuilder.a(paramString);
  }
  
  protected boolean b(String paramString) {
    return MultiListBuilder.b(paramString);
  }
  
  protected Block c(String paramString) {
    return (new MultiListBuilder(paramString)).a();
  }
}
