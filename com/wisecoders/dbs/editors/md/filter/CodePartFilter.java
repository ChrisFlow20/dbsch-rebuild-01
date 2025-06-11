package com.wisecoders.dbs.editors.md.filter;

import com.wisecoders.dbs.editors.md.builder.CodeBuilder;
import com.wisecoders.dbs.editors.md.model.TextOrBlock;
import java.util.ArrayList;
import java.util.List;

public class CodePartFilter extends SyntaxFilter {
  private static final int a = 0;
  
  private static final int b = 1;
  
  private static final int c = 2;
  
  public CodePartFilter(SyntaxFilter paramSyntaxFilter) {
    super(paramSyntaxFilter);
  }
  
  public List a(List paramList) {
    ArrayList<TextOrBlock> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0, j = paramList.size(); i < j; i++) {
      String str1 = a(paramList, i - 1);
      String str2 = a(paramList, i);
      int k = a(str2, str1);
      if (k == 0) {
        stringBuilder.append(str2 + "\n");
      } else {
        StringBuilder stringBuilder1 = a(str2, k);
        boolean bool = false;
        for (int m = i + 1; m < j; m++) {
          str2 = a(paramList, m);
          String str = a(paramList, m + 1);
          if (a(str2, str, k, stringBuilder1)) {
            bool = true;
            i = m;
            break;
          } 
          stringBuilder1.append(str2 + "\n");
        } 
        if (bool) {
          if (!stringBuilder.toString().equals("")) {
            arrayList.add(new TextOrBlock(stringBuilder.toString()));
            stringBuilder = new StringBuilder();
          } 
          arrayList.add(new TextOrBlock((new CodeBuilder(stringBuilder1.toString())).a()));
        } else {
          String str = stringBuilder.append("```").append("\n").append(stringBuilder1.toString()).toString();
          arrayList.add(new TextOrBlock(str));
          stringBuilder = new StringBuilder();
          break;
        } 
      } 
    } 
    if (!stringBuilder.toString().equals(""))
      arrayList.add(new TextOrBlock(stringBuilder.toString())); 
    return arrayList;
  }
  
  private StringBuilder a(String paramString, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramInt == 1)
      stringBuilder.append(paramString).append("\n"); 
    return stringBuilder;
  }
  
  private int a(String paramString1, String paramString2) {
    if (paramString1.trim().startsWith("```"))
      return 2; 
    if (paramString2 == null || paramString2.trim().equals(""))
      return b(paramString1, 1) ? 1 : 0; 
    return 0;
  }
  
  private boolean a(String paramString1, String paramString2, int paramInt, StringBuilder paramStringBuilder) {
    switch (paramInt) {
      case 2:
        if (paramString1.trim().startsWith("```"))
          return true; 
        break;
      case 1:
        if (paramString2 == null) {
          if (!paramString1.trim().equals(""))
            paramStringBuilder.append(paramString1).append("\n"); 
          return true;
        } 
        if (!paramString2.trim().equals("") && paramString1.trim().equals(""))
          return true; 
        break;
    } 
    return false;
  }
  
  private boolean b(String paramString, int paramInt) {
    switch (paramInt) {
      case 2:
        return true;
    } 
    return paramString.startsWith("    ");
  }
  
  private String a(List<String> paramList, int paramInt) {
    try {
      return paramList.get(paramInt);
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      return null;
    } 
  }
}
