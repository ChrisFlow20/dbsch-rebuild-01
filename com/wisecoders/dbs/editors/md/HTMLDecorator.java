package com.wisecoders.dbs.editors.md;

import com.wisecoders.dbs.editors.md.model.Block;
import com.wisecoders.dbs.editors.md.model.BlockType;
import com.wisecoders.dbs.editors.md.model.TableCellAlign;
import com.wisecoders.dbs.editors.md.model.ValuePart;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class HTMLDecorator implements Decorator {
  private StringBuilder a = new StringBuilder();
  
  public void a(String paramString) {}
  
  public void a(List paramList) {
    for (Block block : paramList) {
      try {
        String str;
        switch (HTMLDecorator$2.a[block.b().ordinal()]) {
          case 1:
            str = a(block.c());
            break;
          case 2:
            str = a(block.c(), block.d());
            break;
          case 3:
            str = d(block.f());
            break;
          case 4:
            str = c(block.e());
            break;
          case 5:
            str = b(block.f());
            break;
          default:
            str = a(block.c(), true);
            break;
        } 
        this.a.append(str).append("\n");
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  public void b(String paramString) {
    File file = Paths.get(paramString, new String[0]).toFile();
    try {
      FileWriter fileWriter = new FileWriter(file);
      try {
        fileWriter.write(this.a.toString());
        fileWriter.close();
      } catch (Throwable throwable) {
        try {
          fileWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public String a() {
    return this.a.toString();
  }
  
  private String a(ValuePart[] paramArrayOfValuePart) {
    String str = paramArrayOfValuePart[0].a();
    StringBuilder stringBuilder = new StringBuilder("<pre>");
    stringBuilder.append("<code>");
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    if (str.endsWith("\n"))
      str = str.substring(0, str.length() - "\n".length()); 
    stringBuilder.append(str);
    stringBuilder.append("</code>");
    stringBuilder.append("</pre>\n");
    return stringBuilder.toString();
  }
  
  private String a(ValuePart[] paramArrayOfValuePart, int paramInt) {
    return a(paramArrayOfValuePart, "h" + paramInt);
  }
  
  private String b(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Block block : paramList)
      stringBuilder.append(a(block.b(), block.f())); 
    return stringBuilder.toString();
  }
  
  private String a(BlockType paramBlockType, List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    switch (HTMLDecorator$2.a[paramBlockType.ordinal()]) {
      case 6:
        stringBuilder.append(f(paramList)).append("\n");
        break;
      case 7:
        stringBuilder.append(e(paramList)).append("\n");
        break;
      case 3:
        stringBuilder.append(d(paramList)).append("\n");
        break;
      case 8:
        stringBuilder.append(g(paramList)).append("\n");
        break;
    } 
    return stringBuilder.toString();
  }
  
  private String a(BlockType paramBlockType, String paramString, ValuePart paramValuePart) {
    int i;
    switch (HTMLDecorator$2.a[paramBlockType.ordinal()]) {
      case 9:
        return "<strong>" + paramString + "</strong>";
      case 10:
        return "<em>" + paramString + "</em>";
      case 11:
        return "<del>" + paramString + "</del>";
      case 12:
        paramString = paramString.replaceAll("<", "&lt;");
        paramString = paramString.replaceAll(">", "&gt;");
        return "<code>" + paramString + "</code>";
      case 2:
        i = paramValuePart.c() + 1;
        return "<h" + i + ">" + paramString + "</h" + i + ">";
      case 13:
        return "<a href=\"" + paramValuePart.d() + "\" title=\"" + Tools.b(paramString) + "\">" + paramString + "</a>";
      case 14:
        return "<img src=\"" + paramValuePart.d() + "\" title=\"" + paramValuePart.e() + "\" alt=\"" + paramValuePart
          .e() + "\" />";
      case 15:
        return "<br/>";
    } 
    return paramString;
  }
  
  private String c(List<List> paramList) {
    int i = paramList.size();
    int j = 0;
    for (List list : paramList) {
      int k = list.size();
      if (j < k)
        j = k; 
    } 
    StringBuilder stringBuilder = new StringBuilder("<table>\n");
    for (byte b = 0; b < i; b++) {
      stringBuilder.append("<tr>\n");
      List<Block> list = paramList.get(b);
      for (byte b1 = 0; b1 < j; b1++) {
        Block block = list.get(b1);
        boolean bool = (b == 0) ? true : false;
        stringBuilder.append(a(bool, block.h()));
        try {
          stringBuilder.append(a(((Block)list.get(b1)).c(), false));
        } catch (Exception exception) {
          stringBuilder.append("");
        } 
        stringBuilder.append("</" + (bool ? "th" : "td") + ">\n");
      } 
      stringBuilder.append("</tr>\n");
    } 
    stringBuilder.append("</table>\n");
    return stringBuilder.toString();
  }
  
  private String a(boolean paramBoolean, TableCellAlign paramTableCellAlign) {
    String str = "";
    if (paramTableCellAlign != TableCellAlign.d)
      str = "align=\"" + paramTableCellAlign.a() + "\""; 
    return "<" + (paramBoolean ? "th" : "td") + " " + str + ">";
  }
  
  private String a(List paramList, HTMLDecorator$LineHelper paramHTMLDecorator$LineHelper) {
    StringBuilder stringBuilder = new StringBuilder(paramHTMLDecorator$LineHelper.b() + "\n");
    for (Block block1 : paramList) {
      String str;
      Block block2 = block1.g();
      if (block2.b() == BlockType.h) {
        str = a(block2.c(), block2.d());
      } else {
        str = a(block2.c(), paramHTMLDecorator$LineHelper.a());
      } 
      stringBuilder.append(paramHTMLDecorator$LineHelper.a(block1));
      stringBuilder.append(str).append("\n").append(paramHTMLDecorator$LineHelper.b(block1));
      stringBuilder.append(paramHTMLDecorator$LineHelper.d());
    } 
    stringBuilder.append(paramHTMLDecorator$LineHelper.c() + "\n");
    return stringBuilder.toString();
  }
  
  private String d(List paramList) {
    return a(paramList, new HTMLDecorator$DefaultLineHelper(this, "blockquote"));
  }
  
  private String e(List paramList) {
    return a(paramList, new HTMLDecorator$DefaultLineHelper(this, "ul", "li"));
  }
  
  private String f(List paramList) {
    return a(paramList, new HTMLDecorator$DefaultLineHelper(this, "ol", "li"));
  }
  
  private String g(List paramList) {
    return a(paramList, new HTMLDecorator$1(this, "ul", "li"));
  }
  
  private String a(ValuePart[] paramArrayOfValuePart, boolean paramBoolean) {
    return a(paramArrayOfValuePart, paramBoolean ? "p" : null);
  }
  
  private String a(ValuePart[] paramArrayOfValuePart, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramString != null && !paramString.trim().equals(""))
      stringBuilder.append("<" + paramString + ">"); 
    for (ValuePart valuePart : paramArrayOfValuePart) {
      BlockType[] arrayOfBlockType = valuePart.b();
      String str = valuePart.a();
      if (a(arrayOfBlockType))
        str = valuePart.e(); 
      if (arrayOfBlockType != null)
        for (BlockType blockType : arrayOfBlockType)
          str = a(blockType, str, valuePart);  
      stringBuilder.append(str);
    } 
    if (paramString != null && !paramString.trim().equals(""))
      stringBuilder.append("</" + paramString + ">"); 
    return stringBuilder.toString();
  }
  
  private boolean a(BlockType[] paramArrayOfBlockType) {
    if (paramArrayOfBlockType == null)
      return false; 
    for (BlockType blockType : paramArrayOfBlockType) {
      if (blockType == BlockType.p)
        return true; 
    } 
    return false;
  }
  
  public static void main(String[] paramArrayOfString) {
    String str = "1. aa\n2. bb1.2\n    * cc2.1\n    * dd2.2\n    * ee2.3\n    * ff2.4\n";
    System.out.println(MDTool.a(str));
  }
}
