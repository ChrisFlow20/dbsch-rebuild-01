package com.wisecoders.dbs.editors.md.filter;

import com.wisecoders.dbs.editors.md.builder.TableBuilder;
import com.wisecoders.dbs.editors.md.model.TableCellAlign;
import com.wisecoders.dbs.editors.md.model.TextOrBlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablePartFilter extends SyntaxFilter {
  public TablePartFilter(SyntaxFilter paramSyntaxFilter) {
    super(paramSyntaxFilter);
  }
  
  public List a(List<String> paramList) {
    ArrayList<TextOrBlock> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0, j = paramList.size(); i < j; i++) {
      String str = paramList.get(i);
      if (!a(str)) {
        stringBuilder.append(str + "\n");
      } else {
        if (i + 1 == j) {
          stringBuilder.append(str + "\n");
          break;
        } 
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (!a(paramList.get(i + 1), hashMap)) {
          stringBuilder.append(str + "\n");
        } else {
          int k = i + 1;
          if (!stringBuilder.toString().equals("")) {
            arrayList.add(new TextOrBlock(stringBuilder.toString()));
            stringBuilder = new StringBuilder();
          } 
          ArrayList arrayList1 = new ArrayList();
          for (int m = i; m < j; m++) {
            if (m != k) {
              String str1 = paramList.get(m);
              if (str1 == null || str1.trim().equals("")) {
                List list = b(arrayList1);
                arrayList.add(new TextOrBlock((new TableBuilder(list, hashMap)).a()));
                i = m - 1;
                break;
              } 
              String[] arrayOfString = str1.split("\\|");
              arrayList1.add(Arrays.asList(arrayOfString));
            } 
            if (m == j - 1) {
              List list = b(arrayList1);
              arrayList.add(new TextOrBlock((new TableBuilder(list, hashMap)).a()));
              i = m;
              break;
            } 
          } 
        } 
      } 
    } 
    if (!stringBuilder.toString().equals(""))
      arrayList.add(new TextOrBlock(stringBuilder.toString())); 
    return arrayList;
  }
  
  private boolean a(String paramString) {
    if (paramString.indexOf("|") == -1)
      return false; 
    if (paramString.startsWith("\\|"))
      paramString = paramString.substring(1); 
    if (paramString.endsWith("\\|"))
      paramString = paramString.substring(0, paramString.length() - 1); 
    String[] arrayOfString = paramString.split("\\|");
    if (arrayOfString.length <= 1)
      return false; 
    return true;
  }
  
  private boolean a(String paramString, Map<Integer, TableCellAlign> paramMap) {
    if (paramString == null || paramString.trim().equals(""))
      return false; 
    if (paramString.startsWith("|"))
      paramString = paramString.substring(1); 
    if (paramString.endsWith("|"))
      paramString = paramString.substring(0, paramString.length() - 1); 
    String[] arrayOfString = paramString.split("\\|");
    byte b;
    int i;
    for (b = 0, i = arrayOfString.length; b < i; b++) {
      String str1 = arrayOfString[b];
      String str2 = str1.trim().replaceAll("-", "");
      if (!str2.equals("") && !str2.equals(":") && !str2.equals("::"))
        return false; 
      if (str1.startsWith(":") && str1.endsWith(":")) {
        paramMap.put(Integer.valueOf(b), TableCellAlign.b);
      } else if (str1.endsWith(":")) {
        paramMap.put(Integer.valueOf(b), TableCellAlign.c);
      } else if (str1.startsWith(":")) {
        paramMap.put(Integer.valueOf(b), TableCellAlign.a);
      } else {
        paramMap.put(Integer.valueOf(b), TableCellAlign.d);
      } 
    } 
    return true;
  }
  
  private List b(List<List> paramList) {
    boolean bool1 = true;
    boolean bool2 = true;
    byte b;
    int i;
    for (b = 0, i = paramList.size(); b < i; b++) {
      List<String> list = paramList.get(b);
      if (!((String)list.get(0)).trim().equals("") && bool1)
        bool1 = false; 
      if (!((String)list.get(list.size() - 1)).trim().equals("") && bool2)
        bool2 = false; 
    } 
    if (bool2)
      for (b = 0, i = paramList.size(); b < i; b++) {
        List<String> list = paramList.get(b);
        ArrayList<String> arrayList = new ArrayList();
        byte b1;
        int j;
        for (b1 = 0, j = list.size(); b1 < j; b1++) {
          if (b1 < j - 1)
            arrayList.add(list.get(b1)); 
        } 
        paramList.set(b, arrayList);
      }  
    if (bool1)
      for (b = 0, i = paramList.size(); b < i; b++) {
        List<String> list = paramList.get(b);
        ArrayList<String> arrayList = new ArrayList();
        byte b1;
        int j;
        for (b1 = 0, j = list.size(); b1 < j; b1++) {
          if (b1 > 0)
            arrayList.add(list.get(b1)); 
        } 
        paramList.set(b, arrayList);
      }  
    return paramList;
  }
}
