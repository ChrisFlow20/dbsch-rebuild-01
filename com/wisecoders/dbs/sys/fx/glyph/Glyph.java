package com.wisecoders.dbs.sys.fx.glyph;

import com.wisecoders.dbs.sys.Log;
import javafx.scene.control.Label;

public interface Glyph {
  public static final String a = " ";
  
  Label glyph(String... paramVarArgs);
  
  String getId();
  
  static Glyph a(String paramString) {
    try {
      if (paramString.startsWith("bi-"))
        return BootstrapIcons.valueOf(paramString.substring("bi-".length())); 
      if (paramString.startsWith("letter-"))
        return new LetterIcons(paramString.substring("letter-".length()).charAt(0)); 
    } catch (IllegalArgumentException illegalArgumentException) {
      Log.a("Glyph not found: " + paramString, illegalArgumentException);
    } 
    return BootstrapIcons.exclamation_triangle_fill;
  }
  
  String getHtmlTag();
}
