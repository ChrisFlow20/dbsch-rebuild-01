package com.wisecoders.dbs.editors.md.builder;

import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.BlockType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class MultiListBuilder implements BlockBuilder {
  private String a;
  
  public MultiListBuilder(String paramString) {
    this.a = paramString;
  }
  
  private String c(String paramString) {
    if (paramString == null)
      return ""; 
    String str = " ";
    while (paramString.startsWith(str))
      str = str + " "; 
    return str.substring(1, str.length());
  }
  
  public Block a() {
    BufferedReader bufferedReader = new BufferedReader(new StringReader(this.a));
    Block block = new Block();
    ArrayList<Block> arrayList = new ArrayList();
    block.a(BlockType.c);
    block.c(arrayList);
    try {
      String str = bufferedReader.readLine();
      while (str != null) {
        Block block1 = new Block();
        str = a(block1, str, bufferedReader, new ArrayList());
        arrayList.add(block1);
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
    } 
    return block;
  }
  
  private boolean a(List paramList, MultiListBuilder$TypeAndBlank paramMultiListBuilder$TypeAndBlank) {
    ArrayList<MultiListBuilder$TypeAndBlank> arrayList = new ArrayList();
    for (MultiListBuilder$TypeAndBlank multiListBuilder$TypeAndBlank : paramList) {
      if (multiListBuilder$TypeAndBlank.a().equals(paramMultiListBuilder$TypeAndBlank.a()) && multiListBuilder$TypeAndBlank
        .b() == paramMultiListBuilder$TypeAndBlank.b()) {
        Iterator iterator = paramList.iterator();
        byte b;
        int i;
        for (b = 0, i = arrayList.size(); b < i; b++) {
          iterator.next();
          iterator.remove();
        } 
        return true;
      } 
      arrayList.add(multiListBuilder$TypeAndBlank);
    } 
    return false;
  }
  
  private String a(Block paramBlock, String paramString, BufferedReader paramBufferedReader, List<MultiListBuilder$TypeAndBlank> paramList) {
    String str = c(paramString);
    BlockType blockType = d(paramString);
    ArrayList<Block> arrayList = new ArrayList();
    paramBlock.a(blockType);
    paramBlock.c(arrayList);
    paramList.add(0, new MultiListBuilder$TypeAndBlank(this, str, blockType));
    while (paramString != null) {
      BlockType blockType1 = d(paramString);
      String str1 = c(paramString);
      if (!str1.equals(str) || blockType1 != blockType)
        return paramString; 
      Block block = new Block();
      paramString = paramString.substring(str.length());
      int i = a(paramString, blockType1);
      if (i < 0) {
        paramString = paramBufferedReader.readLine();
        continue;
      } 
      block.a(paramString.substring(0, i));
      paramString = paramString.substring(i + 1).trim();
      if (paramString.equals("")) {
        paramString = paramBufferedReader.readLine();
        continue;
      } 
      arrayList.add(block);
      StringBuffer stringBuffer = new StringBuffer(paramString);
      paramString = a(stringBuffer, paramBufferedReader);
      HeaderBuilder headerBuilder = new HeaderBuilder(stringBuffer.toString());
      if (headerBuilder.b()) {
        block.a(headerBuilder.a());
      } else {
        block.a((new CommonTextBuilder(stringBuffer.toString())).a());
      } 
      if (paramString == null)
        break; 
      blockType1 = d(paramString);
      str1 = c(paramString);
      if (paramString != null) {
        if (str1.equals(str) && blockType1 != blockType)
          return paramString; 
        if (!str1.equals(str) || blockType1 != blockType) {
          if (a(paramList, new MultiListBuilder$TypeAndBlank(this, str1, blockType1)))
            return paramString; 
          paramString = a(block, paramString, paramBufferedReader, paramList);
        } 
      } 
    } 
    return paramString;
  }
  
  private static String a(StringBuffer paramStringBuffer, BufferedReader paramBufferedReader) {
    String str = null;
    while ((str = paramBufferedReader.readLine()) != null) {
      if (a(str) || str.trim().equals(""))
        return str; 
      paramStringBuffer = paramStringBuffer.append("  \n").append(str);
    } 
    return null;
  }
  
  private static BlockType d(String paramString) {
    if (paramString == null)
      return null; 
    if (e(paramString))
      return BlockType.e; 
    if (f(paramString))
      return BlockType.f; 
    if (b(paramString))
      return BlockType.d; 
    if (g(paramString))
      return BlockType.g; 
    return null;
  }
  
  private static int a(String paramString, BlockType paramBlockType) {
    if (paramBlockType == BlockType.e || paramBlockType == BlockType.f)
      return paramString.indexOf(" "); 
    if (paramBlockType == BlockType.d)
      return paramString.indexOf(">"); 
    if (paramBlockType == BlockType.g) {
      int i = paramString.indexOf("[ ]");
      if (i == -1)
        i = paramString.indexOf("[x]"); 
      if (i != -1)
        return i + 3; 
    } 
    return -1;
  }
  
  public static boolean a(String paramString) {
    return (e(paramString) || f(paramString) || b(paramString) || g(paramString));
  }
  
  private static boolean e(String paramString) {
    return Pattern.matches("^[\\d]+\\. [\\d\\D][^\n]*$", paramString.trim());
  }
  
  private static boolean f(String paramString) {
    return (paramString.trim().startsWith("* ") || paramString
      .trim().startsWith("- ") || paramString
      .trim().startsWith("+ "));
  }
  
  public static boolean b(String paramString) {
    return paramString.trim().startsWith(">");
  }
  
  private static boolean g(String paramString) {
    return (paramString.trim().startsWith("[ ]") || paramString.trim().startsWith("[x]"));
  }
  
  public boolean b() {
    return false;
  }
}
