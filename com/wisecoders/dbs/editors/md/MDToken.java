package com.wisecoders.dbs.editors.md;

import com.wisecoders.dbs.editors.md.model.BlockType;
import java.util.Map;

public class MDToken {
  public static final String a = ">";
  
  public static final String b = "```";
  
  public static final String c = "    ";
  
  public static final String d = "#";
  
  public static final String e = "![";
  
  public static final String f = "[";
  
  public static final String g = "* ";
  
  public static final String h = "- ";
  
  public static final String i = "+ ";
  
  public static final String j = "[ ]";
  
  public static final String k = "[x]";
  
  public static final String l = "**";
  
  public static final String m = "__";
  
  public static final String n = "_";
  
  public static final String o = "*";
  
  public static final String p = "~~";
  
  public static final String q = "`";
  
  public static final String r = "  ";
  
  public static final String s = "|";
  
  public static final Map t = new MDToken$1();
  
  public static BlockType a(String paramString) {
    if (paramString.equals(">"))
      return BlockType.d; 
    if (paramString.equals("```"))
      return BlockType.a; 
    if (paramString.equals("#"))
      return BlockType.h; 
    if (paramString.equals("!["))
      return BlockType.k; 
    if (paramString.equals("**") || paramString.equals("__"))
      return BlockType.l; 
    if (paramString.equals("_") || paramString.equals("*"))
      return BlockType.m; 
    if (paramString.equals("~~"))
      return BlockType.n; 
    if (paramString.equals("`"))
      return BlockType.o; 
    if (paramString.equals("["))
      return BlockType.p; 
    if (paramString.equals("  "))
      return BlockType.i; 
    return BlockType.j;
  }
}
