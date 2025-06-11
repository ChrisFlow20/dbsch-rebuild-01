package com.wisecoders.dbs.editors.md.model;

import com.wisecoders.dbs.editors.md.MDToken;
import com.wisecoders.dbs.editors.md.Tools;
import com.wisecoders.dbs.editors.md.filter.CodePartFilter;
import com.wisecoders.dbs.editors.md.filter.HeaderNextLineFilter;
import com.wisecoders.dbs.editors.md.filter.HeaderOneLineFilter;
import com.wisecoders.dbs.editors.md.filter.ListFilter;
import com.wisecoders.dbs.editors.md.filter.TablePartFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analyzer {
  private static List a = Arrays.asList(new String[] { "**", "__", "_", "*", "~~", "`", "![", "[" });
  
  public static List a(String paramString) {
    paramString = c(paramString);
    CodePartFilter codePartFilter = new CodePartFilter(new TablePartFilter(new HeaderOneLineFilter(new ListFilter(new HeaderNextLineFilter(null)))));
    return codePartFilter.d(paramString);
  }
  
  public static List b(String paramString) {
    List<ValuePart> list = a(paramString.trim(), new ArrayList(), new ArrayList());
    if (paramString.endsWith("  "))
      list.add(a("", Arrays.asList(new String[] { "  " }))); 
    return list;
  }
  
  private static List a(String paramString, List paramList1, List paramList2) {
    ArrayList<ValuePart> arrayList = new ArrayList();
    int i = paramString.length();
    int j = i;
    String str = null;
    for (String str1 : a) {
      if (paramList1.contains(str1))
        continue; 
      int i2 = paramString.indexOf(str1);
      if (i2 > -1 && j > i2) {
        j = i2;
        str = str1;
      } 
    } 
    TextLinePiece textLinePiece = a(j, str, paramString);
    int k = i;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (textLinePiece != null) {
      k = textLinePiece.a();
      m = textLinePiece.b();
      if (m < i - 1) {
        if (textLinePiece.e() == TextLinePiece$PieceType.b) {
          n = textLinePiece.b() + 1;
        } else {
          n = textLinePiece.b() + str.length();
        } 
        i1 = i;
      } 
    } else if (str != null) {
      k = str.length();
      n = str.length();
      i1 = i;
    } 
    if (k > 0) {
      ValuePart valuePart = a(paramString.substring(0, k), paramList2);
      arrayList.add(valuePart);
    } 
    if (m > 0) {
      String str1, str2;
      List list;
      List<String> list1 = a(paramList2);
      List<String> list2 = a(paramList1);
      list2.add(str);
      list1.add(str);
      ValuePart valuePart = null;
      switch (Analyzer$1.a[textLinePiece.e().ordinal()]) {
        case 1:
          valuePart = b(textLinePiece.c(), list2, list1);
          str1 = valuePart.e() + "(" + valuePart.e() + ")";
          valuePart.a(str1);
          valuePart.b(textLinePiece.d());
          arrayList.add(valuePart);
          break;
        case 2:
          valuePart = a(textLinePiece.d(), list1);
          valuePart.c(textLinePiece.c());
          valuePart.b(textLinePiece.d());
          arrayList.add(valuePart);
          break;
        default:
          str2 = paramString.substring(textLinePiece.a() + str.length(), m);
          if (str.equals("`"))
            list2 = a; 
          list = a(str2, list2, list1);
          for (ValuePart valuePart1 : list)
            arrayList.add(valuePart1); 
          break;
      } 
    } 
    if (i1 > 0) {
      String str1 = "";
      str1 = paramString.substring(n);
      List list = a(str1, paramList1, paramList2);
      for (ValuePart valuePart : list)
        arrayList.add(valuePart); 
    } 
    return arrayList;
  }
  
  private static List a(List paramList) {
    ArrayList<String> arrayList = new ArrayList();
    for (String str : paramList)
      arrayList.add(str); 
    return arrayList;
  }
  
  private static TextLinePiece a(int paramInt, String paramString1, String paramString2) {
    if (paramString1 == null)
      return null; 
    TextLinePiece textLinePiece = null;
    if (paramString1.equals("[") || paramString1.equals("![")) {
      textLinePiece = a(paramString2, paramString1.equals("["));
    } else {
      int i = paramString2.indexOf(paramString1, paramInt + paramString1.length());
      if (i > -1)
        textLinePiece = new TextLinePiece(paramInt, i, TextLinePiece$PieceType.c); 
    } 
    return textLinePiece;
  }
  
  private static ValuePart a(String paramString, List<String> paramList) {
    ValuePart valuePart = new ValuePart();
    valuePart.a(paramString);
    if (paramList.size() > 0) {
      BlockType[] arrayOfBlockType = new BlockType[paramList.size()];
      byte b = 0;
      for (int i = paramList.size() - 1; i >= 0; i--) {
        arrayOfBlockType[b] = MDToken.a(paramList.get(i));
        b++;
      } 
      valuePart.a(arrayOfBlockType);
    } 
    return valuePart;
  }
  
  private static TextLinePiece a(String paramString, boolean paramBoolean) {
    TextLinePiece textLinePiece = new TextLinePiece();
    textLinePiece.a(paramBoolean ? TextLinePiece$PieceType.a : TextLinePiece$PieceType.b);
    String str = null;
    if (paramBoolean) {
      str = "[";
    } else {
      str = "![";
    } 
    int i = paramString.indexOf(str);
    int j = paramString.indexOf("]", i);
    if (j > 0) {
      int k = paramString.indexOf("(", j);
      if (k > 0 && k == j + 1) {
        int m = paramString.indexOf(")", k);
        if (m > 0) {
          String str1 = paramString.substring(k + 1, m).trim();
          int n = str1.indexOf(" ");
          String str2 = "";
          if (n > -1) {
            str2 = str1.substring(0, n);
          } else {
            str2 = str1;
          } 
          String str3 = paramString.substring(i + str.length(), j);
          textLinePiece.a(i);
          textLinePiece.b(m);
          textLinePiece.a(str3);
          textLinePiece.b(str2);
          return textLinePiece;
        } 
      } 
    } 
    return null;
  }
  
  private static ValuePart b(String paramString, List<String> paramList1, List<String> paramList2) {
    String str = null;
    for (String str1 : a) {
      if (paramList1.contains(str1))
        continue; 
      if (paramString.startsWith(str1)) {
        int i = paramString.indexOf(str1, str1.length());
        if (i > 0) {
          str = str1;
          break;
        } 
      } 
    } 
    if (str == null) {
      ValuePart valuePart = a("", paramList2);
      valuePart.c(paramString);
      return valuePart;
    } 
    paramList1.add(str);
    paramList2.add(str);
    paramString = paramString.substring(str.length(), paramString.length() - str.length());
    return b(paramString, paramList1, paramList2);
  }
  
  private static String c(String paramString) {
    paramString = paramString.replaceAll("\t", "    ");
    paramString = Tools.d(paramString);
    return paramString;
  }
}
