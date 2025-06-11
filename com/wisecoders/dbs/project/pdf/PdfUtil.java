package com.wisecoders.dbs.project.pdf;

import com.wisecoders.dbs.diagram.model.TreeUnit;
import java.text.StringCharacterIterator;

public class PdfUtil {
  public static boolean a(TreeUnit paramTreeUnit) {
    if (a(paramTreeUnit.getName()))
      return true; 
    if (a(paramTreeUnit.getComment()))
      return true; 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b);
      if (a(treeUnit))
        return true; 
    } 
    return false;
  }
  
  private static boolean a(String paramString) {
    if (paramString != null) {
      StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
      char c = stringCharacterIterator.current();
      while (c != Character.MAX_VALUE) {
        if (c != '\r' && Character.UnicodeBlock.of(c) != Character.UnicodeBlock.BASIC_LATIN)
          return true; 
        c = stringCharacterIterator.next();
      } 
    } 
    return false;
  }
}
